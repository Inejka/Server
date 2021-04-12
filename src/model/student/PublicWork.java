package model.student;

import java.io.Serializable;

public class PublicWork implements Serializable {
    private static final long serialVersionUID = 3L;
    final int hours;

    public PublicWork(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

}
