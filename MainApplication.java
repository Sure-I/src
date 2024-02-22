import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class MainApplication {
    public static void main(String[] args) throws Exception {
        String filepath = args[0];

        try {
        ////////////////////this part of codes is used to build parsetree from input file

            CharStream stream = CharStreams.fromFileName(filepath);
            STLexer lexer = new STLexer(stream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            STParser parser = new STParser(tokens);
            ParseTree tree = parser.program();
            //System.out.println(tree.toStringTree(parser));

        /////////////////////////////////////////////////

        ////////////////////this part of codes is used to access nodes in parsetree and to instantiate EMF language model

            ParseTreeWalker myWalker = new ParseTreeWalker();
            MyListener myListener = new MyListener();
            myWalker.walk(myListener, tree);

        /////////////////////////////////////////////////
        } catch (Exception exception) {
            System.err.println("There was an error:\n + exception");
        }
    }
}