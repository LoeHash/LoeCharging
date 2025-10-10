package me.loe.loecharging.Command.Sub;

import me.loe.loecharging.Command.AbstractSubCommand;
import me.loe.loecharging.Command.MainCommand;
import me.loe.loecharging.LoeCharging;
import me.loe.loecharging.Utils.ConfigUtils;
import me.loe.loecharging.Utils.Logger;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/25
 * @ClassInfo
 */
public class ReloadSubCommand extends AbstractSubCommand {

    public ReloadSubCommand(String cmd, String desc) {
        super(cmd,desc);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())){
            return;
        }
        long before = System.currentTimeMillis();
        LoeCharging.configUtils.reloadConfig();
        sender.sendMessage("重载成功! 用时: " + (System.currentTimeMillis() - before) + "ms");
    }


}
