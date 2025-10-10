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
 * @date 2025/9/1
 * @ClassInfo
 */
public class PlaySoundComponent extends AbstractComponent{
    public String sound;
    public String volume;
    public String pitch;

    public PlaySoundComponent(String componentName, String sound, String volume,String pitch) {
        super(componentName);
        this.sound = sound;
        this.volume = volume;
        this.pitch =pitch;
    }

    @Override
    public boolean onCall(Player player, ItemStack stack, AbstractChargeItem aci, int slot, Inventory view) {

        player.playSound(player.getLocation(), this.sound, Float.parseFloat(this.volume),Float.parseFloat(this.pitch) );

        return false;
    }
}
