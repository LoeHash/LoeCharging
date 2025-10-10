package me.loe.loecharging.Command.Sub;

import me.loe.loecharging.Command.AbstractSubCommand;
import me.loe.loecharging.Common.ChargingManager;
import me.loe.loecharging.Common.GUI.AbstractGui;
import me.loe.loecharging.Common.GUI.ExtendGUI;
import me.loe.loecharging.Common.GUI.LevelUpGui;
import me.loe.loecharging.Utils.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/27
 * @ClassInfo
 */
public class OpenGuiSubCommand extends AbstractSubCommand {
    public static Vector<String> guis = new Vector<>();

    public OpenGuiSubCommand(String cmd, String description) {
        super(cmd, description);
        reloadGui();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            return;
        }

        if (args.length < 2){
            sender.sendMessage("请指定一个界面!");
            return;
        }

        String name = args[1];
        if (guis.contains(name)){
            if (!(sender instanceof Player)){
                Logger.outInfo("B","&c只能由玩家使用!");
                return;
            }
            Player player = (Player) sender;

            AbstractGui abstractGui = ChargingManager.subGuiMapping.get(name);
            player.openInventory(abstractGui.initGui(player));
        }else{
            sender.sendMessage("没有此界面!");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return guis;
        }
        return null;
    }

    public static void reloadGui(){
        Enumeration<String> keys = ChargingManager.guiComponentMapping.keys();
        while (keys.hasMoreElements()) {
            String s = keys.nextElement();
            guis.add(s);
        }

        //注册
        LevelUpGui levelUpGui = new LevelUpGui("升级界面");
        ExtendGUI extendGUI = new ExtendGUI("继承界面");

        ChargingManager.subGuiMapping.put(levelUpGui.internalName, levelUpGui);
        ChargingManager.subGuiDisplayNameMapping.put(levelUpGui.guiComponent.title, levelUpGui);

        ChargingManager.subGuiMapping.put(extendGUI.internalName, extendGUI);
        ChargingManager.subGuiDisplayNameMapping.put(extendGUI.guiComponent.title, extendGUI);

    }
}
