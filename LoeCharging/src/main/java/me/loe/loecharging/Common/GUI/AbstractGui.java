package me.loe.loecharging.Common.GUI;

import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.Common.ChargeStone;
import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/27
 * @ClassInfo
 */
public abstract class AbstractGui {

    public String internalName;
    public Inventory inventory;

    private static final Pattern EXPRESSION_PATTERN =
            Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*\\s*[\\+\\-\\*\\/]?\\s*[a-zA-Z0-9_\\.]*\\s*)+");



    public AbstractGui(String internalName) {
        this.internalName = internalName;
    }

    public abstract Inventory initGui(Player player);

    public abstract boolean isGuiItem(ItemStack itemStack);

    public abstract void handlerClick(InventoryClickEvent event);


    public boolean isCustomItem(ItemStack currentItem){
        if (currentItem == null){
            return false;
        }

        return currentItem.hasItemMeta();
    }



}
