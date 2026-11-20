package dev.leg1tka.build;

import com.google.gson.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import org.objectweb.asm.util.CheckClassAdapter;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.regex.*;

/** Reconstitutes the original binary names and native startup calls, without executing JNI. */
public final class NativeAbiAssembler {
    private static String resolve(Map<String,String> mappings, Map<String,List<String>> parents,
                                  String owner, String suffix, Set<String> visited) {
        if (!visited.add(owner)) return null;
        String value=mappings.get(owner+"#"+suffix);
        if (value!=null) return value;
        for(String parent:parents.getOrDefault(owner,List.of())) {
            value=resolve(mappings,parents,parent,suffix,visited);
            if(value!=null) return value;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        JsonObject metadata = JsonParser.parseString(Files.readString(Path.of(args[2]))).getAsJsonObject();
        JsonObject originals = metadata.getAsJsonObject("classes");
        Map<String,String> classNames = new HashMap<>();
        for(var entry:originals.entrySet()) classNames.put(entry.getValue().getAsJsonObject().get("recovered").getAsString(),entry.getKey());
        Map<String,String> fields=new HashMap<>(), methods=new HashMap<>();
        for(JsonElement value:metadata.getAsJsonArray("members")) {
            JsonObject member=value.getAsJsonObject();
            String key=member.get("owner").getAsString()+"#"+member.get("new").getAsString();
            if(member.get("kind").getAsString().equals("field")) fields.put(key,member.get("old").getAsString());
            else methods.put(key+member.get("parameters").getAsString(),member.get("old").getAsString());
        }
        Remapper classOnly=new Remapper() {
            @Override public String map(String name) { return classNames.getOrDefault(name,name); }
        };
        Map<String,byte[]> entries=new LinkedHashMap<>();
        try(JarFile jar=new JarFile(args[0])) {
            var names=jar.entries();
            while(names.hasMoreElements()) {
                JarEntry entry=names.nextElement();
                if(!entry.isDirectory()) entries.put(entry.getName(),jar.getInputStream(entry).readAllBytes());
            }
        }
        // Reconcile decompiler factory names by original descriptor and unique proxy ordinal.
        for(var entry:entries.entrySet()) {
            if(!entry.getKey().endsWith(".class")) continue;
            ClassNode node=new ClassNode();new ClassReader(entry.getValue()).accept(node,0);
            String original=classNames.getOrDefault(node.name,node.name);
            JsonObject info=originals.has(original)?originals.getAsJsonObject(original):null;
            if(info==null) continue;
            for(JsonElement item:info.getAsJsonArray("methods")) {
                JsonObject method=item.getAsJsonObject();String old=method.get("name").getAsString();
                Matcher proxy=Pattern.compile("\\$hello\\d+_proxy(\\d+)").matcher(old);
                if(!proxy.matches()) continue;
                List<MethodNode> candidates=node.methods.stream().filter(m->m.name.matches("create.*(?<![0-9])"+proxy.group(1))
                    && classOnly.mapMethodDesc(m.desc).equals(method.get("descriptor").getAsString())).toList();
                if(candidates.size()==1) {
                    MethodNode candidate=candidates.get(0);
                    methods.put(node.name+"#"+candidate.name+candidate.desc.substring(0,candidate.desc.indexOf(')')+1),old);
                }
            }
        }
        // Restore uniquely identifiable compiler-generated enum arrays and captured fields.
        // Enum switch tables are handled separately: their case numbers may differ between compilers.
        for(var entry:entries.entrySet()) if(entry.getKey().endsWith(".class")) {
            ClassNode source=new ClassNode();new ClassReader(entry.getValue()).accept(source,0);
            String originalName=classNames.getOrDefault(source.name,source.name);
            if(!originals.has(originalName)) continue;
            JsonObject original=originals.getAsJsonObject(originalName);
            for(JsonElement element:original.getAsJsonArray("fields")) {
                JsonObject field=element.getAsJsonObject();
                if((field.get("access").getAsInt()&Opcodes.ACC_SYNTHETIC)==0) continue;
                String name=field.get("name").getAsString(), descriptor=field.get("descriptor").getAsString();
                if(metadata.getAsJsonObject("switchTables").has(originalName)
                    && metadata.getAsJsonObject("switchTables").getAsJsonObject(originalName).has(name)) continue;
                List<FieldNode> candidates=source.fields.stream().filter(f->(f.access&Opcodes.ACC_SYNTHETIC)!=0 && classOnly.mapDesc(f.desc).equals(descriptor)).toList();
                if(candidates.size()==1) fields.put(source.name+"#"+candidates.get(0).name,name);
            }
            for(JsonElement element:original.getAsJsonArray("methods")) {
                JsonObject method=element.getAsJsonObject();
                if((method.get("access").getAsInt()&Opcodes.ACC_SYNTHETIC)==0) continue;
                String descriptor=method.get("descriptor").getAsString();
                long originalCount=original.getAsJsonArray("methods").asList().stream().map(JsonElement::getAsJsonObject)
                    .filter(m->(m.get("access").getAsInt()&Opcodes.ACC_SYNTHETIC)!=0 && m.get("descriptor").getAsString().equals(descriptor)).count();
                List<MethodNode> candidates=source.methods.stream().filter(m->(m.access&Opcodes.ACC_SYNTHETIC)!=0 && classOnly.mapMethodDesc(m.desc).equals(descriptor)).toList();
                if(originalCount==1 && candidates.size()==1) {
                    MethodNode candidate=candidates.get(0);
                    methods.putIfAbsent(source.name+"#"+candidate.name+candidate.desc.substring(0,candidate.desc.indexOf(')')+1),method.get("name").getAsString());
                }
            }
        }
        Map<String,List<String>> parents=new HashMap<>();
        for(var entry:entries.entrySet()) if(entry.getKey().endsWith(".class")) {
            ClassReader reader=new ClassReader(entry.getValue());
            List<String> ancestors=new ArrayList<>(List.of(reader.getInterfaces()));
            if(reader.getSuperName()!=null) ancestors.add(reader.getSuperName());
            parents.put(reader.getClassName(),ancestors);
        }
        Remapper remapper=new Remapper() {
            @Override public String map(String name) { return classNames.getOrDefault(name,name); }
            @Override public String mapFieldName(String owner,String name,String descriptor) { String result=resolve(fields,parents,owner,name,new HashSet<>()); return result==null?name:result; }
            @Override public String mapMethodName(String owner,String name,String descriptor) {
                String result=resolve(methods,parents,owner,name+descriptor.substring(0,descriptor.indexOf(')')+1),new HashSet<>()); return result==null?name:result;
            }
        };
        StringBuilder renames=new StringBuilder("tiny\t2\t0\trecovered-client-intermediary-mc\toriginal-client-intermediary-mc\n");
        for(var entry:entries.entrySet()) if(entry.getKey().endsWith(".class")) {
            ClassNode source=new ClassNode();new ClassReader(entry.getValue()).accept(source,ClassReader.SKIP_CODE);
            renames.append("c\t").append(source.name).append('\t').append(remapper.mapType(source.name)).append('\n');
            for(FieldNode field:source.fields) renames.append("\tf\t").append(field.desc).append('\t').append(field.name).append('\t').append(remapper.mapFieldName(source.name,field.name,field.desc)).append('\n');
            for(MethodNode method:source.methods) renames.append("\tm\t").append(method.desc).append('\t').append(method.name).append('\t').append(remapper.mapMethodName(source.name,method.name,method.desc)).append('\n');
        }
        if(args.length>3) Files.writeString(Path.of(args[3]),renames);
        Set<String> nativeSignatures=new HashSet<>();int restored=0;
        Path output=Path.of(args[1]);Files.createDirectories(output.getParent());
        try(JarOutputStream jar=new JarOutputStream(Files.newOutputStream(output))) {
            for(var entry:entries.entrySet()) {
                String name=entry.getKey();byte[] bytes=entry.getValue();
                if(name.endsWith(".class")) {
                    ClassNode node=new ClassNode();new ClassReader(bytes).accept(new ClassRemapper(node,remapper),0);
                    JsonObject original=originals.has(node.name)?originals.getAsJsonObject(node.name):null;
                    if(metadata.getAsJsonObject("recordLabels").has(node.name)) {
                        String expectedLabels=metadata.getAsJsonObject("recordLabels").get(node.name).getAsString();
                        boolean matched=false;
                        for(MethodNode method:node.methods) if(method.name.equals("toString")) {
                            for(AbstractInsnNode instruction:method.instructions) {
                                if(instruction instanceof InvokeDynamicInsnNode dynamic && dynamic.bsm.getOwner().equals("java/lang/runtime/ObjectMethods")) {
                                    matched=expectedLabels.equals(dynamic.bsmArgs[1]);
                                }
                            }
                        }
                        if(!node.superName.equals("java/lang/Record") || !matched) throw new IllegalStateException("Record semantics were not preserved: "+node.name);
                    }
                    if(original!=null) {
                        for(FieldNode field:node.fields) for(JsonElement element:original.getAsJsonArray("fields")) {
                            JsonObject prior=element.getAsJsonObject();
                            if(field.name.equals(prior.get("name").getAsString()) && field.desc.equals(prior.get("descriptor").getAsString())) field.access=prior.get("access").getAsInt();
                        }
                        if(original.get("protected_initializer").getAsBoolean()) {
                            node.methods.removeIf(m->m.name.equals("<clinit>"));
                            MethodNode initializer=new MethodNode(Opcodes.ACC_STATIC,"<clinit>","()V",null,null);
                            initializer.visitCode();
                            initializer.visitMethodInsn(Opcodes.INVOKESTATIC,"we/are/nuym/initializer/NuymMain","init","()V",false);
                            initializer.visitMethodInsn(Opcodes.INVOKESTATIC,node.name,node.name.replace('/','_')+"_initializer","()V",false);
                            initializer.visitInsn(Opcodes.RETURN);initializer.visitMaxs(0,0);initializer.visitEnd();node.methods.add(initializer);restored++;
                        }
                    }
                    if(metadata.getAsJsonObject("switchTables").has(node.name)) {
                        JsonObject tables=metadata.getAsJsonObject("switchTables").getAsJsonObject(node.name);
                        MethodNode initializer=node.methods.stream().filter(m->m.name.equals("<clinit>")).findFirst().orElse(null);
                        if(initializer==null) {
                            initializer=new MethodNode(Opcodes.ACC_STATIC,"<clinit>","()V",null,null);
                            initializer.instructions.add(new InsnNode(Opcodes.RETURN));node.methods.add(initializer);
                        }
                        MethodNode populate=new MethodNode(Opcodes.ACC_PRIVATE|Opcodes.ACC_STATIC|Opcodes.ACC_SYNTHETIC,"aspect$populateOriginalSwitches","()V",null,null);
                        populate.visitCode();InsnList allocations=new InsnList();
                        for(var tableEntry:tables.entrySet()) {
                            String fieldName=tableEntry.getKey();JsonObject table=tableEntry.getValue().getAsJsonObject();
                            String enumeration=table.get("enum").getAsString();
                            if(node.fields.stream().noneMatch(f->f.name.equals(fieldName))) {
                                int access=Opcodes.ACC_STATIC|Opcodes.ACC_FINAL|Opcodes.ACC_SYNTHETIC;
                                node.fields.add(new FieldNode(access,fieldName,"[I",null,null));
                            }
                            allocations.add(new MethodInsnNode(Opcodes.INVOKESTATIC,enumeration,table.get("valuesMethod").getAsString(),table.get("valuesDescriptor").getAsString(),false));
                            allocations.add(new InsnNode(Opcodes.ARRAYLENGTH));allocations.add(new IntInsnNode(Opcodes.NEWARRAY,Opcodes.T_INT));
                            allocations.add(new FieldInsnNode(Opcodes.PUTSTATIC,node.name,fieldName,"[I"));
                            for(var assignment:table.getAsJsonObject("cases").entrySet()) {
                                Label start=new Label(), end=new Label(), handler=new Label(), after=new Label();
                                populate.visitTryCatchBlock(start,end,handler,"java/lang/NoSuchFieldError");populate.visitLabel(start);
                                populate.visitFieldInsn(Opcodes.GETSTATIC,node.name,fieldName,"[I");
                                populate.visitFieldInsn(Opcodes.GETSTATIC,enumeration,assignment.getKey(),"L"+enumeration+";");
                                populate.visitMethodInsn(Opcodes.INVOKEVIRTUAL,enumeration,"ordinal","()I",false);
                                populate.visitLdcInsn(assignment.getValue().getAsInt());populate.visitInsn(Opcodes.IASTORE);
                                populate.visitLabel(end);populate.visitJumpInsn(Opcodes.GOTO,after);populate.visitLabel(handler);
                                populate.visitFrame(Opcodes.F_SAME1,0,null,1,new Object[]{"java/lang/NoSuchFieldError"});populate.visitInsn(Opcodes.POP);
                                populate.visitLabel(after);populate.visitFrame(Opcodes.F_SAME,0,null,0,null);
                            }
                        }
                        populate.visitInsn(Opcodes.RETURN);populate.visitMaxs(3,0);populate.visitEnd();node.methods.add(populate);
                        allocations.add(new MethodInsnNode(Opcodes.INVOKESTATIC,node.name,populate.name,"()V",false));
                        for(AbstractInsnNode instruction:initializer.instructions.toArray()) if(instruction.getOpcode()==Opcodes.RETURN) {
                            initializer.instructions.insertBefore(instruction,allocations);break;
                        }
                    }
                    if(node.name.equals("dev/leg1tka/recovery/NativeComponents")) for(MethodNode method:node.methods) {
                        if(method.name.equals("usesOriginalAbi") && method.desc.equals("()Z")) {
                            method.instructions.clear();method.tryCatchBlocks.clear();
                            if(method.localVariables!=null)method.localVariables.clear();
                            method.instructions.add(new InsnNode(Opcodes.ICONST_1));method.instructions.add(new InsnNode(Opcodes.IRETURN));
                        }
                    }
                    if(original!=null) {
                        for(MethodNode method:node.methods) for(JsonElement element:original.getAsJsonArray("methods")) {
                            JsonObject prior=element.getAsJsonObject();
                            if(method.name.equals(prior.get("name").getAsString()) && method.desc.equals(prior.get("descriptor").getAsString())) method.access=prior.get("access").getAsInt();
                        }
                        if(node.superName.equals("java/lang/Record") && !original.get("originalRecordAttribute").getAsBoolean()) {
                            node.recordComponents=null;
                            node.access &= ~Opcodes.ACC_RECORD;
                        }
                    }
                    for(MethodNode method:node.methods) if((method.access&Opcodes.ACC_NATIVE)!=0) nativeSignatures.add(node.name+"#"+method.name+method.desc);
                    ClassWriter writer=new ClassWriter(ClassWriter.COMPUTE_MAXS);node.accept(writer);bytes=writer.toByteArray();
                    ClassNode verified=new ClassNode();new ClassReader(bytes).accept(new CheckClassAdapter(verified),0);
                    for(MethodNode method:verified.methods) if((method.access&(Opcodes.ACC_NATIVE|Opcodes.ACC_ABSTRACT))==0) new Analyzer<>(new BasicVerifier()).analyze(verified.name,method);
                    name=node.name+".class";
                }
                jar.putNextEntry(new JarEntry(name));jar.write(bytes);jar.closeEntry();
            }
        }
        int expected=0;
        for(var entry:originals.entrySet()) for(JsonElement element:entry.getValue().getAsJsonObject().getAsJsonArray("methods")) {
            JsonObject method=element.getAsJsonObject();
            if((method.get("access").getAsInt()&Opcodes.ACC_NATIVE)!=0) {
                expected++;
                String signature=entry.getKey()+"#"+method.get("name").getAsString()+method.get("descriptor").getAsString();
                if(!nativeSignatures.contains(signature)) throw new IllegalStateException("Missing original native interface: "+signature);
            }
        }
        if(restored!=27) throw new IllegalStateException("Expected 27 original protected initializers, got "+restored);
        System.out.println("Preserved "+expected+" native declarations and restored "+restored+" original startup sequences. No native code was executed.");
    }
}
