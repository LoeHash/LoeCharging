package me.loe.loecharging.Common;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/27
 * @ClassInfo
 */
public class GuiComponent {
    public String title;
    public int size;
    public List<String> layout;
    public ConcurrentHashMap<Character, ItemStack> itemMap;
    public ItemStack ensureItem;
    public ConcurrentHashMap<String, Integer> toUseIndexMapping;
    public String internalName;

    public GuiComponent(String title,
                        List<String> layout,
                        ConcurrentHashMap<Character, ItemStack> itemMap,
                        ConcurrentHashMap<String, Integer> toUseIndexMapping,
                        String internalName, int size) {
        this.title = title;
        this.layout = layout;
        this.itemMap = itemMap;
        this.toUseIndexMapping = toUseIndexMapping;
        this.internalName = internalName;
        this.size = size;
    }


}
