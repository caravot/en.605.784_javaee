package ravotta.carrie;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;

public class Database {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;

    private static void createConnection(String dsName) {
        // don't establish new connections if we have some already
        try {
            if (conn == null || conn.isClosed()) {
                try {
                    // get jndi for datasource
                    if (ctx == null) {
                        ctx = new InitialContext();
                    }
                    ds = (javax.sql.DataSource) ctx.lookup(dsName);
                    conn = ds.getConnection();
                } catch (Exception except) {
                    except.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static StudentInfo selectStudent(String userid, String password, String dsName) {
        ResultSet resultSet = null;

        // open database connection
        createConnection(dsName);

        StudentInfo studentInfo = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT WHERE USERID = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            resultSet = pstmt.executeQuery();

            try {
                if (resultSet.next()) {
                    studentInfo = new StudentInfo();
                    studentInfo.setFirst_name(resultSet.getString("first_name"));
                    studentInfo.setLast_name(resultSet.getString("last_name"));
                    studentInfo.setAddress(resultSet.getString("address"));
                    studentInfo.setSsn(resultSet.getString("ssn"));
                    studentInfo.setEmail(resultSet.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }

        return studentInfo;
    }

    public static void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqlExcept) {
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
