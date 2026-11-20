package dev.leg1tka.recovery;

/** Never invokes JNI; verifies classification before protected components can be activated. */
public final class NativeComponentsTest {
    static class JavaComponent { public void onTick() {} }
    static class NativeComponent { public native void onTick(); }
    static class InheritedNativeComponent extends NativeComponent {}
    static class NativeInitializerOnly { public static native void original_initializer(); }

    public static void main(String[] args) throws Exception {
        require(NativeComponents.claimUnavailableNotice(NativeComponent.class), "First activation must explain the missing backend");
        require(!NativeComponents.claimUnavailableNotice(NativeComponent.class), "Repeated activation must not spam chat");
        require(NativeComponents.claimUnavailableNotice(InheritedNativeComponent.class), "Each unavailable component needs its own notice");
        require(!dev.leg1tka.settings.ColorSetting.class.isAssignableFrom(dev.leg1tka.settings.KeybindSetting.class), "Keybind and color settings must retain separate identities");
        require(new dev.leg1tka.settings.KeybindSetting("binding", -98).getKey() == -98, "Keybind defaults must survive reconstruction");
        require(NativeComponents.originalName(dev.leg1tka.settings.KeybindSetting.class).equals("dev.leg1tka.o.mm"), "Keybind ABI identity must be preserved");
        require(NativeComponents.originalName(dev.leg1tka.settings.ColorSetting.class).equals("dev.leg1tka.o.mo"), "Color ABI identity must be preserved");
        require(NativeComponents.canDispatch(JavaComponent.class.getDeclaredMethod("onTick")), "Java listeners must remain registered");
        require(!NativeComponents.canDispatch(NativeComponent.class.getDeclaredMethod("onTick")), "Unresolved native callbacks must not reach reflection dispatch");
        require(!NativeComponents.requiresOriginalRuntime(JavaComponent.class), "Java components must stay available");
        require(NativeComponents.requiresOriginalRuntime(NativeComponent.class), "Native callbacks must be detected");
        require(NativeComponents.requiresOriginalRuntime(InheritedNativeComponent.class), "Inherited native callbacks must be detected");
        require(!NativeComponents.requiresOriginalRuntime(NativeInitializerOnly.class), "An ABI initializer is not an event callback");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
