package me.loe.loecharging.CustomComponent.ChargeComponent;

import github.saukiya.sxitem.SXItem;
import io.lumine.xikage.mythicmobs.MythicMobs;
import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.Common.GuiComponent;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import redmi.redmiassylib.RedmiAssyLib;
import redmi.redmiassylib.api.RedmiAssyLibAPI;
import redmi.redmiassylib.util.itemutils.RedmiItemAPI;
import redmi.redmiassylib.util.itemutils.RedmiItemUtils;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/26
 * @ClassInfo
 */
public class ReplaceComponent extends AbstractComponent{
    public String itemLib;
    public String internalName;


    public ReplaceComponent(String componentName, String itemLib, String internalName) {
        super(componentName);
        this.itemLib = itemLib;
        this.internalName = internalName;
    }

    @Override
    public boolean onCall(Player player, ItemStack stack, AbstractChargeItem aci, int slot, Inventory view) {
        switch (this.itemLib) {
            case "MythicMobs":
                ItemStack itemStack = MythicMobs.inst().getItemManager().getItemStack(internalName);
                view.setItem(slot, new ItemStack(Material.AIR));
                player.getInventory().addItem(itemStack);
                return true;
            case "SX-Item":
                ItemStack item = SXItem.getItemManager().getItem(internalName, player, "key", "value");
                view.setItem(slot, new ItemStack(Material.AIR));
                player.getInventory().addItem(item);
                return true;
            case "RedmiAssLib":
                ItemStack redmiItem = RedmiItemAPI.getRedmiItemUtils().getRedmiItem(internalName, player);
                view.setItem(slot, new ItemStack(Material.AIR));
                player.getInventory().addItem(redmiItem);
                System.out.println(redmiItem.getItemMeta().getDisplayName());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("&a组件类型: &e替换组件\n");
        sb.append("&a组件名称: &e").append(componentName).append("\n");
        sb.append("&a物品库: &e").append(itemLib).append("\n");
        sb.append("&a内部名: &e").append(internalName);

        return ColorTansfor.tansColor(sb.toString());
    }
}
