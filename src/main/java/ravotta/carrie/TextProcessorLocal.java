package ravotta.carrie;

import javax.ejb.Local;

@Local
public interface TextProcessorLocal {
    int length(String text);
}