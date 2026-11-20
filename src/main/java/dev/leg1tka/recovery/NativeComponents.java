package dev.leg1tka.recovery;

import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/** Describes unavailable protected components; it does not emulate their algorithms. */
public final class NativeComponents {
    private static final ConcurrentHashMap<Class<?>, Boolean> REQUIRED = new ConcurrentHashMap<>();
    private static final Set<String> UNAVAILABLE_HANDLERS = ConcurrentHashMap.newKeySet();
    private static final Set<Class<?>> UNAVAILABLE_NOTICES = ConcurrentHashMap.newKeySet();
    private static final Properties ORIGINAL_NAMES = loadOriginalNames();
    private NativeComponents() {}

    private static Properties loadOriginalNames() {
        Properties result = new Properties();
        try (var stream = NativeComponents.class.getResourceAsStream("/META-INF/aspect-original-names.properties")) {
            if (stream == null) throw new IllegalStateException("Original symbol registry is missing");
            result.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
            return result;
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Cannot read the original symbol registry", exception);
        }
    }

    /** Preserve the original tie-break order of equally-prioritized event listeners after renaming. */
    public static String originalName(Class<?> type) {
        return ORIGINAL_NAMES.getProperty(type.getName(), type.getName());
    }

    public static boolean canDispatch(Method method) {
        if (usesOriginalAbi() || !Modifier.isNative(method.getModifiers())) return true;
        String key = method.getDeclaringClass().getName() + "#" + method.getName();
        if (UNAVAILABLE_HANDLERS.add(key)) System.err.println("[Native unavailable] " + key + ": original protected runtime required; callback is not registered in source-preview mode.");
        return false;
    }

    public static boolean requiresOriginalRuntime(Class<?> component) {
        return REQUIRED.computeIfAbsent(component, type -> {
            for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
                for (var method : current.getDeclaredMethods()) {
                    if (Modifier.isNative(method.getModifiers()) && !method.getName().endsWith("_initializer")) return true;
                }
            }
            return false;
        });
    }

    /** Rewritten only in the separate original-ABI artifact, whose original native initializers are restored. */
    public static boolean usesOriginalAbi() { return false; }

    static boolean claimUnavailableNotice(Class<?> component) {
        return UNAVAILABLE_NOTICES.add(component);
    }

    public static boolean canActivate(Object component) {
        return true;
    }
}
