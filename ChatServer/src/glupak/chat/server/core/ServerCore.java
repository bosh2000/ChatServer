package glupak.chat.server.core;

import glupak.chat.network.ServerSocketThread;

import java.sql.SQLException;

public class ServerCore {

    private final ChatServerListener chatServerListener;
    private final SecurityManager securityManager;
    private ServerSocketThread serverSocketThread;

    public ServerCore(ChatServerListener chatServerListener, SecurityManager securityManager) {
        this.chatServerListener = chatServerListener;
        this.securityManager = securityManager;
    }

    public void startListening(int port) {
        if (serverSocketThread != null && serverSocketThread.isAlive()) {
            putLog("Сервер уже запущен.");
            return;
        }
        putLog("Start listening on port -" + port);
        securityManager.init();
        serverSocketThread = new ServerSocketThread("ServerSocketThread");
    }

    public void stopListening() {
        if (serverSocketThread == null || !serverSocketThread.isAlive()) {
            putLog("Сервер не запущен");
            return;
        }

        putLog("Stop listening.");
        try {
            serverSocketThread.interrupt();
            securityManager.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropAllClients() {
        putLog("Drop all client.");
    }

    private void putLog(String msg) {
        chatServerListener.onChatServerLog(this, msg);
    }
}
