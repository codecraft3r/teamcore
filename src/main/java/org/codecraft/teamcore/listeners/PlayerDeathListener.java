package org.codecraft.teamcore.listeners;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.codecraft.teamcore.game.GameData;
import org.codecraft.teamcore.game.TeamcoreGameManager;
import org.codecraft.teamcore.worlds.WorldManager;

import net.kyori.adventure.text.Component;
import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        GameData game = TeamcoreGameManager.getCurrentGameForPlayer(player.getName());
        World mainWorld = Bukkit.getWorld("world");

        if (game != null && player.getWorld() != mainWorld) {
            final TextComponent deathMessage = Component.text("GAME OVER")
                    .color(TextColor.color(NamedTextColor.DARK_RED))
                    .decoration(TextDecoration.BOLD, true)
                    .append(Component.newline())
                    .append(Component.text("Game \""+ game.getName() +"\" has ended.")
                            .color(TextColor.color(NamedTextColor.DARK_GRAY))
                            .decoration(TextDecoration.ITALIC, true)
                            .decoration(TextDecoration.BOLD, false)
                    );

            Bukkit.getServer().sendMessage(deathMessage);
            List<String> gameWorlds = game.getWorlds();
            for (String playerName : game.getPlayerNames()) {
                Player gamePlayer = Bukkit.getPlayerExact(playerName);
                if (gamePlayer != null && mainWorld != null)
                    gamePlayer.teleport(mainWorld.getSpawnLocation());
            }
            for (String world : gameWorlds) {
                WorldManager.unloadCustomWorld(world);
            }
            TeamcoreGameManager.removeGameByName(game.getName());
        }
    }
}
