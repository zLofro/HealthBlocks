package me.lofro.game.blocks.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.lofro.game.blocks.BlockManager;
import me.lofro.game.blocks.objects.HealthBlock;
import me.lofro.game.blocks.types.BlockData;
import me.lofro.game.global.utils.ChatFormatter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

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
        var healthBlocks = blockData().getHealthBlockList();

        if (block.getType().equals(Material.AIR)) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl tipo de bloque introducido no es válido."));
            return;
        }

        if (healthBlocks.get(location) != null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido ya existe."));
            return;
        }

        healthBlocks.put(location, new HealthBlock(location, health));
    }

    @Subcommand("remove")
    @CommandCompletion("@location")
    public void removeHealthBlock(CommandSender sender, Location location) {
        var healthBlocks = blockData().getHealthBlockList();

        if (healthBlocks.get(location) == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido no existe."));
            return;
        }

        healthBlocks.remove(location);
    }

    @Subcommand("getHealth")
    @CommandCompletion("@location")
    public void getHealth(CommandSender sender, Location location) {
        var healthBlocks = blockData().getHealthBlockList();

        if (healthBlocks.get(location) == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido no existe."));
            return;
        }

        var exactBlock = healthBlocks.get(location);
        var health = exactBlock.health();

        sender.sendMessage(ChatFormatter.formatWithPrefix("&7La vida del HealthBlock es de &8" + health + "&7."));
    }

    @Subcommand("setHealth")
    @CommandCompletion("@location health")
    public void setHealth(CommandSender sender, Location location, int health) {
        var healthBlocks = blockData().getHealthBlockList();

        if (healthBlocks.get(location) == null) {
            sender.sendMessage(ChatFormatter.formatWithPrefix("&cEl HealthBlock introducido no existe."));
            return;
        }

        var exactBlock = healthBlocks.get(location);

        exactBlock.setHealth(health);
        sender.sendMessage(ChatFormatter.formatWithPrefix("&7La vida del HealthBlock ha sido actualizada a &8" + health + "&7."));
    }

    /**
     * @return The blockData object.
     */
    private BlockData blockData() {
        return blockManager.blockData();
    }

}
