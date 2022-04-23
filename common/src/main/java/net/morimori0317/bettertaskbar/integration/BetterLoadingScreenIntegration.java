package net.morimori0317.bettertaskbar.integration;

import me.shedaniel.betterloadingscreen.api.Job;
import me.shedaniel.betterloadingscreen.api.step.LoadGameSteps;
import net.morimori0317.bettertaskbar.platform.BTExpectPlatform;

public class BetterLoadingScreenIntegration {

    public static boolean isIntegration() {
        return BTExpectPlatform.isModLoaded("better_loading_screen");
    }

    public static double getLoadingProgress() {
        Job job = LoadGameSteps.loadGame();
        return job.getProgress();
    }

}
