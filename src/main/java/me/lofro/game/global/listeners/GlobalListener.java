package me.lofro.game.global.listeners;

import eu.decentsoftware.holograms.api.DHAPI;
import me.lofro.game.blocks.BlockManager;
import me.lofro.game.blocks.events.HealthBlockAddEvent;
import me.lofro.game.blocks.events.HealthBlockRemoveEvent;
import me.lofro.game.global.utils.ChatFormatter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record GlobalListener(BlockManager blockManager) implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        var blockData = blockManager.blockData();

        var action = e.getAction();

        if (action == Action.LEFT_CLICK_BLOCK && e.getClickedBlock() != null) {
            var block = e.getClickedBlock();
            var location = block.getLocation();

            if (blockData.getHealthBlock(location) == null) return;

            var healthBlock = blockData.getHealthBlock(location);

            healthBlock.setHealth(healthBlock.health() - 1);

            if (healthBlock.health() == 0) {
                block.breakNaturally(new ItemStack(Material.AIR), true);
                blockData.removeHealthBlock(location);
            }

            var hologram = DHAPI.getHologram(String.valueOf(healthBlock.getId()));

            if (hologram != null) {

                List<String> lines = List.of(ChatFormatter.format("&a" + healthBlock.health()));
                DHAPI.setHologramLines(hologram, lines);

                if (healthBlock.health() == 0) DHAPI.removeHologram(String.valueOf(healthBlock.getId()));
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        var blockData = blockManager.blockData();
        var block = e.getBlock();
        var location = block.getLocation();

        if (blockData.getHealthBlock(location) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHealthBlockAdd(HealthBlockAddEvent e) {
        var healthBlock = e.getHealthBlock();

        List<String> lines = List.of(ChatFormatter.format("&a" + healthBlock.health()));

        DHAPI.createHologram(String.valueOf(healthBlock.getId()), healthBlock.location().clone().add(0.5, 2, 0.5), false, lines);
    }

    @EventHandler
    public void onHealthBlockRemove(HealthBlockRemoveEvent e) {
        var healthBlock = e.getHealthBlock();

        DHAPI.removeHologram(String.valueOf(healthBlock.getId()));
    }

}
