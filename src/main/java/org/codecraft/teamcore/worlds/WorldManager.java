package org.codecraft.teamcore.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class WorldManager {

    public static List<World> createSurvivalWorld(String worldName) {
        List<World> createdWorlds = new ArrayList<>();

        // Create Normal world
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        World normalWorld = worldCreator.createWorld();
        movePlayersToWorld(normalWorld, Bukkit.getServer().getOnlinePlayers());
        createdWorlds.add(normalWorld);

        // Create Nether dimension
        WorldCreator netherCreator = new WorldCreator(worldName + "_nether");
        netherCreator.environment(World.Environment.NETHER);
        World netherWorld = netherCreator.createWorld();
        movePlayersToWorld(netherWorld, Bukkit.getServer().getOnlinePlayers());
        createdWorlds.add(netherWorld);

        // Create The End dimension
        WorldCreator endCreator = new WorldCreator(worldName + "_the_end");
        endCreator.environment(World.Environment.THE_END);
        World endWorld = endCreator.createWorld();
        movePlayersToWorld(endWorld, Bukkit.getServer().getOnlinePlayers());
        createdWorlds.add(endWorld);

        // save world info
        List<WorldInfo> worlds = WorldInfoHelper.loadWorlds();
        worlds.add(new WorldInfo(worldName));
        worlds.add(new WorldInfo(worldName + "_nether"));
        worlds.add(new WorldInfo(worldName + "_the_end"));
        WorldInfoHelper.saveWorlds(worlds);

        return createdWorlds;
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