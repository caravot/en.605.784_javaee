package ravotta.carrie;

import javax.ejb.Stateless;

@Stateless(mappedName = "StatusBean")
public class Status {
    public Status() {
    }

    public int length(String text) { /* Business method implementation code */
        return text.length();
    }
}
