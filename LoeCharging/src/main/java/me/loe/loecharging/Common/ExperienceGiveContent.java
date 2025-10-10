package me.loe.loecharging.Common;

import org.bukkit.inventory.ItemStack;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/9/1
 * @ClassInfo
 */
public class ExperienceGiveContent {
    public ItemStack newItemStack;
    public long preLevel;
    public long newLevel;

    public ExperienceGiveContent(ItemStack newItemStack, long newLevel, long preLevel) {
        this.newItemStack = newItemStack;
        this.newLevel = newLevel;
        this.preLevel = preLevel;
    }
}
