/* ///该类为资源创建器，用作构造AST，并且整个过程中的所有需要的数据都存放在该类的静态成员中
//////构造器设置为私有，单例模式，只能通过getInstance()方法来获取类唯一实例 
//////使用初始化initResource()前，请设定好filepath*/
package st.pre;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.emf.ecore.EObject;

import parser.GenAST;
import st.basics.BasicsFactory;
import st.declarations.DeclarationsFactory;
import st.elements.ElementsFactory;
import st.expressions.ExpressionsFactory;
import st.initializer.InitializerFactory;
import st.literals.LiteralsFactory;
import st.pous.PousFactory;
import st.statements.StatementsFactory;
import st.types.TypesFactory;
import st.variables.VariablesFactory;

public final class ResourceBuilder {
    private static ResourceBuilder instance;

    public static String filepath;
    public static ParseTree tree;

/* ///引入EMF模型的factory方法，用于给节点创建语言模型实例 */
    public static BasicsFactory baseFactory = BasicsFactory.eINSTANCE;
    public static ElementsFactory elemFactory = ElementsFactory.eINSTANCE;
    public static LiteralsFactory liteFactory = LiteralsFactory.eINSTANCE;
    public static InitializerFactory initFactory = InitializerFactory.eINSTANCE;
    public static TypesFactory typeFactory = TypesFactory.eINSTANCE;
    public static VariablesFactory varFactory = VariablesFactory.eINSTANCE;
    public static PousFactory pouFactory = PousFactory.eINSTANCE;
    public static DeclarationsFactory declFactory = DeclarationsFactory.eINSTANCE;
    public static ExpressionsFactory exprFactory = ExpressionsFactory.eINSTANCE;
    public static StatementsFactory stmtFactory = StatementsFactory.eINSTANCE;

//////mapEmf用来存储ctx对应的实例化的对象，mapRuleName用来存储ctx的对应的字符串
//////mapRuleName的作用是，帮助ctx访问子节点的规则类型，方便ctx进行switch...case...语句 */
    public static Map<ParseTree, EObject> mapEmf = new HashMap<>();
    public static Map<ParseTree, String> mapNodeStr = new HashMap<>();

//////用一个HashMap来记录所有使用到的类型emf，包括自定义的类型和基本数据类型
    public static Map<String, EObject> mapTypeEmf = new HashMap<>();
//////用一个HashMap来记录全局变量
    public static Map<String, EObject> mapGlobalVarEmf = new HashMap<>();
//////用一个HashMap来记录全局范围的函数声明
    public static Map<String, EObject> mapGlobalFunEmf = new HashMap<>();
//////用一个HashMap来记录全局范围的函数块声明
    public static Map<String, EObject> mapGlobalFBEmf = new HashMap<>();

    private ResourceBuilder(String filepath) throws Exception{
        GenAST.generate(filepath);
        tree = GenAST.tree;
    }

    public static void initResource() throws Exception{
        if (instance == null){
            instance = new ResourceBuilder(filepath);
        }
    }

    public static ResourceBuilder getInstance() throws Exception{
        if (instance == null){
            instance = new ResourceBuilder(filepath);
        }

        return instance;
    }
}
