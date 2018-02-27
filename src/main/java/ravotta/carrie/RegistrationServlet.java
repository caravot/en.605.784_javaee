package ravotta.carrie;

import javax.faces.bean.SessionScoped;
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
    private StudentInfo studentInfo;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // create new database class if it doesn't exist
        if (database == null) {
            database = new Database();
        }

        // create new student if one doesn't exist
        if (studentInfo == null) {
            studentInfo = new StudentInfo();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do nothing for now
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String dsName = (String)request.getSession().getAttribute("DATASOURCE_NAME");

        // process part A of registration form
        if (action.equals("registration_formA")) {
            // validate userid and password
            if (validateForm(request.getParameter("userid"), request.getParameter("password"))) {
                studentInfo.setFirst_name(request.getParameter("first_name"));
                studentInfo.setLast_name(request.getParameter("last_name"));
                studentInfo.setUserid(request.getParameter("userid"));
                studentInfo.setPassword(request.getParameter("password"));
                studentInfo.setEmail(request.getParameter("email"));
                studentInfo.setSsn(request.getParameter("ssn"));

                // send user to part B
                RequestDispatcher rd=request.getRequestDispatcher("/registration_b.xhtml");
                rd.include(request, response);
            }
            // invalid userid/password; return to previous page
            else {
                RequestDispatcher rd=request.getRequestDispatcher("/registration_a.xhtml");
                rd.include(request, response);
            }
        }
        // process part B of registration form
        else {
            String address = request.getParameter("address");
            address += " " + request.getParameter("city");
            address += ", " + request.getParameter("state");
            address += " " + request.getParameter("zip");

            // concat all address fields together and trim to 40 characters (DB limit)
            studentInfo.setAddress(address.substring(0, Math.min(address.length(), 40)));

            // add new student to the database
            database.addStudent(studentInfo, dsName);

            // auto login the user
            request.setAttribute("userid", studentInfo.getUserid());
            request.setAttribute("password", studentInfo.getPassword());

            // redirect to the login servlet
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
            requestDispatcher.forward(request, response);
        }
    }

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
