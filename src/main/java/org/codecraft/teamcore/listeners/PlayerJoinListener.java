package org.codecraft.teamcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.codecraft.teamcore.game.GameData;
import org.codecraft.teamcore.game.TeamcoreGameManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameData game = TeamcoreGameManager.getCurrentGameForPlayer(player.getName());
        @NotNull String currentlyIn = player.getWorld().getName();
        if (game == null) {
            return;
        }
        if (game.getWorlds().contains(currentlyIn)) {
            return;
        }
        player.sendMessage("You are not in the correct world for this game. Teleporting you there now...");
        player.teleport(Objects.requireNonNull(Bukkit.getWorld(game.getWorlds().get(0))).getSpawnLocation());



        // You can add any additional logic or actions here
    }
}