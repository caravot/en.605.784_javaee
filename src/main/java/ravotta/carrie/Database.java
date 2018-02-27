package ravotta.carrie;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @param student
     * @param dsName
     */
    public static void addStudent(StudentInfo student, String dsName) {
        // open database connection
        createConnection(dsName);

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

    public static boolean validUserid(String userid, String dsName) {
        ResultSet resultSet;

        // open database connection
        createConnection(dsName);

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

    public static StudentInfo selectStudent(String userid, String password, String dsName) {
        ResultSet resultSet;

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

    public static List<String> selectCourses(String dsName) {
        ResultSet resultSet;
        List<String> courseList = new ArrayList<String>();

        // open database connection
        createConnection(dsName);

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

    public static void addRegistrar(String courseid, int totalRegistered, String dsName) {
        // open database connection
        createConnection(dsName);

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

    public static int selectRegistrar(String courseid, String dsName) {
        ResultSet resultSet;
        int studentsRegistered = 0;

        // open database connection
        createConnection(dsName);

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
