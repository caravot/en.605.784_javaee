package ravotta.carrie;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private static DatabasePopulator databasePopulator;
    public StudentInfo studentInfo = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        databasePopulator = new DatabasePopulator();
        studentInfo = new StudentInfo();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        ResultSet resultSet = databasePopulator.selectStudent(userid, password);

        try {
            if (resultSet.next()) {
                System.out.println("Hooray! Found the user");

                studentInfo = new StudentInfo(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        "8500 Upper Sky Way",
                        "123-456-7890",
                        "carrie@gmail.com"
                );
            } else {
                System.out.println("Oh no! Did not find the user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("/response.xhtml");
        rd.include(request, response);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }


}
