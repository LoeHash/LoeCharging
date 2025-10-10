package me.loe.loecharging.Utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.Vector;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/6/13
 * @ClassInfo
 */
public class ColorTansfor {
    public static String tansColor(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }

    public static List<String> tansColor(List<String> str){
        List<String> strings = new Vector<>();
        for (String s : str) {
            strings.add(ColorTansfor.tansColor(s));
        }
        return strings;
    }
}
