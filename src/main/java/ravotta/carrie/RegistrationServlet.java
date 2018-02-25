package ravotta.carrie;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SessionScoped
public class RegistrationServlet extends HttpServlet {
    private static Database database;

    @Inject
    StudentInfo studentInfo;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do nothing for now
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String datasource_name = (String)request.getSession().getAttribute("DATASOURCE_NAME");

        if (action.equals("registration_formA")) {
            studentInfo.setFirst_name(request.getParameter("first_name"));
            studentInfo.setLast_name(request.getParameter("last_name"));
            studentInfo.setLast_name(request.getParameter("last_name"));
            studentInfo.setSsn(request.getParameter("ssn"));

            RequestDispatcher rd=request.getRequestDispatcher("/registration_b.xhtml");
            rd.include(request, response);
        } else {
            String address = request.getParameter("address");
            address += " " + request.getParameter("city");
            address += ", " + request.getParameter("state");
            address += " " + request.getParameter("zip");
            address = address.substring(0, Math.min(address.length(), 40));

            // add student to database
            database = new Database();

            database.addStudent(studentInfo, datasource_name);

            // close connection when shutting down
            database.shutdown();

            RequestDispatcher rd = request.getRequestDispatcher("/response.xhtml");
            rd.include(request, response);
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
