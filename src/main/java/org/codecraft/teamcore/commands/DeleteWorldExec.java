package org.codecraft.teamcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.codecraft.teamcore.worlds.WorldManager;

public class DeleteWorldExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("deleteworld")) {
            if (args.length < 1) {
                sender.sendMessage("Usage: /deleteworld <worldName>");
                return true;
            }

            String worldName = args[0];
            if (Bukkit.getWorld(worldName) == null) {
                sender.sendMessage("World '" + worldName + "' does not exist.");
                return true;
            }

            WorldManager.unloadCustomWorld(worldName);
            sender.sendMessage("World '" + worldName + "' deleted successfully.");

            return true;
        }
        return false;
    }
}