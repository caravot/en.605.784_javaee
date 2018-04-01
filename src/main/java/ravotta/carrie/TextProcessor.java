package ravotta.carrie;

import javax.ejb.Remote;

@Remote
public interface TextProcessor {
    int length(String text);
}