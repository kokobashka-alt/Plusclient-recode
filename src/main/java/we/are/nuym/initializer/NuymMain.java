/*
 * Decompiled with CFR 0.152.
 */
package we.are.nuym.initializer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class NuymMain {
    private static boolean initialized;
    private static final HttpClient HTTP_CLIENT;

    public static Class<?> lazyFindClass(String name) {
        name = name.replace("/", ".");
        Class<?> klass = null;
        try {
            klass = Class.forName(name, false, NuymMain.class.getClassLoader());
        }
        catch (ClassNotFoundException e2) {
            System.out.println(name);
            throw new RuntimeException(e2);
        }
        return klass;
    }

    public static native void initialize();

    public static native String request();

    public static byte[] fillRequestBuffer(byte[] data) throws Exception {
        String serverUrl = System.getProperty("nuym.references.url", "http://51.68.133.51:3648/api/handshake/batch");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(serverUrl)).header("Content-Type", "application/octet-stream").timeout(Duration.ofSeconds(30L)).POST(HttpRequest.BodyPublishers.ofByteArray(data)).build();
        HttpResponse<byte[]> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());
        byte[] byteBuf = response.body();
        if (response.statusCode() != 200) {
            System.out.println("Error: " + response.statusCode());
            throw new RuntimeException("buffer not received");
        }
        System.out.println("references batch response: " + response.body().length + " bytes");
        return byteBuf;
    }

    public static void init() throws Throwable {
        if (initialized) {
            return;
        }
        initialized = true;
        System.loadLibrary("protection");
        NuymMain.initialize();
        System.out.println("[!] protection initialized");
    }

    static {
        HTTP_CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
    }

    public static native void dev_leg1tka_o_gk_initializer();

    public static native void dev_leg1tka_o_gl_initializer();

    public static native void dev_leg1tka_o_gm_initializer();

    public static native void dev_leg1tka_o_gn_initializer();

    public static native void dev_leg1tka_o_go_initializer();

    public static native void dev_leg1tka_o_gp_initializer();

    public static native void dev_leg1tka_o_gq_initializer();

    public static native void dev_leg1tka_o_gr_initializer();

    public static native void dev_leg1tka_o_gt_initializer();

    public static native void dev_leg1tka_o_gu_initializer();

    public static native void dev_leg1tka_o_gw_initializer();

    public static native void dev_leg1tka_o_gx_initializer();

    public static native void dev_leg1tka_o_gy_initializer();

    public static native void dev_leg1tka_o_gz_initializer();

    public static native void dev_leg1tka_o_ha_initializer();

    public static native void dev_leg1tka_o_hc_initializer();

    public static native void dev_leg1tka_o_hp_initializer();

    public static native void dev_leg1tka_o_ia_initializer();

    public static native void dev_leg1tka_o_ie_initializer();

    public static native void dev_leg1tka_o_ig_initializer();

    public static native void dev_leg1tka_o_ii_initializer();

    public static native void dev_leg1tka_o_ij_initializer();

    public static native void dev_leg1tka_o_il_initializer();

    public static native void dev_leg1tka_o_im_initializer();

    public static native void dev_leg1tka_o_io_initializer();

    public static native void dev_leg1tka_o_ip_initializer();

    public static native void dev_leg1tka_o_jr_initializer();
}

