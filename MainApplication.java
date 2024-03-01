import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class MainApplication {
    public static void main(String[] args) throws Exception {
        String filepath = args[0];

        try {
            ParseTreeWalker myWalker = new ParseTreeWalker();
            ModelCheckListener modelCheckListener = new ModelCheckListener(filepath);
            myWalker.walk(modelCheckListener, modelCheckListener.tree);

        } catch (Exception exception) {
            System.err.println("Error In MainApplication!!!");
        }
    }
}