package fileWorkers;

import model.student.PublicWork;
import model.student.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;


public class Saver {
    private static final Logger logger = LogManager.getLogger(Saver.class);
    final List<Student> toSave;
    final String name;

    public Saver(List<Student> toSave, String name) {
        this.toSave = toSave;
        this.name = name;
    }

    public void save() {
        try {
            File selectedFile = new File("saves/" + name);
            if (!selectedFile.exists())
                selectedFile.createNewFile();
            Transformer saver = TransformerFactory.newInstance().newTransformer();
            saver.transform(new DOMSource(genDoc()), new StreamResult(selectedFile));

        } catch (Exception e) {
            logger.error(e);
        }
    }

    private Document genDoc() throws ParserConfigurationException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element rootElement = doc.createElement("root");
        doc.appendChild(rootElement);
        for (Student student : toSave) {
            rootElement.appendChild(getStudent(doc, student));
        }
        return doc;
    }

    private Node getStudent(Document doc, Student toSave) {
        Element student = doc.createElement("Student");
        student.appendChild(getElement(doc, "surname", toSave.getFio().getSurname()));
        student.appendChild(getElement(doc, "name", toSave.getFio().getName()));
        student.appendChild(getElement(doc, "patronymic", toSave.getFio().getPatronymic()));
        student.appendChild(getElement(doc, "group", String.valueOf(toSave.getGroup().getNumber())));
        student.appendChild(getPublicWorks(doc, toSave.getSemesters()));
        return student;
    }

    private Node getElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    private static Node getPublicWorks(Document doc, PublicWork[] toSave) {
        Element root = doc.createElement("Works");
        for (int i = 0; i < 10; i++) {
            Element node = doc.createElement("Work");
            node.setAttribute("id", String.valueOf(i));
            node.appendChild(doc.createTextNode(String.valueOf(toSave[i].getHours())));
            root.appendChild(node);
        }
        return root;
    }
}
