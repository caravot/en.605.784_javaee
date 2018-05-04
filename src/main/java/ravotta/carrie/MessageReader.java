package ravotta.carrie;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.*;

import weblogic.ejbgen.Constants;
import weblogic.ejbgen.MessageDriven;

/**
 * Demonstrates usage of a Message-Driven EJB. * *
 * @author Copyright (c) 1999-2006 by BEA Systems, Inc. All Rights Reserved.
 */
@MessageDriven(
        maxBeansInFreePool = "1",
        destinationType = "javax.jms.Topic",
        initialBeansInFreePool = "1",
        transTimeoutSeconds = "2",
        defaultTransaction = MessageDriven.DefaultTransaction.REQUIRED,
        durable = Constants.Bool.FALSE,
        ejbName = "MessageReader",
        destinationJndiName = "RegCourseTopic"
)
public class MessageReader implements MessageDrivenBean, MessageListener {
    private static final boolean VERBOSE = true;
    private MessageDrivenContext m_context;
    private int m_tradeLimit;

    /**
     * Sets the session context. * * @param ctx MessageDrivenContext Context for session
     */
    public void setMessageDrivenContext(MessageDrivenContext ctx) {
        m_context = ctx;
    }

    /**
     * Retrieve the int value of the TextMessage and * increment the RMI counter by that much.
     */
    public void onMessage(Message msg) {
        String msgText;
        try {
            if (msg instanceof TextMessage) {
                msgText = ((TextMessage) msg).getText();
            } else if (msg instanceof MapMessage) {
                MapMessage msg2 = (MapMessage) msg;

                msgText = String.format("UserId: {%s}, " +
                                "Student_Name: {%s}, " +
                                "Course_ID: {%s}, " +
                                "Course_Name: {%s}, " +
                                "Date_of_Registration: {%s}",
                        msg2.getString("UserId"),
                        msg2.getString("Student_Name"),
                        msg2.getString("Course_ID"),
                        msg2.getString("Course_Name"),
                        msg2.getString("Date_of_Registration"));
            } else {
                msgText = msg.toString();
            }

            System.out.println("JMS received: " + msgText);
        } catch (Exception ex) {
            System.err.println("An exception occurred: " + ex.getMessage());

        }
    }

    /**
     * This method is required by the EJB Specification, * but is not used by this example.
     */
    public void ejbCreate() {
         System.out.println("Encountered the following naming exception: ");
    }

    /**
     * This method is required by the EJB Specification, * but is not used by this example.
     */
    public void ejbRemove() {
    }

    static void p(String s) {
        System.out.println("*** " + s);
    }

    private void log(String s) {
        if (VERBOSE) System.out.println(s);
    }
}
