package model;

import model.student.FIO;
import model.student.Group;
import model.student.PublicWork;
import model.student.Student;

import java.util.*;

public class Model {


    public void setStudents(List<Student> students) {
        this.students.clear();
        this.students.add(students);
    }

    public void addStudents(List<Student> students) {
        this.students.add(students);
    }

    private final Stack<List<Student>> students = new Stack<>();

    public Model() {
        students.add(new LinkedList<>());
    }

    public Model(List<Student> students) {
        this.students.add(students);
    }

    public void addStudent(String surname, String name, String patronymic, int number, PublicWork[] publicWorks) {
        students.lastElement().add(new Student(new FIO(surname, name, patronymic), new Group(number), publicWorks));
    }

    public Student getStudent(int position) {
        if (position < students.lastElement().size())
            return students.lastElement().get(position);
        else return null;
    }

    public int getStudentsCount() {
        return students.lastElement().size();
    }

    public void removeStudent(Student student) {
        students.lastElement().removeIf(i -> i.equals(student));
    }

    public void pop() {
        students.pop();
    }

    public List<Student> searchStudent(String surname, int lowerLimit, int upperLimit) {
        if (this.students.size() != 1) this.students.pop();
        List<Student> toReturn = new LinkedList<>();
        for (Student i : students.lastElement())
            if (surname.equals(i.getFio().getSurname())) {
                boolean toAdd = true;
                for (PublicWork j : i.getSemesters())
                    if (!(lowerLimit <= j.getHours() && upperLimit >= j.getHours())) {
                        toAdd = false;
                        break;
                    }
                if (toAdd) toReturn.add(i);
            }
        return toReturn;
    }

    public List<Student> searchStudent(Group group) {
        if (this.students.size() != 1) this.students.pop();
        List<Student> toReturn = new LinkedList<>();
        for (Student i : students.lastElement())
            if (i.getGroup().equals(group))
                toReturn.add(i);
        return toReturn;
    }

    public List<Student> searchStudent(String surname) {
        if (this.students.size() != 1) this.students.pop();
        List<Student> toReturn = new LinkedList<>();
        for (Student i : students.lastElement())
            if (i.getFio().getSurname().equals(surname))
                toReturn.add(i);
        return toReturn;
    }


    public List<Student> searchStudent(Group group, int lowerLimit, int upperLimit) {
        if (this.students.size() != 1) this.students.pop();
        var toReturn = new LinkedList<Student>();
        for (Student i : students.lastElement())
            if (group.equals(i.getGroup())) {
                boolean toAdd = true;
                for (PublicWork j : i.getSemesters())
                    if (!(lowerLimit <= j.getHours() && upperLimit >= j.getHours())) {
                        toAdd = false;
                        break;
                    }
                if (toAdd) toReturn.add(i);
            }
        return toReturn;
    }

    public void removeStudent() {
        var toDeleteList = students.pop();
        for (Student i : toDeleteList)
            removeStudent(i);
    }

    public PublicWork[] transform(int[] toTransform) {
        PublicWork[] toReturn = new PublicWork[10];
        for (int i = 0; i < 10; i++)
            toReturn[i] = new PublicWork(toTransform[i]);
        return toReturn;
    }

    public List<Integer> getGroupsId() {
        Set<Integer> set = new LinkedHashSet<>();
        for (Student i : students.lastElement())
            set.add(i.getGroup().getNumber());
        return new LinkedList<>(set);
    }

    public List<Student> getStudents() {
        return students.lastElement();
    }

    public void addStudent(Student student) {
        students.lastElement().add(student);
    }
}
