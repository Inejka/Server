package support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;


public class EmptySocket extends Thread {
    private static final Logger logger = LogManager.getLogger(EmptySocket.class);

    final Set<Socket> addresses;

    public EmptySocket(Set<Socket> addresses) {
        this.addresses = addresses;
        start();
    }

    @Override
    public void run() {
        try {
            for (Socket address : addresses) {
                address.close();
            }
            Socket sock = new Socket("127.0.0.1", 9090);
            new ObjectOutputStream(sock.getOutputStream()).writeObject(new
                    Message(MessageType.ACTION, Commands.closeTab));
            sock.close();
        } catch (Exception e) {

            logger.error(e);
        }
    }
}
