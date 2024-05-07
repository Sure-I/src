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
    static Map<String, EObject> mapTypeEmf = new HashMap<>();
    static Map<String, EObject> mapGlobalVarEmf = new HashMap<>();
    static Map<String, EObject> mapGlobalFunEmf = new HashMap<>();
    static Map<String, EObject> mapGlobalFBEmf = new HashMap<>();

    public static void main(String[] args) throws Exception {
        String filepath = args[0];
        
        CharStream stream = CharStreams.fromFileName(filepath);
        STLexer lexer = new STLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        STParser parser = new STParser(tokens);
        tree = parser.program();
       

            ParseTreeWalker myWalker = new ParseTreeWalker();
            //首次遍历AST进行EMF模型实例化
            GenModelListener genModelListener = new GenModelListener();

            myWalker.walk(genModelListener, tree);
            mapEmf = genModelListener.mapEmf;
            mapNodeStr = genModelListener.mapNodeStr;
            mapTypeEmf = genModelListener.mapTypeEmf;
            mapGlobalVarEmf = genModelListener.mapGlobalVarEmf;
            mapGlobalFunEmf = genModelListener.mapGlobalFunEmf;
            mapGlobalFBEmf = genModelListener.mapGlobalFBEmf;
            
            //第二次遍历进行语法检查
            ModelCheckListener modelCheckListener = new ModelCheckListener();
            modelCheckListener.mapEmf = mapEmf;
            modelCheckListener.mapNodeStr = mapNodeStr;
            modelCheckListener.mapTypeEmf = mapTypeEmf;
            modelCheckListener.mapGlobalVarEmf = mapGlobalVarEmf;
            modelCheckListener.mapGlobalFunEmf = mapGlobalFunEmf;
            modelCheckListener.mapGlobalFBEmf = mapGlobalFBEmf;
            myWalker.walk(modelCheckListener, tree);


    }
}