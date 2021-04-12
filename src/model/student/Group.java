package model.student;

import java.io.Serializable;

public class Group implements Serializable {
    private static final long serialVersionUID = 2L;
    final int number;

    public Group(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) return this.number == ((Group) obj).number;
        else return false;
    }
}
