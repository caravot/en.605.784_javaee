package ravotta.carrie;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handle registration of a new student
 */
@SessionScoped
@Named
public class RegistrationServlet extends HttpServlet {
    // database commands
    private static Database database;

    // student to track through registration process

    @Inject
    private StudentInfo studentInfo;

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
    }

    /**
     * Handle post action request and save registration form parts
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // process registration form
        if (action.equals("registration_formB")) {
            String address = request.getParameter("street");
            address += " " + request.getParameter("city");
            address += ", " + request.getParameter("state");
            address += " " + request.getParameter("zip");

            // concat all address fields together and trim to 40 characters (DB limit)
            studentInfo.setAddress(address.substring(0, Math.min(address.length(), 40)));

            // add new student to the database
            database.addStudent(studentInfo);

            // auto login the user
            request.setAttribute("userid: ", studentInfo.getUserid());
            request.setAttribute("password: ", studentInfo.getPassword());

            // redirect to the login servlet
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
            requestDispatcher.forward(request, response);
        }
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
        // do nothing
    }
}
