package me.cat.skilled.mixin;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.skills.ranger.active.VoidwalkerSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderDispatcher.class)
public class EntityRendererDispatcherMixin {
    @Shadow private boolean shouldRenderShadow;

    @Redirect(method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;shouldRenderShadow:Z"))
    public boolean test(EntityRenderDispatcher instance) {
        if (PlayerSkillManager.getSkillInstance(Minecraft.getInstance().player, SkillRegistry.VOIDWALKER.getSkillId()) instanceof VoidwalkerSkill voidwalkerSkill) {
            return !voidwalkerSkill.isVoidwalking();
        }
        return this.shouldRenderShadow;
    }

}
