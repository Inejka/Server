package model.student;

import java.io.Serializable;

public class PackedSurnameAndLimits implements Serializable {
    private static final long serialVersionUID = 6L;
    private final String surname;
    private final int lowerLimit;
    private final int upperLimit;

    public String getSurname() {
        return surname;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public PackedSurnameAndLimits(String surname, int lowerLimit, int upperLimit) {
        this.surname = surname;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }
}
