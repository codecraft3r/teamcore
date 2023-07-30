package org.codecraft.teamcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ListGamesExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("listgames")) {
            World[] worlds = Bukkit.getWorlds().toArray(new World[0]);

            sender.sendMessage("Worlds:");
            for (World world : worlds) {
                sender.sendMessage("- " + world.getName());
            }

            return true;
        }
        return false;
    }
}
