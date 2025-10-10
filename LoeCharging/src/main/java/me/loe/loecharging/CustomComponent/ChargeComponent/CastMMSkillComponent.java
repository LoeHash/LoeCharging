package me.loe.loecharging.CustomComponent.ChargeComponent;

import io.lumine.xikage.mythicmobs.skills.Skill;
import io.lumine.xikage.mythicmobs.skills.SkillManager;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import me.loe.loecharging.Common.AbstractChargeItem;
import me.loe.loecharging.LoeCharging;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/9/2
 * @ClassInfo
 */
public class CastMMSkillComponent extends AbstractComponent{
    public String internalName;

    public CastMMSkillComponent(String componentName, String internalName) {
        super(componentName);
    }

    @Override
    public boolean onCall(Player player, ItemStack stack, AbstractChargeItem aci, int slot, Inventory inventory) {


        return false;
    }
}
