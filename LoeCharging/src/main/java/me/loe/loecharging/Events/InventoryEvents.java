package me.loe.loecharging.Events;

import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Common.GUI.AbstractGui;
import me.loe.loecharging.Common.GuiComponent;
import me.loe.loecharging.Utils.LoreUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/28
 * @ClassInfo
 */
public class InventoryEvents implements Listener {
    public static Hashtable<String, GuiComponent> guiTitleName = new Hashtable<>();

    public InventoryEvents() {
        //初始化特殊标题名
        ConcurrentHashMap<String, GuiComponent> guiMapping = ChargingManager.guiComponentMapping;
        Enumeration<String> keys = guiMapping.keys();

        while (keys.hasMoreElements()) {
            GuiComponent guiComponent = guiMapping.get(keys.nextElement());
            guiTitleName.put(guiComponent.title, guiComponent);
            System.out.println(guiComponent.title);

        }
    }

    @EventHandler
    public void clickEvents(InventoryClickEvent event){
        long l = System.currentTimeMillis();

        InventoryView view = event.getView();
        String title = view.getTitle();
        if (!guiTitleName.containsKey(title)) {
            return;
        }
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null){
            return;
        }

        if (!currentItem.hasItemMeta())
            return;

        AbstractGui abstractGui = ChargingManager.subGuiMapping.get(LoreUtils.cleanString('§', title));

        if (!abstractGui.isGuiItem(currentItem)){
            return;
        }

        //逻辑处理
        GuiComponent guiComponent = ChargingManager.guiComponentTitleMapping.get(title);

        if (currentItem.getItemMeta().getDisplayName().equals(guiComponent.ensureItem.getItemMeta().getDisplayName())){
            abstractGui.handlerClick(event);
        }

        event.setCancelled(true);




        System.out.println(System.currentTimeMillis() - l + "ms");
    }

    //close event
    @EventHandler
    public void closeEvents(InventoryCloseEvent event){
        InventoryView view = event.getView();
        if (!guiTitleName.containsKey(view.getTitle())) {
            return;
        }

        GuiComponent guiComponent = guiTitleName.get(view.getTitle());

        Collection<Integer> values = guiComponent.toUseIndexMapping.values();

        for (Integer value : values) {
            event.getPlayer().getInventory().addItem(view.getItem(value));
            view.setItem(value, new ItemStack(Material.AIR));
        }


    }
}
