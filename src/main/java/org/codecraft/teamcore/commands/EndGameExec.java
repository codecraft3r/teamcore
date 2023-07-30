package org.codecraft.teamcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codecraft.teamcore.game.GameData;
import org.codecraft.teamcore.game.TeamcoreGameManager;
import org.codecraft.teamcore.worlds.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class EndGameExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("endgame")) {
            if (args.length < 1) {
                sender.sendMessage("Usage: /endgame <worldName>");
                return true;
            }

            String worldName = args[0];
            if (Bukkit.getWorld(worldName) == null) {
                sender.sendMessage("Game '" + worldName + "' does not exist.");
                return true;
            }

            if (Bukkit.getWorld("world") != null) {
                GameData game = TeamcoreGameManager.getGameByName(worldName);
                List<Player> players = new ArrayList<>();
                if (game != null) {
                    for (String playerName : game.getPlayerNames()) {
                        Player player = Bukkit.getPlayerExact(playerName);
                        if (player != null)
                            players.add(player);
                    }
                }
                WorldManager.movePlayersToWorld(Bukkit.getWorld("world"), players);

                WorldManager.unloadCustomWorld(worldName);
                WorldManager.unloadCustomWorld(worldName + "_nether");
                WorldManager.unloadCustomWorld(worldName + "_the_end");

                sender.sendMessage("World '" + worldName + "' deleted successfully.");

                TeamcoreGameManager.removeGameByName(worldName);
            }

            return true;
        }
        return false;
    }
}