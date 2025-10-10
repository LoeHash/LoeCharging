package me.loe.loecharging.Events.Calls;

import me.loe.loecharging.Common.AbstractChargeItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/9/1
 * @ClassInfo
 */
public class ChargeItemLevelupEvent extends Event implements Cancellable {
    /**
     * 具体触发了升级的AbstractChargeItem
     */
    private final AbstractChargeItem aci;

    /**
     * 具体等级
     */
    private final long level;

    /**
     * 谁触发的
     */
    private final Player player;

    /**
     * 触发的实际充能物品
     */
    private final ItemStack itemStack;

    /**
     * 这个物品所在的具体索引
     */
    private final int slot;

    /**
     * 具体的view
     */
    private final Inventory inventory;


    private boolean isCancelled;

    public static final HandlerList HANDLERS = new HandlerList();


    public ChargeItemLevelupEvent(AbstractChargeItem aci,
                                  long level,
                                  Player player,
                                  ItemStack itemStack,
                                  int slot,
                                  Inventory inventory) {
        this.aci = aci;
        this.level = level;
        this.player = player;
        this.itemStack = itemStack;
        this.slot = slot;
        this.inventory = inventory;
        this.isCancelled = false;

    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }



    public AbstractChargeItem getAci() {
        return aci;
    }

    public long getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
