package me.lofro.game.blocks.types;

import lombok.Getter;
import me.lofro.game.blocks.objects.HealthBlock;
import me.lofro.game.blocks.utils.IdProvider;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;

public class BlockData {

    private final @Getter IdProvider idProvider;
    private final HashMap<Location, HealthBlock> healthBlockList;

    public BlockData() {
        this.healthBlockList = new HashMap<>();
        this.idProvider = new IdProvider();
    }

    public BlockData(IdProvider idProvider, HashMap<Location, HealthBlock> healthBlockList) {
        this.idProvider = idProvider;
        this.healthBlockList = healthBlockList;
    }

    public HealthBlock addHealthBlock(Location location, int health) {
        if (healthBlockList.containsKey(location))
            throw new IllegalArgumentException("Block is already in this dataset.");

        int id = idProvider.getID();

        var healthBlock = new HealthBlock(id, location, health);
        healthBlockList.put(location, healthBlock);
        return healthBlock;
    }

    public HealthBlock removeHealthBlock(Location location) throws IllegalArgumentException {
        var healthBlock = getHealthBlock(location);

        if (healthBlock == null)
            throw new IllegalArgumentException("The block in location " + location + " is not a HealthBlock.");

        return removeHealthBlock(healthBlock);
    }

    private HealthBlock removeHealthBlock(final HealthBlock healthBlock) {
        idProvider.addId(healthBlock.getId());

        healthBlockList.remove(healthBlock.location());
        return healthBlock;
    }

    public HealthBlock setHealth(Location location, int health) {
        var healthBlock = getHealthBlock(location);

        if (healthBlock == null)
            throw new IllegalArgumentException("The block in location " + location + " is not a HealthBlock.");

        return setHealth(location, health);
    }

    private HealthBlock setHealth(HealthBlock healthBlock, int health) {
        healthBlock.setHealth(health);

        return healthBlock;
    }

    public int setHealth(Location location) {
        var healthBlock = getHealthBlock(location);

        if (healthBlock == null)
            throw new IllegalArgumentException("The block in location " + location + " is not a HealthBlock.");

        return getHealth(healthBlock);
    }

    private int getHealth(HealthBlock healthBlock) {
        return healthBlock.health();
    }

    public HealthBlock getHealthBlock(Location location) {
        return healthBlockList.get(location);
    }

    public List<HealthBlock> healthBlockListValues() {
        return healthBlockList.values().stream().toList();
    }

}
