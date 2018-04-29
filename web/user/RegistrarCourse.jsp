
<%@ page import="ravotta.carrie.CoursesSupportBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.jms.*" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="ravotta.carrie.StudentInfo" %>

<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>

<%
    String action = request.getParameter("action");

    if (action != null && action.equalsIgnoreCase("register")) {
        String url = "t3://localhost:7001";
        String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
        String TOPIC = "RegCourseTopic";
        String JMS_FACTORY = "weblogic.jms.ConnectionFactory";

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);

        Context ctx = new InitialContext(env);

        TopicConnectionFactory tconFactory = null;

        try {
            tconFactory = (TopicConnectionFactory) PortableRemoteObject.narrow(
                    ctx.lookup(JMS_FACTORY),
                    TopicConnectionFactory.class);
            TopicConnection tcon = tconFactory.createTopicConnection();
            TopicSession tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(TOPIC), Topic.class);
            TopicPublisher tpublisher = tsession.createPublisher(topic);
            TextMessage msg = tsession.createTextMessage();
            
            // get session scoped bean
            HttpSession hsession = request.getSession();

            // logged in student's information
            StudentInfo studentInfo = (StudentInfo) hsession.getAttribute("StudentInfo");

            // get courseid/name from concatenated string
            String[] parts = request.getParameter("course").split("_");
            String courseId = parts[0];
            String courseName = parts[1];

            // create logging message
            String message = "User attempted to register: ";
            message += "UserId: " + studentInfo.getUserid();
            message += ", Student_Name: " + studentInfo.getFirst_name() + " " + studentInfo.getLast_name();
            message += ", Course_ID: " + courseId;
            message += ", Course_Name: " + courseName;
            message += ", Date_of_Registration: " + new Date();

            System.out.println("JMS sent: " + message);

            tcon.start();
            msg.setText(message);
            tpublisher.publish(msg);
            tpublisher.close();
            tsession.close();
            tcon.close();

            // send to registration page
            RequestDispatcher rd = request.getRequestDispatcher("/registration");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<html>
<head>
    <title>SRS - Course Registration</title>
</head>
<body>
<h1>Course Registration</h1>
<form action="RegistrarCourse.jsp" method="post">
    <input type="hidden" name="action" value="register">
    <select id="course" name="course">
        <%
            CoursesSupportBean coursesSupportBean = new CoursesSupportBean();
            List<String> courses = coursesSupportBean.getCourses();
            for (String cname : courses) {
        %>
            <option value="<%= cname %>"><%= cname %></option>
        <% } %>
    </select>

    <input type="submit" value="Register">
</form>
</body>
</html>