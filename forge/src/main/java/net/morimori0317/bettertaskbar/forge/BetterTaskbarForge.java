package net.morimori0317.bettertaskbar.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.morimori0317.bettertaskbar.BetterTaskbar;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

@Mod(BetterTaskbar.MODID)
public class BetterTaskbarForge {
    private static final DefaultArtifactVersion SKIP_DISPLAY_TEST_FORGE_VERSION = new DefaultArtifactVersion("49.0.3");

    public BetterTaskbarForge() {
        IModFileInfo forgeMod = ModList.get().getModFileById("forge");
        DefaultArtifactVersion forgeVersion = new DefaultArtifactVersion(forgeMod.versionString());

        if (forgeVersion.compareTo(SKIP_DISPLAY_TEST_FORGE_VERSION) < 0) {
            ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (remote, isServer) -> true));
        }

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(RenderHandlerForge.class);
    }
}
