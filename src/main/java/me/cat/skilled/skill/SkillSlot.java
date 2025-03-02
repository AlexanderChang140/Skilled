package me.cat.skilled.skill;

import java.util.HashMap;
import java.util.Map;

public enum SkillSlot {
    PRIMARY(0),
    SECONDARY(1),
    TERTIARY(2);

    private final int index;

    private static final Map<Integer, SkillSlot> NUMBER_TO_ENUM_MAP = new HashMap<>();

    static {
        for (SkillSlot slot : SkillSlot.values()) {
            NUMBER_TO_ENUM_MAP.put(slot.getIndex(), slot);
        }
    }

    public static SkillSlot fromNumber(int number) {
        return NUMBER_TO_ENUM_MAP.getOrDefault(number, null);
    }

    SkillSlot(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
