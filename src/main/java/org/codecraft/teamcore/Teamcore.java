package org.codecraft.teamcore;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.codecraft.teamcore.commands.*;
import org.codecraft.teamcore.listeners.PlayerDeathListener;
import org.codecraft.teamcore.listeners.PlayerJoinListener;
import org.codecraft.teamcore.listeners.PlayerPortalListener;
import org.codecraft.teamcore.worlds.WorldManager;

public final class Teamcore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Teamcore has been enabled!");

        // Register commands
        getCommand("startgame").setExecutor(new StartGameExec());
        getCommand("movetogame").setExecutor(new MoveToGameExec());
        getCommand("endgame").setExecutor(new EndGameExec());
        getCommand("listgames").setExecutor(new ListGamesExec());
        getCommand("currentworld").setExecutor(new CurrentWorldExec());

        // load worlds
        WorldManager.loadAllWorlds();

        // Register listeners
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerPortalListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
