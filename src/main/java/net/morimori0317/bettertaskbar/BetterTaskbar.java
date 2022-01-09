package net.morimori0317.bettertaskbar;

import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

@Mod(BetterTaskbar.MODID)
public class BetterTaskbar {
    public static final String MODID = "bettertaskbar";

    public BetterTaskbar() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, isServer) -> true));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            BetterTaskbarAPI btm = BetterTaskbarAPI.getInstance();
            if (btm.isSupport())
                btm.setState(BetterTaskbarAPI.State.WAIT);
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);

        BetterTaskbarAPI.getInstance().registerProgressScreen(n -> {
            if (n instanceof LevelLoadingScreen loadingScreen)
                return new BetterTaskbarAPI.ProgressTotal(Math.max(loadingScreen.progressListener.getProgress(), 1), 100, null);

            if (n instanceof ProgressScreen || n instanceof ConnectScreen)
                return new BetterTaskbarAPI.ProgressTotal(-1, -1, BetterTaskbarAPI.State.WAIT);

            if (n instanceof ConfirmScreen)
                return new BetterTaskbarAPI.ProgressTotal(-1, -1, BetterTaskbarAPI.State.PAUSE);

            return null;
        });
    }
}