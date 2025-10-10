package me.loe.loecharging.CustomComponent.ChargeComponent;


import me.clip.placeholderapi.PlaceholderAPI;
import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Common.GuiComponent;
import me.loe.loecharging.Utils.ColorTansfor;

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
public class CommandComponent extends AbstractComponent{
    public String command;

    public CommandComponent(String componentName, String command) {
        super(componentName);
        this.command = command;
    }

    @Override
    public boolean onCall(Player player, ItemStack stack, AbstractChargeItem aci, int slot, Inventory view) {

        String commandParsed = PlaceholderAPI.setPlaceholders(player, command);

        //处理内部占位符
        commandParsed = ChargingManager.handleInternalPlaceHolder(aci, commandParsed);

        //执行
        try {
            if (player.isOp()){
                player.performCommand(commandParsed);
            }else{
                player.setOp(true);
                player.performCommand(commandParsed);
                player.setOp(false);
            }

            return true;
        }catch (Exception e){
            player.setOp(false);
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("&a组件类型: &e命令组件\n");
        sb.append("&a组件名称: &e").append(componentName).append("\n");
        sb.append("&a命令: &e").append(command);

        return ColorTansfor.tansColor(sb.toString());
    }


}
