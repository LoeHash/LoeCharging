package me.loe.loecharging.Utils;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/7/28
 * @ClassInfo
 */
public class Info {


    public static void showInfo(JavaPlugin ins,char color){
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃                                                                                                          ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃  &"+color+"&l██╗      ██████╗ ███████╗██████╗ ███████╗ ██████╗ ██████╗ ███╗   ███╗██████╗  ██████╗ ███████╗███████╗  ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃  &"+color+"&l██║     ██╔═══██╗██╔════╝██╔══██╗██╔════╝██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔═══██╗██╔════╝██╔════╝  ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃  &"+color+"&l██║     ██║   ██║█████╗  ██║  ██║█████╗  ██║     ██║   ██║██╔████╔██║██████╔╝██║   ██║███████╗█████╗    ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃  &"+color+"&l██║     ██║   ██║██╔══╝  ██║  ██║██╔══╝  ██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██║   ██║╚════██║██╔══╝    ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃  &"+color+"&l███████╗╚██████╔╝███████╗██████╔╝███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ╚██████╔╝███████║███████╗  ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃  &"+color+"&l╚══════╝ ╚═════╝ ╚══════╝╚═════╝ ╚══════╝╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝       ╚═════╝ ╚══════╝╚══════╝  ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┃                                                                                                          ┃"));
        ins.getLogger().info(ColorTansfor.tansColor("&"+color+"&l┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"));
    }

}
