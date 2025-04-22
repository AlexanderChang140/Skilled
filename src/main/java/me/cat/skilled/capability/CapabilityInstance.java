package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class CapabilityInstance implements INBTSerializable<CompoundTag> {
    private boolean isDirty = false;

    public abstract void copyFrom(CapabilityInstance capabilityInstance);

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }
}
