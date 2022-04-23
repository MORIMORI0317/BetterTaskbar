package net.morimori0317.bettertaskbar.platform.forge;

import net.minecraftforge.fml.ModList;

public class BTExpectPlatformImpl {

    public static boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }
}
