package net.byalexius.rewards.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ReceiveRewardEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter
    private final Player player;

    @Getter
    private final int day;

    private boolean isCancelled;

    public ReceiveRewardEvent(Player player, int day) {
        this.player = player;
        this.day = day;
        this.isCancelled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
