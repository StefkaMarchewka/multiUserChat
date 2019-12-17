public class Message {
    private String content;
    private  long clientId;
    private String clientName;

    public Message(String content, String clientName, long clientId){
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

    public long getClientId() {
        return clientId;
    }
}
