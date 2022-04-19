package me.lofro.game.blocks.events;

import lombok.Getter;
import me.lofro.game.blocks.objects.HealthBlock;
import me.lofro.game.global.events.BaseEvent;

public class HealthBlockRemoveEvent extends BaseEvent {

    private final @Getter
    HealthBlock healthBlock;

    public HealthBlockRemoveEvent(HealthBlock healthBlock, boolean async) {
        super(async);
        this.healthBlock = healthBlock;
    }

    public HealthBlockRemoveEvent(HealthBlock healthBlock) {
        super(false);
        this.healthBlock = healthBlock;
    }

}
