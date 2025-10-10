package me.loe.loecharging.Utils;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.skills.Skill;
import io.lumine.xikage.mythicmobs.skills.SkillCaster;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.SkillTrigger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Optional;

public class MythicMobsSkillCaster {
    private final MythicMobs mythicMobs;
    private final MobManager mobManager;

    public MythicMobsSkillCaster() {
        Plugin mythicMobsPlugin = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs");
        if (mythicMobsPlugin instanceof MythicMobs) {
            this.mythicMobs = (MythicMobs) mythicMobsPlugin;
            this.mobManager = mythicMobs.getMobManager();
        } else {
            throw new RuntimeException("MythicMobs plugin not found!");
        }
    }

    /**
     * 获取实体的 SkillCaster 表示（MythicMobs 4.11 版本）
     * 在 4.11 版本中，只有 ActiveMob 才能作为 SkillCaster
     * @param entity Bukkit 实体
     * @return SkillCaster 实例（如果实体是 MythicMob）
     */
    public Optional<SkillCaster> getSkillCaster(Entity entity) {
        // 在 4.11 版本中，只有 ActiveMob 才能作为 SkillCaster
        ActiveMob activeMob = mobManager.getMythicMobInstance(entity);
        if (activeMob != null) {
            return Optional.of(activeMob);
        }

        // 在 4.11 版本中，普通实体（如玩家）通常不能作为 SkillCaster
        // 如果需要让普通实体使用技能，可能需要其他方法
        Bukkit.getLogger().warning("Entity is not a MythicMob and cannot be a SkillCaster in MythicMobs 4.11: " + entity.getType());
        return Optional.empty();
    }

    /**
     * 释放 MythicMobs 技能（MythicMobs 4.11 版本）
     * @param skillName 技能名称
     * @param caster 施法者实体（必须是 MythicMob）
     * @param trigger 触发实体(可为null)
     * @param origin 起源位置(可为null)
     * @param power 技能威力
     * @return 是否成功执行
     */
    public boolean castSkill(String skillName, Entity caster, Entity trigger, Location origin, float power) {
        // 获取 SkillManager
        io.lumine.xikage.mythicmobs.skills.SkillManager skillManager = mythicMobs.getSkillManager();

        // 获取技能
        Optional<Skill> skillOptional = skillManager.getSkill(skillName);
        if (!skillOptional.isPresent()) {
            Bukkit.getLogger().warning("Skill not found: " + skillName);
            return false;
        }
        Skill skill = skillOptional.get();

        // 获取 SkillCaster（必须是 ActiveMob）
        Optional<SkillCaster> casterOptional = getSkillCaster(caster);
        if (!casterOptional.isPresent()) {
            Bukkit.getLogger().warning("Entity is not a valid SkillCaster (must be a MythicMob): " + caster.getType());
            return false;
        }
        SkillCaster skillCaster = casterOptional.get();

        // 转换实体和位置
        AbstractEntity abstractTrigger = trigger != null ? BukkitAdapter.adapt(trigger) : null;
        AbstractLocation abstractOrigin = origin != null ? BukkitAdapter.adapt(origin) : null;

        // 创建技能元数据并执行
        SkillMetadata skillMetadata = new SkillMetadata(
                SkillTrigger.API,
                skillCaster,
                abstractTrigger,
                abstractOrigin,
                new HashSet<>(),
                new HashSet<>(),
                power
        );

        // 检查技能是否可用
        if (!skill.isUsable(skillMetadata)) {
            Bukkit.getLogger().warning("Skill is not usable: " + skillName);
            return false;
        }

        // 执行技能
        skill.execute(skillMetadata);
        return true;
    }

    /**
     * 为 ActiveMob 释放技能
     * @param skillName 技能名称
     * @param activeMob MythicMobs 的 ActiveMob
     * @param trigger 触发实体(可为null)
     * @param origin 起源位置(可为null)
     * @return 是否成功执行
     */
    public boolean castSkill(String skillName, ActiveMob activeMob, Entity trigger, Location origin) {
        // 获取 SkillManager
        io.lumine.xikage.mythicmobs.skills.SkillManager skillManager = mythicMobs.getSkillManager();

        // 获取技能
        Optional<Skill> skillOptional = skillManager.getSkill(skillName);
        if (!skillOptional.isPresent()) {
            return false;
        }
        Skill skill = skillOptional.get();

        // 转换实体和位置
        AbstractEntity abstractTrigger = trigger != null ? BukkitAdapter.adapt(trigger) : null;
        AbstractLocation abstractOrigin = origin != null ? BukkitAdapter.adapt(origin) : null;

        // 创建技能元数据并执行
        SkillMetadata skillMetadata = new SkillMetadata(
                SkillTrigger.API,
                activeMob,
                abstractTrigger,
                abstractOrigin,
                new HashSet<>(),
                new HashSet<>(),
                activeMob.getPower()
        );

        skill.execute(skillMetadata);
        return true;
    }

    /**
     * 简化的技能释放方法
     * @param skillName 技能名称
     * @param caster 施法者实体（必须是 MythicMob）
     * @return 是否成功执行
     */
    public boolean castSkill(String skillName, Entity caster) {
        return castSkill(skillName, caster, null, null, 1.0f);
    }

    /**
     * 检查技能是否可用
     * @param skillName 技能名称
     * @param caster 施法者实体（必须是 MythicMob）
     * @return 技能是否可用
     */
    public boolean isSkillUsable(String skillName, Entity caster) {
        // 获取 SkillManager
        io.lumine.xikage.mythicmobs.skills.SkillManager skillManager = mythicMobs.getSkillManager();

        // 获取技能
        Optional<Skill> skillOptional = skillManager.getSkill(skillName);
        if (!skillOptional.isPresent()) {
            return false;
        }
        Skill skill = skillOptional.get();

        // 获取 SkillCaster（必须是 ActiveMob）
        Optional<SkillCaster> casterOptional = getSkillCaster(caster);
        if (!casterOptional.isPresent()) {
            return false;
        }
        SkillCaster skillCaster = casterOptional.get();

        // 创建技能元数据并检查可用性
        SkillMetadata skillMetadata = new SkillMetadata(
                SkillTrigger.API,
                skillCaster,
                skillCaster.getEntity(),
                null,
                new HashSet<>(),
                new HashSet<>(),
                1.0f
        );

        return skill.isUsable(skillMetadata);
    }

    /**
     * 获取实体的 ActiveMob 表示（如果是 MythicMobs 生物）
     * @param entity Bukkit 实体
     * @return ActiveMob 实例（如果存在）
     */
    public ActiveMob getActiveMob(Entity entity) {
        return mobManager.getMythicMobInstance(entity);
    }
}
