import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class ClientTextProcessor {
    @EJB
    static TextProcessor tp;

    public static void main(String[] args) {
        ClientTextProcessor clienttext = new ClientTextProcessor();
        System.out.println("INPUT TEXT: " + args[0]);
        clienttext.doTest(args[0]);
    }

    void doTest(String in) {
        InitialContext ic;
        try {
            Hashtable ht = new Hashtable();

            // set WLS connection information
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            ht.put(Context.PROVIDER_URL, "t3://localhost:7001");

            ic = new InitialContext(ht);
            tp = (TextProcessor) ic.lookup("TextProcessor#TextProcessor");

            // output variables
            System.out.println("ClientText Lookup");
            System.out.println("Printing return result");

            int i = tp.length(in);
            System.out.println(i);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}