package me.lofro.game;

import co.aikar.commands.PaperCommandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.lofro.game.blocks.BlockManager;
import me.lofro.game.blocks.adapters.RuntimeTypeAdapterFactory;
import me.lofro.game.blocks.objects.HealthBlock;
import me.lofro.game.data.DataManager;
import me.lofro.game.data.adapters.LocationSerializer;
import me.lofro.game.global.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockHealth extends JavaPlugin {

    private @Getter static BlockHealth instance;

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationSerializer())
            .registerTypeAdapter(Location[].class, LocationSerializer.getArraySerializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                    .of(HealthBlock.class, "type"))
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private @Getter DataManager dataManager;
    private @Getter BlockManager blockManager;

    private @Getter PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        try {
            this.dataManager = new DataManager(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.blockManager = new BlockManager(this);

        Bukkit.getConsoleSender().sendMessage(ChatFormatter.componentWithPrefix("&aEl plugin ha sido iniciado con éxito."));
    }

    @Override
    public void onDisable() {
        dataManager.save();

        Bukkit.getConsoleSender().sendMessage(ChatFormatter.componentWithPrefix("&aEl plugin ha sido desactivado con éxito."));
    }

    /**
     * A static method to access the Gson object globally.
     *
     * @return gson with all adapter types registered.
     */
    public static Gson gson() {
        return gson;
    }

}