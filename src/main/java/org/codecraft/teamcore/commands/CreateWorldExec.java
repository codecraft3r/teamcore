package org.codecraft.teamcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codecraft.teamcore.worlds.WorldManager;

import java.util.Collection;

public class CreateWorldExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("createworld")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by players.");
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage("Usage: /createworld <world name> <player names...>");
                return true;
            }

            String worldName = args[0];
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();

            WorldManager.createSurvivalWorld(worldName);
            World world = Bukkit.getWorld(worldName);
            sender.sendMessage("World created successfully.");

            WorldManager.movePlayersToWorld(world, players);
            sender.sendMessage("Players moved to world successfully.");

            return true;
        }
        return false;
    }
}