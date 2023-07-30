package org.codecraft.teamcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.codecraft.teamcore.game.GameData;
import org.codecraft.teamcore.game.TeamcoreGameManager;
import org.codecraft.teamcore.worlds.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class StartGameExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("startgame")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by players.");
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage("Usage: /creategame <world name> <player names...>");
                return true;
            }

            // Get game name
            String gameName = args[0];
            // Get players
            List<Player> players = new ArrayList<>();
            for (String arg : args) {
                if (arg.equals(gameName))
                    continue;
                Player player = Bukkit.getPlayerExact(arg);
                if (player == null) {
                    sender.sendMessage("Player '" + arg + "' does not exist.");
                } else {
                    players.add(player);
                }
            }

            // remove players from game
            for (Player player : players) {
                GameData game = TeamcoreGameManager.getCurrentGameForPlayer(player.getName());
                if (game != null)
                    TeamcoreGameManager.removePlayerFromGame(game.getName(), player.getName());
            }

            // Create worlds
            List<World> worlds = WorldManager.createSurvivalWorld(gameName);
            sender.sendMessage("World created successfully.");

            // Move players to world
            WorldManager.movePlayersToWorld(worlds.get(0), players);
            sender.sendMessage("Players moved to world successfully.");

            // Create game data
            List<String> worldNames = new ArrayList<>();
            for (World world : worlds) {
                worldNames.add(world.getName());
            }

            List<String> playerNames = new ArrayList<>();
            for (Player player : players) {
                playerNames.add(player.getName());
            }

            // Save game data
            GameData game = new GameData(gameName, worldNames, playerNames);
            TeamcoreGameManager.addGame(game);


            return true;
        }
        return false;
    }
}