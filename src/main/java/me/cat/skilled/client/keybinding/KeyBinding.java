package me.cat.skilled.client.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import me.cat.skilled.Skilled;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skilled.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBinding {
    public static final String KEY_CATEGORY_SKILLED = "key.category.skilled.skilled";
    public static final String KEY_PRIMARY_ABILITY = "key.skilled.primary_ability";

    public static final KeyMapping PRIMARY_ABILITY_KEY = new KeyMapping(KEY_PRIMARY_ABILITY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, -1, KEY_CATEGORY_SKILLED);

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.PRIMARY_ABILITY_KEY);
    }
}
