package model.student;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 4L;
    final FIO fio;
    final Group group;
    PublicWork[] semesters = new PublicWork[10];

    public Student(FIO fio, Group group, PublicWork[] semesters) {
        this.fio = fio;
        this.group = group;
        this.semesters = semesters;
    }

    public FIO getFio() {
        return fio;
    }

    public Group getGroup() {
        return group;
    }

    public PublicWork[] getSemesters() {
        return semesters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        for (int i = 0; i < 10; i++) if (semesters[i].getHours() != student.semesters[i].getHours()) return false;
        return fio.getSurname().equals(student.getFio().getSurname()) &&
                fio.getName().equals(student.getFio().getName()) &&
                fio.getPatronymic().equals(student.getFio().getPatronymic()) &&
                group.getNumber() == student.getGroup().getNumber();
    }


}
