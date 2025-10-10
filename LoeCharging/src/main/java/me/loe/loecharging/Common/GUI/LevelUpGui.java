package me.loe.loecharging.Common.GUI;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import me.loe.loecharging.Common.*;
import me.loe.loecharging.Events.Calls.ChargeItemLevelupEvent;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/27
 * @ClassInfo
 */
public class LevelUpGui extends AbstractGui{
    public GuiComponent guiComponent;


    public LevelUpGui(String internalName) {
        super(internalName);
        guiComponent = ChargingManager.guiComponentMapping.get(internalName);

    }

    @Override
    public Inventory initGui(Player player) {
        Inventory inventory = Bukkit.createInventory(player, guiComponent.size, guiComponent.title);

        int index = 0;
        for (String s : guiComponent.layout) {
            char[] chars = s.toCharArray();
            for (char c : chars) {

                ItemStack itemStack = guiComponent.itemMap.get(c);

                inventory.setItem(index, itemStack);
                index++;
            }
        }

        return inventory;
    }

    @Override
    public boolean isGuiItem(ItemStack itemStack) {

        ConcurrentHashMap<Character, ItemStack> itemMap = guiComponent.itemMap;
        ItemMeta itemMeta = itemStack.getItemMeta();


        Enumeration<Character> keys = itemMap.keys();
        while (keys.hasMoreElements()) {
            Character character = keys.nextElement();

            ItemStack guiItem = itemMap.get(character);

            if (!guiItem.hasItemMeta()){
                continue;
            }

            if (guiItem.getItemMeta().getDisplayName().equals(itemMeta.getDisplayName())){
                return true;
            }0


        }

        return false;
    }

    @Override
    public void handlerClick(InventoryClickEvent event) {
        //处理点击
        ConcurrentHashMap<String, Integer> toUseIndexMapping = guiComponent.toUseIndexMapping;
        Inventory inventory = event.getInventory();
        InventoryView view = event.getView();
        HumanEntity whoClicked = event.getWhoClicked();

        //获取充能石与升级物品1
        Integer chargeStoneIndex = toUseIndexMapping.get("充能物品");
        ItemStack levelUpItem = view.getItem(toUseIndexMapping.get("升级物品"));
        ItemStack chargeItem = view.getItem(chargeStoneIndex);

        //首先检查充能物品基本信息
        if (!isCustomItem(levelUpItem)){

            whoClicked.sendMessage(ColorTansfor.tansColor("&c升级物品错误!"));
            view.close();
            return;
        }

        ItemMeta levelUpItemMeta = levelUpItem.getItemMeta();

        //检查物品名
        AbstractChargeItem abstractChargeItem = ChargingManager.cachedMatchNameAci.get(levelUpItemMeta.getDisplayName());
        if (abstractChargeItem == null){

            whoClicked.sendMessage(ColorTansfor.tansColor("&c物品不可被升级!"));
            view.close();
            return;
        }

        //获取充能方式
        if (!(abstractChargeItem.growingWays.equals("ALL") || abstractChargeItem.growingWays.equals("ChargeItem"))) {
            whoClicked.sendMessage(ColorTansfor.tansColor("&c此物品只能通过击杀怪物成长"));
            view.close();
            return;
        }
        //获取升级物品的变量
        ConcurrentHashMap<String, Object> varHashMap = ChargingManager.solveItemVar(levelUpItem);


        if ((Long) varHashMap.get("current_item_level") >= ChargingManager.globalLevelEnum.size()
                && abstractChargeItem.experience.equals("Enum")){
            whoClicked.sendMessage(ColorTansfor.tansColor("&c当前物品已经满级"));
            view.close();
            return;
        }

        if ((Long) varHashMap.get("current_item_level") >= abstractChargeItem.maxLevel){
            whoClicked.sendMessage(ColorTansfor.tansColor("&c当前物品已经满级"));
            view.close();
            return;
        }

        //检查充能石
        if (!isCustomItem(chargeItem)){
            whoClicked.sendMessage(ColorTansfor.tansColor("&c充能石错误!"));
            view.close();
            return;
        }

        //检查充能石物品名
        ItemMeta chargeItemMeta = chargeItem.getItemMeta();
        ChargeStone chargeStone = ChargingManager.chargeStoneMatchNameHashMap.get(chargeItemMeta.getDisplayName());
        if (chargeStone == null){
            whoClicked.sendMessage(ColorTansfor.tansColor("&c充能石不可用!"));
            view.close();
            return;
        }

        //获取提供的能量
        long provideEnergy = chargeStone.getProvideEnergy();


        ExperienceGiveContent experienceGiveContent = ChargingManager.giveChargeItemExp(
                levelUpItem,
                provideEnergy,
                (Player) whoClicked,
                abstractChargeItem,
                varHashMap);

        //减去一个充能石
        chargeItem.setAmount(chargeItem.getAmount() - 1);

        //如果升级了, 获取对应的事件
        if (experienceGiveContent.newLevel > experienceGiveContent.preLevel){

            //创建升级对象并call
            ChargeItemLevelupEvent chargeItemLevelupEvent = new ChargeItemLevelupEvent(abstractChargeItem,
                    experienceGiveContent.newLevel,
                    (Player) whoClicked,
                    chargeItem,
                    guiComponent.toUseIndexMapping.get("升级物品"),
                    inventory);
            Bukkit.getServer().getPluginManager().callEvent(chargeItemLevelupEvent);

            //如果没有被取消
            if (!chargeItemLevelupEvent.isCancelled()){
                ChargingManager.getLevelEventComponent(abstractChargeItem,
                        experienceGiveContent.newLevel,
                        (Player) whoClicked,
                        chargeItem,
                        guiComponent.toUseIndexMapping.get("升级物品"),
                        inventory);
            }
        }


        view.setItem(chargeStoneIndex, chargeItem);
    }



}
