package me.lofro.game.blocks;

import com.google.common.collect.ImmutableList;
import me.lofro.game.BlockHealth;
import me.lofro.game.blocks.commands.HealthBlockCMD;
import me.lofro.game.blocks.types.BlockData;
import me.lofro.game.data.utils.JsonConfig;
import me.lofro.game.global.interfaces.Restorable;
import me.lofro.game.global.utils.Commands;

public class BlockManager extends Restorable<BlockHealth> {

    private BlockData blockData;

    private final HealthBlockCMD blockDataCMD;

    /**
     * Constructor for Bukkit instantiation.
     *
     * @param instance The instance of the plugin.
     */
    public BlockManager(final BlockHealth instance) {
        super(instance);
        restore(instance.getDataManager().blockDataConfig());

        this.blockDataCMD = new HealthBlockCMD(this);

        Commands.registerCommands(instance.getCommandManager(), blockDataCMD);

        // Sets the location command completion.
        BlockHealth.getInstance().getCommandManager().getCommandCompletions().registerCompletion(
                "@location", c -> ImmutableList.of("x,y,z"));
    }

    /**
     * Constructor for Testing environments.
     */
    public BlockManager() {
        super();
        this.blockData = new BlockData();

        this.blockDataCMD = new HealthBlockCMD(this);
    }

    @Override
    protected void restore(JsonConfig jsonConfig) {
        this.blockData = BlockHealth.gson().fromJson(jsonConfig.getJsonObject(), BlockData.class);
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(BlockHealth.gson().toJsonTree(blockData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The blockData object.
     */
    public BlockData blockData() {
        return blockData;
    }

}