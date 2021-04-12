package model.student;

import java.io.Serializable;
import java.util.Objects;

public class FIO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String surname;  // Фамилия
    private final String name;     // Имя
    private final String patronymic;   //Отчество

    public FIO(String surname,String name,String patronymic){
        this.surname=surname;
        this.name=name;
        this.patronymic=patronymic;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FIO fio = (FIO) o;
        return Objects.equals(surname, fio.surname) && Objects.equals(name, fio.name) && Objects.equals(patronymic, fio.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic);
    }
}
