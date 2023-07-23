package org.codecraft.teamcore;

import org.bukkit.plugin.java.JavaPlugin;
import org.codecraft.teamcore.commands.CreateWorldExec;
import org.codecraft.teamcore.commands.DeleteWorldExec;
import org.codecraft.teamcore.commands.ListWorldsExec;
import org.codecraft.teamcore.commands.MoveToWorldExec;
import org.codecraft.teamcore.worlds.WorldManager;

public final class Teamcore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Teamcore has been enabled!");

        // Register commands
        getCommand("createworld").setExecutor(new CreateWorldExec());
        getCommand("movetoworld").setExecutor(new MoveToWorldExec());
        getCommand("deleteworld").setExecutor(new DeleteWorldExec());
        getCommand("listworlds").setExecutor(new ListWorldsExec());

        // load worlds
        WorldManager.loadAllWorlds();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
