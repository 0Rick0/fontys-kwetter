package nl.rickrongen.fontys.kwetter.WebSocket;

/**
 * Created by rick on 5/3/17.
 */
public class WsMessage<T> {
    private String type;
    private T data;

    public WsMessage() {
    }

    public WsMessage(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
