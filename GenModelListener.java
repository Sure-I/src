/* ///针对Listener进行方法重载，用于实例化AST相对应的EMF语言模型
//////目前已完成如下部分
//////expression
//////基本数据类型
//////数字类常量
//////字符类常量
//////基础数据类型的变量声明及初始化
//////stmt_list
//////assign_stmt
//////if_stmt
//////for_stmt
//////while_stmt, repeat_stmt, 这两项未经测试
//////完成所有statement
//////subrange_type_decl
//////subrange_type_init
//////enum_type_decl
//////enum_type_init
//////array_type_decl
//////array_type_init
//////struct_type_decl
//////struct_type_init
//////function
//////fB
//////program
////////////////////////////////////////////////////////////////////////////////////////////// */

/* ///整体思路如下
//////针对生成语法树的每一个节点进行遍历，节点会有enter()和exit()一对方法
//////根据情况不同，在enter或exit时创建对应的emf模型(大多数情况为exit时)
//////enter一般用于传递namespace
//////考虑到AST中的一些节点和其子节点存在reference关系，需要在父节点或子节点中设定
//////这里使用规则名ruleName来进行筛选并执行操作
///////////////////////////////////////////////////////////////////////////////////////////// */

import java.io.IOException;
import java.io.PipedOutputStream;
import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.plaf.nimbus.State;

import org.antlr.v4.codegen.model.decl.Decl;
import org.antlr.v4.parse.ANTLRParser.delegateGrammar_return;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
/* import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl; */

import st.basics.*;
import st.elements.*;
import st.literals.*;
import st.initializer.*;
import st.types.*;
import st.variables.*;
import st.pous.*;
import st.declarations.*;
import st.expressions.*;
import st.statements.*;




public class GenModelListener extends STBaseListener{

/* ///引入EMF模型的factory方法，用于给节点创建语言模型实例 */
    BasicsFactory baseFactory = BasicsFactory.eINSTANCE;
    ElementsFactory elemFactory = ElementsFactory.eINSTANCE;
    LiteralsFactory liteFactory = LiteralsFactory.eINSTANCE;
    InitializerFactory initFactory = InitializerFactory.eINSTANCE;
    TypesFactory typeFactory = TypesFactory.eINSTANCE;
    VariablesFactory varFactory = VariablesFactory.eINSTANCE;
    PousFactory pouFactory = PousFactory.eINSTANCE;
    DeclarationsFactory declFactory = DeclarationsFactory.eINSTANCE;
    ExpressionsFactory exprFactory = ExpressionsFactory.eINSTANCE;
    StatementsFactory stmtFactory = StatementsFactory.eINSTANCE;


/* ///实例化过程使用多个HashMap
//////mapEmf用来存储ctx对应的实例化的对象，mapRuleName用来存储ctx的对应的字符串
//////mapRuleName的作用是，帮助ctx访问子节点的规则类型，方便ctx进行switch...case...语句 */
    public Map<ParseTree, EObject> mapEmf = new HashMap<>();
    public Map<ParseTree, String> mapNodeStr = new HashMap<>();

//////用一个HashMap来记录所有使用到的类型emf，包括自定义的类型和基本数据类型
    public Map<String, EObject> mapTypeEmf = new HashMap<>();
//////用一个HashMap来记录全局变量
    public Map<String, EObject> mapGlobalVarEmf = new HashMap<>();
//////用一个HashMap来记录全局范围的函数声明
    public Map<String, EObject> mapGlobalFunEmf = new HashMap<>();
//////用一个HashMap来记录全局范围的函数块声明
    public Map<String, EObject> mapGlobalFBEmf = new HashMap<>();


//////setFromChildEmf()方法，获取某个子节点的emf并关联
    private void setFromChildEmf(ParserRuleContext ctx, int i){
        EObject emf = mapEmf.get(ctx.getChild(i));
        mapEmf.put(ctx, emf);
    }
//////setFromParentEmf()方法，获取父节点的emf并关联
    private void setFromParentEmf(ParserRuleContext ctx){
        EObject emf = mapEmf.get(ctx.getParent());
        mapEmf.put(ctx, emf);
    }
//////getEmf()方法，获取节点自身的emf并返回该对象
    private EObject getEmf(ParserRuleContext ctx){
        EObject emf = mapEmf.get(ctx);
        return emf;
    }
//////getChildEmf()，获取第i个子节点的emf并返回该对象
    private EObject getChildEmf(ParserRuleContext ctx, int i){
        EObject emf = mapEmf.get(ctx.getChild(i));
        return emf;
    }
//////getChildEmf()，获取父节点的emf并返回该对象
    private EObject getParentEmf(ParserRuleContext ctx){
        EObject emf = mapEmf.get(ctx.getParent());
        return emf;
    }
//////getVarInNamespace(),获取Namespace中的Variable
    private Variable getVarInNamespace(String varName, Namespace namespaceEmf){
        if(namespaceEmf.getDeclType().getLiteral() == "GLOBAl"){
            return (Variable)mapGlobalVarEmf.get(varName);
        }
        POUDeclaration POUDecl = (POUDeclaration)(namespaceEmf.getNamespace()); 
        return getVarInDecl(varName, POUDecl);
    }
//////getVarInDecl(),获取POUDeclaration中的Variable
    private Variable getVarInDecl(String varName, POUDeclaration POUDecl){
        for(int i = 0; i < POUDecl.getVariable().size(); i++){
            if(varName.equals(POUDecl.getVariable().get(i).getName())) 
                return POUDecl.getVariable().get(i);
        }

        return null;
    }
//////getFunInNamespace(),获取Namespace中的Variable
    private Function getFunInNamespace(String funName, Namespace namespaceEmf){
        if(namespaceEmf.getDeclType().getLiteral() == "GLOBAl"){
            return (Function)mapGlobalFunEmf.get(funName);
        }
        POUDeclaration POUDecl = (POUDeclaration)(namespaceEmf.getNamespace()); 
        for(int i = 0; i < POUDecl.getFunction().size(); i++){
            if(funName.equals(POUDecl.getFunction().get(i).getName()))
                return POUDecl.getFunction().get(i);
        }

        return null;
    }

    

/* ///获取ruleNode的规则名 */
    @Override public void enterEveryRule(ParserRuleContext ctx) { 
        String ruleName = STParser.ruleNames[ctx.getRuleIndex()];
        mapNodeStr.put(ctx, ruleName);
        //System.out.println(ruleName);
    }

	@Override public void exitEveryRule(ParserRuleContext ctx) { 

    }

/* ///TerminalNode的规则名并不会在AST中被表示出来，所以直接获取terminalNode的字符串 */
	@Override public void visitTerminal(TerminalNode node) { 
/*         ///下面三行代码用于获取TerminalNode的规则名
        String ruleName = STParser.VOCABULARY.getSymbolicName(node.getSymbol().getType());
        mapNodeStr.put(node, ruleName);
        //System.out.println(ruleName); */

        String nodeStr = node.getSymbol().getText();
        mapNodeStr.put(node, nodeStr);
        //System.out.println(nodeStr);
    }

	@Override public void visitErrorNode(ErrorNode node) { }

/* ///Program为根节点，表示整个源代码程序不对应EMF模型，
//////在此节点中进行所有实例化模型的持久化 */
/*     @Override public void enterProgram(STParser.ProgramContext ctx){ }

    @Override public void exitProgram(STParser.ProgramContext ctx) { } */

/* ///以下部分为所有的节点的用户操作
//////整体逻辑为
//////在enter()方法进入节点时，实例化对应的EMF模型对象存储至HashMap中
//////根据walker的遍历规则，完成enter()方法后，会进行递归，遍历子树直至叶子节点，再执行exit()方法
//////在exit()方法退出节点时，完成模型中的引用设置 */


/* ////////////////////////////////////////////////////////////////////////////
//////以下是expression的部分
//////exit节点时对子节点进行判断，确定是哪一种expression
//////实例化emf并且配置属性中的reference和attribute */

	@Override public void enterExpression(STParser.ExpressionContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                //System.err.println("no namespace in parentNode in Expression");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterExpression!!!");
        }
    }

	@Override public void exitExpression(STParser.ExpressionContext ctx) { 
        ParseTree childNode = ctx.getChild(0);
        Namespace namespaceEmf = (Namespace)getEmf(ctx);

        if(childNode instanceof ErrorNode){ 

        }
        else if(childNode instanceof TerminalNode){
            TerminalNode tNode = (TerminalNode)childNode;
            UnaryExpression emf = exprFactory.createUnaryExpression();
            emf.setNamespace(namespaceEmf);
            mapEmf.put(ctx, emf);
            emf.setTestString("unary_expr_emf");

            emf.setExpression( (Expression)getChildEmf(ctx, 1) );
            emf.setType(emf.getExpression().getType());
            String nodeStr = mapNodeStr.get(tNode);
            switch(nodeStr){
                case "(":
                    //System.out.println("PARETHESES");
                    emf.setOperator(UnaryOperator.PARENTHESES);
                    break;
                case "^":
                    emf.setOperator(UnaryOperator.DEREFERENCE);
                    break;
                case "-":
                    emf.setOperator(UnaryOperator.NEGATION);
                    break;
                case "+":
                    emf.setOperator(UnaryOperator.UNRAYPLUS);
                    break;
                case "NOT":
                    emf.setOperator(UnaryOperator.COMPLEMENT);
                    break;
                default: //System.out.println("expression error!!!");
            }
        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "expression" ){
            BinaryExpression emf = exprFactory.createBinaryExpression();
            emf.setNamespace(namespaceEmf);
            mapEmf.put(ctx, emf);

            emf.setTestString("binary_expr_emf");
            emf.setFirstExpression( (Expression)getChildEmf(ctx, 0) );
            emf.setSecondExpression( (Expression)getChildEmf(ctx, 2) );
            emf.setType(emf.getFirstExpression().getType());

            String nodeStr = mapNodeStr.get(ctx.getChild(1));
            switch(nodeStr){
                case "**":
                    emf.setOperator(BinaryOperator.EXPONENTIATION);
                    break;
                case "*":
                    emf.setOperator(BinaryOperator.MULTIPLY);
                    break;
                case "/":
                    emf.setOperator(BinaryOperator.DIVIDE);
                    break;
                case "MOD":
                    emf.setOperator(BinaryOperator.MODULO);
                    break;
                case "+":
                    emf.setOperator(BinaryOperator.ADD);
                    break;
                case "-":
                    emf.setOperator(BinaryOperator.SUBSTRACT);
                    break;
                case "=":
                    emf.setOperator(BinaryOperator.EQUAL);
                    break;
                case "<>":
                    emf.setOperator(BinaryOperator.INEQUAL);
                    break;
                case "<":
                    emf.setOperator(BinaryOperator.LESS_THAN);
                    break;
                case "<=":
                    emf.setOperator(BinaryOperator.LESS_THAN_OR_EQUAL);
                    break;
                case ">":
                    emf.setOperator(BinaryOperator.GREATER_THAN);
                    break;
                case ">=":
                    emf.setOperator(BinaryOperator.GTEATER_THAN_OR_EQUAL);
                    break;
                case "&":
                    emf.setOperator(BinaryOperator.BOOLEAN_AND);
                    break;
                case "AND":
                    emf.setOperator(BinaryOperator.BOOLEAN_AND);
                    break;
                case "XOR":
                    emf.setOperator(BinaryOperator.BOOLEAN_EXCLUSIVE_OR);
                    break;
                case "OR":
                    emf.setOperator(BinaryOperator.BOOLEAN_OR);
                    break;
                default: //System.out.println("expression error!!!");
            }

        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "constant" ){
            LiteralExpression emf = exprFactory.createLiteralExpression();
            emf.setNamespace(namespaceEmf);
            mapEmf.put(ctx, emf);
            emf.setTestString("constant_expr_emf");
            emf.setLiteral( (Literal)getChildEmf(ctx, 0) );

            if(emf.getLiteral().getType() == LiteralType.INTEGER){
                if(mapTypeEmf.get("INTEGER") == null){
                    TypeDeclaration typeDeclEmf = declFactory.createTypeDeclaration();
                    UserDefinedDataType typeEmf = typeFactory.createUserDefinedDataType();
                    typeDeclEmf.setType(typeEmf);
                    typeEmf.setTypeDeclaration(typeDeclEmf);
                    typeEmf.setName("INTEGER");
                    mapTypeEmf.put("INTEGER", typeEmf);
                    emf.setType(typeEmf);
                }
                else{emf.setType((Type)mapTypeEmf.get("INTEGER"));}
            }
            else if(emf.getLiteral().getType() == LiteralType.SINGLE_BYTE_CHAR){
                if(mapTypeEmf.get("SINGLE_BYTE_CHAR") == null){
                    TypeDeclaration typeDeclEmf = declFactory.createTypeDeclaration();
                    UserDefinedDataType typeEmf = typeFactory.createUserDefinedDataType();
                    typeDeclEmf.setType(typeEmf);
                    typeEmf.setTypeDeclaration(typeDeclEmf);
                    typeEmf.setName("SINGLE_BYTE_CHAR");
                    mapTypeEmf.put("SINGLE_BYTE_CHAR", typeEmf);
                    emf.setType(typeEmf);
                }
                else{emf.setType((Type)mapTypeEmf.get("SINGLE_BYTE_CHAR"));}
            }
            else if(emf.getLiteral().getType() == LiteralType.REAL){
                if(mapTypeEmf.get("REAL") == null){
                    TypeDeclaration typeDeclEmf = declFactory.createTypeDeclaration();
                    UserDefinedDataType typeEmf = typeFactory.createUserDefinedDataType();
                    typeDeclEmf.setType(typeEmf);
                    typeEmf.setTypeDeclaration(typeDeclEmf);
                    typeEmf.setName("REAL");
                    mapTypeEmf.put("REAL", typeEmf);
                    emf.setType(typeEmf);
                }
                else{emf.setType((Type)mapTypeEmf.get("REAL"));}
            }
            else{}
        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "enum_value" ){
            LiteralExpression emf = exprFactory.createLiteralExpression();
            emf.setNamespace(namespaceEmf);
            mapEmf.put(ctx, emf);

            emf.setTestString("enum_value_expr_emf");
        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "var_access" ){
            VariableExpression emf = exprFactory.createVariableExpression();
            emf.setNamespace(namespaceEmf);
            mapEmf.put(ctx, emf);
            emf.setVariable((Variable)getChildEmf(ctx, 0));
            emf.setType(emf.getVariable().getType());

            emf.setTestString("var_access_expr_emf");
        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "func_call" ){
            setFromChildEmf(ctx, 0);
            FunctionCall emf = (FunctionCall)getEmf(ctx);
            emf.setNamespace(namespaceEmf);
            emf.setTestString("func_call_expr_emf");
        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "quote_value" ){ 
            QuotevalueExpression emf = exprFactory.createQuotevalueExpression();
            emf.setNamespace(namespaceEmf);
            mapEmf.put(ctx, emf);
            QuoteValue valueEmf = (QuoteValue)mapEmf.get(ctx.getChild(0));
            emf.setQuoteValue(valueEmf);
            emf.setType(valueEmf.getQuoteType());
        }
        else{ }
    }

/* /////////////////////////////////////////////////////////////////////////
//////以下是关于statement的部分
//////
////// */
    @Override public void enterStatements(STParser.StatementsContext ctx) { 
        try{
            StatementBody emf = stmtFactory.createStatementBody();
            Namespace namespaceEmf = baseFactory.createNamespace();

            emf.setNamespace(namespaceEmf);

            String parentStr = mapNodeStr.get(ctx.getParent());
            switch(parentStr){
                case "prog_decl":
                    namespaceEmf.setDeclType(DeclType.PROGRAM);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "func_decl":
                    namespaceEmf.setDeclType(DeclType.FUNCTION);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "fb_decl":
                    namespaceEmf.setDeclType(DeclType.FB);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "method_decl":
                    namespaceEmf.setDeclType(DeclType.METHOD);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                default: 
                    if(mapEmf.get(ctx.getParent()) instanceof Namespace){
                        emf.setNamespace((Namespace)mapEmf.get(ctx.getParent()));
                    }
                    else{

                    }
                    break;
            }

            mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterStatements!!!");
        }
    }

    @Override public void exitStatements(STParser.StatementsContext ctx) { 
        try{
            StatementBody emf = (StatementBody)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                if(childNode instanceof STParser.StmtContext){
                    Statement childEmf0 = (Statement)getChildEmf(ctx, i);
                    childEmf0.setTestString("stmt"+ i);
                    emf.getStatement().add(childEmf0);
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitStatements!!!");
        }
    }

    @Override public void enterStmt(STParser.StmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else if(getParentEmf(ctx) instanceof StatementBody){
                Namespace namespaceEmf = ((StatementBody)getParentEmf(ctx)).getNamespace();
                mapEmf.put(ctx, namespaceEmf);
            }
        } catch(Exception exception){
            System.err.println("Error In enterStmt!!!");
        }
    }

    @Override public void exitStmt(STParser.StmtContext ctx) { 
        setFromChildEmf(ctx, 0);
        //System.out.println((Statement)getEmf(ctx).getTestString());
    }

    @Override public void enterSelection_stmt(STParser.Selection_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in selection_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterSelection_stmt!!!");
        }
    }

	@Override public void exitSelection_stmt(STParser.Selection_stmtContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterIteration_stmt(STParser.Iteration_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in iteration_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterIteration_stmt!!!");
        }
    }

	@Override public void exitIteration_stmt(STParser.Iteration_stmtContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterIf_stmt(STParser.If_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in if_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterIf_stmt!!!");
        }
    }

    @Override public void exitIf_stmt(STParser.If_stmtContext ctx) { 
        IfStatement emf = stmtFactory.createIfStatement();
        emf.setTestString("if_stmt_emf");
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = mapNodeStr.get(ctx.getChild(i));
            switch(childNodeStr){
                case "expression":
                    emf.setCondition((Expression)getChildEmf(ctx, i));
                    break;
                case "stmt_list":
                    emf.setThenStatement((StatementBody)getChildEmf(ctx, i));
                    break;
                case "elsif_stmt":
                    emf.getElseIfStatement().add((IfStatement)getChildEmf(ctx, i));
                    break;
                case "else_stmt":
                    emf.setElseStatement((StatementBody)getChildEmf(ctx, i));
                    break;
                default: ;
            }
        }
    }

    @Override public void enterElsif_stmt(STParser.Elsif_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in elseif_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterElseif_stmt!!!");
        }
    }

	@Override public void exitElsif_stmt(STParser.Elsif_stmtContext ctx) { 
        IfStatement emf = stmtFactory.createIfStatement();
        emf.setTestString("if_stmt_emf");
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = mapNodeStr.get(ctx.getChild(i));
            switch(childNodeStr){
                case "expression":
                    emf.setCondition((Expression)getChildEmf(ctx, i));
                    break;
                case "stmt_list":
                    emf.setThenStatement((StatementBody)getChildEmf(ctx, i));
                    break;
                default: //System.out.println("elsif_stmt error!!!");
            }
        }
    }

	@Override public void enterElse_stmt(STParser.Else_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in else_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterElse_stmt!!!");
        }
    }

	@Override public void exitElse_stmt(STParser.Else_stmtContext ctx) { 
        setFromChildEmf(ctx, 1);
    }

    @Override public void enterAssign_stmt(STParser.Assign_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in assign_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterAssign_stmt!!!");
        }
    }

	@Override public void exitAssign_stmt(STParser.Assign_stmtContext ctx) { 
        String childNodeStr = mapNodeStr.get(ctx.getChild(0));
        switch(childNodeStr){
            case "variable": 
                AssignmentStatement emf0 = stmtFactory.createAssignmentStatement();
                Namespace namespaceEmf = (Namespace)getEmf(ctx);
                emf0.setNamespace(namespaceEmf);
                emf0.setTestString("assign_stmt_emf");
                mapEmf.put(ctx, emf0);
                for(int i = 0; i < ctx.getChildCount(); i++){
                    EObject childEmf = getChildEmf(ctx, i);
                    if(childEmf instanceof Variable){
                        Variable childEmf0 = (Variable)childEmf;
                        emf0.setVariable(childEmf0);
                    }
                    else if(childEmf instanceof Expression){
                        Expression childEmf1 = (Expression)childEmf;
                        emf0.setExpression(childEmf1);
                    }
                    else if(childEmf instanceof QuoteValue){
                        QuoteValue childEmf1 = (QuoteValue)childEmf;
                        emf0.setQuoteValue(childEmf1);
                    }
                    else{ }
                }
                break;
            case "ref_assign": 

                break;
            case "assignment_attempt": 

                break;
        }
    }

    @Override public void enterFor_stmt(STParser.For_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in for_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterFor_stmt!!!");
        }
    }

	@Override public void exitFor_stmt(STParser.For_stmtContext ctx) { 
        setFromChildEmf(ctx, 3);
        ForStatement emf = (ForStatement)getEmf(ctx);
        for(int i = 0; i < ctx.getChildCount(); i++){
            ParseTree childNode = ctx.getChild(i);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "control_variable":
                    emf.setControlVariable(childNode.getText());
                    break;
                case "stmt_list":
                    emf.setStatementBody((StatementBody)mapEmf.get(childNode));
                    break;
                default: ;
            }
        }

        //System.out.println(((LiteralExpression)emf.getEndExpression()).getLiteral().getValue());
    }

	@Override public void enterFor_list(STParser.For_listContext ctx) { }

	@Override public void exitFor_list(STParser.For_listContext ctx) { 
        ForStatement emf = stmtFactory.createForStatement();
        mapEmf.put(ctx, emf);
        emf.setTestString("for_stmt_emf");
        for(int i = 0; i < ctx.getChildCount(); i++){
            ParseTree childNode = ctx.getChild(i);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "start_expr":
                    emf.setStartExpression((Expression)getChildEmf(ctx, i));
                    break;
                case "end_expr":
                    emf.setEndExpression((Expression)getChildEmf(ctx, i));
                    break;
                case "step_expr":
                    emf.setStepExpression((Expression)getChildEmf(ctx, i));
                    break;
                default: ;
            }
        }
    }

    @Override public void enterStart_expr(STParser.Start_exprContext ctx) { }

	@Override public void exitStart_expr(STParser.Start_exprContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

	@Override public void enterEnd_expr(STParser.End_exprContext ctx) { }

	@Override public void exitEnd_expr(STParser.End_exprContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

	@Override public void enterStep_expr(STParser.Step_exprContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterWhile_stmt(STParser.While_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in while_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterWhile_stmt!!!");
        }
    }

	@Override public void exitWhile_stmt(STParser.While_stmtContext ctx) { 
        WhileStatement emf = stmtFactory.createWhileStatement();
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            ParseTree childNode = ctx.getChild(i);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "expression":
                    emf.setCondition((Expression)getChildEmf(ctx, i));
                    break;
                case "stmt_list":
                    emf.setStatementBody((StatementBody)getChildEmf(ctx, i));
                    break;
                default: ;
            }
        }
    }

	@Override public void enterRepeat_stmt(STParser.Repeat_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in repeat_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterRepeat_stmt!!!");
        }
    }

	@Override public void exitRepeat_stmt(STParser.Repeat_stmtContext ctx) { 
        RepeatStatement emf = stmtFactory.createRepeatStatement();
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            ParseTree childNode = ctx.getChild(i);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "stmt_list":
                    emf.setStatementBody((StatementBody)getChildEmf(ctx, i));
                    break;
                case "expression":
                    emf.setCondition((Expression)getChildEmf(ctx, i));
                    break;
                default: ;
            }
        }
    }

    @Override public void enterExit_stmt(STParser.Exit_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in exit_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterExit_stmt!!!");
        }
    }

	@Override public void exitExit_stmt(STParser.Exit_stmtContext ctx) { 
        ExitStatement emf = stmtFactory.createExitStatement();
        mapEmf.put(ctx, emf);
    }

	@Override public void enterContinue_stmt(STParser.Continue_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in continue_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterContinue_stmt!!!");
        }
    }

	@Override public void exitContinue_stmt(STParser.Continue_stmtContext ctx) { 
        ContinueStatement emf = stmtFactory.createContinueStatement();
        mapEmf.put(ctx, emf);
    }

    @Override public void enterReturn_stmt(STParser.Return_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in return_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterReturn_stmt!!!");
        }
    }

	@Override public void exitReturn_stmt(STParser.Return_stmtContext ctx) { 
        ReturnStatement emf = stmtFactory.createReturnStatement();
        mapEmf.put(ctx, emf);
        try{
            for(int i = 0; i < ctx.getChildCount(); i++){
                if(mapNodeStr.get(ctx.getChild(i)) == "expression"){
                    emf.setExpression(((Expression)mapEmf.get(ctx.getChild(i))));
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitReturn_stmt!!!");
        }
    }

    @Override public void enterCase_stmt(STParser.Case_stmtContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_stmt");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_stmt!!!");
        }
    }

	@Override public void exitCase_stmt(STParser.Case_stmtContext ctx) { 
        Namespace namespaceEmf = (Namespace)getEmf(ctx);
        CaseStatement emf = stmtFactory.createCaseStatement();
        emf.setNamespace(namespaceEmf);
        mapEmf.put(ctx, emf);
        emf.setTestString("case_stmt_emf");
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = mapNodeStr.get(ctx.getChild(i));
            if(childNodeStr == "expression"){
                emf.setSelector((Expression)getChildEmf(ctx, i));
            }
            else if(childNodeStr == "case_selection"){ 
                emf.getGroupStatement().add((GroupStatement)getChildEmf(ctx, i));
            }
            else if(childNodeStr == "stmt_list"){
                emf.setElseStatement((StatementBody)getChildEmf(ctx, i));
            }
            else{ }
        }
    }

    //case_slection对应ECore中的GroupStatement类
	@Override public void enterCase_selection(STParser.Case_selectionContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_selection");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_selection!!!");
        }
    }

	@Override public void exitCase_selection(STParser.Case_selectionContext ctx) { 
        setFromChildEmf(ctx, 0);
        GroupStatement emf = (GroupStatement)getEmf(ctx);
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = mapNodeStr.get(ctx.getChild(i));
            if(childNodeStr == "stmt_list"){
                emf.setStatementBody((StatementBody)getChildEmf(ctx, i));
            }
            else{ }
        }

        //System.out.println(((LiteralExpression)emf.getExprLabel().get(0)).getLiteral().getValue());
    }

	@Override public void enterCase_list(STParser.Case_listContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_list");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_list!!!");
        }
    }

	@Override public void exitCase_list(STParser.Case_listContext ctx) { 
        Namespace namespaceEmf = (Namespace)getEmf(ctx);
        GroupStatement emf = stmtFactory.createGroupStatement();
        emf.setNamespace(namespaceEmf);
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            EObject childEmf = getChildEmf(ctx, i);
            if(childEmf instanceof Expression){
                emf.getExprLabel().add((Expression)childEmf);
            }
            else if(childEmf instanceof SubrangeType){
                emf.getSubrangeLabel().add((SubrangeType)childEmf);
            }
            else{ }
        }
    }

	@Override public void enterCase_list_elem(STParser.Case_list_elemContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_list_elem");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_list_elem!!!");
        }
    }

	@Override public void exitCase_list_elem(STParser.Case_list_elemContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

/* ////////////////////////////////////////////////////////////////////////
//////以下是关于constant的部分
//////ST.g4文件中的constant规则，对应语言模型ECore的Literal
////// */
    @Override public void enterConstant(STParser.ConstantContext ctx) { }

    @Override public void exitConstant(STParser.ConstantContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterNumeric_literal(STParser.Numeric_literalContext ctx) { }

    @Override public void exitNumeric_literal(STParser.Numeric_literalContext ctx) {
        if(ctx.getChild(0) instanceof RuleContext){
            setFromChildEmf(ctx, 0);
        }
        else{
            NumericLiteral emf = liteFactory.createNumericLiteral();
            mapEmf.put(ctx, emf);
            emf.setType(LiteralType.REAL);
            String value = ctx.getText();
            emf.setValue(value);
        }
    }

    @Override public void enterInt_literal(STParser.Int_literalContext ctx) { }

	@Override public void exitInt_literal(STParser.Int_literalContext ctx) { 
        NumericLiteral emf = liteFactory.createNumericLiteral();
        mapEmf.put(ctx, emf);
        emf.setType(LiteralType.INTEGER);
        String value = ctx.getText();
        //System.out.println(value);
        emf.setValue(value);
    }

	@Override public void enterBool_literal(STParser.Bool_literalContext ctx) { }

	@Override public void exitBool_literal(STParser.Bool_literalContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        emf.setType(LiteralType.BOOLEAN);
        String value = ctx.getText();
        emf.setValue(value);
    }

    @Override public void enterChar_literal(STParser.Char_literalContext ctx) { }

	@Override public void exitChar_literal(STParser.Char_literalContext ctx) { 
        CharLiteral emf = liteFactory.createCharLiteral();
        mapEmf.put(ctx, emf);
        emf.setType(LiteralType.SINGLE_BYTE_CHAR);
        String value = ctx.getText();
        emf.setValue(value);
    }

/* ////////////////////////////////////////////////////////
//////以下是关于type的部分
//////
//////
////// */
    @Override public void enterType_name(STParser.Type_nameContext ctx) { }

    @Override public void exitType_name(STParser.Type_nameContext ctx) { 
        try{
            String typeName = ctx.getText();
            String parentNodeStr = mapNodeStr.get(ctx.getParent());
            switch(parentNodeStr){
                case "simple_type_decl":
    
                    break;
                case "subrange_type_decl":
                    if(mapTypeEmf.get(typeName) == null){ 
                        SubrangeType emfSubrangeType = typeFactory.createSubrangeType();
                        emfSubrangeType.setTypeDeclaration(((TypeDeclaration)mapEmf.get(ctx.getParent())));
                        mapEmf.put(ctx, emfSubrangeType);
                        emfSubrangeType.setName(typeName);
                        mapTypeEmf.put(typeName, emfSubrangeType);
                    }
                    else{
                        System.err.println("Error: < type '" + typeName + "' is already exist >");
                        System.exit(0);
                    }
                    break;
                case "enum_type_decl":
                    EnumType emfEnumType = typeFactory.createEnumType();
                    emfEnumType.setTypeDeclaration(((TypeDeclaration)mapEmf.get(ctx.getParent())));
                    mapEmf.put(ctx, emfEnumType);
                    emfEnumType.setName(typeName);
                    mapTypeEmf.put(typeName, emfEnumType);
                    break;
                case "array_type_decl":
                    ArrayType emfArrayType = typeFactory.createArrayType();
                    emfArrayType.setTypeDeclaration(((TypeDeclaration)mapEmf.get(ctx.getParent())));
                    mapEmf.put(ctx, emfArrayType);
                    emfArrayType.setName(typeName);
                    mapTypeEmf.put(typeName, emfArrayType);
                    break;
                case "struct_type_decl":
                    StructType emfStructType = typeFactory.createStructType();
                    emfStructType.setTypeDeclaration(((TypeDeclaration)mapEmf.get(ctx.getParent())));
                    mapEmf.put(ctx, emfStructType);
                    emfStructType.setName(typeName);
                    mapTypeEmf.put(typeName, emfStructType);
                    break;
                default:
                    if(mapTypeEmf.get(typeName) == null){
                        System.err.println("Error: <type " + typeName + " is not declared!!!>");
                        System.exit(0);
                    }
                    else{
                        Type emf0 = (Type)mapTypeEmf.get(typeName);
                        mapEmf.put(ctx, emf0);
                    }
                    break;
            }
        } catch(Exception exception){
            System.err.println("Error In Type_name!!!");
        }
    }

    @Override public void enterData_type_decl(STParser.Data_type_declContext ctx) { }

    @Override public void exitData_type_decl(STParser.Data_type_declContext ctx) { 
        try{
            setFromChildEmf(ctx, 1);
        } catch(Exception exception){
            System.err.println("Error In Data_type_decl!!!");
        }
    }

    @Override public void enterType_decl_list(STParser.Type_decl_listContext ctx) { }

	@Override public void exitType_decl_list(STParser.Type_decl_listContext ctx) { 
        try{
            TypeDeclList emf = declFactory.createTypeDeclList();
            mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_decl"){
                    emf.getTypeDeclatation().add((TypeDeclaration)getChildEmf(ctx, i));
                }
            }

            //System.out.println(emf.getTypeDeclatation().size() + " declaration in type_decl_list");
        } catch(Exception exception){
            System.err.println("Error In Type_decl_list!!!");
        }
    }

    @Override public void enterType_decl(STParser.Type_declContext ctx) { }

    @Override public void exitType_decl(STParser.Type_declContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterSubrange_type_decl(STParser.Subrange_type_declContext ctx) { 
        SubrangeTypeDecl emf = declFactory.createSubrangeTypeDecl();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitSubrange_type_decl(STParser.Subrange_type_declContext ctx) {
        try{ 
            SubrangeTypeDecl emf = (SubrangeTypeDecl)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((SubrangeType)getChildEmf(ctx, i));
                }
            }

            if(emf.getTypeAccess() == null){
                //System.out.println(emf.getType().getName() + ": new subrange_type_decl");
                //System.out.println(((LiteralExpression)emf.getSubrange().getLowerBound()).getLiteral().getValue());
            }
            else{
                //System.out.println(emf.getType().getName() + ": type_access->" + emf.getTypeAccess().getName());
            }
        } catch(Exception exception){
            System.err.println("Error In Subrange_type_decl!!!");
        }
    }

	@Override public void enterSubrange_spec(STParser.Subrange_specContext ctx) { }

	@Override public void exitSubrange_spec(STParser.Subrange_specContext ctx) { 
        try{
            EObject parentEmf = getParentEmf(ctx);
            if(parentEmf instanceof Declaration){
                setFromParentEmf(ctx);
                SubrangeTypeDecl emf0 = (SubrangeTypeDecl)parentEmf;
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    emf0.setBaseType((ElementaryDataType)getChildEmf(ctx, 0));
                    emf0.setSubrange((Subrange)getChildEmf(ctx, 2));
                }
            }
            else if(parentEmf instanceof Initializer){ 
                SubrangeTypeDecl emf1 = declFactory.createSubrangeTypeDecl();
                mapEmf.put(ctx, emf1);
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)getChildEmf(ctx, 0));
                    ((SubrangeInit)parentEmf).setType((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    emf1.setBaseType((ElementaryDataType)getChildEmf(ctx, 0));
                    emf1.setSubrange((Subrange)getChildEmf(ctx, 2));
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In Subrange_spec!!!");
        }
    }

    @Override public void enterSubrange(STParser.SubrangeContext ctx) { }

	@Override public void exitSubrange(STParser.SubrangeContext ctx) { 
        Subrange emf = declFactory.createSubrange();
        mapEmf.put(ctx, emf);
        try{
            emf.setLowerBound((Expression)getChildEmf(ctx, 0));
            emf.setUpperBound((Expression)getChildEmf(ctx, 2));
        } catch(Exception exception){
            System.err.println("Error In Subrange!!!");
        }
    }

    @Override public void enterEnum_type_decl(STParser.Enum_type_declContext ctx) { 
        EnumTypeDecl emf = declFactory.createEnumTypeDecl();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitEnum_type_decl(STParser.Enum_type_declContext ctx) { 
        try{
            EnumTypeDecl emf = (EnumTypeDecl)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((EnumType)getChildEmf(ctx, i));
                }
            }

            if(emf.getTypeAccess() == null){
                //System.out.println(emf.getType().getName() + ": new enum_type_decl");
            }
            else{
                //System.out.println(emf.getType().getName() + ": type_access->" + emf.getTypeAccess().getName());
            }
        } catch(Exception exception){
            System.err.println("Error In Enum_type_decl!!!");
        }
    }

	@Override public void enterEnum_spec(STParser.Enum_specContext ctx) { }

	@Override public void exitEnum_spec(STParser.Enum_specContext ctx) { 
        try{
            EObject parentEmf = getParentEmf(ctx);
            if(parentEmf instanceof Declaration){
                setFromParentEmf(ctx);
                EnumTypeDecl emf0 = (EnumTypeDecl)parentEmf;
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "enum_value"){
                            //System.out.println(((Literal)getChildEmf(ctx, i)).getValue());
                            emf0.getEnumValue().add((Literal)getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "named_spec"){ }
                    }
                }
            }
            else if(parentEmf instanceof Initializer){ 
                EnumTypeDecl emf1 = declFactory.createEnumTypeDecl();
                mapEmf.put(ctx, emf1);
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)getChildEmf(ctx, 0));
                    ((EnumInit)parentEmf).setType((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "enum_value"){
                            //System.out.println(((Literal)getChildEmf(ctx, i)).getValue());
                            emf1.getEnumValue().add((Literal)getChildEmf(ctx, i));
                        }
                    }
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In Enum_spec!!!");
        }
    }

	@Override public void enterNamed_spec(STParser.Named_specContext ctx) { }

	@Override public void exitNamed_spec(STParser.Named_specContext ctx) { }

	@Override public void enterQuote_value(STParser.Quote_valueContext ctx) { }

	@Override public void exitQuote_value(STParser.Quote_valueContext ctx) {
        try{
            QuoteValue emf = liteFactory.createQuoteValue();
            mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setQuoteType((Type)mapTypeEmf.get(ctx.getChild(i).getText()));
                    //System.out.println(ctx.getChild(i).getText());
                }
                else if(childNodeStr == "enum_value"){
                    emf.setEnumValue((Literal)mapEmf.get(ctx.getChild(i)));
                    //System.out.println(emf.getEnumValue().getValue());
                }
                else{ }
            }
        } catch(Exception exception){
            System.err.println("Error In Quote_value!!!");
        }
    }

	@Override public void enterEnum_value(STParser.Enum_valueContext ctx) { }

	@Override public void exitEnum_value(STParser.Enum_valueContext ctx) { 
        try{
            Literal emf = elemFactory.createLiteral();
            mapEmf.put(ctx, emf);
            emf.setValue(ctx.getText());
        } catch(Exception exception){
            System.err.println("Error In Enum_value!!!");
        }
    }

    @Override public void enterArray_type_decl(STParser.Array_type_declContext ctx) { 
        ArrayTypeDecl emf = declFactory.createArrayTypeDecl();
        emf.setTestString("array_type_decl testString");
        mapEmf.put(ctx, emf);
    }

	@Override public void exitArray_type_decl(STParser.Array_type_declContext ctx) { 
        try{ 
            ArrayTypeDecl emf = (ArrayTypeDecl)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((ArrayType)getChildEmf(ctx, i));
                }
            }

            if(emf.getTypeAccess() == null){
                //System.out.println(emf.getType().getName() + ": new subrange_type_decl");
                //System.out.println(((LiteralExpression)emf.getSubrange().getLowerBound()).getLiteral().getValue());
            }
            else{
                //System.out.println(emf.getType().getName() + ": type_access->" + emf.getTypeAccess().getName());
            }
        } catch(Exception exception){
            System.err.println("Error In Array_type_decl!!!");
        }
    }

	@Override public void enterArray_spec(STParser.Array_specContext ctx) { }

	@Override public void exitArray_spec(STParser.Array_specContext ctx) { 
        try{
            EObject parentEmf = getParentEmf(ctx);
            if(parentEmf instanceof ArrayTypeDecl){
                setFromParentEmf(ctx);
                ArrayTypeDecl emf0 = (ArrayTypeDecl)parentEmf;
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "subrange"){
                            emf0.getSubrange().add((Subrange)getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "data_type_access"){
                            emf0.setInsideType((Type)getChildEmf(ctx, i));
                        }
                        else{ }
                    }
                }
            }
            else if(parentEmf instanceof Initializer){ 
                ArrayTypeDecl emf1 = declFactory.createArrayTypeDecl();
                emf1.setTestString("array_type_decl testString");
                mapEmf.put(ctx, emf1);
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)getChildEmf(ctx, 0));
                    ((ArraySpecInit)parentEmf).setType((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "subrange"){
                            emf1.getSubrange().add((Subrange)getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "data_type_access"){
                            emf1.setInsideType((Type)getChildEmf(ctx, i));
                        }
                        else{ }
                    }
                }
            }
            else if(parentEmf instanceof StructElemDecl){
                ArrayTypeDecl emf1 = declFactory.createArrayTypeDecl();
                emf1.setTestString("array_type_decl testString");
                mapEmf.put(ctx, emf1);
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "subrange"){
                            emf1.getSubrange().add((Subrange)getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "data_type_access"){
                            emf1.setInsideType((Type)getChildEmf(ctx, i));
                        }
                        else{ }
                    }
                }
            }
        } catch(Exception exception){
            System.err.println("Error In Array_spec!!!");
        }
    }

    @Override public void enterArray_elem_init_value(STParser.Array_elem_init_valueContext ctx) { }

	@Override public void exitArray_elem_init_value(STParser.Array_elem_init_valueContext ctx) { 
        try{
            ArrayElemInit emf = initFactory.createArrayElemInit();
            mapEmf.put(ctx, emf);
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "expression":
                    emf.setType(((Expression)mapEmf.get(childNode)).getType());
                    emf.setExpression((Expression)getChildEmf(ctx, 0));
                    //System.out.println(((LiteralExpression)emf.getExpression()).getLiteral().getValue());
                    break;
                case "struct_init":
                    emf.setStruct((StructInit)getChildEmf(ctx, 0));
                    break;
                case "array_init":
                    emf.setArray((ArrayInit)getChildEmf(ctx, 0));
                    break;
            }
        }catch(Exception exception){
            System.err.println("Error In Array_elem_init_value!!!");
        }
    }


    @Override public void enterStruct_type_decl(STParser.Struct_type_declContext ctx) { 
        StructTypeDecl emf = declFactory.createStructTypeDecl();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitStruct_type_decl(STParser.Struct_type_declContext ctx) { 
        try{ 
            StructTypeDecl emf = (StructTypeDecl)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((StructType)getChildEmf(ctx, i));
                }
            }

            if(emf.getTypeAccess() == null){
                //System.out.println(emf.getType().getName() + ": new struct_type_decl");
            }
            else{
                //System.out.println(emf.getType().getName() + ": type_access->" + emf.getTypeAccess().getName());
            }
        } catch(Exception exception){
            System.err.println("Error In Struct_type_decl!!!");
        }
    }

	@Override public void enterStruct_spec(STParser.Struct_specContext ctx) { }

	@Override public void exitStruct_spec(STParser.Struct_specContext ctx) { 
        try{
            EObject parentEmf = getParentEmf(ctx);
            if(parentEmf instanceof Declaration){
                setFromParentEmf(ctx);
                StructTypeDecl emf0 = (StructTypeDecl)parentEmf;
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        if(mapNodeStr.get(ctx.getChild(i)) == "struct_elem_decl"){
                            emf0.getElement().add((StructElemDecl)mapEmf.get(ctx.getChild(i)));
                        }
                    }
                }
            }
            else if(parentEmf instanceof Initializer){ 
                if(mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    StructType type = (StructType)getChildEmf(ctx, 0);
                    StructTypeDecl emf0 = (StructTypeDecl)type.getTypeDeclaration();
                    mapEmf.put(ctx, emf0);
                    
                    ((StructSpecInit)parentEmf).setType((Type)getChildEmf(ctx, 0));
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In Struct_spec!!!");
        }
    }

	@Override public void enterStruct_elem_decl(STParser.Struct_elem_declContext ctx) { 
        StructElemDecl emf = declFactory.createStructElemDecl();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitStruct_elem_decl(STParser.Struct_elem_declContext ctx) { 
        try{
            StructElemDecl emf = (StructElemDecl)getEmf(ctx);
            String childNodeStr = mapNodeStr.get(ctx.getChild(2));
            switch(childNodeStr){
                case "data_type_access":
                    emf.setType((Type)mapEmf.get(ctx.getChild(2)));
                    break;
                default:
                    emf.setNoNameType((TypeDeclaration)mapEmf.get(ctx.getChild(2)));
                    //System.out.println(emf.getNoNameType().getTestString());
                break;
            }
        } catch(Exception exception){
            System.err.println("Error In Struct_elem_decl!!!");
        }
    }

    @Override public void enterStruct_elem_name(STParser.Struct_elem_nameContext ctx) { }

	@Override public void exitStruct_elem_name(STParser.Struct_elem_nameContext ctx) { 
        try{
            setFromParentEmf(ctx);
            if(getEmf(ctx) instanceof StructElemDecl){ 
                StructElemDecl emf = (StructElemDecl)getEmf(ctx);
                emf.setName(ctx.getText());
            }
            else if(getEmf(ctx) instanceof StructElemInit){
                StructElemInit emf = (StructElemInit)getEmf(ctx);
                emf.setName(ctx.getText());
            }
        } catch(Exception exception){
            System.err.println("Error In Struct_elem_name!!!");
        }
    }


    @Override public void enterElem_type_name(STParser.Elem_type_nameContext ctx) { }

    @Override public void exitElem_type_name(STParser.Elem_type_nameContext ctx) { 
        ParseTree childNode = ctx.getChild(0);
        if(childNode instanceof TerminalNode){
            String typeName = ctx.getText();
            //System.out.println(typeName);
            if(mapTypeEmf.get(typeName) == null){
                ElementaryDataType emf = typeFactory.createElementaryDataType();
                mapEmf.put(ctx, emf);
                mapTypeEmf.put(typeName, emf);
                switch(typeName){
                    case "BOOL":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.BOOL);
                        break;
                    case "SINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.SINT);
                        break;
                    case "INT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.INT);
                        break;
                    case "DINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.DINT);
                        break;
                    case "LINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LINT);
                        break;
                    case "USINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.USINT);
                        break;
                    case "UINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.UINT);
                        break;
                    case "UDINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.UDINT);
                        break;
                    case "ULINT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.ULINT);
                        break;
                    case "REAL":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.REAL);
                        break;
                    case "LREAL":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LREAL);
                        break;
                    case "TIME":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.TIME);
                        break;
                    case "LTIME":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LTIME);
                        break;
                    case "DATE":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.DATE);
                        break;
                    case "LDATE":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LDATE);
                        break;
                    case "TOD":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.TOD);
                        break;
                    case "LTOD":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LTOD);
                        break;
                    case "DT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.DT);
                        break;
                    case "LDT":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LDT);
                        break;
                    case "CHAR":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.CHAR);
                        break;
                    case "WCHAR":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.WCHAR);
                        break;
                    case "BYTE":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.BYTE);
                        break;
                    case "WORD":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.WORD);
                        break;
                    case "DWORD":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.DWORD);
                        break;
                    case "LWORD":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.LWORD);
                        break;
/*                     case "":
                        emf.setName(typeName);
                        emf.setType(PreDefinedEDType.);
                        break; */
                    default: //System.out.println("elem_type_name Error!");
                }
            }
            else{
                ElementaryDataType emf = (ElementaryDataType)mapTypeEmf.get(typeName);
                mapEmf.put(ctx, emf);
            }
        }
        else{
            String typeName = childNode.getText();
            if(mapTypeEmf.get(typeName) == null){
                switch (typeName) {
                    case "STRING":
                        ElementaryDataType emf0 = typeFactory.createElementaryDataType();
                        mapEmf.put(ctx, emf0);
                        mapEmf.put(childNode, emf0);
                        mapTypeEmf.put(typeName, emf0);
                        emf0.setName(typeName);
                        emf0.setType(PreDefinedEDType.STRING);
                        break;
                    case "WSTRING":
                        ElementaryDataType emf1 = typeFactory.createElementaryDataType();
                        mapEmf.put(ctx, emf1);
                        mapEmf.put(childNode, emf1);
                        mapTypeEmf.put(typeName, emf1);
                        emf1.setName(typeName);
                        emf1.setType(PreDefinedEDType.WSTRING);
                        break;                      
                    default:
                        UserDefinedDataType emf2 = typeFactory.createUserDefinedDataType();
                        TypeDeclaration emf3 = declFactory.createTypeDeclaration();
                        emf2.setTypeDeclaration(emf3);
                        mapEmf.put(ctx, emf2);
                        mapEmf.put(childNode, emf2);
                        mapTypeEmf.put(typeName, emf2);
                        emf2.setName(typeName);
                }
            }
            else{
                EObject emf = mapTypeEmf.get(typeName);
                mapEmf.put(ctx, emf);
                mapEmf.put(childNode, emf);
            }
        }
    }

    @Override public void enterData_type_access(STParser.Data_type_accessContext ctx) { }

	@Override public void exitData_type_access(STParser.Data_type_accessContext ctx) { 
        try{
            String childNodeStr = mapNodeStr.get(ctx.getChild(0));
            if(childNodeStr == "elem_type_name"){
                Type emf = (Type)mapTypeEmf.get(ctx.getText());
                mapEmf.put(ctx, emf);
            }
            else if(childNodeStr == "derived_type_access"){
                setFromChildEmf(ctx, 0);
            }
            else{ }
        }catch(Exception exception){
            System.err.println("Error In Data_type_access!!!");
        }
    }

    @Override public void enterDerived_type_access(STParser.Derived_type_accessContext ctx) { }

    @Override public void exitDerived_type_access(STParser.Derived_type_accessContext ctx) { 
        try{
            setFromChildEmf(ctx, 0);
        }catch(Exception exception){
            System.err.println("Error In Derived_type_access!!!");
        }
    }

    @Override public void enterType_access(STParser.Type_accessContext ctx) { }

	@Override public void exitType_access(STParser.Type_accessContext ctx) { 
        try{
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    Type emf = (Type)mapTypeEmf.get(ctx.getChild(i).getText());
                    mapEmf.put(ctx, emf);
                }
            }
        }catch(Exception exception){
            System.err.println("Error In Type_access!!!");
        }
    }

    @Override public void enterString_type_access(STParser.String_type_accessContext ctx) { }

	@Override public void exitString_type_access(STParser.String_type_accessContext ctx) { 
        try{
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "string_Type_Name"){
                    Type emf = (Type)mapTypeEmf.get(ctx.getChild(i).getText());
                    mapEmf.put(ctx, emf);
                }
            }
        }catch(Exception exception){
            System.err.println("Error In String_type_access!!!");
        }
    }



/* ////////////////////////////////////////////////////////
//////以下是关于variable的部分
//////all_var_decls节点会实例化为VariableDeclaration类的对象
//////子节点关联到父节点的实例化对象，然后在子节点中根据节点名来设置其中的section
////// */
    @Override public void enterAll_var_decls(STParser.All_var_declsContext ctx) { 
        try{ 
            VariableDeclaration emf = declFactory.createVariableDeclaration();
            
            Namespace namespaceEmf = baseFactory.createNamespace();

            emf.setNamespace(namespaceEmf);

            String parentStr = mapNodeStr.get(ctx.getParent());
            switch(parentStr){
                case "all_decl":
                    namespaceEmf.setDeclType(DeclType.GLOBAL);
                    break;
                case "prog_decl":
                    namespaceEmf.setDeclType(DeclType.PROGRAM);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "func_decl":
                    namespaceEmf.setDeclType(DeclType.FUNCTION);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "fb_decl":
                    namespaceEmf.setDeclType(DeclType.FB);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "method_decl":
                    namespaceEmf.setDeclType(DeclType.METHOD);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                case "class_decl":
                
                    break;
                default: break;
            }

            mapEmf.put(ctx, emf);
        }catch(Exception exception){
            System.err.println("Error In enterAll_var_decls!!!");
        }
    }

    @Override public void exitAll_var_decls(STParser.All_var_declsContext ctx) { 
        try{ 
            //VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
            //System.out.println(emf.getNamespace().getDeclType());
            //System.out.println(emf.getNamespace().getNamespace().getPOU().getName());
            //System.out.println(((VariableDeclaration)mapEmf.get(ctx)).getTestString());

        }catch(Exception exception){
            System.err.println("Error In exitAll_var_decls!!!");
        }
    }

    @Override public void enterIo_var_decls(STParser.Io_var_declsContext ctx) { 
        setFromParentEmf(ctx);
    }

    @Override public void exitIo_var_decls(STParser.Io_var_declsContext ctx) { }

    @Override public void enterVar_decls(STParser.Var_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR);
        emf.setTestString("var_test");
    }

    @Override public void exitVar_decls(STParser.Var_declsContext ctx) { }

    @Override public void enterVar_input_decls(STParser.Var_input_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_INPUT);
        emf.setTestString("var_input_test");
    }

    @Override public void exitVar_input_decls(STParser.Var_input_declsContext ctx) { }

    @Override public void enterVar_output_decls(STParser.Var_output_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_OUTPUT);
        emf.setTestString("var_output_test");
    }

    @Override public void exitVar_output_decls(STParser.Var_output_declsContext ctx) { }

    @Override public void enterVar_in_out_decls(STParser.Var_in_out_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_IN_OUT);
    }

    @Override public void exitVar_in_out_decls(STParser.Var_in_out_declsContext ctx) { }

    @Override public void enterVar_external_decls(STParser.Var_external_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_EXTERNAL);
    }

    @Override public void exitVar_external_decls(STParser.Var_external_declsContext ctx) { }

    @Override public void enterVar_global_decls(STParser.Var_global_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_EXTERNAL);
    }

    @Override public void exitVar_global_decls(STParser.Var_global_declsContext ctx) { }

    @Override public void enterVar_temp_decls(STParser.Var_temp_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_TEMP);
    }

    @Override public void exitVar_temp_decls(STParser.Var_temp_declsContext ctx) { }

    @Override public void enterVar_access_decls(STParser.Var_access_declsContext ctx) { 
        setFromParentEmf(ctx);
        VariableDeclaration emf = (VariableDeclaration)getEmf(ctx);
        emf.setSection(VariableSection.VAR_ACCESS);
    }

    @Override public void exitVar_access_decls(STParser.Var_access_declsContext ctx) { }

    @Override public void enterVariable_list(STParser.Variable_listContext ctx) { 
            VariableList emf = varFactory.createVariableList();
            emf.setNamespace((Namespace)getParentEmf(ctx));
            mapEmf.put(ctx, emf);
    }

	@Override public void exitVariable_list(STParser.Variable_listContext ctx) { 
        try{ 
            VariableList emf = (VariableList)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                if(childNode instanceof STParser.Variable_nameContext){
                    Variable varEmf = (Variable)mapEmf.get(childNode);
                    emf.getVariable().add(varEmf);
                }
            }
        } catch(Exception exception){
            System.err.println("Error In Variable_list!!!");
        }
    }

    @Override public void enterVariable_name(STParser.Variable_nameContext ctx) { }

	@Override public void exitVariable_name(STParser.Variable_nameContext ctx) { 
        try{
            ParserRuleContext parentNode = ctx.getParent();
            String parentNodeStr = mapNodeStr.get(parentNode);
            String varName = ctx.getText();
            switch(parentNodeStr){
                case "variable_list":
                    VariableList parentEmf0 = (VariableList)mapEmf.get(parentNode);
                    Namespace namespaceEmf0 = parentEmf0.getNamespace();
                    //System.out.println(namespaceEmf0.getDeclType().getLiteral());
                    if((namespaceEmf0.getDeclType().getLiteral()).equals("GLOBAL")){
                        if(mapGlobalVarEmf.get(varName) == null){
                            Variable emf0 = elemFactory.createVariable();
                            mapGlobalVarEmf.put(varName, emf0);
                            emf0.setNamespace(namespaceEmf0);
                            emf0.setVariableList(parentEmf0);

                            mapEmf.put(ctx, emf0);

                            String name = ctx.getText();
                            emf0.setName(name);
                        }
                        else{
                            System.err.println("Error: <" + ctx.getText() + " is already exist!!!>");
                            System.exit(0);
                        }
                    }
                    else{ 
                        if(getVarInNamespace(varName, namespaceEmf0) == null){
                            Variable emf0 = elemFactory.createVariable();
                            namespaceEmf0.getNamespace().getVariable().add(emf0);
                            emf0.setNamespace(namespaceEmf0);
                            emf0.setVariableList(parentEmf0);

                            mapEmf.put(ctx, emf0);

                            String name = ctx.getText();
                            emf0.setName(name);
                        }
                        else{
                            System.err.println("Error: <" + ctx.getText() + " is already exist!!!>");
                            System.exit(0);
                        }
                    }
                    break;
                case "var_access":
                    Namespace namespaceEmf1 = (Namespace)getParentEmf(ctx);
                    if((getVarInNamespace(varName, namespaceEmf1) == null) && (mapGlobalVarEmf.get(varName) == null)){
                        System.err.println("Error: <" + ctx.getText() + " is not declared!!!>");
                        System.exit(0);
                    }                    
                    Variable emf1 = getVarInNamespace(varName, namespaceEmf1);
                    mapEmf.put(ctx, emf1);
                    break;
                case "loc_var_decl":
                    
                    break;
                case "loc_partly_var":
                    
                    break;
                case "param_assign":
                    Function emfFunc = ((FunctionCall)mapEmf.get(ctx.getParent().getParent())).getFunction();
                    FunctionDeclaration emfFunDecl = (FunctionDeclaration)emfFunc.getDeclaration();
                    if(getVarInDecl(varName, emfFunDecl) == null){
                        System.err.println("Error: <" + ctx.getText() + " is not declared!!!>");
                        System.exit(0);
                    }
                    Variable emf3 = getVarInDecl(varName, emfFunDecl);
                    mapEmf.put(ctx, emf3);
                    break;
                default: ;
            }
        } catch(Exception exception){
            System.err.println("Error In exitVariable_name!!!");
        }
    }

    @Override public void enterVariable(STParser.VariableContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else if(getParentEmf(ctx) instanceof Statement){
                Namespace namespaceEmf = ((Statement)getParentEmf(ctx)).getNamespace();
                mapEmf.put(ctx, namespaceEmf);
            }
        } catch(Exception exception){
            System.err.println("Error In enterVariable!!!");
        }
    }

	@Override public void exitVariable(STParser.VariableContext ctx) { 
        try{
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "symbolic_variable":
                    Variable emf0 = (Variable)mapEmf.get(childNode);
                    mapEmf.put(ctx, emf0);
                    break;
                default: ;
            }
        } catch(Exception exception){
            System.err.println("Error In Variable!!!");
        }
    }

    @Override public void enterSymbolic_variable(STParser.Symbolic_variableContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("Error: no Namespace in ParentNode in enterSymbolic_variable!!!");
            }
            //System.out.println(((Namespace)getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterSymbolic_variable!!!");
        }
    }

	@Override public void exitSymbolic_variable(STParser.Symbolic_variableContext ctx) {
        try{
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = mapNodeStr.get(childNode);
            if(childNodeStr == "var_access"){
                Variable emf0 = (Variable)mapEmf.get(childNode);
                mapEmf.put(ctx, emf0);
            }
            else{ 
                for(int i = 0; i < ctx.getChildCount(); i++){ 
                    childNodeStr = mapNodeStr.get(ctx.getChild(i));
                    switch(childNodeStr){
                        case "THIS":

                            break;
                        case "namespace_name":

                            break;
                        case "var_access":

                            break;
                        case "multi_elem_var":

                            break;
                        default: ;
                    }
                }
            }
        } catch(Exception exception){
            System.err.println("Error In Symbolic_variable!!!");
        }
    }

    @Override public void enterVar_access(STParser.Var_accessContext ctx) { 
        try{
            if(getParentEmf(ctx) instanceof Namespace) setFromParentEmf(ctx);
            else{
                System.err.println("Error: no Namespace in ParentNode in enterVar_access!!!");
            }
        } catch(Exception exception){
            System.err.println("Error In enterVar_access!!!");
        }
    }

	@Override public void exitVar_access(STParser.Var_accessContext ctx) { 
        try{
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "variable_name":
                    setFromChildEmf(ctx, 0);
                    break;
                case "ref_deref":

                    break;
                default: //System.out.println("Input Error!");
            }        
        } catch(Exception exception){
            System.err.println("Error In Var_access!!!");
        }
    }


/* ////////////////////////////////////////////////////////
//////以下是关于initiate的部分
//////
//////
////// */
    @Override public void enterDecl_common_part(STParser.Decl_common_partContext ctx) {
        try{
            VariableDeclaration parentEmf = (VariableDeclaration)getParentEmf(ctx);
            Namespace namespaceEmf = parentEmf.getNamespace();
            mapEmf.put(ctx, namespaceEmf);
            //System.out.println(((Namespace)getEmf(ctx)).getDeclType().getLiteral());
        } catch(Exception exception){
            System.err.println("Error In enterDecl_common_part!!!");
        }
    }

    ////decl_common_part的实例化对象为Initializer，实例化步骤在子节点中进行完成，本节点中进行关联
    ////在exit中完成容器declaration以及属性variableList的设定，并且完成VariableList对象和Variable对象的属性设置
    @Override public void exitDecl_common_part(STParser.Decl_common_partContext ctx) {
        try{
            setFromChildEmf(ctx, 2);
            VariableInitializer emf = (VariableInitializer)mapEmf.get(ctx); 


            //System.out.println(emf.getTestString());

            //initializer属于variableDeclaration，而且一个variableDeclaration中可能含有多个initializer，在此设定容器关系
            ParserRuleContext parentNode = ctx.getParent();
            VariableDeclaration parentEmf = (VariableDeclaration)mapEmf.get(parentNode);
            emf.setDeclaration(parentEmf);
            parentEmf.getInitializer().add(emf);

            //设定variable_list的容器关系
            STParser.Variable_listContext variableListNode = (STParser.Variable_listContext)ctx.getChild(0);
            VariableList variableListEmf = (VariableList)mapEmf.get(variableListNode);
            variableListEmf.setInitializer(emf);
            emf.setVariableList(variableListEmf);

            //同步设定variable_list和variable的类型
            for(int i = 0; i < variableListNode.getChildCount(); i++){
                ParseTree childNode = variableListNode.getChild(i);
                if(childNode instanceof STParser.Variable_nameContext){
                    Variable varEmf = (Variable)mapEmf.get(childNode);
                    //System.out.println("var_name:" + varEmf.getName());
                    if(emf.getType() == null){ 
                        //System.err.println(varEmf.getName() + ": No Type Obtained In Decl_common_part!!!");
                        //System.exit(0);
                    }
                    else{
                        variableListEmf.setType(emf.getType());
                        varEmf.setType(emf.getType());
                        //System.out.println("type:" + varEmf.getType().getName());
                    }
                }
            }
        } catch(Exception exception){
            System.err.println("Error In Decl_common_part!!!");
        }
    }

    @Override public void enterSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        SimpleInit emf = initFactory.createSimpleInit();
        mapEmf.put(ctx, emf);
        emf.setTestString("simple_spec_init_emf");
    }

    @Override public void exitSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        try{
            SimpleInit emf = (SimpleInit)getEmf(ctx);

            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodestr = mapNodeStr.get(ctx.getChild(i));
                if(childNodestr == "elem_type_name"){
                    Type typeEmf = (Type)mapTypeEmf.get(ctx.getChild(i).getText());
                    emf.setType(typeEmf);
                }
                else if(childNodestr == "expression"){
                    Expression exprEmf = (Expression)getChildEmf(ctx, i);
                    emf.setValue(exprEmf);
                }
                else{ }
            }

        } catch(Exception exception){
            System.err.println("Error In Simple_spec_init!!!");
        }
    }

    @Override public void enterStruct_spec_init(STParser.Struct_spec_initContext ctx) { 
        StructSpecInit emf = initFactory.createStructSpecInit();
        mapEmf.put(ctx, emf);
        emf.setTestString("struct_spec_init_emf");
    }

	@Override public void exitStruct_spec_init(STParser.Struct_spec_initContext ctx) { 
        StructSpecInit emf = (StructSpecInit)getEmf(ctx);

        emf.setStructInit((StructInit)mapEmf.get(ctx.getChild(2)));
    }

	@Override public void enterStruct_init(STParser.Struct_initContext ctx) { 
        StructInit emf = initFactory.createStructInit();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitStruct_init(STParser.Struct_initContext ctx) { 
        StructInit emf = (StructInit)getEmf(ctx);
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = mapNodeStr.get(ctx.getChild(i));
            if(childNodeStr == "struct_elem_init"){
                emf.getElemInit().add(((StructElemInit)mapEmf.get(ctx.getChild(i))));
            }
        }
    }

	@Override public void enterStruct_elem_init(STParser.Struct_elem_initContext ctx) { 
        StructElemInit emf = initFactory.createStructElemInit();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitStruct_elem_init(STParser.Struct_elem_initContext ctx) { 
        try{
            StructElemInit emf = (StructElemInit)getEmf(ctx);
            String childNodeStr = mapNodeStr.get(ctx.getChild(2));
            EObject childEmf = mapEmf.get(ctx.getChild(2));
            switch(childNodeStr){
                case"expression":
                    emf.setExpression((Expression)childEmf);
                break;
                case"array_init":
                    emf.setArray((ArrayInit)childEmf);
                break;
                case"struct_init":
                    emf.setStruct((StructInit)childEmf);
                break;
                case"ref_value":
                
                break;
                default:
                
                break;
            }

        } catch(Exception exception){
            System.err.println("Error In Struct_elem_init!!!");
        }
    }



    @Override public void enterSubrange_spec_init(STParser.Subrange_spec_initContext ctx) { 
        SubrangeInit emf = initFactory.createSubrangeInit();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitSubrange_spec_init(STParser.Subrange_spec_initContext ctx) { 
        try{
            SubrangeInit emf = (SubrangeInit)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "subrange_spec"){
                    SubrangeTypeDecl typeDeclEmf = (SubrangeTypeDecl)getChildEmf(ctx, i);
                    emf.setNoNameType(typeDeclEmf);
                    if(typeDeclEmf.getType() == null){ }
                    else{
                        emf.setType(typeDeclEmf.getType());
                    }
                }
                else if(childNodeStr == "expression"){
                    emf.setValue((Expression)getChildEmf(ctx, i));
                }
                else{ }
            }

            //System.out.println(((LiteralExpression)emf.getValue()).getLiteral().getValue());
        } catch(Exception exception){
            System.err.println("Error In Subrange_spec_init!!!");
        }
    }

    @Override public void enterEnum_spec_init(STParser.Enum_spec_initContext ctx) { 
        EnumInit emf = initFactory.createEnumInit();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitEnum_spec_init(STParser.Enum_spec_initContext ctx) { 
        try{
            EnumInit emf = (EnumInit)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "enum_spec"){
                    EnumTypeDecl typeDeclEmf = (EnumTypeDecl)getChildEmf(ctx, i);
                    emf.setNoNameType(typeDeclEmf);
                    if(typeDeclEmf.getType() == null){ }
                    else{
                        emf.setType(typeDeclEmf.getType());
                    }
                }
                else if(childNodeStr == "quote_value"){
                    emf.setValue((QuoteValue)getChildEmf(ctx, i));
                }
            }

            //System.out.println(emf.getValue());
        } catch(Exception exception){
            System.err.println("Error In Enum_spec_init!!!");
        }
    }

    @Override public void enterArray_spec_init(STParser.Array_spec_initContext ctx) { 
        ArraySpecInit emf = initFactory.createArraySpecInit();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitArray_spec_init(STParser.Array_spec_initContext ctx) { 
        try{
            ArraySpecInit emf = (ArraySpecInit)getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "array_spec"){
                    ArrayTypeDecl typeDeclEmf = (ArrayTypeDecl)getChildEmf(ctx, i);
                    emf.setNoNameType(typeDeclEmf);
                    if(typeDeclEmf.getType() == null){ }
                    else{
                        emf.setType(typeDeclEmf.getType());
                    }
                }
            }

/*             for(int i = 0; i < emf.getValue().getElements().size(); i++){
                System.out.println(((LiteralExpression)emf.getValue().getElements().get(i).getExpression()).getLiteral().getValue());
            } */
        } catch(Exception exception){
            System.err.println("Error In Array_spec_init!!!");
        }
    }

	@Override public void enterArray_init(STParser.Array_initContext ctx) { }

	@Override public void exitArray_init(STParser.Array_initContext ctx) { 
        try{
            ArrayInit emf = initFactory.createArrayInit();
            if(ctx.getChild(1) instanceof TerminalNode){
                int count = Integer.parseInt(ctx.getChild(1).getText());
                ArrayElemInit childEmf = null;
                for(int i = 0; i < ctx.getChildCount(); i++){
                    String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                    if(childNodeStr == "array_elem_init_value"){
                        childEmf = (ArrayElemInit)getChildEmf(ctx, i);
                    }
                    else{ }
                }
                for(int i = 0; i < count; i++){
                    emf.getElement().add(childEmf);
                }
            }
            else if(ctx.getChild(1) instanceof RuleContext){
                for(int i = 0; i < ctx.getChildCount(); i++){
                    String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                    if(childNodeStr == "array_elem_init_value"){
                        emf.getElement().add((ArrayElemInit)getChildEmf(ctx, i));
                    }
                    else{ }
                }
            }
            else{ }
        }catch(Exception exception){
            System.err.println("Array_init!!!");
        }
    }


/* ////////////////////////////////////////////////////////
//////以下是关于POU的部分
//////
//////
////// */
	@Override public void enterProg_decl(STParser.Prog_declContext ctx) { 
        try{
            ProgramDeclaration emf = declFactory.createProgramDeclaration();
            Namespace namespaceEmf = baseFactory.createNamespace();
            emf.setNamespace(namespaceEmf);
            namespaceEmf.setDeclType(DeclType.GLOBAL);
            mapEmf.put(ctx, emf);
        }catch(Exception exception){
            System.err.println("Error in enterProg_decl!!!");
        }
    }

	@Override public void exitProg_decl(STParser.Prog_declContext ctx) { 
        try{
            ProgramDeclaration emf = (ProgramDeclaration)getEmf(ctx);
            emf.setPOU((Program)mapEmf.get(ctx.getChild(1)));
        }catch(Exception exception){
            System.err.println("Error in exitProg_decl!!!");
        }
    }

	@Override public void enterProg_name(STParser.Prog_nameContext ctx) { }

	@Override public void exitProg_name(STParser.Prog_nameContext ctx) { 
        Program emf = pouFactory.createProgram();
        emf.setName(ctx.getText());
        emf.setDeclaration(((ProgramDeclaration)mapEmf.get(ctx.getParent())));
        mapEmf.put(ctx, emf);
    }

    @Override public void enterMethod_decl(STParser.Method_declContext ctx) { 
        MethodDeclaration emf = declFactory.createMethodDeclaration();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitMethod_decl(STParser.Method_declContext ctx) { 
        try{
            MethodDeclaration emf = (MethodDeclaration)getEmf(ctx);


        } catch(Exception exception){
            System.err.println("Error in Method_decl!!!");
        }
    }

    @Override public void enterMethod_name(STParser.Method_nameContext ctx) { }

	@Override public void exitMethod_name(STParser.Method_nameContext ctx) { }

    @Override public void enterFunc_decl(STParser.Func_declContext ctx) { 
        try{ 
            FunctionDeclaration emf = declFactory.createFunctionDeclaration();
            Namespace namespaceEmf = baseFactory.createNamespace();
            emf.setNamespace(namespaceEmf);

            String parentStr = mapNodeStr.get(ctx.getParent());
            switch(parentStr){
                case "all_decl":
                    namespaceEmf.setDeclType(DeclType.GLOBAL);;
                    break;
                case "prog_decl":
                    namespaceEmf.setDeclType(DeclType.PROGRAM);
                    namespaceEmf.setNamespace((POUDeclaration)mapEmf.get(ctx.getParent()));
                    break;
                default: break;
            }

            //System.out.println(emf.getNamespace().getDeclType());
            mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in enterFunc_decl!!!");
        }
    }

	@Override public void exitFunc_decl(STParser.Func_declContext ctx) { 
        FunctionDeclaration emf = (FunctionDeclaration)getEmf(ctx);
        if(mapTypeEmf.get("VOID") == null){
            ElementaryDataType emfVOID = typeFactory.createElementaryDataType();
            emfVOID.setName("VOID");
            emfVOID.setType(PreDefinedEDType.VOID);
            mapTypeEmf.put("VOID", emfVOID);
        }
        try{
            emf.setType((Type)mapTypeEmf.get("VOID"));
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "derived_func_name":
                        emf.setPOU((Function)mapEmf.get(ctx.getChild(i)));
                        break;
                    case "data_type_access":
                        emf.setType((Type)mapEmf.get(ctx.getChild(i)));
                        break;
                    case "using_directive":

                        break;
                    case "all_var_decls":

                        break;
                    case "method_decls":

                        break;
                    case "statements":

                        break;
                    default:

                        break;
                }
            }
        } catch(Exception exception){
            System.err.println("Error in exitFunc_decl!!!");
        }
    }

	@Override public void enterFunc_name(STParser.Func_nameContext ctx) { 
        Namespace namespaceEmf = ((FunctionCall)getParentEmf(ctx)).getNamespace();
        mapEmf.put(ctx, namespaceEmf);
    }

	@Override public void exitFunc_name(STParser.Func_nameContext ctx) { 
        setFromChildEmf(ctx, 0);
        ((FunctionCall)getParentEmf(ctx)).setFunction((Function)getEmf(ctx));
        //System.out.println(((FunctionCall)getParentEmf(ctx)).getFunction().getName());
    }

	@Override public void enterStd_func_name(STParser.Std_func_nameContext ctx) { }

	@Override public void exitStd_func_name(STParser.Std_func_nameContext ctx) { }

	@Override public void enterDerived_func_name(STParser.Derived_func_nameContext ctx) { }

	@Override public void exitDerived_func_name(STParser.Derived_func_nameContext ctx) { 
        try{
            String funName = ctx.getText();
            if((mapNodeStr.get(ctx.getParent())).equals("func_decl")){
                Namespace namespaceEmf = ((FunctionDeclaration)mapEmf.get(ctx.getParent())).getNamespace();
                if(namespaceEmf.getDeclType().getLiteral().equals("GLOBAL")){
                    if(mapGlobalFunEmf.get(funName) == null){
                        Function emf = pouFactory.createFunction();
                        emf.setNamespace(namespaceEmf);
                        mapEmf.put(ctx, emf);
                        emf.setName(funName);
                        mapGlobalFunEmf.put(funName, emf);
                    }
                    else{
                        System.err.println(funName + " already exsit!!!");
                        System.exit(0);
                    }
                }
                else{
                    if(getFunInNamespace(funName, namespaceEmf) == null){
                        Function emf = pouFactory.createFunction();
                        emf.setNamespace(namespaceEmf);
                        namespaceEmf.getNamespace().getFunction().add(emf);
                        mapEmf.put(ctx, emf);
                        emf.setName(funName);
                    }
                    else{
                        System.err.println(funName + " already exsit!!!");
                        System.exit(0);
                    }
                }
            }
            else if(mapNodeStr.get(ctx.getParent()) == "func_name"){
                Namespace namespaceEmf = (Namespace)getParentEmf(ctx);
                if(getFunInNamespace(funName, namespaceEmf) == null){
                    if(mapGlobalFunEmf.get(funName) == null){
                        System.err.println(funName + " does not declared!!!");
                        System.exit(0);
                    }
                    else{
                        mapEmf.put(ctx, mapGlobalFunEmf.get(funName));
                    }
                }
                else{
                    mapEmf.put(ctx, getFunInNamespace(funName, namespaceEmf));
                }

            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error in exitDerived_func_name!!!");
        }
    }

	@Override public void enterFunc_call(STParser.Func_callContext ctx) { 
        FunctionCall emf = exprFactory.createFunctionCall();
        Namespace namespaceEmf = (Namespace)getParentEmf(ctx);
        emf.setNamespace(namespaceEmf);
        mapEmf.put(ctx, emf);
    }

	@Override public void exitFunc_call(STParser.Func_callContext ctx) { 
        try{
            FunctionCall emf = (FunctionCall)getEmf(ctx);
            for(int i =0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "func_name":
                        Function emfFunction = (Function)getChildEmf(ctx, 0);
                        emf.setFunction(emfFunction);
                        emf.setType(((FunctionDeclaration)(emfFunction.getDeclaration())).getType());
                        break;
                    case "param_assign":
                        emf.getParameter().add((Parameter)mapEmf.get(ctx.getChild(i)));
                        break;
                    default:

                        break;
                }
            }
            //System.out.println(emf.getType().getName());
        } catch(Exception exception){
            System.err.println("Error in exitFunc_call!!!");
        }
    }

    @Override public void enterParam_assign(STParser.Param_assignContext ctx) { 
        try{
            String parentNodeStr = mapNodeStr.get(ctx.getParent());
            switch(parentNodeStr){
                case"func_call":
                    FunctionCall parentEmf0 = (FunctionCall)getParentEmf(ctx);
                    Namespace namespaceEmf0 = parentEmf0.getNamespace();
                    mapEmf.put(ctx, namespaceEmf0);
                    break;
                case"invocation":
                    break;    
            }
        } catch(Exception exception){
            System.err.println("Error in enterParam_assign!!!");
        }
    }

	@Override public void exitParam_assign(STParser.Param_assignContext ctx) { 
        try{
            Parameter emf = exprFactory.createParameter();
            mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "variable_name":
                        emf.setName(((Variable)mapEmf.get(ctx.getChild(i))).getName());
                        //System.out.println(emf.getName());
                        break;
                    case "expression":
                        emf.setExpression((Expression)mapEmf.get(ctx.getChild(i)));
                        //System.out.println(emf.getExpression().getType().getName());
                        break;
                    case "ref_assign":

                        break;
                    default:

                        break;
                }
            }
        } catch(Exception exception){
            System.err.println("Error in exitParam_assign!!!");
        }
    }

}