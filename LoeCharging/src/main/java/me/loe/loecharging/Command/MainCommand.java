package me.loe.loecharging.Command;

import me.loe.loecharging.Command.Sub.*;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/12
 * @ClassInfo
 */
public class MainCommand implements CommandExecutor, TabCompleter {
    public MainCommand() {
        new ReloadSubCommand("reload", "重载插件").registerCommand();
        new ExpressionSubCommand("exptest", "测试表达式: <表达式id> <等级>").registerCommand();
        new DebugSubCommand("debug", "测试: <子项>").registerCommand();
        new OpenGuiSubCommand("open", "打开指定界面").registerCommand();
        new ShowLoresSubCommand("lores", "展示lores").registerCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b<------&cLoeCharging&b----->"));
            for (AbstractSubCommand subCommand : AbstractSubCommand.subCommands) {
                sender.sendMessage(ColorTansfor.tansColor("----> &c" + subCommand.cmd + ": " + subCommand.description));
            }
            return false;
        }

        ArrayList<AbstractSubCommand> subCommands = AbstractSubCommand.subCommands;

        for (AbstractSubCommand subCommand : subCommands) {
            if (subCommand.cmd.equals(args[0])){
                subCommand.onCommand(sender, args);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if (args.length == 1){
            for (AbstractSubCommand subCommand : AbstractSubCommand.subCommands) {
                arrayList.add(subCommand.cmd);
            }
        }else if (args.length == 2){
            for (AbstractSubCommand subCommand : AbstractSubCommand.subCommands) {
                if (subCommand.cmd.equals(args[0])){
                    List<String> strings = subCommand.onTabComplete(sender, args);
                    return strings == null ? arrayList : strings;
                }
            }
        }else {
            for (AbstractSubCommand subCommand : AbstractSubCommand.subCommands) {
                if (subCommand.cmd.equals(args[0])){
                    List<String> strings = subCommand.onTabComplete(sender, args);
                    return strings == null ? arrayList : strings;
                }
            }
        }


        return arrayList;
    }


}
