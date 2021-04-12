package fileWorkers;

import model.student.FIO;
import model.student.Group;
import model.student.PublicWork;
import model.student.Student;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;



public class Loader {
    final List<Student> toSave;
    String name;

    public Loader(List<Student> toSave, String name) {
        this.toSave = toSave;
        this.name = name;
    }

    public void load() {
        try {
            File selectedFile = new File("saves/" + name);
            SAXParserFactory.newInstance().newSAXParser().parse(selectedFile, new MyHandler(toSave));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyHandler extends DefaultHandler {
        String surname, name, patronymic;
        int group;
        PublicWork[] works;
        final List<Student> toAdd;
        String lastName;
        int i;

        public MyHandler(List<Student> toAdd) {
            this.toAdd = toAdd;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("Student")) works = new PublicWork[10];
            lastName = qName;
            if (qName.equals("Work")) i = Integer.parseInt(attributes.getValue("id"));
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastName.equals("surname"))
                    surname = information;
                if (lastName.equals("name"))
                    name = information;
                if (lastName.equals("patronymic"))
                    patronymic = information;
                if (lastName.equals("group"))
                    group = Integer.parseInt(information);
                if (lastName.equals("Work"))
                    works[i] = new PublicWork(Integer.parseInt(information));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("Student"))
                toAdd.add(new Student(new FIO(surname, name, patronymic), new Group(group), works));
        }

    }

}
