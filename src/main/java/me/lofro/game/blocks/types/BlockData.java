package me.lofro.game.blocks.types;

import lombok.Getter;
import me.lofro.game.blocks.objects.HealthBlock;
import org.bukkit.Location;

import java.util.HashMap;

public class BlockData {

    private final @Getter HashMap<Location, HealthBlock> healthBlockList;

    public BlockData() {
        this.healthBlockList = new HashMap<>();
    }

    public BlockData(HashMap<Location, HealthBlock> healthBlockList) {
        this.healthBlockList = healthBlockList;
    }

}
