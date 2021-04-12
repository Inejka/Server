package support;


import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 7L;
    private final MessageType type;
    private Object data;
    private final Commands command;

    public MessageType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public Commands getCommand() {
        return command;
    }

    public Message(MessageType type, Commands command, Object data) {
        this.type = type;
        this.data = data;
        this.command = command;
    }

    public Message(MessageType type, Commands command) {
        this.type = type;
        this.command = command;
    }
}
