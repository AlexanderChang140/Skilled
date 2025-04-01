package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class CapabilityInstance implements INBTSerializable<CompoundTag> {
    public abstract void copyFrom(CapabilityInstance capabilityInstance);
}
