import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.emf.ecore.EObject;

public class MainApplication {
	static ParseTree tree;

    static Map<ParseTree, EObject> mapEmf = new HashMap<>();
    static Map<ParseTree, String> mapNodeStr = new HashMap<>();
    static Map<String, EObject> mapVarEmf = new HashMap<>();
    static Map<String, EObject> mapTypeEmf = new HashMap<>();
    static Map<String, EObject> mapFunEmf = new HashMap<>();
    static Map<String, EObject> mapMethodEmf = new HashMap<>();
    static Map<String, EObject> mapFBEmf = new HashMap<>();

    public static void main(String[] args) throws Exception {
        String filepath = args[0];
        
        CharStream stream = CharStreams.fromFileName(filepath);
        STLexer lexer = new STLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        STParser parser = new STParser(tokens);
        tree = parser.program();
       
        try {
            ParseTreeWalker myWalker = new ParseTreeWalker();
            //首次遍历AST进行EMF模型实例化
            GenModelListener genModelListener = new GenModelListener();

            myWalker.walk(genModelListener, tree);
            mapEmf = genModelListener.mapEmf;
            mapNodeStr = genModelListener.mapNodeStr;
            mapVarEmf = genModelListener.mapVarEmf;
            mapTypeEmf = genModelListener.mapTypeEmf;
            mapFunEmf = genModelListener.mapFunEmf;
            mapMethodEmf = genModelListener.mapMethodEmf;
            mapFBEmf = genModelListener.mapFBEmf;
            
            //第二次遍历进行语法检查
            ModelCheckListener modelCheckListener = new ModelCheckListener();
            modelCheckListener.mapEmf = mapEmf;
            modelCheckListener.mapNodeStr = mapNodeStr;
            modelCheckListener.mapVarEmf = mapVarEmf;
            modelCheckListener.mapTypeEmf = mapTypeEmf;
            modelCheckListener.mapFunEmf = mapFunEmf;
            modelCheckListener.mapMethodEmf = mapMethodEmf;
            modelCheckListener.mapFBEmf = mapFBEmf;
            myWalker.walk(modelCheckListener, tree);

        } catch (Exception exception) {
            System.err.println("Error In MainApplication!!!");
        }
    }
}