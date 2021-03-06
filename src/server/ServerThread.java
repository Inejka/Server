package server;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import support.EmptySocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;


public class ServerThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(ServerThread.class);

    final Thread thread;
    boolean isOn = false;
    boolean alive = true;
    private ServerSocket serverSocket;
    EmptySocket emptySocket;
    final Set<Socket> addresses = new LinkedHashSet<>();
    final Function<String, Tab> createTab;

    public ServerThread(Function<String, Tab> createTab) {
        thread = new Thread(this);
        this.createTab = createTab;
        try {
            serverSocket = new ServerSocket(9090);
        } catch (IOException exception) {
            logger.error(exception);
        }
        thread.start();
    }

    public void stop() {
        alive = false;
        new EmptySocket(addresses);
    }

    public void enable() {
        isOn = true;
    }

    public void disable() {
        if (isOn) {
            isOn = false;
            if (emptySocket == null || !emptySocket.isAlive()) emptySocket = new EmptySocket(addresses);
        }

    }

    @Override
    public void run() {
        try {
            while (alive) {
                Socket clientSocket = serverSocket.accept();
                if (isOn)
                    startSocket(clientSocket);
                else
                    clientSocket.close();
                addresses.removeIf(Socket::isClosed);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        closeSockets();
    }

    private void startSocket(Socket clientSocket) {
        var temp = new ClientSocket(clientSocket);
        Platform.runLater(
                () -> temp.setTab(createTab.apply(clientSocket.getInetAddress().toString())));
        addresses.add(clientSocket);
    }

    private void closeSockets() {
        for (Socket address : addresses) {
            try {
                address.close();
            } catch (IOException exception) {
                logger.error(exception);
            }
        }
    }
}
