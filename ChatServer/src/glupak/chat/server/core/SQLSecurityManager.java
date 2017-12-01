package glupak.chat.server.core;

import java.sql.*;

public class SQLSecurityManager implements SecurityManager {

    private Connection connection;
    private PreparedStatement statement;

    @Override
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat_db.sqlite");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dispose() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String getNick(String login, String password) {
        String rq = "SELECT nickname FROM users WHERE login=? and password=?";
        try {
            statement = connection.prepareStatement(rq);
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nickname");
                } else return null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
