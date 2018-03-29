import javax.ejb.Remote;

@Remote
public interface TextProcessor {
    int length(String text);
}