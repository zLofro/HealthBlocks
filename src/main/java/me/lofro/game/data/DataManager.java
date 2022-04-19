package me.lofro.game.data;

import me.lofro.game.BlockHealth;
import me.lofro.game.data.utils.JsonConfig;
import me.lofro.game.global.interfaces.Instantiable;

public class DataManager extends Instantiable<BlockHealth> {

    private final JsonConfig blockDataConfig;

    /**
     * Default constructor for bukkit instantiation.
     *
     * @param instance The plugin instance.
     */
    public DataManager(final BlockHealth instance) throws Exception {
        super(instance);
        this.blockDataConfig = JsonConfig.cfg("healthBlockDataConfig.json", instance);
    }

    /**
     * A method that saves the current state of the application to json files.
     */
    public void save() {
        ins().getBlockManager().save(blockDataConfig);
    }

    /**
     * @return the blockDataConfig.
     */
    public JsonConfig blockDataConfig() {
        return blockDataConfig;
    }

}
