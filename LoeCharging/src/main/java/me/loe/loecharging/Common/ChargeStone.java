package me.loe.loecharging.Common;

import me.loe.loecharging.Utils.ColorTansfor;

/**
 * @author Loe.
 * @project Plugin
 * @date 2025/8/25
 * @ClassInfo
 */
public class ChargeStone {
    private String internalName;
    private String matchName;
    private long provideEnergy;

    public ChargeStone(String internalName, String matchName, long provideEnergy) {
        this.internalName = internalName;
        this.matchName = matchName;
        this.provideEnergy = provideEnergy;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public long getProvideEnergy() {
        return provideEnergy;
    }

    public void setProvideEnergy(long provideEnergy) {
        this.provideEnergy = provideEnergy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("&a内部名: &e").append(internalName).append("\n");
        sb.append("&a匹配名: &e").append(matchName).append("\n");
        sb.append("&a提供能量: &e").append(provideEnergy);

        return ColorTansfor.tansColor(sb.toString());
    }
}
