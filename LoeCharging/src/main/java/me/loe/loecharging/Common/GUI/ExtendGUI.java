package me.loe.loecharging.Common.GUI;

import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Common.GuiComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/9/13
 * @ClassInfo
 */
public class ExtendGUI extends AbstractGui{
    public GuiComponent guiComponent;
    public ExtendGUI(String internalName) {
        super(internalName);
        guiComponent = ChargingManager.guiComponentMapping.get(internalName);
    }

    @Override
    public Inventory initGui(Player player) {
        return null;
    }

    @Override
    public boolean isGuiItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public void handlerClick(InventoryClickEvent event) {
        ConcurrentHashMap<String, Integer> toUseIndexMapping = guiComponent.toUseIndexMapping;
        Inventory inventory = event.getInventory();


        ItemStack beExtendItem = inventory.getItem(toUseIndexMapping.get("被继承物品"));
        inventory.getItem(toUseIndexMapping.get("继承物品"))
    }
}
