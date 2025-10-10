package me.loe.loecharging.Command.Sub;

import me.loe.loecharging.Command.AbstractSubCommand;
import me.loe.loecharging.Utils.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/9/8
 * @ClassInfo
 */
public class ShowLoresSubCommand extends AbstractSubCommand {
    public ShowLoresSubCommand(String cmd, String description) {
        super(cmd, description);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (checkPermission(sender)) {
            sender.sendMessage("you can't do this!");
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack itemInHand = player.getItemInHand();

            if (!itemInHand.hasItemMeta()){
                return;
            }

            ItemMeta itemMeta = itemInHand.getItemMeta();
            for (String s : itemMeta.getLore()) {
                Logger.outInfo(s);
            }
        }
    }
}
