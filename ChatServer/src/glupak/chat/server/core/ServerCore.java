package glupak.chat.server.core;

import java.sql.SQLException;

public class ServerCore {

    private final ChatServerListener chatServerListener;
    private final SecurityManager securityManager;

    public ServerCore(ChatServerListener chatServerListener,SecurityManager securityManager){
        this.chatServerListener=chatServerListener;
        this.securityManager=securityManager;
    }

    public void startListening(int port){
        putLog("Start listening on port -"+port);
        securityManager.init();
    }
    public void stopListening(){
        putLog("Stop listening.");
        try {
            securityManager.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropAllClients(){
        putLog("Drop all client.");
    }

    private void putLog(String msg){
        chatServerListener.onChatServerLog(this,msg);
    }
}
