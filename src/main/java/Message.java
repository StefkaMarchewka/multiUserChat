import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private String clientId;
    private String clientName;

    public Message(){
    }

    public Message(String content, String clientName, String clientId){
        this.content = content;
        this.clientName = clientName;
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getContent() {
        return content;
    }

    public String getClientId() {
        return clientId;
    }
}
