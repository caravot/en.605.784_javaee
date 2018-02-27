package ravotta.carrie;

import javax.enterprise.context.SessionScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SessionScoped
public class RegistrationController extends HttpServlet {
    private String DATASOURCE_NAME;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        DATASOURCE_NAME = servletConfig.getInitParameter("DATASOURCE_NAME");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do nothing for now
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set datasource name for other servlets
        request.getSession().setAttribute("DATASOURCE_NAME", DATASOURCE_NAME);

        // get the action parameter to see where we redirect next
        String action = request.getParameter("action");

        // login the user
        if (action.equals("login")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
            requestDispatcher.forward(request, response);
        }
        // display first part of the registration form
        else if (action.equals("registration_formA")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration");
            requestDispatcher.forward(request, response);
        }
        // display second part of the registration form
        else if (action.equals("registration_formB")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration");
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
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.html");
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
