package org.codecraft.teamcore.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeamcoreGameManager {
    private static final String FILE_NAME = "game_state.json";

    // Add a game to the existing game state JSON
    public static void addGame(GameData gameData) {
        List<GameData> gameDataList = loadGameStateJson();

        // Add the new game data to the list
        gameDataList.add(gameData);

        // Convert the list to JSON string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(gameDataList);

        // Save to file
        saveJsonToFile(json);
    }

    // Remove a game from the game state JSON by name
    public static void removeGameByName(String name) {
        List<GameData> gameDataList = loadGameStateJson();

        // Remove the game data from the list by name
        Iterator<GameData> iterator = gameDataList.iterator();
        while (iterator.hasNext()) {
            GameData gameData = iterator.next();
            if (gameData.getName().equals(name)) {
                iterator.remove();
            }
        }

        // Convert the list to JSON string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(gameDataList);

        // Save to file
        saveJsonToFile(json);
    }

    // Add a player to a game by name
    public static void addPlayerToGame(String gameName, String playerName) {
        List<GameData> gameDataList = loadGameStateJson();

        // Find the game data by name
        for (GameData gameData : gameDataList) {
            if (gameData.getName().equals(gameName)) {
                // Add the player to the game
                gameData.getPlayerNames().add(playerName);
                break;
            }
        }

        // Convert the list to JSON string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(gameDataList);

        // Save to file
        saveJsonToFile(json);
    }

    // Get the current game for a player by name
    public static GameData getCurrentGameForPlayer(String playerName) {
        List<GameData> gameDataList = loadGameStateJson();

        // Find the game data that the player is currently in
        for (GameData gameData : gameDataList) {
            if (gameData.getPlayerNames().contains(playerName)) {
                return gameData;
            }
        }

        return null; // Return null if the player is not in any game
    }

    // Get a game by name
    public static GameData getGameByName(String name) {
        List<GameData> gameDataList = loadGameStateJson();

        // Find the game data by name
        for (GameData gameData : gameDataList) {
            if (gameData.getName().equals(name)) {
                return gameData;
            }
        }

        return null; // Return null if the game is not found
    }

    // Remove a player from a game by name
    public static void removePlayerFromGame(String gameName, String playerName) {
        List<GameData> gameDataList = loadGameStateJson();

        // Find the game data by name
        for (GameData gameData : gameDataList) {
            if (gameData.getName().equals(gameName)) {
                // Remove the player from the game by name
                gameData.getPlayerNames().remove(playerName);
                break;
            }
        }

        // Convert the list to JSON string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(gameDataList);

        // Save to file
        saveJsonToFile(json);
    }

    // Load the game state JSON and return as List of GameData dataclass
    public static List<GameData> loadGameStateJson() {
        List<GameData> gameDataList;

        try {
            // Read from file
            String json = readFile();

            // Convert JSON string to List of GameData dataclass
            Type listType = new TypeToken<List<GameData>>() {}.getType();
            gameDataList = new Gson().fromJson(json, listType);
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, return an empty list
            gameDataList = new ArrayList<>();
        }

        return gameDataList;
    }

    // Save the JSON string to file
    private static void saveJsonToFile(String json) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read the JSON string from file
    private static String readFile() throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found");
        }
        return stringBuilder.toString();
    }
}

