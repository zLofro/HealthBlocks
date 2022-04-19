package me.lofro.game.blocks.objects;

import lombok.Setter;
import org.bukkit.Location;

public class HealthBlock {

    private @Setter Location location;
    private @Setter int health;

    public HealthBlock(Location location, int health) {
        this.location = location;
        this.health = health;
    }

    /**
     *
     * @return the HealthBlock location.
     */
    public Location location() {
        return location;
    }

    /**
     *
     * @return the HealthBlock health.
     */
    public int health() {
        return health;
    }

}
