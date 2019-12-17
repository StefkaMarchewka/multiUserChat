public class Client {
    private static String clientName;
    private String clientId;

    public Client(String clientName, String id){
        this.clientName = clientName;
        this.clientId = id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientId() {
        return clientId;
    }

}
