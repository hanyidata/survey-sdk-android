package cn.xmplus.sdk;

import java.util.Optional;

/**
 * Global Config
 */
public class HYConfig {
    private static String server;
    private static Float density;

    public static void setDensity(Float density) {
        HYConfig.density = density;
    }

    public static void setServer(String server) {
        HYConfig.server = server;
    }

    public static Float getDensity() {
        return density;
    }

    public static String getServer() {
        return server;
    }
}
