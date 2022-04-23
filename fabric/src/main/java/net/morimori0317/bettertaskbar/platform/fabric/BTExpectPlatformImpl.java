package net.morimori0317.bettertaskbar.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class BTExpectPlatformImpl {
    public static boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }
}
