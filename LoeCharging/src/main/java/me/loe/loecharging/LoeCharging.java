package me.loe.loecharging;

import io.lumine.xikage.mythicmobs.MythicMobs;
import me.loe.loecharging.Command.MainCommand;
import me.loe.loecharging.Events.InventoryEvents;
import me.loe.loecharging.Events.MythicMobsDeath;
import me.loe.loecharging.Utils.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoeCharging extends JavaPlugin {
    public static ConfigUtils configUtils;
    public static EconomyUtils economyUtils;
    public static LoeCharging inst;
    public static MythicMobsSkillCaster msc;

    @Override
    public void onEnable() {
        inst = this;
        Logger.initLogger(this);

        configUtils = new ConfigUtils(this);
        economyUtils = new EconomyUtils(this);
        msc = new MythicMobsSkillCaster();
//        Metrics metrics = new Metrics(this, 26909);


        //注册命令
        CommandRegister.regCommand(this, new MainCommand(), "lcg");

        //注册事件
        EventRegister.regEvent(this, new MythicMobsDeath());
        EventRegister.regEvent(this, new InventoryEvents());

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
