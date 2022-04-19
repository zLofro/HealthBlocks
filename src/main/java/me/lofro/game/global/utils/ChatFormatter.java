package me.lofro.game.global.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

public class ChatFormatter {

    public static final String name = ChatFormatter.format("&cHealth&rBlocks");
    public static final String prefix = ChatFormatter.format(name + " &7>> ");

    /**
     * Function to translate the given text into ChatColor format.
     *
     * @param text to translate.
     * @return ChatColor format string.
     */
    public static String format(String text) {return ChatColor.translateAlternateColorCodes('&', text);}

    /**
     * Function to translate the given component text into ChatColor format.
     *
     * @param text to translate.
     * @return ChatColor format component.
     */
    public static Component componentFormat(String text) {return Component.text(ChatColor.translateAlternateColorCodes('&', text));}

    public static String formatWithPrefix(String text) {return prefix + format(text);}

    public static Component componentWithPrefix(String text) {return componentFormat(prefix + text);}

}
