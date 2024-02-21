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
////////////////////////////////////////////////////////////////////////////////////////////// */

/* ///整体思路如下
//////针对生成语法树的每一个节点进行遍历，节点会有enter()和exit()一对方法
//////根据情况不同，在enter或exit时创建对应的emf模型(大多数情况为exit时)
//////考虑到AST中的一些节点和其子节点存在reference关系，需要在父节点或子节点中设定
//////这里使用规则名ruleName来进行筛选并执行操作
///////////////////////////////////////////////////////////////////////////////////////////// */
package classes;


import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

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

import classes.STParser.All_var_declsContext;
import classes.STParser.Decl_common_partContext;
import classes.STParser.Elem_type_nameContext;
import classes.STParser.StmtContext;
import classes.STParser.Variable_listContext;
import classes.STParser.Variable_nameContext;

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



public class MyListener extends STBaseListener{

/* ///引入EMF模型的factory方法，用于给节点创建语言模型实例 */
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

//////用一个HashMap来记录所有的变量声明，用于在引用变量时快速查找
    public Map<String, EObject> mapVarEmf = new HashMap<>();

//////用一个HashMap来记录所有使用到的类型emf，包括自定义的类型和基本数据类型
    public Map<String, EObject> mapTypeEmf = new HashMap<>();

//////用一个HashMap来记录function

//////用一个HashMap来记录method

//////setFromChildEmf()方法，获取某个子节点的emf并关联
    private void setFromChildEmf(ParserRuleContext ctx, int i){
        EObject emf = mapEmf.get(ctx.getChild(i));
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
        System.out.println(ruleName); */

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

    }

	@Override public void exitExpression(STParser.ExpressionContext ctx) { 
        ParseTree childNode = ctx.getChild(0);
        if(childNode instanceof ErrorNode){ 

        }
        else if(childNode instanceof TerminalNode){
            TerminalNode tNode = (TerminalNode)childNode;
            UnaryExpression emf = exprFactory.createUnaryExpression();
            mapEmf.put(ctx, emf);
            emf.setTestString("unary_expr_emf");

            emf.setExpression( (Expression)getChildEmf(ctx, 1) );
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
                default: System.out.println("expression error!!!");
            }


        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "expression" ){
            BinaryExpression emf = exprFactory.createBinaryExpression();
            mapEmf.put(ctx, emf);

            emf.setTestString("binary_expr_emf");
            emf.setFirstExpression( (Expression)getChildEmf(ctx, 0) );
            emf.setSecondExpression( (Expression)getChildEmf(ctx, 2) );

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
                default: System.out.println("expression error!!!");
            }

        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "constant" ){
            LiteralExpression emf = exprFactory.createLiteralExpression();
            mapEmf.put(ctx, emf);

            emf.setLiteral( (Literal)getChildEmf(ctx, 0) );
        }
/*         else if( mapNodeStr.get(ctx.getChild(0)) == "enum_value" ){
            LiteralExpression emf = exprFactory.create();
            mapEmf.put(ctx, emf);
        } */
        else if( mapNodeStr.get(ctx.getChild(0)) == "variable_access" ){
            VariableExpression emf = exprFactory.createVariableExpression();
            mapEmf.put(ctx, emf);

        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "func_call" ){
            FunctionCall emf = exprFactory.createFunctionCall();
            mapEmf.put(ctx, emf);

        }
        else{ }
    }

/* /////////////////////////////////////////////////////////////////////////
//////以下是关于statement的部分
//////
////// */
    @Override public void enterStatements(STParser.StatementsContext ctx) { }

    @Override public void exitStatements(STParser.StatementsContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterStmt(STParser.StmtContext ctx) { }

    @Override public void exitStmt(STParser.StmtContext ctx) { 
        setFromChildEmf(ctx, 0);
        //Statement emf = (Statement)getEmf(ctx);
        //System.out.println(emf.getTestString());
    }

    @Override public void enterStmt_list(STParser.Stmt_listContext ctx) { }

    @Override public void exitStmt_list(STParser.Stmt_listContext ctx) { 
        StatementBody emf = stmtFactory.createStatementBody();
        mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            ParseTree childNode = ctx.getChild(i);
            if(childNode instanceof StmtContext){
                Statement childEmf0 = (Statement)getChildEmf(ctx, i);
                childEmf0.setTestString("stmt"+ i);
                emf.getStatement().add(childEmf0);
            }
        }
        //System.out.println(emf.getStatement().size());
    }

    @Override public void enterSelection_stmt(STParser.Selection_stmtContext ctx) { }

	@Override public void exitSelection_stmt(STParser.Selection_stmtContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterIteration_stmt(STParser.Iteration_stmtContext ctx) { }

	@Override public void exitIteration_stmt(STParser.Iteration_stmtContext ctx) { 
        setFromChildEmf(ctx, 0);
    }

    @Override public void enterIf_stmt(STParser.If_stmtContext ctx) { }

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

    @Override public void enterElsif_stmt(STParser.Elsif_stmtContext ctx) { }

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
                default: System.out.println("elsif_stmt error!!!");
            }
        }
    }

	@Override public void enterElse_stmt(STParser.Else_stmtContext ctx) { }

	@Override public void exitElse_stmt(STParser.Else_stmtContext ctx) { 
        setFromChildEmf(ctx, 1);
    }

    @Override public void enterAssign_stmt(STParser.Assign_stmtContext ctx) { }

	@Override public void exitAssign_stmt(STParser.Assign_stmtContext ctx) { 
        String childNodeStr = mapNodeStr.get(ctx.getChild(0));
        switch(childNodeStr){
            case "variable": 
                AssignmentStatement emf0 = stmtFactory.createAssignmentStatement();
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
        ForStatement emf = stmtFactory.createForStatement();
        mapEmf.put(ctx, emf);
        emf.setTestString("for_stmt_emf");
    }

	@Override public void exitFor_stmt(STParser.For_stmtContext ctx) { 
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

        //String endExpr = ((LiteralExpression)emf.getEndExpression()).getLiteral().getValue();
        //System.out.println(endExpr);
    }

	@Override public void enterFor_list(STParser.For_listContext ctx) { }

	@Override public void exitFor_list(STParser.For_listContext ctx) { 
        ForStatement emf = (ForStatement)mapEmf.get(ctx.getParent());
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

    @Override public void enterWhile_stmt(STParser.While_stmtContext ctx) { }

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

	@Override public void enterRepeat_stmt(STParser.Repeat_stmtContext ctx) { }

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

    @Override public void enterExit_stmt(STParser.Exit_stmtContext ctx) { }

	@Override public void exitExit_stmt(STParser.Exit_stmtContext ctx) { 
        ExitStatement emf = stmtFactory.createExitStatement();
        mapEmf.put(ctx, emf);
    }

	@Override public void enterContinue_stmt(STParser.Continue_stmtContext ctx) { }

	@Override public void exitContinue_stmt(STParser.Continue_stmtContext ctx) { 
        ContinueStatement emf = stmtFactory.createContinueStatement();
        mapEmf.put(ctx, emf);
    }

    @Override public void enterCase_stmt(STParser.Case_stmtContext ctx) { 
        CaseStatement emf = stmtFactory.createCaseStatement();
        mapEmf.put(ctx, emf);
        emf.setTestString("case_stmt_emf");
    }

	@Override public void exitCase_stmt(STParser.Case_stmtContext ctx) { 
        CaseStatement emf = (CaseStatement)getEmf(ctx);
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
        GroupStatement emf = stmtFactory.createGroupStatement();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitCase_selection(STParser.Case_selectionContext ctx) { 
        GroupStatement emf = (GroupStatement)getEmf(ctx);
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = mapNodeStr.get(ctx.getChild(i));
            if(childNodeStr == "stmt_list"){
                emf.setStatementBody((StatementBody)getChildEmf(ctx, i));
            }
            else{ }
        }

        //String exprLabel = ((LiteralExpression)emf.getExprLabel().get(0)).getLiteral().getValue();
        //System.out.println(exprLabel);
    }

	@Override public void enterCase_list(STParser.Case_listContext ctx) { }

	@Override public void exitCase_list(STParser.Case_listContext ctx) { 
        GroupStatement emf = (GroupStatement)mapEmf.get(ctx.getParent());
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

	@Override public void enterCase_list_elem(STParser.Case_list_elemContext ctx) { }

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
        NumericLiteral emf = liteFactory.createNumericLiteral();
        mapEmf.put(ctx, emf);
        String childNodeStr = mapNodeStr.get(ctx.getChild(0));
        switch(childNodeStr){
            case "int_literal": 
                emf.setType(LiteralType.INTEGER);
                break;
            case "real_interal": 
                emf.setType(LiteralType.REAL);
                break;
            case "real_interal_exponent": 
                emf.setType(LiteralType.REAL_EXPONENT);
                break;
            case "bool_literal": 
                emf.setType(LiteralType.BOOLEAN);
                break;
            case "typed_literal": 
                emf.setType(LiteralType.TYPED);
                break;
        }
        String value = ((Literal)getChildEmf(ctx, 0)).getValue();
        emf.setValue(value);
        //System.out.println(value);

    }

    @Override public void enterInt_literal(STParser.Int_literalContext ctx) { }

	@Override public void exitInt_literal(STParser.Int_literalContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        String value = ctx.getText();
        //System.out.println(value);
        emf.setValue(value);
    }

	@Override public void enterReal_literal(STParser.Real_literalContext ctx) { }

	@Override public void exitReal_literal(STParser.Real_literalContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        String value = ctx.getText();
        emf.setValue(value);
    }

	@Override public void enterReal_literal_exponent(STParser.Real_literal_exponentContext ctx) { }

	@Override public void exitReal_literal_exponent(STParser.Real_literal_exponentContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        String value = ctx.getText();
        emf.setValue(value);
    }

	@Override public void enterBool_literal(STParser.Bool_literalContext ctx) { }

	@Override public void exitBool_literal(STParser.Bool_literalContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        String value = ctx.getText();
        emf.setValue(value);
    }

	@Override public void enterTyped_literal(STParser.Typed_literalContext ctx) { }

	@Override public void exitTyped_literal(STParser.Typed_literalContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        String value = ctx.getText();
        emf.setValue(value);
    }


/* ////////////////////////////////////////////////////////
//////以下是关于type的部分
//////
//////
////// */
    @Override public void enterElem_type_name(STParser.Elem_type_nameContext ctx) { 

    }

    @Override public void exitElem_type_name(STParser.Elem_type_nameContext ctx) { 
        ParseTree childNode = ctx.getChild(0);
        if(childNode instanceof TerminalNode){
            String typeName = ctx.getText();
            if(mapTypeEmf.get(typeName) == null){
                ElementaryDataType emf = typeFactory.createElementaryDataType();
                mapEmf.put(ctx, emf);
                mapTypeEmf.put(typeName, emf);
                switch(typeName){
                    case "BOOL":
                        //System.out.println(typeName);
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
                    default: System.out.println("elem_type_name Error!");
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

    @Override public void enterDerived_type_access(STParser.Derived_type_accessContext ctx) { }

    @Override public void exitDerived_type_access(STParser.Derived_type_accessContext ctx) { }

    @Override public void enterType_access(STParser.Type_accessContext ctx) { }

	@Override public void exitType_access(STParser.Type_accessContext ctx) { }



/* ////////////////////////////////////////////////////////
//////以下是关于variable的部分
//////all_var_decls节点会实例化为VariableDeclaration类的对象
//////子节点关联到父节点的实例化对象，然后在子节点中根据节点名来设置其中的section
////// */
    @Override public void enterAll_var_decls(STParser.All_var_declsContext ctx) { 
        VariableDeclaration emf = declFactory.createVariableDeclaration();
        mapEmf.put(ctx, emf);
    }

    @Override public void exitAll_var_decls(STParser.All_var_declsContext ctx) { 
        //String str = ((VariableDeclaration)mapEmf.get(ctx)).getTestString();
        //System.out.println(str);
    }

    @Override public void enterIo_var_decls(STParser.Io_var_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
    }

    @Override public void exitIo_var_decls(STParser.Io_var_declsContext ctx) { }

    @Override public void enterVar_decls(STParser.Var_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR);
        emf.setTestString("var_test");
    }

    @Override public void exitVar_decls(STParser.Var_declsContext ctx) { }

    @Override public void enterVar_input_decls(STParser.Var_input_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_INPUT);
        emf.setTestString("var_input_test");
    }

    @Override public void exitVar_input_decls(STParser.Var_input_declsContext ctx) { }

    @Override public void enterVar_output_decls(STParser.Var_output_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_OUTPUT);
        emf.setTestString("var_output_test");
    }

    @Override public void exitVar_output_decls(STParser.Var_output_declsContext ctx) { }

    @Override public void enterVar_in_out_decls(STParser.Var_in_out_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_IN_OUT);
    }

    @Override public void exitVar_in_out_decls(STParser.Var_in_out_declsContext ctx) { }

    @Override public void enterVar_external_decls(STParser.Var_external_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_EXTERNAL);
    }

    @Override public void exitVar_external_decls(STParser.Var_external_declsContext ctx) { }

    @Override public void enterVar_global_decls(STParser.Var_global_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_EXTERNAL);
    }

    @Override public void exitVar_global_decls(STParser.Var_global_declsContext ctx) { }

    @Override public void enterVar_temp_decls(STParser.Var_temp_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_TEMP);
    }

    @Override public void exitVar_temp_decls(STParser.Var_temp_declsContext ctx) { }

    @Override public void enterVar_access_decls(STParser.Var_access_declsContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration emf = (VariableDeclaration)mapEmf.get(parentNode);
        mapEmf.put(ctx, emf);
        emf.setSection(VariableSection.VAR_ACCESS);
    }

    @Override public void exitVar_access_decls(STParser.Var_access_declsContext ctx) { }

    @Override public void enterVar_local_decls(STParser.Var_local_declsContext ctx) { }

    @Override public void exitVar_local_decls(STParser.Var_local_declsContext ctx) { }

    @Override public void enterVariable_list(STParser.Variable_listContext ctx) { 
        VariableList emf = varFactory.createVariableList();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitVariable_list(STParser.Variable_listContext ctx) { 
        VariableList emf = (VariableList)getEmf(ctx);
        for(int i = 0; i < ctx.getChildCount(); i++){
            ParseTree childNode = ctx.getChild(i);
            if(childNode instanceof Variable_nameContext){
                Variable varEmf = (Variable)mapEmf.get(childNode);
                emf.getVariable().add(varEmf);
            }
        }
    }

    @Override public void enterVariable_name(STParser.Variable_nameContext ctx) { }

	@Override public void exitVariable_name(STParser.Variable_nameContext ctx) { 
        ParserRuleContext parentNode = ctx.getParent();
        String parentNodeStr = mapNodeStr.get(parentNode);
        switch(parentNodeStr){
            case "variable_list":
                VariableList parentEmf = (VariableList)mapEmf.get(parentNode);

                Variable emf = elemFactory.createVariable();
                mapEmf.put(ctx, emf);
                emf.setVariableList(parentEmf);

                String name = ctx.getText();
                emf.setName(name);
                mapVarEmf.put(name, emf);
                break;
            case "var_access":
                Variable emf0 = (Variable)mapVarEmf.get(ctx.getText());
                mapEmf.put(ctx, emf0);
                break;
            case "loc_var_decl":
                
                break;
            case "loc_partly_var":
                
                break;
            default: System.out.println("Input Error!");
        }
    }

    @Override public void enterVariable(STParser.VariableContext ctx) { }

	@Override public void exitVariable(STParser.VariableContext ctx) { 
        ParseTree childNode = ctx.getChild(0);
        String childNodeStr = mapNodeStr.get(childNode);
        switch(childNodeStr){
            case "symbolic_variable":
                Variable emf0 = (Variable)mapEmf.get(childNode);
                mapEmf.put(ctx, emf0);
                break;
            default: ;
        }
    }

    @Override public void enterSymbolic_variable(STParser.Symbolic_variableContext ctx) { }

	@Override public void exitSymbolic_variable(STParser.Symbolic_variableContext ctx) {
        for(int i = 0; i < ctx.getChildCount(); i++){ 
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "namespace_name":

                    break;
                case "var_access":
                    Variable emf0 = (Variable)mapEmf.get(childNode);
                    mapEmf.put(ctx, emf0);
                    break;
                case "multi_elem_var":

                    break;
                default: ;
            }
        }
    }

    @Override public void enterVar_access(STParser.Var_accessContext ctx) { }

	@Override public void exitVar_access(STParser.Var_accessContext ctx) { 
        ParseTree childNode = ctx.getChild(0);
        String childNodeStr = mapNodeStr.get(childNode);
        switch(childNodeStr){
            case "variable_name":
                String varName = childNode.getText();
                Variable emf0 = (Variable)mapVarEmf.get(varName);
                mapEmf.put(ctx, emf0);
                break;
            case "ref_deref":

                break;
            default: System.out.println("Input Error!");
        }
    }


/* ////////////////////////////////////////////////////////
//////以下是关于initiate的部分
//////
//////
////// */
    @Override public void enterDecl_common_part(STParser.Decl_common_partContext ctx) { }

    ////decl_common_part的实例化对象为Initializer，实例化步骤在子节点中进行完成，本节点中进行关联
    ////在exit中完成容器declaration以及属性variableList的设定，并且完成VariableList对象和Variable对象的属性设置
    @Override public void exitDecl_common_part(STParser.Decl_common_partContext ctx) {
        VariableInitializer emf = (VariableInitializer)mapEmf.get(ctx); 
        Type typeEmf = emf.getType();
        //String testString = emf.getTestString();
        //System.out.println(testString);

        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration parentEmf = (VariableDeclaration)mapEmf.get(parentNode);
        emf.setDeclaration(parentEmf);
        parentEmf.getInitializer().add(emf);

        Variable_listContext variableListNode = (Variable_listContext)ctx.getChild(0);
        VariableList variableListEmf = (VariableList)mapEmf.get(variableListNode);
        variableListEmf.setInitializer(emf);
        variableListEmf.setType(typeEmf);
        emf.setVariableList(variableListEmf);

        for(int i = 0; i < variableListNode.getChildCount(); i++){
            ParseTree childNode = variableListNode.getChild(i);
            if(childNode instanceof Variable_nameContext){
                Variable varEmf = (Variable)mapEmf.get(childNode);
                varEmf.setType(typeEmf);
                //System.out.println(varEmf.getName());
                //System.out.println(varEmf.getType().getName());
            }
        }
    }

    @Override public void enterSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        VariableInitializer emf = initFactory.createVariableInitializer();
        mapEmf.put(ctx, emf);
        //emf.setTestString("simple");

        ParserRuleContext parentNode = ctx.getParent();
        String parentNodeStr = mapNodeStr.get(parentNode);
        switch(parentNodeStr){
            case "decl_common_part":
                mapEmf.put(parentNode, emf);
                break;
            case "simple_type_decl":

                break;
            case "struct_elem_decl":

                break;
            case "loc_var_spec_init":

                break;
        }
    }

    @Override public void exitSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        VariableInitializer emf = (VariableInitializer)getEmf(ctx);
        Elem_type_nameContext typeNode = (Elem_type_nameContext)ctx.getChild(0);
        Type typeEmf = (Type)mapEmf.get(typeNode);
        emf.setType(typeEmf);

        if(ctx.getChildCount() == 3){
            Expression exprEmf = (Expression)getChildEmf(ctx, 2);
            emf.setValue(exprEmf);
        }
    }

    @Override public void enterStruct_spec_init(STParser.Struct_spec_initContext ctx) { 
        VariableInitializer emf = initFactory.createVariableInitializer();
        mapEmf.put(ctx, emf);
        //emf.setTestString("struct");

        ParserRuleContext parentNode = ctx.getParent();
        String parentNodeStr = mapNodeStr.get(parentNode);
        switch(parentNodeStr){
            case "decl_common_part":
                mapEmf.put(parentNode, emf);
                break;
        }
    }

	@Override public void exitStruct_spec_init(STParser.Struct_spec_initContext ctx) { }
}