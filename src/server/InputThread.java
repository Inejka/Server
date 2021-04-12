package server;

import support.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.function.Consumer;


public class InputThread implements Runnable {
    Thread thread;
    InputStream inputStream;
    Consumer<Message> consumer;

    public InputThread(InputStream inputStream, Consumer<Message> consumer) {
        thread = new Thread(this);
        this.inputStream = inputStream;
        this.consumer = consumer;
        thread.start();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true) consumer.accept((Message) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException exception) {
            //exception.printStackTrace();
        }


    }
}
