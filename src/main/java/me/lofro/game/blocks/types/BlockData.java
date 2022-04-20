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

    /**
     *
     * Creates a HealthBlock with the given Location and Health.
     *
     * @param location location of the HealthBlock.
     * @param health health of the HealthBlock.
     * @return the HealthBlock.
     */
    public HealthBlock addHealthBlock(Location location, int health) {
        if (healthBlockList.containsKey(location))
            throw new IllegalArgumentException("Block is already in this dataset.");

        int id = idProvider.getID();

        var healthBlock = new HealthBlock(id, location, health);
        healthBlockList.put(location, healthBlock);
        return healthBlock;
    }

    /**
     *
     * Removes the HealthBlock assigned to a specific Location.
     *
     * @param location of the HealthBlock.
     * @return the HealthBlock.
     * @throws IllegalArgumentException if the HealthBlock does not exist.
     */
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

    /**
     *
     * Sets the health of the HealthBlock based on its Location.
     *
     * @param location of the HealthBlock.
     * @param health to update.
     * @return the HealthBlock.
     */
    public HealthBlock setHealth(Location location, int health) {
        var healthBlock = getHealthBlock(location);

        if (healthBlock == null)
            throw new IllegalArgumentException("The block in location " + location + " is not a HealthBlock.");

        return setHealth(healthBlock, health);
    }

    private HealthBlock setHealth(HealthBlock healthBlock, int health) {
        healthBlock.setHealth(health);

        return healthBlock;
    }

    /**
     *
     * @param location of the HealthBlock.
     * @return the current health of the block.
     */
    public int getHealth(Location location) {
        var healthBlock = getHealthBlock(location);

        if (healthBlock == null)
            throw new IllegalArgumentException("The block in location " + location + " is not a HealthBlock.");

        return getHealth(healthBlock);
    }

    private int getHealth(HealthBlock healthBlock) {
        return healthBlock.health();
    }

    /**
     *
     * Gets the HealthBlock assigned to a Location.
     *
     * @param location of the HealthBlock.
     * @return the HealthBlock.
     */
    public HealthBlock getHealthBlock(Location location) {
        return healthBlockList.get(location);
    }

    /**
     *
     * @return a list of all the HealthBlocks registered in the plugin.
     */
    public List<HealthBlock> healthBlockListValues() {
        return healthBlockList.values().stream().toList();
    }

}
