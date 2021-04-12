package Server;

import fileWorkers.Loader;
import fileWorkers.Saver;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import model.Model;
import model.student.Group;
import model.student.PackedGroupAndLimits;
import model.student.PackedSurnameAndLimits;
import model.student.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import support.Message;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

//request one class
public class ClientSocket implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientSocket.class);

    private Thread thread;
    private Socket socket;
    ObjectOutput objectOutput;
    String command;
    private Model model = new Model();
    Tab tab;

    ClientSocket(Socket socket) {
        thread = new Thread(this);
        this.socket = socket;
        try {
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            logger.error(exception);
        }
        thread.start();
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    private void appendText(String text) {
        if (tab == null) return;
        TextArea addToText = (TextArea) tab.getContent();
        addToText.appendText("\n" + text);
    }

    private LinkedList<String> getFiles() {
        LinkedList<String> toReturn = new LinkedList<>();
        File folder = new File("saves");
        for (File i : folder.listFiles())
            toReturn.add(i.getName());
        return toReturn;
    }

    public void sendData(Object toSend) {
        try {
            objectOutput.writeObject(toSend);
            objectOutput.flush();
        } catch (IOException exception) {
            logger.error(exception);
        }
        appendText("Data sended " + toSend);
    }

    public void response(Message message) {
        if ((message.getData() == null)) {
            appendText(message.getType() + "  " + message.getCommand());
        } else {
            appendText(message.getType() + "  " + message.getCommand() + "  " + message.getData());
        }
        switch (message.getType()) {
            case POST -> postMessage(message);
            case GET -> getMessage(message);
            case ACTION -> actionMessage(message);
        }
    }

    private void actionMessage(Message message) {
        switch (message.getCommand()) {
            case setStudents -> model.setStudents((List<Student>) message.getData());
            case addStudent -> model.addStudent((Student) message.getData());
            case removeStudent -> model.removeStudent((Student) message.getData());
            case removeStudents -> model.removeStudent();
            case close -> {
                try {
                    socket.close();
                } catch (IOException exception) {
                    logger.error(exception);
                }
            }
            case load -> new Loader(model.getStudents(), (String) message.getData()).load();
            case save -> new Saver(model.getStudents(), (String) message.getData()).save();
            case closeTab -> Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        logger.error(e);
                    }
                    tab.getTabPane().getTabs().remove(tab);
                }
            });
            case pop -> model.pop();
        }
    }

    private void getMessage(Message message) {
        switch (message.getCommand()) {
            case getStudentsCount -> sendData(model.getStudentsCount());
            case getGroupsId -> sendData(model.getGroupsId());
            case getStudents -> sendData(model.getStudents());
            case getFiles -> sendData(getFiles());
        }

    }

    private void postMessage(Message message) {
        switch (message.getCommand()) {
            case getStudent -> sendData(model.getStudent((int) message.getData()));
            case searchStudentSurnameLimits -> model.addStudents(model.searchStudent(
                    ((PackedSurnameAndLimits) message.getData()).getSurname(),
                    ((PackedSurnameAndLimits) message.getData()).getLowerLimit(),
                    ((PackedSurnameAndLimits) message.getData()).getUpperLimit()));
            case searchStudentGroup -> model.addStudents(model.searchStudent((Group) message.getData()));
            case searchStudentSurname -> model.addStudents(model.searchStudent((String) message.getData()));
            case searchStudentGroupLimits -> model.addStudents(model.searchStudent(
                    ((PackedGroupAndLimits) message.getData()).getGroup(),
                    ((PackedGroupAndLimits) message.getData()).getLowerLimit(),
                    ((PackedGroupAndLimits) message.getData()).getUpperLimit()));

        }

    }

    public void run() {
        try {
            new InputThread(socket.getInputStream(), this::response);
        } catch (IOException exception) {
            logger.error(exception);
        }
        while (!socket.isClosed()) {

        }
        appendText("Socket stopped");
    }
}
