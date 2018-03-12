package ravotta.carrie;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean(name="navigationController")
@RequestScoped
public class NavigationController implements Serializable {
    private boolean authenticated = false;

    /**
     * Creates a new instance of NavigationController
     */
    public NavigationController() {
    }

    public String pageA() {
        return "navA";
    }

    public String pageB() {
        return "navB";
    }

    /**
     * Utilizing implicit navigation, a page name can be returned from an
     * action method rather than listing a navigation-rule within faces-config.xml
     *
     * @return
     */
    public String nextPage() {
        // Perform some task, then implicitly list a page to render
        return "navC";
    }

    /**
     * Demonstrates the use of conditional navigation
     */
    public void login() {
        // Perform some task
        setAuthenticated(true);
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @param authenticated the authenticated to set
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
