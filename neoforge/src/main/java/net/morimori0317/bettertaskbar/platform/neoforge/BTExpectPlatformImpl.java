package net.morimori0317.bettertaskbar.platform.neoforge;


import net.neoforged.fml.ModList;

public class BTExpectPlatformImpl {

    public static boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }
}
