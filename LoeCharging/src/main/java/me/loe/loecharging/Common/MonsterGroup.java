package me.loe.loecharging.Common;

import me.loe.loecharging.Utils.ColorTansfor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/25
 * @ClassInfo
 */
public class MonsterGroup {
    private String internalName;
    private ConcurrentHashMap<String, Long> groupIdentify;

    public MonsterGroup(String internalName, ConcurrentHashMap<String, Long> groupIdentify) {
        this.internalName = internalName;
        this.groupIdentify = groupIdentify;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public ConcurrentHashMap<String, Long> getGroupIdentify() {
        return groupIdentify;
    }

    public void setGroupIdentify(ConcurrentHashMap<String, Long> groupIdentify) {
        this.groupIdentify = groupIdentify;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("&a内部名: &e").append(internalName).append("\n");
        sb.append("&a怪物标识:\n");

        if (groupIdentify.isEmpty()) {
            sb.append("  &7无\n");
        } else {
            for (Map.Entry<String, Long> entry : groupIdentify.entrySet()) {
                sb.append("  &6").append(entry.getKey()).append(": &e").append(entry.getValue()).append("\n");
            }
        }

        return ColorTansfor.tansColor(sb.toString());
    }
}
