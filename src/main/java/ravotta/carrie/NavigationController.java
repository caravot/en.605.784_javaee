package ravotta.carrie;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class NavigationController implements Serializable {
    private boolean authenticated = false;

    // attempts left to attempt login
    private int loginAttemptsLeft = 4;

    @ManagedProperty(value="#{StudentInfo}")
    private StudentInfo studentInfo;

    /**
     * Attempt login
     */
    public void login() {
        // verify if the userid exists in the database
        boolean loggedIn = studentInfo.loginStudent();

        // bad login attempt
        if (!loggedIn) {
            // decrease login attempts user has left
            setLoginAttemptsLeft(getLoginAttemptsLeft() - 1);
        }

        // max attempts hit so close session
        if (getLoginAttemptsLeft() <= 0) {

        }

        setAuthenticated(loggedIn);
    }

    /**
     * Log user out
     */
    public void logout() {
        // clear user out
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        request.getSession(false).invalidate();

        // un-authenticate user
        setAuthenticated(false);
    }

    /**
     * Get login attempts left
     *
     * @return number of login attempts left
     */
    public int getLoginAttemptsLeft() {
        return this.loginAttemptsLeft;
    }

    /**
     * Set login attempts left
     *
     * @param loginAttemptsLeft
     */
    public void setLoginAttemptsLeft(int loginAttemptsLeft) {
        this.loginAttemptsLeft = loginAttemptsLeft;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Set if the user is authenticated
     *
     * @param authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }


    /**
     * Get the student information object
     *
     * @return studentinfo class object
     */
    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    /**
     * Set student information object
     *
     * @param studentInfo
     */
    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    /**
     * Navigate to next page
     *
     * @param action
     * @return string of next page
     */
    public String nextPage(String action) {
        return action;
    }
}
