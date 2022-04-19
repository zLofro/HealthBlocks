package me.lofro.game.blocks.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class HealthBlock {

    private @Setter Location location;
    private @Setter int health;
    private final @Getter int id;

    public HealthBlock(int id, Location location, int health) {
        this.id = id;
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
