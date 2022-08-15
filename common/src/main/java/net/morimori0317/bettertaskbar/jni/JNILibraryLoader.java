package net.morimori0317.bettertaskbar.jni;

import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class JNILibraryLoader {
    private static final Logger logger = LogManager.getLogger(JNILibraryLoader.class);
    private static boolean inited;
    private static boolean loaded;
    private static Path libFolderPath;

    public static synchronized boolean init() {
        if (inited)
            return false;
        inited = true;

        libFolderPath = BetterTaskbarAPI.getInstance().getLibraryFolderPath();

        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String arc = System.getProperty("os.arch").toLowerCase(Locale.ROOT);

        if (os.contains("windows") && arc.contains("amd64")) {
            try {
                loaded = loadExtractLibrary("dll", 0);
                if (loaded)
                    logger.info("Loaded the Better taskbar library");
                else
                    logger.error("Failed to load library of Better taskbar");
            } catch (Exception ex) {
                logger.error("Failed to load library of Better taskbar", ex);
            }
        } else {
            logger.error("Better taskbar does not support this os");
        }

        return false;
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static boolean isInited() {
        return inited;
    }

    private static boolean loadExtractLibrary(String extend, int libVersion) throws IOException {
        String name = "BTLib" + (libVersion == 0 ? "" : libVersion) + "." + extend;
        File fil = libFolderPath.resolve(name).toFile();
        if (fil.exists()) {
            try {
                System.load(fil.getAbsolutePath());
                return true;
            } catch (Throwable ex) {
                logger.error("Failed to load the existing library. Extract again", ex);
                Files.delete(fil.toPath());
                extractLibrary(extend, libVersion);
                System.load(fil.getAbsolutePath());
                return true;
            }
        } else {
            extractLibrary(extend, libVersion);
            System.load(fil.getAbsolutePath());
            return true;
        }
    }

    private static void extractLibrary(String extend, int libVersion) throws IOException {
        String pp = "/assets/bettertaskbar/natives/BTLib." + extend;
        InputStream stream = JNILibraryLoader.class.getResourceAsStream(pp);

        if (stream == null)
            stream = ClassLoader.getSystemResourceAsStream(pp);

        if (stream == null)
            throw new IOException("Library does not exist");

        String name = "BTLib" + (libVersion <= 0 ? "" : libVersion) + "." + extend;
        Path path = libFolderPath.resolve(name);
        Files.write(path, stream.readAllBytes());
    }
}
