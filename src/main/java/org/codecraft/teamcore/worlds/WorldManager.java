package org.codecraft.teamcore.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.List;

public class WorldManager {

    public static void createSurvivalWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        World world = worldCreator.createWorld();
        movePlayersToWorld(world, Bukkit.getServer().getOnlinePlayers());

        // save world info
        List<WorldInfo> worlds = WorldInfoHelper.loadWorlds();
        WorldInfoHelper.addByName(worlds, world.getName());
        WorldInfoHelper.saveWorlds(worlds);

    }

    public static void loadAllWorlds() {
        List<WorldInfo> worlds = WorldInfoHelper.loadWorlds();
        for (WorldInfo worldInfo : worlds) {
            String worldName = worldInfo.getWorldName();
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                WorldCreator worldCreator = new WorldCreator(worldName);
                world = Bukkit.createWorld(worldCreator);
            }
        }
    }

    public static void movePlayersToWorld(World world, Collection<? extends Player> players) {
        for (Player player : players) {
            player.teleport(world.getSpawnLocation());
        }
    }

    public static void unloadCustomWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, false);
            List<WorldInfo> worlds = WorldInfoHelper.loadWorlds();
            WorldInfoHelper.removeByName(worlds, worldName);
            WorldInfoHelper.saveWorlds(worlds);
        }
    }

}