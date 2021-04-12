package Server;

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

    Thread thread;
    boolean isOn = false;
    boolean alive = true;
    private ServerSocket serverSocket;
    EmptySocket emptySocket;
    Set<Socket> addresses = new LinkedHashSet<>();
    Function<String, Tab> createTab;

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
                if (isOn) {
                    if (!addresses.contains(clientSocket)) {
                        var temp = new ClientSocket(clientSocket);
                        Platform.runLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        temp.setTab(createTab.apply(clientSocket.getInetAddress().toString()));
                                    }
                                });
                        addresses.add(clientSocket);
                    }
                } else {
                    clientSocket.close();
                }
                addresses.removeIf(Socket::isClosed);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        for (Socket address : addresses) {
            try {
                address.close();
            } catch (IOException exception) {
                logger.error(exception);
            }
        }
    }
}
