package ravotta.carrie;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationController extends HttpServlet {
    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        System.out.println("<h1>" + message + "</h1>");
    }

    public void destroy() {
        // do nothing.
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
