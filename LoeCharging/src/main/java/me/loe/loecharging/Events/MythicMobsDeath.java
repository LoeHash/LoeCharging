package me.loe.loecharging.Events;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Common.ExperienceGiveContent;
import me.loe.loecharging.Common.MonsterGroup;
import me.loe.loecharging.Events.Calls.ChargeItemLevelupEvent;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/13
 * @ClassInfo
 */
public class MythicMobsDeath implements Listener {

    @EventHandler
    public void mmDeath(MythicMobDeathEvent event) {
        LivingEntity killer = event.getKiller();
        if (!(killer instanceof Player)) {
            return;
        }

        Player player = (Player) killer;


        PlayerInventory inventory = player.getInventory();
        ItemStack itemInMainHand = inventory.getItemInMainHand();

        if (itemInMainHand.getType().equals(Material.AIR)){
            return;
        }

        if (!itemInMainHand.hasItemMeta())
            return;

        AbstractChargeItem abstractChargeItem = ChargingManager.cachedMatchNameAci.get(itemInMainHand.getItemMeta().getDisplayName());

        if (abstractChargeItem == null) {
            return;
        }

        String growingWays = abstractChargeItem.growingWays;

        if (!(growingWays.equals("ALL") || growingWays.equals("MonsterGroup"))) {
            return;
        }

        List<MonsterGroup> monsterGroups = abstractChargeItem.monsterGroups;
        String internalName = event.getMobType().getInternalName();

        for (MonsterGroup monsterGroup : monsterGroups) {
            ConcurrentHashMap<String, Long> groupIdentify = monsterGroup.getGroupIdentify();

            Long aLong = groupIdentify.get(internalName);
            if (aLong == null) {
                continue;
            }

            ExperienceGiveContent experienceGiveContent = ChargingManager.giveChargeItemExp(itemInMainHand, aLong, player, abstractChargeItem);

            if (experienceGiveContent.newLevel > experienceGiveContent.preLevel){
                inventory.setItemInMainHand(experienceGiveContent.newItemStack);

                int heldItemSlot = inventory.getHeldItemSlot();

                ChargeItemLevelupEvent chargeItemLevelupEvent = new ChargeItemLevelupEvent(
                        abstractChargeItem,
                        experienceGiveContent.newLevel,
                        player,
                        experienceGiveContent.newItemStack,
                        heldItemSlot,
                        inventory);

                Bukkit.getServer().getPluginManager().callEvent(chargeItemLevelupEvent);

                if (!chargeItemLevelupEvent.isCancelled()){
                    ChargingManager.getLevelEventComponent(abstractChargeItem,
                            experienceGiveContent.newLevel,
                            player,
                            experienceGiveContent.newItemStack,
                            heldItemSlot,
                            inventory);
                }
            }else{
                inventory.setItemInMainHand(experienceGiveContent.newItemStack);
            }



            player.sendMessage(ColorTansfor.tansColor("&7[&c!&7] 成功吸收 " + event.getMob().getDisplayName() + " &b的能量"));
        }




    }
}
