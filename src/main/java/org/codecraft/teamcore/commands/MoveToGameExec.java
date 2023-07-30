package org.codecraft.teamcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codecraft.teamcore.game.GameData;
import org.codecraft.teamcore.game.TeamcoreGameManager;

public class MoveToGameExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("movetogame")) {
            if (args.length < 1) {
                sender.sendMessage("Usage: /movetogame <world name> <player names...>");
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

                // Check if player is already in a game
                GameData game = TeamcoreGameManager.getCurrentGameForPlayer(player.getName());
                if (game != null) {
                    // Transfer player from current game to new game
                    TeamcoreGameManager.removePlayerFromGame(game.getName(), player.getName());
                    TeamcoreGameManager.addPlayerToGame(worldName, player.getName());
                }

                // Teleport player to new world
                player.teleport(world.getSpawnLocation());
                player.sendMessage("You have been moved to the game: "  + "\"" + worldName + "\".");
                sender.sendMessage("\"" + player.getName() + "\" has been moved to the game: " + "\"" + worldName + "\".");
            }

            return true;
        }
        return false;
    }
}