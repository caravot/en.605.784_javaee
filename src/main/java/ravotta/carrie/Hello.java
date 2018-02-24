package ravotta.carrie;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@RequestScoped
@ManagedBean(name = "hello")
public class Hello {
    private String name;

    public Hello() {
    }

    public String getName() {
        return name;
    }

    public void setName(String user_name) {
        this.name = user_name;
    }
}
