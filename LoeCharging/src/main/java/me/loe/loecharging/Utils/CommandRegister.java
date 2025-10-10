package me.loe.loecharging.Utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/12
 * @ClassInfo
 */
public class CommandRegister {

    public static void regCommand(JavaPlugin javaPlugin, CommandExecutor commandExecutor, String cmd){
        javaPlugin.getCommand(cmd).setExecutor(commandExecutor);
    }

}
