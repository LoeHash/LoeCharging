package me.loe.loecharging.Utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/13
 * @ClassInfo
 */
public class Logger {
    public static  JavaPlugin ins = null;
    private static java.util.logging.Logger logger;
    public static ConcurrentHashMap<String,String> logType = new ConcurrentHashMap<>();

    public static void initLogger(JavaPlugin jp){
        ins = jp;
        logger = jp.getLogger();
        logType.put("G", "&7[&aGreat&7]");
        logType.put("B", "&7[&cBad&7]");
        logType.put("E", "&7[&4Error&7]");

    }

    public static void outInfo(String info){
        logger.info(ColorTansfor.tansColor(info));
    }

    public static void outInfo(String type, String info){
        type = type.toUpperCase();
        outInfo(ColorTansfor.tansColor(logType.get(type) + " " + info));
    }

    public static String getLogType(String type){
        return logType.get(type);
    }

}
