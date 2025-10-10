package me.loe.loecharging.Utils;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/9
 * @ClassInfo lore工具类
 */
public class LoreUtils {

    /**
     * 检查物品的Lore是否包含某个String
     * @param item  必须非原版物品
     * @param key   包含的key
     * @return      true包含, false否
     */
    public static boolean isLoreContainString(ItemStack item, String key){
        List<String> lore = item.getItemMeta().getLore();

        if (lore == null){
            return false;
        }

        for (String s : lore) {
            System.out.println(s);
            if (s.contains(key)){
                return true;
            }
        }

        return false;
    }

    public static int getLoreContainStringIndex(ItemStack item, String key){
        List<String> lore = item.getItemMeta().getLore();

        if (lore == null){
            return -1;
        }

        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(key)) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * @param by
     * @param str
     * @return
     */
    public static String cleanString(char by, String str){
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == by){
                i++;
            }else{
                stringBuilder.append(chars[i]);
            }
        }
        return stringBuilder.toString();
    }
}
