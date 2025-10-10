package me.loe.loecharging.CustomComponent.ChargeComponent;

import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.Common.GuiComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/26
 * @ClassInfo
 */
public abstract class AbstractComponent {
    public String componentName;

//    public abstract boolean onCall(Player player, ItemStack stack, AbstractChargeItem aci, GuiComponent gc, InventoryView view);

    public abstract boolean onCall(Player player,
                                   ItemStack stack,
                                   AbstractChargeItem aci,
                                   int slot,
                                   Inventory inventory);
    public AbstractComponent(String componentName) {
        this.componentName = componentName;
    }
}
