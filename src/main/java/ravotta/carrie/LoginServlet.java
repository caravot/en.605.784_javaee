package ravotta.carrie;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static Database database;
    private int loginAttemptsLeft;

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
        if (database == null) {
            database = new Database();
        }

        loginAttemptsLeft = Integer.parseInt(servletConfig.getInitParameter("MAX_LOGIN_ATTEMPTS"));
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        String datasource_name = (String)request.getSession().getAttribute("DATASOURCE_NAME");

        // valid userid/password input; verify exist in database
        if (validateForm(userid, password)) {
            studentInfo = database.selectStudent(userid, password, datasource_name);

            if (studentInfo != null) {
                request.setAttribute("message",
                        "Welcome to the site, " + studentInfo.getFirst_name() + " " + studentInfo.getLast_name());
            } else {
                request.setAttribute("message", "Sorry, you don't have an account. You must register first.");
            }
        }
        // invalid login
        else {
            // decrease login attempts user has left
            loginAttemptsLeft--;

            // invalid username/password; let user try again
            if (loginAttemptsLeft > 0) {
                request.setAttribute("message", "Invalid userid/password input. Please try again.");
            }
            // out of login attempts; kill session
            else if (loginAttemptsLeft == 0) {
                request.setAttribute("message", "You have reached the maximum allowed attempts. Closing session.");
            }
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
        database.shutdown();
    }
}
