package me.lofro.game.blocks;

import com.google.common.collect.ImmutableList;
import eu.decentsoftware.holograms.api.DHAPI;
import me.lofro.game.HealthBlocks;
import me.lofro.game.blocks.commands.HealthBlockCMD;
import me.lofro.game.blocks.types.BlockData;
import me.lofro.game.data.utils.JsonConfig;
import me.lofro.game.global.interfaces.Restorable;
import me.lofro.game.global.utils.ChatFormatter;
import me.lofro.game.global.utils.Commands;

import java.util.List;

public class BlockManager extends Restorable<HealthBlocks> {

    private BlockData blockData;

    private final HealthBlockCMD blockDataCMD;

    /**
     * Constructor for Bukkit instantiation.
     *
     * @param instance The instance of the plugin.
     */
    public BlockManager(final HealthBlocks instance) {
        super(instance);
        restore(instance.getDataManager().blockDataConfig());

        this.blockDataCMD = new HealthBlockCMD(this);

        Commands.registerCommands(instance.getCommandManager(), blockDataCMD);

        // Sets the location command completion.
        HealthBlocks.getInstance().getCommandManager().getCommandCompletions().registerCompletion(
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
        this.blockData = HealthBlocks.gson().fromJson(jsonConfig.getJsonObject(), BlockData.class);

        blockData.healthBlockListValues().forEach(healthBlock -> {
            List<String> lines = List.of(ChatFormatter.format("&a" + healthBlock.health()));

            DHAPI.createHologram(String.valueOf(healthBlock.getId()), healthBlock.location().clone().add(0.5, 2, 0.5), false, lines);
        });
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(HealthBlocks.gson().toJsonTree(blockData).getAsJsonObject());
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
