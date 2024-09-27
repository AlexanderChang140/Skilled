package me.cat.skilled.reward;

import me.cat.skilled.capability.PlayerSkillsProvider;
import net.minecraft.resources.ResourceLocation;
import net.puffish.skillsmod.SkillsMod;
import net.puffish.skillsmod.api.SkillsAPI;
import net.puffish.skillsmod.api.json.JsonElement;
import net.puffish.skillsmod.api.json.JsonObject;
import net.puffish.skillsmod.api.reward.Reward;
import net.puffish.skillsmod.api.reward.RewardConfigContext;
import net.puffish.skillsmod.api.reward.RewardDisposeContext;
import net.puffish.skillsmod.api.reward.RewardUpdateContext;
import net.puffish.skillsmod.api.util.Problem;
import net.puffish.skillsmod.api.util.Result;

import java.util.*;

public class SkillReward implements Reward {
    public static final ResourceLocation ID = SkillsMod.createIdentifier("skill");

    private final String skillId;

    private SkillReward(String skillId) {
        this.skillId = skillId;
    }

    public static void register() {
        SkillsAPI.registerReward(ID, SkillReward::create);
    }

    private static Result<SkillReward, Problem> create(RewardConfigContext context) {
        return context.getData()
                .andThen(JsonElement::getAsObject)
                .andThen(SkillReward::create);
    }

    private static Result<SkillReward, Problem> create(JsonObject rootObject) {
        var problems = new ArrayList<Problem>();

        var optSkillId = rootObject.get("skill")
                .andThen(JsonElement::getAsString)
                .ifFailure(problems::add)
                .getSuccess();

        if (problems.isEmpty()) {
            return Result.success(new SkillReward(
                    optSkillId.orElseThrow()
            ));
        }
        else {
            return Result.failure(Problem.combine(problems));
        }
    }

    @Override
    public void update(RewardUpdateContext rewardUpdateContext) {
        int count = rewardUpdateContext.getCount();

        rewardUpdateContext.getPlayer().getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(skills ->
                skills.setSkillLevel(skillId, count));
    }

    @Override
    public void dispose(RewardDisposeContext rewardDisposeContext) {
    }
}
