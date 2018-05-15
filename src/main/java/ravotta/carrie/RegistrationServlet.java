package ravotta.carrie;

import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handle registration
 */
@SessionScoped
public class RegistrationServlet extends HttpServlet {
    // database commands
    private static Database database;

    @EJB
    Status status;

    @EJB
    RegistrarCourseBean registrarCourseBean;

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
     * Handle post action requests from user
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // complete course registration
        if (action.equalsIgnoreCase("registerORIG")) {
            String course = request.getParameter("course");
            RegistrationSupportBean registrationSupportBean = new RegistrationSupportBean();

            // set course information
            registrationSupportBean.setCourse(course);

            // get course ID
            String courseId = registrationSupportBean.getCourseId();

            // get number of students currently registered
            int currentRegistered = database.selectRegistrar(courseId);

            // registration is still open; add student
            if (currentRegistered < registrationSupportBean.getCourseCapacity()) {
                try {
                    database.addRegistrar(courseId, currentRegistered + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // send user to success page
                RequestDispatcher rd = request.getRequestDispatcher("/user/success.xhtml");
                rd.include(request, response);
            }
            // registration is full
            else {
                // send user to error page
                RequestDispatcher rd = request.getRequestDispatcher("/user/error.xhtml");
                rd.include(request, response);
            }
        }
        else if (action.equalsIgnoreCase("register")) {
            String course = request.getParameter("course");

            RegistrationSupportBean registrationSupportBean = new RegistrationSupportBean();

            // set course information
            registrationSupportBean.setCourse(course);

            // get course ID
            String courseId = registrationSupportBean.getCourseId();

            registrarCourseBean.setCourseId(Integer.parseInt(courseId));

            for (int i = 0; i < 5; i++) {
                int timeout = 15000;

                if (i % 2 == 0) {
                    timeout = 2000;
                }

                // get number of students currently registered
                int currentRegistered = database.selectRegistrar(courseId);


                System.out.println("+++++++++++++++++++++");
                System.out.println("Timeout: " + timeout);
                System.out.println("Total registered before insert: " + currentRegistered);

                registrarCourseBean.setCurrentRegistered(currentRegistered);
                registrarCourseBean.addRegistrar(timeout);

                int currentRegisteredAfter = database.selectRegistrar(courseId);
                System.out.println("Total registered after insert: " + currentRegisteredAfter);
            }
        }
    }

    /**
     * Handle get action requests from user
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String isPost = (String) session.getAttribute("isPost");

        // redirect to doPost
        if (isPost != null && isPost.equalsIgnoreCase("true")) {
            this.doPost(request, response);
        } else {
            String courseID = request.getParameter("courseID");

            // write response to user
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // validate course id
            if (validateForm(courseID)) {
                List<CoursesBean> registrationList = null;
                String htmlResult = "<h1>Status Report</h1>\n" +
                        "<table border='1'><tr><th>ID</th><th>Title</th><th>Student Registered</th></tr>";

                // only select one course
                if (courseID.length() > 0) {
                    registrationList = status.getStatus(Integer.parseInt(courseID));
                } else {
                    registrationList = status.getAllStatus();
                }

                // loop over results from status report request and generate table output
                for (int i = 0; i < registrationList.size(); i++) {
                    CoursesBean c = registrationList.get(i);

                    // create HTML table row string
                    htmlResult += "<tr><td>" + c.getCourseid() + "</td>" +
                            "<td>" + c.getCourse_name() + "</td>" +
                            "<td>" + c.getRegistrar().getNumber_students_registered() + "</td>" +
                            "</tr>";
                }

                out.println(htmlResult + "</table>");
            }
            // course id provided by user not found
            else {
                out.println("Error: Invalid course ID.");
            }
        }
    }

    /**
     * Validate the course exists
     *
     * @param courseId
     * @return if the course exists
     */
    private boolean validateForm(String courseId) {
        // if course id provided check that course exists
        if (courseId.length() > 0) {
            // if -1 returned the course doesn't exist
            int currentRegistered = database.selectRegistrar(courseId);

            if (currentRegistered == -1) {
                return false;
            }
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