package ravotta.carrie;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class myServlet extends javax.servlet.http.HttpServlet {
    @EJB
    private TextProcessorLocal tp;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, java.io.IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<body>\n" +
                "<h1>Hello length of your string is </h1>\n" +
                tp.length("123456789") +
                "</body></html>"
        );
    }
}