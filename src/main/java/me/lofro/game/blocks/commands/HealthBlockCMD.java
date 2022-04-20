package me.lofro.game.blocks.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import eu.decentsoftware.holograms.api.DHAPI;
import me.lofro.game.blocks.BlockManager;
import me.lofro.game.blocks.events.HealthBlockAddEvent;
import me.lofro.game.blocks.events.HealthBlockRemoveEvent;
import me.lofro.game.blocks.types.BlockData;
import me.lofro.game.global.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.List;

@CommandAlias("healthBlock")
@CommandPermission("admin.perm")
public class HealthBlockCMD extends BaseCommand {

    private final BlockManager blockManager;

    public HealthBlockCMD(BlockManager blockManager) {
        this.blockManager = blockManager;
    }

    @Subcommand("add")
    @CommandCompletion("@location health")
    public void addHealthBlock(CommandSender sender, Location location, int health) {
        var block = location.getBlock();
        var blockData = blockManager.blockData();

        if (block.getType().equals(Material.AIR)) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl tipo de bloque introducido no es válido."));
            return;
        }

        if (blockData.getHealthBlock(location) != null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido ya existe."));
            return;
        }

        var healthBlock = blockData.addHealthBlock(location, health);

        Bukkit.getPluginManager().callEvent(new HealthBlockAddEvent(healthBlock));
        sender.sendMessage(ChatFormatter.formatWithPrefix("&7El HealthBlock ha sido añadido con éxito."));
    }

    @Subcommand("remove")
    @CommandCompletion("@location")
    public void removeHealthBlock(CommandSender sender, Location location) {
        var blockData = blockManager.blockData();

        if (blockData.getHealthBlock(location) == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido no existe."));
            return;
        }

        var healthBlock = blockData.removeHealthBlock(location);

        Bukkit.getPluginManager().callEvent(new HealthBlockRemoveEvent(healthBlock));
        sender.sendMessage(ChatFormatter.formatWithPrefix("&7El HealthBlock ha sido eliminado con éxito."));
    }

    @Subcommand("getHealth")
    @CommandCompletion("@location")
    public void getHealth(CommandSender sender, Location location) {
        var blockData = blockManager.blockData();

        if (blockData.getHealthBlock(location) == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido no existe."));
            return;
        }

        sender.sendMessage(ChatFormatter.formatWithPrefix("&7La vida del HealthBlock es de &a" + blockData.getHealth(location) + "&7."));
    }

    @Subcommand("setHealth")
    @CommandCompletion("@location health")
    public void setHealth(CommandSender sender, Location location, int health) {
        var blockData = blockManager.blockData();

        if (blockData.getHealthBlock(location) == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido no existe."));
            return;
        }

        var healthBlock = blockData.setHealth(location, health);

        var hologram = DHAPI.getHologram(String.valueOf(healthBlock.getId()));

        if (hologram == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl holograma introducido no existe."));
            return;
        }

        List<String> lines = List.of(ChatFormatter.format("&a" + healthBlock.health()));
        DHAPI.setHologramLines(hologram, lines);

        sender.sendMessage(ChatFormatter.formatWithPrefix("&7La vida del HealthBlock ha sido actualizada a &a" + health + "&7."));
    }

    /**
     * @return The blockData object.
     */
    private BlockData blockData() {
        return blockManager.blockData();
    }

}
