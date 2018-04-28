# Overview

In this module, students have to develop JMS-based enhancement to the "Student Registration" system. As you remember, we already implemented the Course use case where a student can register for the course based on availability.

Your assignment is to add one more function into this business logic. During the process we want to have some kind of notification to capture any activity within the Course use case. Usually, this is an email or log file; for this assignment we will use a log file. (To send an email we would use the JavaMail API.)

Let's create a Java-based client/receiver of the message sent after the process of registering. Specifically, every time a student makes an attempt to register for the course, the RegistrarCourse.jsp (see previous homework on JSPs) will send a MapMessage to the designated topic, RegCourseTopic, with the following keys within a message:

* User_ID
* Student_Name
* Course_ID
* Course_Name
* Date_of_Registration

A permanently running client/receiver, MessageReader, will receive the message and send a string to be printed on standard output (console) showing all the elements of the message.

It remains running and waiting for the next messages to arrive. It repeats the same process for every message.

The implementation will require making some changes on WebLogic Server configuration related to setting of JMS Server. Please use the Admin Console only to make those changes.