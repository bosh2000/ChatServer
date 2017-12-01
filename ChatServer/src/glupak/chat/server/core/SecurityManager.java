package glupak.chat.server.core;

import java.sql.SQLException;

public interface SecurityManager {

    void init();

    String getNick(String login, String password);

    void dispose() throws SQLException;
}
