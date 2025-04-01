package me.cat.skilled.category.node;

import me.cat.skilled.category.reward.SkillReward;
import me.cat.skilled.skill.SkillData;

public class SkillNode extends Node {
    public SkillNode(String nodeId, boolean isRoot, int requiredPoints, int cost, SkillData skillData, NodeView nodeView) {
        super(
                nodeId,
                isRoot,
                skillData.getMaxLevel(),
                requiredPoints,
                cost,
                nodeView
                );
        addReward(new SkillReward(skillData.getSkillId()));
    }

    public static NodeView skillToNode(SkillData skillData, int size, int x, int y) {
        return new NodeView(skillData.getTitle(), skillData::getDesc, skillData.getIcon(), size, x, y);
    }
}
