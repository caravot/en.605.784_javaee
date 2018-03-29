import javax.ejb.Stateless;

@Stateless(mappedName = "TextProcessor")
public class TextProcessorBean implements TextProcessor, TextProcessorLocal {
    public TextProcessorBean() {
    }

    public int length(String text) { /* Business method implementation code */
        return text.length();
    }
}