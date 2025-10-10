package me.loe.loecharging.Common;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import me.loe.loecharging.Common.GUI.AbstractGui;
import me.loe.loecharging.CustomComponent.ChargeComponent.AbstractComponent;
import me.loe.loecharging.Utils.ColorTansfor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/25
 * @ClassInfo
 */
public class ChargingManager {

    //充能石集合
    public static Vector<ChargeStone> chargeStone = new Vector<>();
    //充能石mapping
    public static HashMap<String, ChargeStone> chargeStoneMatchNameHashMap = new HashMap<>();
    //可充能的物品标识
    public static String mainItemMatch;
    //怪物组
    public static ConcurrentHashMap<String,MonsterGroup>  monsterGroups = new ConcurrentHashMap<>();
    //怪物组的怪物与经验的直接映射
    public static ConcurrentHashMap<String, Long> directlyMonsterMapping = new ConcurrentHashMap<>();
    //全局等级枚举映射
    public static ConcurrentHashMap<Long, Long> globalLevelEnum = new ConcurrentHashMap<>();
    //全局等级公式映射
    public static ConcurrentHashMap<String, Expression> globalLevelFormula = new ConcurrentHashMap<>();
    //抽象充能物品缓存
    public static ConcurrentHashMap<String, AbstractChargeItem> cachedInternalNameAbstractChargeItem = new ConcurrentHashMap<>();
    //具体物品的缓存
    public static ConcurrentHashMap<String, AbstractChargeItem> cachedMatchNameAci = new ConcurrentHashMap<>();
    //gui抽象组件
    public static ConcurrentHashMap<String, GuiComponent> guiComponentMapping = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, GuiComponent> guiComponentTitleMapping = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, AbstractGui> subGuiMapping = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, AbstractGui> subGuiDisplayNameMapping = new ConcurrentHashMap<>();
    //正则匹配
    public static Pattern pattern = Pattern.compile("(&[0-9a-f])*[^0-9]*([1-9]\\d*|0)(?=[a-zA-Z]|\\s|$)");
    //原生的mainItemMatch索引
    public static ConcurrentHashMap<String, Integer> nativeItemIndexMapping = new ConcurrentHashMap<>();

    public static List<String> showInfo(String subDebug) {
        List<String> result = new ArrayList<>();

        switch (subDebug.toLowerCase()) {
            case "chargestone":
                result.add(ColorTansfor.tansColor("&a===== 充能石信息 ====="));
                if (chargeStone.isEmpty()) {
                    result.add(ColorTansfor.tansColor("&7无充能石数据"));
                } else {
                    for (ChargeStone stone : chargeStone) {
                        result.add(stone.toString());
                        result.add(""); // 空行分隔
                    }
                }
                break;

            case "monstergroup":
                result.add(ColorTansfor.tansColor("&a===== 怪物组信息 ====="));
                if (monsterGroups.isEmpty()) {
                    result.add(ColorTansfor.tansColor("&7无怪物组数据"));
                } else {
                    for (MonsterGroup group : monsterGroups.values()) {
                        result.add(group.toString());
                        result.add(""); // 空行分隔
                    }
                }
                break;

            case "directlymonstermapping":
                result.add(ColorTansfor.tansColor("&a===== 怪物经验映射 ====="));
                if (directlyMonsterMapping.isEmpty()) {
                    result.add(ColorTansfor.tansColor("&7无怪物经验映射数据"));
                } else {
                    for (ConcurrentHashMap.Entry<String, Long> entry : directlyMonsterMapping.entrySet()) {
                        result.add(ColorTansfor.tansColor("&6" + entry.getKey() + ": &e" + entry.getValue()));
                    }
                }
                break;

            case "globallevelenum":
                result.add(ColorTansfor.tansColor("&a===== 全局等级枚举 ====="));
                if (globalLevelEnum.isEmpty()) {
                    result.add(ColorTansfor.tansColor("&7无全局等级枚举数据"));
                } else {
                    for (ConcurrentHashMap.Entry<Long, Long> entry : globalLevelEnum.entrySet()) {
                        result.add(ColorTansfor.tansColor("&6等级 " + entry.getKey() + ": &e" + entry.getValue()));
                    }
                }
                break;

            case "globallevelformula":
                result.add(ColorTansfor.tansColor("&a===== 全局等级公式 ====="));
                if (globalLevelFormula.isEmpty()) {
                    result.add(ColorTansfor.tansColor("&7无全局等级公式数据"));
                } else {
                    for (ConcurrentHashMap.Entry<String, Expression> entry : globalLevelFormula.entrySet()) {
                        result.add(ColorTansfor.tansColor("&6" + entry.getKey() + ": &e" + entry.getValue().toString()));
                    }
                }
                break;

            case "abstractchargeitem":
                result.add(ColorTansfor.tansColor("&a===== 抽象充能物品 ====="));
                if (cachedInternalNameAbstractChargeItem.isEmpty()) {
                    result.add(ColorTansfor.tansColor("&7无抽象充能物品数据"));
                } else {
                    for (AbstractChargeItem item : cachedInternalNameAbstractChargeItem.values()) {
                        result.add(item.toString());
                        result.add(""); // 空行分隔
                    }
                }
                break;

            case "mainitemmatch":
                result.add(ColorTansfor.tansColor("&a===== 主物品匹配 ====="));
                result.add(ColorTansfor.tansColor("&6主物品匹配: &e" + (mainItemMatch != null ? mainItemMatch : "未设置")));
                break;

            default:
                result.add(ColorTansfor.tansColor("&c未知的调试类型: " + subDebug));
                result.add(ColorTansfor.tansColor("&a可用类型: chargestone, monstergroup, directlymonstermapping, globallevelenum, globallevelformula, abstractchargeitem, mainitemmatch"));
                break;
        }

        return result;
    }

    /**
     * 获取充能物品的相应变量
     * @param levelUpItem
     * @return
     */
    public static ConcurrentHashMap<String, Object> solveItemVar(ItemStack levelUpItem){
        ConcurrentHashMap<String, Object> stringLongConcurrentHashMap = new ConcurrentHashMap<>();

        ItemMeta itemMeta = levelUpItem.getItemMeta();

        List<String> lore = itemMeta.getLore();

        //检查是否是第一次升级
        for (String s : lore) {
            if (s.contains(mainItemMatch)) {
                stringLongConcurrentHashMap.put("current_item_level", 0L);
                break;
            }
        }
        AbstractChargeItem abstractChargeItem = cachedMatchNameAci.get(itemMeta.getDisplayName());

        //如果current_item_level键所对应的值并不是null
        //说明是第一次升级
        if (stringLongConcurrentHashMap.get("current_item_level") == null){
            for (String s : lore) {
                if (s.contains("当前等级")){
                    Matcher matcher = pattern.matcher(s);

                    if (matcher.find()) {
                        String level = matcher.group(2);
                        stringLongConcurrentHashMap.put("current_item_level", Long.parseLong(level));
                    }
                }else if (s.contains("当前经验")){
                    Matcher matcher = pattern.matcher(s);

                    if (matcher.find()) {
                        String exp = matcher.group(2);
                        stringLongConcurrentHashMap.put("current_item_exp", Long.parseLong(exp));
                    }
                }
            }
        }else {
            stringLongConcurrentHashMap.put("current_item_exp", 0L);
        }


        //解析一下次升级需要的经验值
        switch (abstractChargeItem.experience) {
            case "Formula":
                Expression expression = globalLevelFormula.get(abstractChargeItem.formula);
                Long execute = (Long) expression.execute(stringLongConcurrentHashMap);
                stringLongConcurrentHashMap.put("current_item_nextexp", execute);
                break;
            case "Enum":
                stringLongConcurrentHashMap.put("current_item_nextexp", Long.parseLong(String.valueOf(globalLevelEnum.get((Long) stringLongConcurrentHashMap.get("current_item_level") + 1))));
                break;
        }


        return stringLongConcurrentHashMap;
    }


    public static long getAbsChargeItemNextExp(AbstractChargeItem abstractChargeItem, ConcurrentHashMap<String, Object> env){
        //解析一下次升级需要的经验值
        switch (abstractChargeItem.experience) {
            case "Formula":
                Expression expression = globalLevelFormula.get(abstractChargeItem.formula);

                return (Long) expression.execute(env);
            case "Enum":
                return Long.parseLong(String.valueOf(globalLevelEnum.get((Long) env.get("current_item_level") + 1)));
            default:
                return -1L;
        }
    }

    public static int getMainMatcherLoreIndex(List<String> levelUpItemMetaLore){
        for (int i = 0; i < levelUpItemMetaLore.size(); i++) {
            if (levelUpItemMetaLore.get(i).contains(ChargingManager.mainItemMatch)){
                return i;
            }
        }

        return -1;
    }

    public static AbstractComponent getLevelEventComponent(AbstractChargeItem aci,
                                                           long level,
                                                           Player player,
                                                           ItemStack itemStack,
                                                           int slot,
                                                           Inventory inventory){
        //获取等级事件集合
        ConcurrentHashMap<Long, Vector<AbstractComponent>> levelEvents = aci.levelEvents;

        if (levelEvents == null){
            return null;
        }

        //组件
        Vector<AbstractComponent> abstractComponents = levelEvents.get(level);

        if (abstractComponents == null){
            return null;
        }

        for (AbstractComponent abstractComponent : abstractComponents) {
            if (abstractComponent == null){
                return null;
            }

            //调用组件的处理方法
            abstractComponent.onCall(player, itemStack, aci, slot, inventory);
            System.out.println("test...");
        }

        return null;
    }


    public static String handleInternalPlaceHolder(AbstractChargeItem aci, String str){
        ConcurrentHashMap<String, String> varMappingInAll = aci.varMappingInAll;

        char[] chars = str.toCharArray();
        int[] leftIndexArr = new int[chars.length];
        int[] rightIndexArr = new int[chars.length];
        int leftIndex= 0;
        int rightIndex = 0;
        int leftCount = 0;
        int rightCount = 0;
        String replace = str;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '<'){
                leftIndexArr[leftIndex++] = i;
                leftCount++;
            }else if (chars[i] == '>'){
                rightIndexArr[rightIndex++] = i;
                rightCount++;
            }
        }

        if (leftCount != rightCount){
            return null;
        }

        //leftCount or rightCount is all right
        for (int i = 0; i < leftCount; i++) {
            String replaceStr = replace.substring(leftIndexArr[i], rightIndexArr[i] + 1);

            String substring = replaceStr.substring(1, replaceStr.length() - 1);

            str = str.replace(replaceStr, varMappingInAll.get(substring));

        }

        return str;

    }
    
    public static ExperienceGiveContent giveChargeItemExp(ItemStack chargeItem, long provideEnergy, Player player, AbstractChargeItem aci){

        //获取升级物品的变量
        ConcurrentHashMap<String, Object> varHashMap = ChargingManager.solveItemVar(chargeItem);

        return giveChargeItemExp(chargeItem, provideEnergy, player, aci, varHashMap);
    }

    public static ExperienceGiveContent giveChargeItemExp(ItemStack chargeItem, long provideEnergy, Player player, AbstractChargeItem aci
    , ConcurrentHashMap<String, Object> varHashMap){
        ExperienceGiveContent experienceGiveContent = new ExperienceGiveContent(chargeItem, 0, 0);

        ItemMeta chargeItemItemMeta = chargeItem.getItemMeta();

        //下一级需要的经验值
        Long pre_item_level = (Long) varHashMap.get("current_item_level");

        if (pre_item_level >= ChargingManager.globalLevelEnum.size()
                && aci.experience.equals("Enum")){
            experienceGiveContent.newLevel = pre_item_level;
            experienceGiveContent.preLevel = pre_item_level;
            player.sendMessage(ColorTansfor.tansColor("&c当前物品已经满级"));
            return experienceGiveContent;
        }

        if (pre_item_level >= aci.maxLevel){
            experienceGiveContent.newLevel = pre_item_level;
            experienceGiveContent.preLevel = pre_item_level;
            player.sendMessage(ColorTansfor.tansColor("&c当前物品已经满级"));
            return experienceGiveContent;
        }

        experienceGiveContent.preLevel = pre_item_level;

        Long current_item_nextexp = (Long) varHashMap.get("current_item_nextexp");
        Long current_item_exp = (Long) varHashMap.get("current_item_exp");

        //如果当前物品的经验值+充能石提供的能量大于升级到下一级需要的经验值
        if (current_item_exp + provideEnergy >= current_item_nextexp){
            long current_item_level = (Long) varHashMap.get("current_item_level") + 1;
            varHashMap.put("current_item_exp", 0);
            varHashMap.put("current_item_level", current_item_level);
            experienceGiveContent.newLevel = current_item_level;

            //更新等级后, 再获取当前新等级升级到下一级所需的经验值
            varHashMap.put("current_item_nextexp", ChargingManager.getAbsChargeItemNextExp(aci, varHashMap));
        }else{
            varHashMap.put("current_item_exp", ((Number) varHashMap.get("current_item_exp")).longValue() + provideEnergy);
            experienceGiveContent.newLevel = pre_item_level;
        }


        //获取要改变的最初lore
        List<String> changeLore = aci.changeLore;
        Vector<String> finalLores = new Vector<>(changeLore);

        for (int i = 0; i < changeLore.size(); i++) {
            String s = changeLore.get(i);

            //获取公式起始与结束符
            int startIndex= s.indexOf("<");
            int endIndex = s.indexOf(">");

            if (startIndex == -1 || endIndex == -1 || (endIndex < startIndex)){
                throw new NumberFormatException("非法!");
            }

            //将公式截取出来:
            String goingToReplaceString = s.substring(startIndex, endIndex + 1);
            String finalString = goingToReplaceString.substring(1, goingToReplaceString.length() - 1);

            Expression compile = AviatorEvaluator.compile(finalString);
            Long execute = ((Number) compile.execute(varHashMap)).longValue();

            //接下来将计算好的值重新拼接
            //先匹配数学表达式

            String finalStr = s.replace(goingToReplaceString, execute + "");


            finalLores.set(i, ColorTansfor.tansColor(finalStr));
        }

        //最后把lore全部设置到原lore中
        List<String> levelUpItemMetaLore = chargeItemItemMeta.getLore();

        //检查主匹配词条的索引
        int index = ChargingManager.getMainMatcherLoreIndex(levelUpItemMetaLore);

        if (index != -1){
            ChargingManager.nativeItemIndexMapping.put(aci.matchName, index);
            levelUpItemMetaLore.remove(index);
            levelUpItemMetaLore.addAll(index, finalLores);
        }else{
            System.out.println(chargeItemItemMeta.getDisplayName() + "无mainMatch");
            Integer targetIndx = ChargingManager.nativeItemIndexMapping.get(aci.matchName);
            System.out.println(targetIndx);
            if (targetIndx == null) {
                System.out.println("null!");
                for (int i = 0; i < levelUpItemMetaLore.size(); i++) {
                    if (levelUpItemMetaLore.get(i).contains("下一级所需经验")){
                        targetIndx = i - changeLore.size() + 1;
                        System.out.println(i);
                        System.out.println(changeLore.size());
                        ChargingManager.nativeItemIndexMapping.put(aci.matchName, targetIndx);
                    }
                }
                //如果还是等于null
                if (targetIndx == null){
                    targetIndx = -1;
                }
            }


            //先删除原来的lore
            int size = aci.changeLore.size();
            for (int i = 0; i < size; i++) {
                levelUpItemMetaLore.remove(targetIndx.intValue());
            }
            //再进行添加
            levelUpItemMetaLore.addAll(targetIndx, finalLores);
        }

        //收尾工作
        chargeItemItemMeta.setLore(levelUpItemMetaLore);
        chargeItem.setItemMeta(chargeItemItemMeta);

        experienceGiveContent.newItemStack = chargeItem;

        return experienceGiveContent;
    }

    public static void reload() {
        // 清空所有集合
        clearAllData();

    }

    /**
     * 清空所有数据集合
     */
    public static void clearAllData() {
        chargeStone.clear();
        chargeStoneMatchNameHashMap.clear();
        monsterGroups.clear();
        directlyMonsterMapping.clear();
        globalLevelEnum.clear();
        globalLevelFormula.clear();
        cachedInternalNameAbstractChargeItem.clear();
        cachedMatchNameAci.clear();
        guiComponentMapping.clear();
        guiComponentTitleMapping.clear();
        subGuiMapping.clear();
        subGuiDisplayNameMapping.clear();

        // 重置字符串变量
        mainItemMatch = null;
    }

}
