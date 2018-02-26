package ravotta.carrie;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private static Database database;
    private int loginAttemptsLeft;

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

        if (userid == null) {
            userid = (String)request.getAttribute("userid");
        }

        if (password == null) {
            password = (String) request.getAttribute("password");
        }

        String datasource_name = (String)request.getSession().getAttribute("DATASOURCE_NAME");

        // valid userid/password input; verify exist in database
        if (validateForm(userid, password)) {
            StudentInfo studentInfo = database.selectStudent(userid, password, datasource_name);

            if (studentInfo != null) {
                String message = "<h2>Welcome to the site, " +
                        studentInfo.getFirst_name() + " " + studentInfo.getLast_name() + "</h2>" +
                        "<h2>Select your next action</h2>" +
                        "<form id=\"loginForm\" method=\"post\" action=\"registrationcontroller\">" +
                        "<input type=\"radio\" name=\"action\" value=\"courses\"/> Register for the course<br />" +
                        "<input type=\"radio\" name=\"action\" value=\"logout\"/> Logout<br />" +
                        "<button type=\"submit\">Submit</button>";

                writeResponse(response, message);
            } else {
                writeResponse(response, "</h2>Sorry, you don't have an account. You must register first.</h2>");
            }
        }
        // invalid login
        else {
            // decrease login attempts user has left
            loginAttemptsLeft--;

            // invalid username/password; let user try again
            if (loginAttemptsLeft > 0) {
                writeResponse(response, "<h2>Invalid userid/password input. Please try again.</h2>");
            }
            // out of login attempts; kill session
            else if (loginAttemptsLeft == 0) {
                writeResponse(response, "<h2>You have reached the maximum allowed attempts. Closing session.</h2>");
            }
        }
    }

    public void writeResponse(HttpServletResponse response, String message) throws IOException {
        // set content type and other response header fields first
        response.setContentType("text/html");

        // then write the data of the response
        PrintWriter out = response.getWriter();
        out.println(message);
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
    }
}
