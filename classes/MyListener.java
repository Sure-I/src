package classes;


import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

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
import classes.STParser.Variable_listContext;

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
    StatementsFactory statFactory = StatementsFactory.eINSTANCE;

/* ///使用HashMap数据结构来存储EMF模型对象，key为语法树节点，value为实例化的EMF模型
//////并且按照EMF包结构进行划分，便于在创建reference时进行关联 */
/*     public Map<ParseTree, EObject> mapTypeEmf = new HashMap<>();
    public Map<ParseTree, EObject> mapVarEmf = new HashMap<>();
    public Map<ParseTree, EObject> mapPouEmf = new HashMap<>();
    public Map<ParseTree, EObject> mapDeclEmf = new HashMap<>();
    public Map<ParseTree, EObject> mapEmf = new HashMap<>();
    public Map<ParseTree, EObject> mapStatEmf = new HashMap<>(); */


/* ///实例化过程使用两个HashMap
//////mapEmf用来存储ctx对应的实例化的对象，mapRuleName用来存储ctx的对应的字符串
//////mapRuleName的作用是，帮助ctx访问子节点的规则类型，方便ctx进行switch...case...语句 */
    public Map<ParseTree, EObject> mapEmf = new HashMap<>();
    public Map<ParseTree, String> mapNodeStr = new HashMap<>();


/* ///获取ruleNode的规则名 */
    @Override public void enterEveryRule(ParserRuleContext ctx) { 
        String ruleName = STParser.ruleNames[ctx.getRuleIndex()];
        mapNodeStr.put(ctx, ruleName);
/*         System.out.println(ruleName); */
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
/*         System.out.println(nodeStr); */
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
/*             System.out.println("1"); */
            TerminalNode tNode = (TerminalNode)childNode;
            UnaryExpression emf = exprFactory.createUnaryExpression();
            mapEmf.put(ctx, emf);

            emf.setExpression( (Expression)mapEmf.get(ctx.getChild(1)) );
            String nodeStr = mapNodeStr.get(tNode);
            switch(nodeStr){
                case "(":
/*                     System.out.println("PARETHESES"); */
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
            }


        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "expression" ){
/*             System.out.println("2"); */
            BinaryExpression emf = exprFactory.createBinaryExpression();
            mapEmf.put(ctx, emf);

            emf.setFirstExpression( (Expression)mapEmf.get(ctx.getChild(0)) );
            emf.setSecondExpression( (Expression)mapEmf.get(ctx.getChild(2)) );

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
            }

        }
        else if( mapNodeStr.get(ctx.getChild(0)) == "constant" ){
            LiteralExpression emf = exprFactory.createLiteralExpression();
            mapEmf.put(ctx, emf);

            emf.setLiteral( (Literal)mapEmf.get(ctx.getChild(0)) );
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
/*         else{
            报错；
        } */
    }

/* /////////////////////////////////////////////////////////////////////////
//////以下是关于statement的部分
//////
////// */

/* ////////////////////////////////////////////////////////////////////////
//////以下是关于constant的部分
//////ST.g4文件中的constant规则，对应语言模型ECore的Literal
////// */
    @Override public void enterConstant(STParser.ConstantContext ctx) { 

    }

    @Override public void exitConstant(STParser.ConstantContext ctx) { 
        
    }

    @Override public void enterNumeric_literal(STParser.Numeric_literalContext ctx) { 

    }

    @Override public void exitNumeric_literal(STParser.Numeric_literalContext ctx) { 
        NumericLiteral emf = liteFactory.createNumericLiteral();
        mapEmf.put(ctx, emf);
        String nodeStr = mapNodeStr.get(ctx.getChild(0));
        switch(nodeStr){
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
        String value = ((Literal)mapEmf.get(ctx.getChild(0))).getValue();
        emf.setValue(value);
/*         System.out.println(value); */

    }

    @Override public void enterInt_literal(STParser.Int_literalContext ctx) { }

	@Override public void exitInt_literal(STParser.Int_literalContext ctx) { 
        Literal emf = elemFactory.createLiteral();
        mapEmf.put(ctx, emf);
        String value = ctx.getText();
/*         System.out.println(value); */
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
        ElementaryDataType emf = typeFactory.createElementaryDataType();
        mapEmf.put(ctx, emf);
        String typeName = ctx.getText();
        switch(typeName){
            case "BOOL":
/*                 System.out.println(typeName); */
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
/*             case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break;
            case "":
                emf.setName(typeName);
                emf.setType(PreDefinedEDType.);
                break; */
        }
    }

    @Override public void enterDerived_type_access(STParser.Derived_type_accessContext ctx) { 

    }

    @Override public void exitDerived_type_access(STParser.Derived_type_accessContext ctx) { 
        
    }


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
        String str = ((VariableDeclaration)mapEmf.get(ctx)).getTestString();
/*         System.out.println(str); */
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

    @Override public void enterVar_local_decls(STParser.Var_local_declsContext ctx) { 

    }

    @Override public void exitVar_local_decls(STParser.Var_local_declsContext ctx) { }

    @Override public void enterVariable_list(STParser.Variable_listContext ctx) { 
        VariableList emf = varFactory.createVariableList();
        mapEmf.put(ctx, emf);
    }

	@Override public void exitVariable_list(STParser.Variable_listContext ctx) { }

/* ////////////////////////////////////////////////////////
//////以下是关于initiate的部分
//////
//////
////// */
    @Override public void enterDecl_common_part(STParser.Decl_common_partContext ctx) { }

    @Override public void exitDecl_common_part(STParser.Decl_common_partContext ctx) {
        VariableInitializer emf = (VariableInitializer)mapEmf.get(ctx); 
        //String testString = emf.getTestString();
        //System.out.println(testString);
        ParserRuleContext parentNode = ctx.getParent();
        VariableDeclaration parentEmf = (VariableDeclaration)mapEmf.get(parentNode);
        emf.setDeclaration(parentEmf);
        parentEmf.getInitializer().add(emf);
        
        Variable_listContext variableListNode = (Variable_listContext)ctx.getChild(0);
        VariableList variableListEmf = (VariableList)mapEmf.get(variableListNode);
        emf.setVariableList(variableListEmf);
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
        }
    }

    @Override public void exitSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        VariableInitializer emf = (VariableInitializer)mapEmf.get(ctx);
        Elem_type_nameContext typeNode = (Elem_type_nameContext)ctx.getChild(0);
        Type typeEmf = (Type)mapEmf.get(typeNode);
        emf.setType(typeEmf);
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