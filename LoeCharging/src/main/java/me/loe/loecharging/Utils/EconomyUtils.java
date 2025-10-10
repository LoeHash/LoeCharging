package me.loe.loecharging.Utils;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/6/13
 * @ClassInfo
 */
public class EconomyUtils {
    private static  Economy econ = null;
    private static  Permission perms = null;
    private static  Chat chat = null;

    public EconomyUtils(JavaPlugin javaPlugin) {
        if (!setupEconomy(javaPlugin) ) {
            javaPlugin.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", javaPlugin.getDescription().getName()));
            javaPlugin.getServer().getPluginManager().disablePlugin(javaPlugin);
        }
    }

    private boolean setupEconomy(JavaPlugin javaPlugin) {
        if (javaPlugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = javaPlugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEcon() {
        return econ;
    }
}
