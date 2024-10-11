package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;

public interface SerializedSkill {
    CompoundTag saveNbt();
    void loadNbt(CompoundTag tag);
}
