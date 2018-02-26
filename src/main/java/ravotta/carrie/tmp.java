package ravotta.carrie;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;
import java.util.Hashtable;

public class tmp {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;

    public static void main(String[] args) throws NoSuchMethodException {
        createContext();
        createConnection();
        queryDelete();
        querySelect();
        shutdown();
        closeContext();
    }

    public static void queryDelete() {
        try {
            stmt = conn.createStatement();
            String insertQuery = "DELETE FROM STUDENT WHERE FIRST_NAME = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, "Carrie");
            pstmt.execute();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static void querySelect() {
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            ResultSet results = pstmt.executeQuery();

            try {
                ResultSetMetaData rsmd = results.getMetaData();
                int numberCols = rsmd.getColumnCount();

                for (int i = 1; i <= numberCols; i++) {
                    System.out.print(rsmd.getColumnLabel(i) + "\t\t");
                }

                System.out.print("\n\n");

                while (results.next()) {
                    String rowResult = "";

                    for (int i = 1; i <= 7; i++) {
                        String tmp = results.getString(i);

                        rowResult += tmp + "\t\t";
                    }

                    System.out.println(rowResult);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static void createContext() {
        try {
            Hashtable env = new Hashtable();

            // specifies the factory to be used to create the context
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

            ctx = new InitialContext(env);
            System.out.println("Initial context created");
        } catch (NamingException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createConnection() {
        try {
            ds = (javax.sql.DataSource) ctx.lookup("jhuDataSource2");
            conn = ds.getConnection();
        } catch (Exception except) {
            except.printStackTrace();
        }
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
