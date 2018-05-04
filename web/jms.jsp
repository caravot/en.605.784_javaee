
<%@ page import="ravotta.carrie.CoursesSupportBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.jms.*" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>

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
            MapMessage msg = tsession.createMapMessage();

            // create logging message
            msg.setString("UserId", "1");
            msg.setString("Student_Name", "Carrie Ravotta");
            msg.setString("Course_ID", "1");
            msg.setString("Course_Name", "Testing Course");
            msg.setString("Date_of_Registration", new Date().toString());

            System.out.println("JMS sent");

            tcon.start();
            tpublisher.publish(msg);
            tpublisher.close();
            tsession.close();
            tcon.close();
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
<form action="jms.jsp" method="post">
    <input type="hidden" name="action" value="register">
    <input type="submit" value="Register">
</form>
</body>
</html>