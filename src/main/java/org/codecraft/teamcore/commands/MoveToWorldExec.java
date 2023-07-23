package org.codecraft.teamcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoveToWorldExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("movetoworld")) {
            if (args.length < 2) {
                sender.sendMessage("Usage: /movetoworld <world name> <player names...>");
                return true;
            }

            String worldName = args[0];
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                sender.sendMessage("The specified world does not exist.");
                return true;
            }

            for (int i = 1; i < args.length; i++) {
                String playerName = args[i];
                Player player = Bukkit.getPlayer(playerName);

                if (player == null) {
                    sender.sendMessage("Player not found: " + playerName);
                    continue;
                }

                player.teleport(world.getSpawnLocation());
                player.sendMessage("You have been moved to the world: " + worldName);
                sender.sendMessage(player.getName() + " has been moved to the world: " + worldName);
            }

            return true;
        }
        return false;
    }
}