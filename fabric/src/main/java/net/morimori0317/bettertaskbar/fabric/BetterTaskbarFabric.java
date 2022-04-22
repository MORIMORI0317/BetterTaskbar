package net.morimori0317.bettertaskbar.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.morimori0317.bettertaskbar.BetterTaskbar;

public class BetterTaskbarFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterTaskbar.init();
    }
}
