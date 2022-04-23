package net.morimori0317.bettertaskbar.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class BTExpectPlatform {
    @ExpectPlatform
    public static boolean isModLoaded(String name) {
        throw new AssertionError();
    }
}
