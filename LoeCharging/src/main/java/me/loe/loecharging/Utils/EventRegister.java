package me.loe.loecharging.Utils;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/12
 * @ClassInfo 注册事件
 */
public class EventRegister {
    public static void regEvent(JavaPlugin ins, Listener listener){
        ins.getServer().getPluginManager().registerEvents(listener,ins);
    }
}
