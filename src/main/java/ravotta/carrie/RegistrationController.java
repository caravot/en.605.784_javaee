package ravotta.carrie;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides control when registering a new student
 */
//@SessionScoped
@ApplicationScoped
@Named
public class RegistrationController extends HttpServlet {
//    @Inject
//    StudentInfo studentInfo;

    /**
     * Initialize the servlet
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Database database = new Database(
                config.getInitParameter("DATASOURCE_NAME"),
                config.getInitParameter("WLS_URL")
        );
    }


    /**
     * Handle get action and pass to post action
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * Handle post action from other objects
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the action parameter to see where we redirect next
        String action = request.getParameter("action");

        // login the user
        if (action.equals("login")) {
            System.out.println("here");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
            requestDispatcher.forward(request, response);
        }
        // show courses
        else if (action.equals("courses")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/CoursesJSP.jsp");
            requestDispatcher.include(request, response);
        }
        // logout
        else if (action.equals("logout")) {
            request.getSession().invalidate();
            request.logout();
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.xhtml");
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
