package projeto.utils;


import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author kermeson
 */

public class ConnectionFactory {
    public static Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/wikinews","root", "12345");
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public static void closeConnection(Connection conn) throws Exception {
        close(conn, null, null);
    }

    private static void close(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        try {
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
             throw new Exception(e.getMessage());
        }
    }
}

