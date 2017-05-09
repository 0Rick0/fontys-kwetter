package nl.rickrongen.fontys.kwetter.WebSocket;

/**
 * Created by rick on 5/4/17.
 */
public class DeserializableKwet {
    private String username;
    private String text;

    public DeserializableKwet() {
    }

    public DeserializableKwet(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
