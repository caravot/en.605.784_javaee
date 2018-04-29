<%@ page import="javax.naming.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.jms.*" %>
<%@ page import="javax.rmi.PortableRemoteObject" %>

<html>
<head>
    <title>JMS Text</title>
</head>
<body>
<h1>JMS Test</h1>
<%! private int accessCount = 0; %>
<h2># accesses to this page: <%= ++accessCount %></h2>
<%
    String url = "t3://localhost:7001";
    String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    String TOPIC = "RegCourseTopic";
    String JMS_FACTORY = "weblogic.jms.ConnectionFactory";

    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);

    Context ctx = new InitialContext(env);

    TopicConnectionFactory tconFactory = (TopicConnectionFactory) PortableRemoteObject.narrow(
            ctx.lookup(JMS_FACTORY),
            TopicConnectionFactory.class);
    TopicConnection tcon = tconFactory.createTopicConnection();
    TopicSession tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
    Topic topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(TOPIC), Topic.class);
    TopicPublisher tpublisher = tsession.createPublisher(topic);
    TextMessage msg = tsession.createTextMessage();

    String message = "jmsTest was called ... " + accessCount;
    System.out.println("........" + message);

    tcon.start();
    msg.setText(message);
    tpublisher.publish(msg);
    tpublisher.close();
    tsession.close();
    tcon.close();
%>
</body>
</html>