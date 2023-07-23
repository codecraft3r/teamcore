package org.codecraft.teamcore.worlds;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

class WorldInfoHelper {
    private static final String FILE_PATH = "./worlds.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Save a JSON array of WorldInfo objects to a file
    public static void saveWorlds(List<WorldInfo> worlds) {
        String json = GSON.toJson(worlds);
        Path filePath = Paths.get(FILE_PATH);

        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(filePath.getParent());

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Write the JSON to the file
            FileWriter writer = new FileWriter(filePath.toFile());
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load a JSON array of WorldInfo objects from a file
    public static List<WorldInfo> loadWorlds() {
        List<WorldInfo> worlds = new ArrayList<>();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            worlds = GSON.fromJson(reader, new TypeToken<List<WorldInfo>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return worlds;
    }

    // Add a new WorldInfo object to the list based on worldName
    public static void addByName(List<WorldInfo> worlds, String worldName) {
        WorldInfo newWorld = new WorldInfo(worldName);
        worlds.add(newWorld);
    }

    // Remove a WorldInfo object from the list based on worldName
    public static void removeByName(List<WorldInfo> worlds, String worldName) {
        worlds.removeIf(world -> world.getWorldName().equals(worldName));
    }
}
