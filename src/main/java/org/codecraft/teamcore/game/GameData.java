package org.codecraft.teamcore.game;

import java.util.List;

public class GameData {
    private String name;
    private List<String> worlds;
    private List<String> playerNames;

    public GameData(String name, List<String> worlds, List<String> playerNames) {
        this.name = name;
        this.worlds = worlds;
        this.playerNames = playerNames;
    }

    public String getName() {
        return name;
    }

    public List<String> getWorlds() {
        return worlds;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }
}
