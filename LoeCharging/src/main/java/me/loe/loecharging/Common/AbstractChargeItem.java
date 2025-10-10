package me.loe.loecharging.Common;

import me.loe.loecharging.CustomComponent.ChargeComponent.AbstractComponent;
import me.loe.loecharging.Utils.ColorTansfor;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/26
 * @ClassInfo
 */
public class AbstractChargeItem {
    public String internalName;
    public String matchName;
    public long maxLevel;
    public ConcurrentHashMap<Long, Vector<AbstractComponent>> levelEvents;
    public String growingWays;
    public List<MonsterGroup> monsterGroups;
    public String experience;
    public String formula;
    public List<String> changeLore;
    public String checkSlot;

    public ConcurrentHashMap<String, String> varMappingInAll = new ConcurrentHashMap<>();

    /**
     *
     * @param internalName  内部名
     * @param maxLevel      最大等级
     * @param levelEvents   等级事件
     * @param growingWays   成长方式
     * @param monsterGroups 怪物组
     * @param experience    经验方式
     * @param formula       公式
     * @param changeLore    要改变的lore
     * @param checkSlot     检查的槽位
     */
    public AbstractChargeItem(String internalName, long maxLevel, ConcurrentHashMap<Long, Vector<AbstractComponent>> levelEvents, String growingWays, List<MonsterGroup> monsterGroups, String experience, String formula, List<String> changeLore, String checkSlot, String matchName) {
        this.internalName = internalName;
        this.maxLevel = maxLevel;
        this.levelEvents = levelEvents;
        this.growingWays = growingWays;
        this.monsterGroups = monsterGroups;
        this.experience = experience;
        this.formula = formula;
        this.changeLore = changeLore;
        this.checkSlot = checkSlot;
        this.matchName = matchName;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // 基础信息
        sb.append("&a内部名: &e").append(internalName).append("\n");
        sb.append("&a匹配名: &e").append(matchName).append("\n");
        sb.append("&a最大等级: &e").append(maxLevel).append("\n");
        sb.append("&a成长方式: &e").append(growingWays).append("\n");
        sb.append("&a经验公式: &e").append(experience).append("\n");
        sb.append("&a属性公式: &e").append(formula).append("\n");
        sb.append("&a检查槽位: &e").append(checkSlot).append("\n");

        // 等级事件
        sb.append("&a等级事件:\n");
        if (levelEvents.isEmpty()) {
            sb.append("  &7无\n");
        } else {
            for (Map.Entry<Long, Vector<AbstractComponent>> entry : levelEvents.entrySet()) {
                sb.append("  &6等级 ").append(entry.getKey()).append(": &e");
                for (AbstractComponent component : entry.getValue()) {
                    sb.append(component.toString()).append(" ");
                }
                sb.append("\n");
            }
        }

        // 怪物组
        sb.append("&a怪物组:\n");
        if (monsterGroups == null || monsterGroups.isEmpty()) {
            sb.append("  &7无\n");
        } else {
            for (MonsterGroup group : monsterGroups) {
                sb.append("  &e").append(group.toString()).append("\n");
            }
        }

        // 要改变的Lore
        sb.append("&a要改变的Lore:\n");
        if (changeLore == null || changeLore.isEmpty()) {
            sb.append("  &7无\n");
        } else {
            for (String lore : changeLore) {
                sb.append("  &e").append(lore).append("\n");
            }
        }

        return ColorTansfor.tansColor(sb.toString());
    }
}

