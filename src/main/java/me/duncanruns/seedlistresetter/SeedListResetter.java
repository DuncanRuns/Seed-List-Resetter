package me.duncanruns.seedlistresetter;

import me.duncanruns.seedlistresetter.mixin.CreateWorldScreenAccess;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SeedListResetter implements ModInitializer {

    public static final String MOD_ID = "seedlistresetter";
    public static final String MOD_NAME = "Seed List Resetter";
    public static Logger LOGGER = LogManager.getLogger();
    public static boolean isPlaying = false;
    public static boolean loopPrevent = false;

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static boolean nextSeed(Screen screen) {
        try {
            String seed = grabSeed();
            if (seed != null) {
                isPlaying = true;
                createWorld(seed, screen);
                System.out.println("Reached");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void createWorld(String seed, Screen screen) {
        MinecraftClient client = MinecraftClient.getInstance();
        CreateWorldScreen createWorldScreen = new CreateWorldScreen(screen);
        createWorldScreen.init(client, screen.width, screen.height);
        CreateWorldScreenAccess createWorldScreenAccess = (CreateWorldScreenAccess) createWorldScreen;

        createWorldScreenAccess.getLevelNameField().setText("Seed-" + Long.toHexString(Long.parseLong(seed)));
        createWorldScreenAccess.getSeedField().setText(seed);

        createWorldScreenAccess.invokeCreateLevel();
    }

    private static String grabSeed() throws IOException {
        File seedFile = new File("seeds.txt");
        if (!seedFile.exists()) {
            return null;
        }
        String seedFileString = FileUtils.readFileToString(seedFile, "utf-8");
        if (seedFileString.replaceAll("[^\\d.-]", "").equals("")) {
            return null;
        }
        int newLineInd = seedFileString.indexOf("\n");
        String seed;
        if (newLineInd == -1) {
            seed = seedFileString.replaceAll("[^\\d.-]", "");
            FileWriter writer = new FileWriter(seedFile);
            writer.close();
        } else {
            seed = seedFileString.substring(0, newLineInd).replaceAll("[^\\d.-]", "").replace("\n\n", "\n");
            FileWriter writer = new FileWriter(seedFile);
            writer.write(seedFileString.substring(newLineInd + 1));
            writer.close();
        }
        return seed;
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
    }

}