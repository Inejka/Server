package model.student;

import java.io.Serializable;

public class PackedGroupAndLimits implements Serializable {
    private static final long serialVersionUID = 5L;
    private final Group group;
    private final int lowerLimit;
    private final int upperLimit;

    public Group getGroup() {
        return group;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public PackedGroupAndLimits(Group group, int lowerLimit, int upperLimit) {
        this.group = group;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }
}
