package me.loe.loecharging.Command;

import me.loe.loecharging.LoeCharging;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/25
 * @ClassInfo
 */
public abstract class AbstractSubCommand {
    String cmd = "";
    String description = "";
    public static ArrayList<AbstractSubCommand> subCommands = new ArrayList<>();


    public AbstractSubCommand(String cmd, String description) {
        this.cmd = cmd;
        this.description = description;
    }

    //抽象onCommand
    public abstract void onCommand(CommandSender sender, String[] args);

    //子类重写
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    public String getPermission() {
        return LoeCharging.inst.getName() + "." + cmd + ".use";
    }

    public void registerCommand() {
        subCommands.add(this);
    }

    public String getDescription() {
        return description;
    }

    public String getCmd() {
        return cmd;
    }

    public boolean checkPermission(CommandSender sender){
        return !sender.hasPermission(this.getPermission());
    }
}
