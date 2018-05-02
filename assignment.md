# Overview

In this module, students have to develop a message-driven bean as further enhancement to our "Student Registration" system. As you remember, in Module 11 you implemented a MessageReader class as a stand-alone Java application to receive and process a message sent by the RegistrarCourse.jsp.

I want you to change the implementation of the MessageReader from an application to a message-driven bean that will be hosted by the WebLogic Server. A permanently running MDB, MessageReader, receives the message from the RegistrarCourse.jsp and prints it into the WebLogic log file. The structure and the content of the message is the same as you implemented in the previous assignment with the JMS client.

The implementation will require making some changes to the WebLogic Server configuration related to the setting of JMS Server. Please use the Admin Console only to make those changes.