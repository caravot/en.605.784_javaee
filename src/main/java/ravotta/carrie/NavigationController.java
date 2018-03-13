package ravotta.carrie;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class NavigationController implements Serializable {
    private boolean authenticated = false;

    // attempts left to attempt login
    private int loginAttemptsLeft;

    @Inject
    private StudentInfo studentInfo;

    /**
     * Creates a new instance of NavigationController
     */
    public NavigationController() {
    }

    /**
     * Attempt login
     */
    public void login() {
        // database commands
        Database database = new Database();

        // verify if the userid exists in the database
        boolean userIdExists = database.validUserid(studentInfo.getUserid());

        // decrease login attempts user has left
        this.loginAttemptsLeft--;

        this.authenticated = userIdExists;
    }

    /**
     * Log user out
     */
    public void logout() {
        this.authenticated = false;
    }

    /**
     * @return number of login attempts left
     */
    public int loginAttemptsLeft() {
        return loginAttemptsLeft;
    }

    public int getLoginAttemptsLeft() {
        return loginAttemptsLeft;
    }

    public void setLoginAttemptsLeft(int loginAttemptsLeft) {
        this.loginAttemptsLeft = loginAttemptsLeft;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public String nextPage(String action) {
        return action;
    }
}
