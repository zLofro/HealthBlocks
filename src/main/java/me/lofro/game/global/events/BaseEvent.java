package me.lofro.game.global.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Template event.
 */
public class BaseEvent extends Event {

    private static final @Getter HandlerList HandlerList = new HandlerList();

    private final @Getter HandlerList Handlers = HandlerList;

    public BaseEvent(boolean async) {
        super(async);
    }

    public BaseEvent() {
        this(false);
    }

}
