package glupak.chat.server.core;

public class ServerCore {

    private final ChatServerListener chatServerListener;

    public ServerCore(ChatServerListener chatServerListener){
        this.chatServerListener=chatServerListener;
    }

    public void startListening(int port){
        putLog("Start listening on port -"+port);
    }
    public void stopListening(){
        putLog("Stop listening.");
    }
    public void dropAllClients(){
        putLog("Drop all client.");
    }

    private void putLog(String msg){
        chatServerListener.onChatServerLog(this,msg);
    }
}
