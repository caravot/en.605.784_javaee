package ravotta.carrie;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;

public class DatabasePopulator {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;

    private static void createConnection() {
        // don't establish new connections if we have some already
        if (ctx == null && conn == null) {
            try {
                // get jndi
                Context ctx = new InitialContext();
                ds = (javax.sql.DataSource) ctx.lookup("jhuDataSource2");
                conn = ds.getConnection();
            } catch (Exception except) {
                System.out.println("DatabasePopulator.createConnection");
                except.printStackTrace();
            }
        }
    }

    public static ResultSet selectStudent(String userid, String password) {
        ResultSet resultSet = null;
        createConnection();

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT WHERE USERID = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            resultSet = pstmt.executeQuery();
        } catch (SQLException sqlExcept) {
            System.out.println("DatabasePopulator.selectStudent");
            sqlExcept.printStackTrace();
        }

        return resultSet;
    }

    public static void shutdown() {
        System.out.println("Closing DB connection");
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqlExcept) {
            System.out.println("DatabasePopulator.shutdown");
            System.out.println(sqlExcept.getMessage());
        }
    }

    public static void closeContext() {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                System.out.println("Failed to close context due to: " + e);
            }
        }
    }
}
