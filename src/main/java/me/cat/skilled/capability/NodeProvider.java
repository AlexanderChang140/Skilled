package me.cat.skilled.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NodeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<NodeCap> NODES = CapabilityManager.get(new CapabilityToken<>() { });

    private NodeCap nodes;
    private final LazyOptional<NodeCap> optional = LazyOptional.of(this::createNodeCap);

    private NodeCap createNodeCap() {
        if (this.nodes == null) {
            this.nodes = new NodeCap();
        }
        return nodes;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == NODES) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nodes.saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        nodes.loadNBTData(nbt);
    }
}
