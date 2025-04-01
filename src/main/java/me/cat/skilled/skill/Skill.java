package me.cat.skilled.skill;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class Skill implements INBTSerializable<CompoundTag> {
    protected SkillData skillData;
    protected int level = 1;

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", level);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        level = tag.getInt("level");
    }

    public SkillData getSkillData() {
        return skillData;
    }

    public void setSkillData(SkillData skillData) {
        this.skillData = skillData;
    }
}
