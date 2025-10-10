package me.loe.loecharging.Command.Sub;

import com.googlecode.aviator.Expression;
import me.loe.loecharging.Command.AbstractSubCommand;
import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Utils.Logger;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/25
 * @ClassInfo
 */
public class ExpressionSubCommand extends AbstractSubCommand {

    //内部变量列表维护
    enum InternalVar{

        Current_Item_Level("current_item_level");

        InternalVar(String current_item_level) {

        }
    }


    public ExpressionSubCommand(String cmd, String desc) {
        super(cmd,desc);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())){
            return;
        }

        if (args.length != 3) {
            sender.sendMessage(super.getDescription());
            return;
        }

        String expFlag = args[1];
        String level = args[2];

        long formulaExperience = getFormulaExperience(Long.parseLong(level), expFlag);

        if (formulaExperience < 0){
            return;
        }

        sender.sendMessage(formulaExperience+"");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        Enumeration<String> keys = ChargingManager.globalLevelFormula.keys();
        while (keys.hasMoreElements()) {
            list.add(keys.nextElement());
        }
        return list;
    }

    public long getFormulaExperience(long level, String expFlag){
        HashMap<String, Object> env = new HashMap<>();
        try {
            env.put("current_item_level", level);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Logger.outInfo("E", "数值类型错误!");
            return -1;
        }

        Expression expression = ChargingManager.globalLevelFormula.get(expFlag);
        Long finalLevel = (Long) expression.execute(env);
        env = null;
        return finalLevel;
    }

}
