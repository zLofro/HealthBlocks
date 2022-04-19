package me.lofro.game.global.utils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;

public class Commands {

    public static void registerCommands(PaperCommandManager manager, BaseCommand... commandExecutors) {
        for (BaseCommand commandExecutor : commandExecutors) {
            manager.registerCommand(commandExecutor);
        }
    }

}
