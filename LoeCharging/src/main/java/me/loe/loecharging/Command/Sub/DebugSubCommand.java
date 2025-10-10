package me.loe.loecharging.Command.Sub;

import me.loe.loecharging.Command.AbstractSubCommand;
import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/26
 * @ClassInfo
 */
public class DebugSubCommand  extends AbstractSubCommand {
    public List<String> supportDebug = new Vector<>();

    public DebugSubCommand(String cmd, String description) {
        super(cmd, description);
        supportDebug.add("ChargeStone");
        supportDebug.add("MonsterGroup");
        supportDebug.add("DirectlyMonsterMapping");
        supportDebug.add("GlobalLevelEnum");
        supportDebug.add("GlobalLevelFormula");
        supportDebug.add("AbstractChargeItem");

    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())){
            return;
        }

        String subDebug = args[1];

        List<String> info = ChargingManager.showInfo(subDebug);

        sender.sendMessage(ColorTansfor.tansColor("&e----------- &c"+subDebug+" &e-----------"));

        for (String s : info) {
            sender.sendMessage(s);
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if(args.length == 2){
            return supportDebug;
        }

        return arrayList;
    }
}
