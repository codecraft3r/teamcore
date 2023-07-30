package org.codecraft.teamcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CurrentWorldExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String worldName = player.getWorld().getName();
            player.sendMessage("Current World: " + worldName);
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
        return true;
    }
}
