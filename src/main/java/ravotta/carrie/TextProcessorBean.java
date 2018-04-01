package ravotta.carrie;

import javax.ejb.Stateless;

@Stateless(mappedName = "TextProcessor")
public class TextProcessorBean implements TextProcessor, TextProcessorLocal {
    public TextProcessorBean() {
    }

    /* Business method implementation code */
    public int length(String text) {
        return text.length();
    }
}