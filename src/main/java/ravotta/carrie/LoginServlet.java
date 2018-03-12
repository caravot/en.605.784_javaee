package ravotta.carrie;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Login functions and methods
 */
public class LoginServlet extends HttpServlet {
    // database commands
    private static Database database;

    // keep track of authentication
    NavigationController navigationController;

    // attempts left to attempt login
    private int loginAttemptsLeft;

    /**
     * Initialize the servlet
     *
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // create new database class if it doesn't exist
        if (database == null) {
            database = new Database();
        }

        navigationController = new NavigationController();

        // set max login attempts
        loginAttemptsLeft = Integer.parseInt(servletConfig.getInitParameter("MAX_LOGIN_ATTEMPTS"));
    }

    /**
     * Perform actions against the servlet
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentInfo studentInfo = null;
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        // some userid/password parameters are sent via added request attributes
        if (userid == null) {
            userid = (String)request.getAttribute("userid");
        }
        if (password == null) {
            password = (String) request.getAttribute("password");
        }

        // verify if the userid exists in the database
        boolean userIdExists = database.validUserid(userid);

        // valid input; verify exist in database
        if (userIdExists) {
            studentInfo = database.selectStudent(userid, password);
        }

        // user exists; display selection options
        if (studentInfo != null) {
            System.out.println("herefrestesrt");
            navigationController.setAuthenticated(true);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.xhtml");
            requestDispatcher.forward(request, response);
//            String message = "<h2>Welcome to the site, " +
//                studentInfo.getFirst_name() + " " + studentInfo.getLast_name() + "</h2>" +
//                "<h2>Select your next action</h2>" +
//                "<form id=\"loginForm\" method=\"post\" action=\"registrationcontroller\">" +
//                "<input type=\"radio\" name=\"action\" value=\"courses\" checked=\"checked\"/> Register for the course<br />" +
//                "<input type=\"radio\" name=\"action\" value=\"logout\"/> Logout<br />" +
//                "<button type=\"submit\">Submit</button>";
//
//            writeResponse(response, message);
        }
        // user doesn't exist
        else if (!userIdExists) {
            writeResponse(response, "</h2>Sorry, you don't have an account. You must register first.</h2>");
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
                request.getSession().invalidate();
            }
        }
    }

    /**
     * Write response directly to user
     *
     * @param response
     * @param message
     * @throws IOException
     */
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
