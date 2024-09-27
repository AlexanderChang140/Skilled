package me.cat.skilled;

import com.mojang.logging.LogUtils;
import me.cat.skilled.registry.AttributeRegistry;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.reward.ForgeAttributeReward;
import me.cat.skilled.reward.SkillReward;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Skilled.MODID)
public class Skilled {

    public static final String MODID = "skilled";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Skilled() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EffectRegistry.register(modEventBus);
        AttributeRegistry.register(modEventBus);

        SkillReward.register();
        ForgeAttributeReward.register();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }
}
