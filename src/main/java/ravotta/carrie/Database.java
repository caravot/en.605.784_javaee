package ravotta.carrie;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * All database methods/commands
 */
public class Database {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static Context ctx = null;
    private static javax.sql.DataSource ds = null;

    /**
     * Create connection to the database
     *
     * @param dsName Datasource name
     */
    private static void createConnection(String dsName, String wlsUrl) {
        // don't establish new connections if we have some already
        try {
            if (conn == null || conn.isClosed()) {
                try {
                    // get jndi for datasource
                    if (ctx == null) {
                        Hashtable env = new Hashtable();

                        // specifies the factory to be used to create the context
                        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

                        // specifies the URL of the WebLogic Server
                        env.put(Context.PROVIDER_URL, wlsUrl);

                        ctx = new InitialContext(env);
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

    /**
     * Add a student to the database
     *
     * @param student Student bean
     * @param dsName Datasource name
     */
    public static void addStudent(StudentInfo student, String dsName, String wlsUrl) {
        // open database connection
        createConnection(dsName, wlsUrl);

        String insertQuery = "INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, SSN, EMAIL, ADDRESS, USERID, PASSWORD) " +
                "values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            pstmt.setString(1, student.getFirst_name());
            pstmt.setString(2, student.getLast_name());
            pstmt.setString(3, student.getSsn());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getUserid());
            pstmt.setString(7, student.getPassword());

            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }
    }

    /**
     * Determine if userid exists in the database
     *
     * @param userid Userid to lookup
     * @param dsName Datasource name
     * @return boolean if userid exists
     */
    public static boolean validUserid(String userid, String dsName, String wlsUrl) {
        ResultSet resultSet;

        // open database connection
        createConnection(dsName, wlsUrl);

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM STUDENT WHERE USERID = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, userid);

            resultSet = pstmt.executeQuery();

            try {
                if (resultSet.next()) {
                    return true;
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

        return false;
    }

    /**
     * Select a student from the database
     *
     * @param userid Userid to query for
     * @param password Password to query for
     * @param dsName Datasource name
     * @return StudentInfo object of the student looked up
     */
    public static StudentInfo selectStudent(String userid, String password, String dsName, String wlsUrl) {
        ResultSet resultSet;

        // open database connection
        createConnection(dsName, wlsUrl);

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
                    studentInfo.setUserid(resultSet.getString("userid"));
                    studentInfo.setPassword(resultSet.getString("password"));
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

    /**
     * Get all courses from database
     *
     * @param dsName Datasource name
     * @return List<String> of courses
     */
    public static List<String> selectCourses(String dsName, String wlsUrl) {
        ResultSet resultSet;
        List<String> courseList = new ArrayList<String>();

        // open database connection
        createConnection(dsName, wlsUrl);

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM COURSES";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            resultSet = pstmt.executeQuery();

            try {
                while(resultSet.next()) {
                    courseList.add(resultSet.getString("COURSEID") + "_" + resultSet.getString("COURSE_NAME"));
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

        return courseList;
    }

    /**
     * Register a student for a course
     *
     * @param courseid Courseid to register to
     * @param totalRegistered New value of total students registered
     * @param dsName Datasource name
     */
    public static void addRegistrar(String courseid, int totalRegistered, String dsName, String wlsUrl) {
        // open database connection
        createConnection(dsName, wlsUrl);

        try {
            String q;
            stmt = conn.createStatement();

            if (totalRegistered == 1) {
                q = "INSERT INTO REGISTRAR (number_students_registered, courseid) VALUES (?, ?)";
            } else {
                q = "UPDATE REGISTRAR SET number_students_registered = ? WHERE courseid = ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(q);
            pstmt.setInt(1, totalRegistered);
            pstmt.setString(2, courseid);
            pstmt.execute();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            // close connection when done
            shutdown();
        }
    }

    /**
     * Get registration information for a course
     *
     * @param courseid Courseid to query for
     * @param dsName Datasource name
     * @return number of students registered already
     */
    public static int selectRegistrar(String courseid, String dsName, String wlsUrl) {
        ResultSet resultSet;
        int studentsRegistered = 0;

        // open database connection
        createConnection(dsName, wlsUrl);

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String insertQuery = "SELECT * FROM REGISTRAR WHERE courseid = ?";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, courseid);

            resultSet = pstmt.executeQuery();

            try {
                if (resultSet.next()) {
                    studentsRegistered = resultSet.getInt("number_students_registered");
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

        return studentsRegistered;
    }

    /**
     * Disconnect from the database
     */
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

    /**
     * Close connection to WLS
     */
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
