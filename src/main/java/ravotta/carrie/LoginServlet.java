package ravotta.carrie;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private static DatabasePopulator databasePopulator;

    @Inject
    StudentInfo studentInfo;

    private boolean validateForm(String userid, String password) {
        // length of both vars should be 8 characters
        if (userid.length() != 8 || password.length() != 8) {
            return false;
        }

        // both vars cannot contain a space
        if (userid.contains(" ") || password.contains(" ")) {
            return false;
        }

        return true;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        if (databasePopulator == null) {
            databasePopulator = new DatabasePopulator();
        }
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        // valid userid/password input; verify exist in database
        if (validateForm(userid, password)) {
            ResultSet resultSet = databasePopulator.selectStudent(userid, password);

            try {
                if (resultSet.next()) {
                    studentInfo.setFirst_name(resultSet.getString("first_name"));
                    studentInfo.setLast_name(resultSet.getString("last_name"));
                    studentInfo.setAddress(resultSet.getString("address"));
                    studentInfo.setSsn(resultSet.getString("ssn"));
                    studentInfo.setEmail(resultSet.getString("email"));

                    request.setAttribute("message",
                            "Welcome to the site, " + studentInfo.getFirst_name() + " " + studentInfo.getLast_name());
                } else {
                    request.setAttribute("message", "Sorry, you don't have an account. You must register first.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // invalid username/password; let user try again
        else {
            request.setAttribute("message", "Invalid userid/password input. Please try again.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/response.xhtml");
        rd.include(request, response);
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        // close connection when shutting down
        databasePopulator.shutdown();
    }
}
