package me.loe.loecharging.Utils;


import com.googlecode.aviator.AviatorEvaluator;
import me.loe.loecharging.Command.Sub.OpenGuiSubCommand;
import me.loe.loecharging.Common.*;
import me.loe.loecharging.CustomComponent.ChargeComponent.*;
import me.loe.loecharging.LoeCharging;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtils {

    private LoeCharging ins;
    public File configFile;
    public FileConfiguration config;

    public ConfigUtils(LoeCharging ins) {
        this.ins = ins;
        config = ins.getConfig();
        reloadConfig();

    }

    public void reloadConfig(){
        ins.reloadConfig();
        config = ins.getConfig();
        ChargingManager.reload();
        loadConfig();
        OpenGuiSubCommand.reloadGui();
    }

    public void loadConfig() {
        File dataFolder = ins.getDataFolder();
        if (!dataFolder.exists()) {
            ins.saveDefaultConfig();
        }
        configFile = new File(dataFolder, "config.yml");


        loadConfigFiled();

    }

    /**
     * 加载配置项
     */
    public void loadConfigFiled(){
        if (loadChargeStones()) {
            Logger.outInfo("G"," &a充能物品 读取成功!");
        }else{
            Logger.outInfo("B"," &c充能物品 读取失败!");
        }

        if (loadMainItemMatch()) {
            Logger.outInfo("G"," &a主match 读取成功!");
        }else{
            Logger.outInfo("B"," &c主match 读取失败!");
        }

        if (loadMonsterGroups()) {
            Logger.outInfo("G"," &a怪物组 读取成功!");
        }else{
            Logger.outInfo("B"," &c怪物组 读取失败!");
        }

        if (loadGlobalLevelEnum()) {
            Logger.outInfo("G"," &a全局等级枚举 读取成功!");
        }else{
            Logger.outInfo("B"," &c全局等级枚举 读取失败!");
        }

        if (loadGlobalLevelFormula()) {
            Logger.outInfo("G"," &a全局等级公式 读取成功!");
        }else{
            Logger.outInfo("B"," &c全局等级公式 读取失败!");
        }

        if (loadCustomChargeItem()) {
            Logger.outInfo("G","&a抽象充能物品 读取成功!");
        }else{
            Logger.outInfo("B","&c抽象充能物品 读取失败!");
        }

        if (loadGuiComponent()){
            Logger.outInfo("G","&a抽象GUI 读取成功!");
        }else{
            Logger.outInfo("B","&c抽象GUI 读取失败!");
        }
    }

    private boolean loadChargeStones(){
        ConfigurationSection chargeItems = null;
        try {
            chargeItems = config.getConfigurationSection("ChargeStone");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Set<String> keys = chargeItems.getKeys(false);

        for (String key : keys) {
            try {
                ChargeStone chargeStone = new ChargeStone(key, ColorTansfor.tansColor(chargeItems.getString(key + ".name")), Long.parseLong(chargeItems.getString(key + ".value")));
                ChargingManager.chargeStone.add(chargeStone);
                ChargingManager.chargeStoneMatchNameHashMap.put(chargeStone.getMatchName(), chargeStone);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean loadMainItemMatch(){
        try {
            ChargingManager.mainItemMatch = config.getString("MainItemMatch");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean loadMonsterGroups(){
        ConfigurationSection monsterGroups = null;
        try {
            monsterGroups = config.getConfigurationSection("MonsterGroup");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Set<String> keys = monsterGroups.getKeys(false);

        for (String key : keys) {
            try {
                List<String> stringList = monsterGroups.getStringList(key);
                ConcurrentHashMap<String, Long> monsterMap = new ConcurrentHashMap<>();
                for (String tag : stringList) {
                    String[] split = tag.split(":");
                    //内部映射
                    monsterMap.put(split[0], Long.parseLong(split[1]));
                    //直接映射
                    ChargingManager.directlyMonsterMapping.put(split[0], Long.parseLong(split[1]));
                }
                ChargingManager.monsterGroups.put(key, new MonsterGroup(key, monsterMap));
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean loadGlobalLevelEnum(){
        List<String> lvlEnum = null;
        try {
            lvlEnum = config.getStringList("Global-Level-Enum");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        for (String lvl : lvlEnum) {
            String[] split = lvl.split(":");
            try {
                ChargingManager.globalLevelEnum.put(Long.parseLong(split[0]), Long.parseLong(split[1]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private boolean loadGlobalLevelFormula(){
        List<String> lvlFormulas = null;
        try {
            lvlFormulas = config.getStringList("Global-Level-Formula");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        for (String lvlFormula : lvlFormulas) {
            String[] split = lvlFormula.split(":");
            try {
                ChargingManager.globalLevelFormula.put(split[0], AviatorEvaluator.compile(split[1], true));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private boolean loadCustomChargeItem(){
        ConfigurationSection customItemSec = null;
        try {
            customItemSec = config.getConfigurationSection("Charging-Item");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Set<String> keys = customItemSec.getKeys(false);

        for (String key : keys) {
            try {
                //获取匹配名
                String matchName = ColorTansfor.tansColor(customItemSec.getString(key + ".MatchName"));
                //获取最大等级
                String maxLevel = customItemSec.getString(key + ".MaxLevel");
                //获取事件列表
                ConcurrentHashMap<Long, Vector<AbstractComponent>> levelEvents = new ConcurrentHashMap<>();

                ConfigurationSection lvlEventSec = customItemSec.getConfigurationSection(key + ".LevelEvents");
                Set<String> lvlEventSecKeys = lvlEventSec.getKeys(false);
                for (String lvlEventSecKey : lvlEventSecKeys) {
                    List<String> eventsList = lvlEventSec.getStringList(lvlEventSecKey);
                    Vector<AbstractComponent> abstractComponents = new Vector<>();

                    for (String event : eventsList) {
                        String[] split = event.split("#");
                        switch (split[0]) {
                            case "[command]":
                                abstractComponents.add(new CommandComponent("[command]", split[1]));
                                break;
                            case "[replace]":
                                abstractComponents.add(new ReplaceComponent("[replace]", split[1], split[2]));
                                break;
                            case "[delete]":
                                abstractComponents.add(new DeleteComponent("[delete]"));
                                break;
                            case "[playsound]":
                                abstractComponents.add(new PlaySoundComponent("[playsound]", split[1], split[2],split[3]));
                                break;
                        }
                    }

                    levelEvents.put(Long.valueOf(lvlEventSecKey), abstractComponents);
                }

                //成长方式
                String growingWay = customItemSec.getString(key + ".Growing-Ways");
                Vector<MonsterGroup> monsterGroups = new Vector<>();

                //获取升级的怪物组
                if (growingWay.equals("ALL") || growingWay.equals("MonsterGroup")){
                    List<String> monsterGroup = customItemSec.getStringList(key + ".MonsterGroup");
                    for (String monster : monsterGroup) {
                        monsterGroups.add(ChargingManager.monsterGroups.get(monster));
                    }
                }

                String experience = customItemSec.getString(key + ".Experience");
                String formulaType = null;
                if (experience.equals("Formula")){
                    formulaType = customItemSec.getString(key + ".Formula-Type");
                }

                List<String> changeLores = customItemSec.getStringList(key + ".Change-Lore");
                String checkSlot = customItemSec.getString(key + ".Check-Slot");

                AbstractChargeItem abstractChargeItem = new AbstractChargeItem(
                        key, Long.parseLong(maxLevel),
                        levelEvents,
                        growingWay,
                        monsterGroups,
                        experience,
                        formulaType,
                        changeLores,
                        checkSlot,
                        matchName
                );

                abstractChargeItem.varMappingInAll.put("current_item_name", matchName);
                abstractChargeItem.varMappingInAll.put("current_item_maxlevel", maxLevel);


                //抽象物品缓存
                ChargingManager.cachedInternalNameAbstractChargeItem.put(key, abstractChargeItem);
                //实际itemStack缓存
                ChargingManager.cachedMatchNameAci.put(ColorTansfor.tansColor(abstractChargeItem.matchName), abstractChargeItem);

            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
        
    }

    public boolean loadGuiComponent(){
        ConfigurationSection gui = config.getConfigurationSection("GUI");
        Set<String> keys = gui.getKeys(false);

        for (String key : keys) {
            int index = 0;
            ConfigurationSection specialGui = gui.createSection(key);
            String title = ColorTansfor.tansColor(specialGui.getString("标题"));
            List<String> layout = specialGui.getStringList("布局");
            ConcurrentHashMap<Character, ItemStack> charToItemMap = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, Integer> wayToIndex = new ConcurrentHashMap<>();
            ItemStack ensureItem = null;
            for (String layoutInfo : layout) {

                char[] chars = layoutInfo.toCharArray();
                for (char aChar : chars) {

                    if (!charToItemMap.containsKey(aChar)){
                        ConfigurationSection itemSec = specialGui.getConfigurationSection("物品集." + aChar);

                        String toUse = itemSec.getString("作用");
                        switch (toUse) {
                            case "升级物品":
                            case "充能物品":
                            case "被继承物品":
                            case "继承物品":
                                wayToIndex.put(toUse, index);
                                break;
                        }

                        ItemStack itemStack;

                        int id = itemSec.getInt("Id");

                        if (id == 0){
                            itemStack = new ItemStack(Material.AIR);

                        }else{
                            int data = itemSec.getInt("Data");
                            String display = ColorTansfor.tansColor(itemSec.getString("显示名"));
                            List<String> lore = ColorTansfor.tansColor(itemSec.getStringList("Lore"));

                            itemStack = new ItemStack(
                                    id,1,(short) data);
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName(display);
                            itemMeta.setLore(lore);
                            itemStack.setItemMeta(itemMeta);

                            if (toUse.equals("确认")){
                                ensureItem = itemStack;
                            }

                        }
                        charToItemMap.put(aChar,itemStack);
                    }
                    index++;
                }
            }

            GuiComponent guiComponent = new GuiComponent(title, layout, charToItemMap, wayToIndex,key, index);
            guiComponent.ensureItem = ensureItem;
            ChargingManager.guiComponentMapping.put(key, guiComponent);
            ChargingManager.guiComponentTitleMapping.put(title, guiComponent);

        }

        return true;
    }

}
