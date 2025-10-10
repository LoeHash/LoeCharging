package me.loe.loecharging.CustomComponent.ChargeComponent;

import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.Common.GuiComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/9/1
 * @ClassInfo
 */
public class DeleteComponent extends AbstractComponent{
    public DeleteComponent(String componentName) {
        super(componentName);
    }

    @Override
    public boolean onCall(Player player, ItemStack stack, AbstractChargeItem aci, int slot, Inventory view) {
        //删除某个Item
        view.setItem(slot, new ItemStack(Material.AIR));
        return true;

    }
}
