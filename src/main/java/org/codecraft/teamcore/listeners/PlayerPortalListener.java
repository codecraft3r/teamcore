package org.codecraft.teamcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.codecraft.teamcore.game.TeamcoreGameManager;

import java.util.List;

public class PlayerPortalListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        PlayerTeleportEvent.TeleportCause portalCause = event.getCause();
        if (portalCause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            // Teleport the player to the desired Nether dimension
            List<String> worlds = TeamcoreGameManager.getCurrentGameForPlayer(event.getPlayer().getName()).getWorlds();
            if (worlds == null || worlds.size() < 2) {
                event.setCancelled(true);
                return;
            }

            World destinationWorld = Bukkit.getWorld(worlds.get(1));
            if (destinationWorld != null && !destinationWorld.equals(event.getPlayer().getWorld())) {
                Location destination = event.getTo();
                destination.setWorld(destinationWorld);
                event.setTo(destination);
            } else {
                Location destination = event.getTo();
                destination.setWorld(Bukkit.getWorld(worlds.get(0)));
                event.setTo(destination);
            }
        } else if (portalCause.equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            // Teleport the player to the desired End dimension
            List<String> worlds = TeamcoreGameManager.getCurrentGameForPlayer(event.getPlayer().getName()).getWorlds();
            if (worlds == null || worlds.size() < 3) {
                event.setCancelled(true);
                return;
            }

            World destinationWorld = Bukkit.getWorld(worlds.get(2));
            if (destinationWorld != null && !destinationWorld.equals(event.getPlayer().getWorld())) {
                Location destination = event.getTo();
                destination.setWorld(destinationWorld);
                event.setTo(destination);
            } else {
                Location destination = event.getTo();
                destination.setWorld(Bukkit.getWorld(worlds.get(0)));
                event.setTo(destination);
            }
        } else if (portalCause.equals(PlayerTeleportEvent.TeleportCause.END_GATEWAY)) {
            List<String> worlds = TeamcoreGameManager.getCurrentGameForPlayer(event.getPlayer().getName()).getWorlds();
            Location destination = event.getTo();
            Player player = event.getPlayer();
            if (destination.getWorld() != null && destination.getWorld().getEnvironment() == World.Environment.THE_END) {
                // If the destination is already in the End, return without modifying the teleportation
                return;
            } else {
                if (worlds.size() > 0) {
                    event.setCancelled(true);
                    player.teleport(Bukkit.getWorld(worlds.get(0)).getSpawnLocation());

                }
            }
        }
    }
}
