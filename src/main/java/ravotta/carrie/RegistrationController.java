package ravotta.carrie;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SessionScoped
public class RegistrationController extends HttpServlet {
    private String message;
    private String DATASOURCE_NAME;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        DATASOURCE_NAME = servletConfig.getInitParameter("DATASOURCE_NAME");
    }

    public void action(String action) {
        System.out.println("Attempting to do item: " + action);

        try {
            // login user
            FacesMessage facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Invalid LoginServlet",
                    "Invalid LoginServlet"
            );
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        } catch (Exception ex) {
            FacesMessage facesMsg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Invalid LoginServlet",
                    "Invalid LoginServlet"
            );
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do nothing for now
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("DATASOURCE_NAME", DATASOURCE_NAME);
        // get the action parameter to see where we redirect next
        String action = request.getParameter("action");

        if (action.equals("login")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
            requestDispatcher.forward(request, response);
        }

//        if (p.equals("servlet")) {
//            RequestDispatcher rd=request.getRequestDispatcher("servlet2");
//            rd.forward(request, response);
//        } else {
//            System.out.println("Sorry UserName or Password Error!");
//            RequestDispatcher rd=request.getRequestDispatcher("/index.xhtml");
//            rd.include(request, response);
//        }
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
