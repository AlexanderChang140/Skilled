package me.cat.skilled.registry;

import me.cat.skilled.capability.NodeCap;
import me.cat.skilled.capability.SkillCap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.*;

public class CapabilityRegistry {
    private static final Map<String, Capability<?>> REGISTRY = new HashMap<>();

    public static final Capability<SkillCap> SKILLS = register(CapabilityManager.get(new CapabilityToken<>() { }));
    public static final Capability<NodeCap> NODES = register(CapabilityManager.get(new CapabilityToken<>() { }));

    private static <T> Capability<T> register(Capability<T> capability) {
        REGISTRY.put(capability.getName(), capability);
        return capability;
    }

    public static Collection<Capability<?>> getCapabilities() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }

    public static Capability<?> getCapability(String capabilityId) {
        return REGISTRY.get(capabilityId);
    }
}
