package dev.leg1tka.build;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

/** Stages supplied binaries without loading or executing native code. */
public final class PrepareNativeClient {
    public static void main(String[] args) throws IOException {
        Path original = Path.of(args[0]).toRealPath();
        Path destination = Path.of(args[1]).toAbsolutePath().normalize();
        Path artifact = Path.of(args[2]).toRealPath();
        Files.createDirectories(destination);
        destination = destination.toRealPath();
        if (destination.startsWith(original) || original.startsWith(destination))
            throw new IOException("Native staging must be separate from the original client");
        try (var existing = Files.walk(destination)) {
            for (Path path : existing.toList()) {
                if (Files.isSymbolicLink(path) || !path.toRealPath().startsWith(destination))
                    throw new IOException("Unexpected redirected staging path: " + path);
            }
        }
        for (String required : List.of("launcher/vm-host.exe", "runtime/bin/server/runtime.dll",
                "natives/protection.dll", "lib/game.jar", "game/assets/indexes/19.json")) {
            if (!Files.isRegularFile(original.resolve(required))) throw new IOException("Missing supplied file: " + required);
        }
        try (JarFile jar = new JarFile(artifact.toFile())) {
            if (jar.getEntry("dev/leg1tka/o/gi.class") == null)
                throw new IOException("Expected the original-ABI artifact, not the source-preview jar");
        }
        for (String directory : List.of("runtime", "lib", "natives", "game/assets"))
            copyTree(original.resolve(directory), destination.resolve(directory));
        copy(original.resolve("launcher/vm-host.exe"), destination.resolve("launcher/vm-host.exe"));
        copy(artifact, destination.resolve("game/mods/aspect.jar"));
        try (var mods = Files.list(original.resolve("game/mods"))) {
            List<Path> api = mods.filter(path -> path.getFileName().toString().startsWith("fabric-api-")
                    && path.toString().endsWith(".jar")).toList();
            if (api.size() != 1) throw new IOException("Expected one supplied Fabric API jar");
            copy(api.getFirst(), destination.resolve("game/mods/" + api.getFirst().getFileName()));
        }
        List<String> options = new ArrayList<>(Files.readAllLines(original.resolve("launcher/vm-options.txt"), StandardCharsets.UTF_8));
        String classpath;
        try (var libraries = Files.list(destination.resolve("lib"))) {
            classpath = String.join(";", libraries.filter(path -> path.toString().endsWith(".jar"))
                    .map(path -> "lib/" + path.getFileName()).sorted().toList());
        }
        boolean replaced = false;
        for (int index = 0; index < options.size(); index++) {
            if (options.get(index).startsWith("-Djava.class.path=")) {
                options.set(index, "-Djava.class.path=" + classpath);
                replaced = true;
            }
        }
        if (!replaced) throw new IOException("Original classpath option is missing");
        List<String> game = new ArrayList<>(Files.readAllLines(original.resolve("launcher/game-args.txt"), StandardCharsets.UTF_8));
        int username = game.indexOf("--username");
        if (username < 0 || username + 1 >= game.size()) throw new IOException("Original username argument is missing");
        game.set(username + 1, "tmereadysrs");
        Files.write(destination.resolve("launcher/vm-options.txt"), options, StandardCharsets.UTF_8);
        Files.write(destination.resolve("launcher/game-args.txt"), game, StandardCharsets.UTF_8);
        System.out.println("Native client staged (not executed): " + destination);
    }

    private static void copyTree(Path source, Path target) throws IOException {
        try (var files = Files.walk(source)) {
            for (Path path : files.toList()) {
                if (Files.isSymbolicLink(path)) throw new IOException("Unexpected symbolic link: " + path);
                Path output = target.resolve(source.relativize(path));
                if (Files.isDirectory(path)) Files.createDirectories(output);
                else copy(path, output);
            }
        }
    }

    private static void copy(Path source, Path target) throws IOException {
        Files.createDirectories(target.getParent());
        if (Files.isSymbolicLink(target)) throw new IOException("Unexpected staging link: " + target);
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
