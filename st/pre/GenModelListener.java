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
package st.pre;

import parser.*;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.plaf.nimbus.State;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.container.ModuleRevisionBuilder.GenericInfo;

import st.basics.*;
import st.elements.*;
import st.literals.*;
import st.initializer.*;
import st.types.*;
import st.variables.*;
import st.pous.*;
import st.declarations.*;
import st.expressions.*;
import st.genmodel.GenAll_decl;
import st.genmodel.GenAll_var_decls;
import st.genmodel.GenArray_elem_init_value;
import st.genmodel.GenArray_init;
import st.genmodel.GenArray_spec;
import st.genmodel.GenArray_spec_init;
import st.genmodel.GenArray_type_decl;
import st.genmodel.GenAssign_stmt;
import st.genmodel.GenBool_literal;
import st.genmodel.GenCase_list;
import st.genmodel.GenCase_list_elem;
import st.genmodel.GenCase_selection;
import st.genmodel.GenCase_stmt;
import st.genmodel.GenChar_literal;
import st.genmodel.GenConstant;
import st.genmodel.GenContinue_stmt;
import st.genmodel.GenData_type_access;
import st.genmodel.GenData_type_decl;
import st.genmodel.GenDecl_common_part;
import st.genmodel.GenDerived_fb_name;
import st.genmodel.GenDerived_func_name;
import st.genmodel.GenDerived_type_access;
import st.genmodel.GenElem_type_name;
import st.genmodel.GenElse_stmt;
import st.genmodel.GenElsif_stmt;
import st.genmodel.GenEnd_expr;
import st.genmodel.GenEnum_spec;
import st.genmodel.GenEnum_spec_init;
import st.genmodel.GenEnum_type_decl;
import st.genmodel.GenEnum_value;
import st.genmodel.GenExit_stmt;
import st.genmodel.GenExpression;
import st.genmodel.GenFb_decl;
import st.genmodel.GenFb_instance_name;
import st.genmodel.GenFor_list;
import st.genmodel.GenFor_stmt;
import st.genmodel.GenFunc_call;
import st.genmodel.GenFunc_decl;
import st.genmodel.GenFunc_name;
import st.genmodel.GenIf_stmt;
import st.genmodel.GenInt_literal;
import st.genmodel.GenInvocation;
import st.genmodel.GenIo_var_decls;
import st.genmodel.GenIteration_stmt;
import st.genmodel.GenMethod_decl;
import st.genmodel.GenMethod_name;
import st.genmodel.GenNumeric_literal;
import st.genmodel.GenParam_assign;
import st.genmodel.GenProg_decl;
import st.genmodel.GenProg_name;
import st.genmodel.GenQuote_value;
import st.genmodel.GenRepeat_stmt;
import st.genmodel.GenReturn_stmt;
import st.genmodel.GenSelection_stmt;
import st.genmodel.GenSimple_spec_init;
import st.genmodel.GenStart_expr;
import st.genmodel.GenStatements;
import st.genmodel.GenStep_stmt;
import st.genmodel.GenStmt;
import st.genmodel.GenString_type_access;
import st.genmodel.GenStruct_elem_decl;
import st.genmodel.GenStruct_elem_init;
import st.genmodel.GenStruct_elem_name;
import st.genmodel.GenStruct_init;
import st.genmodel.GenStruct_spec;
import st.genmodel.GenStruct_spec_init;
import st.genmodel.GenStruct_type_decl;
import st.genmodel.GenSubprog_ctrl_stmt;
import st.genmodel.GenSubrange;
import st.genmodel.GenSubrange_spec;
import st.genmodel.GenSubrange_spec_init;
import st.genmodel.GenSubrange_type_decl;
import st.genmodel.GenSubscript_list;
import st.genmodel.GenSymbolic_variable;
import st.genmodel.GenTime_literal;
import st.genmodel.GenType_access;
import st.genmodel.GenType_decl;
import st.genmodel.GenType_decl_list;
import st.genmodel.GenType_name;
import st.genmodel.GenVar_access_decls;
import st.genmodel.GenVar_decls;
import st.genmodel.GenVar_external_decls;
import st.genmodel.GenVar_global_decls;
import st.genmodel.GenVar_in_out_decls;
import st.genmodel.GenVar_input_decls;
import st.genmodel.GenVar_output_decls;
import st.genmodel.GenVar_temp_decls;
import st.genmodel.GenVariable;
import st.genmodel.GenVariable_list;
import st.genmodel.GenVariable_name;
import st.genmodel.GenWhile_stmt;
import st.statements.*;

public class GenModelListener extends STBaseListener{

    

/* ///获取ruleNode的规则名 */
    @Override public void enterEveryRule(ParserRuleContext ctx) { 
        String ruleName = STParser.ruleNames[ctx.getRuleIndex()];
        ResourceBuilder.mapNodeStr.put(ctx, ruleName);
        //System.out.println(ruleName);
    }

	@Override public void exitEveryRule(ParserRuleContext ctx) { 

    }

/* ///TerminalNode的规则名并不会在AST中被表示出来，所以直接获取terminalNode的字符串 */
	@Override public void visitTerminal(TerminalNode node) { 
/*         ///下面三行代码用于获取TerminalNode的规则名
        String ruleName = STParser.VOCABULARY.getSymbolicName(node.getSymbol().getType());
        ResourceBuilder.mapNodeStr.put(node, ruleName);
        //System.out.println(ruleName); */

        String nodeStr = node.getSymbol().getText();
        ResourceBuilder.mapNodeStr.put(node, nodeStr);
        //System.out.println(nodeStr);
    }

	@Override public void visitErrorNode(ErrorNode node) { }




/* ///Program为根节点，表示整个源代码程序不对应EMF模型，
//////在此节点中进行所有实例化模型的持久化 */
/*     @Override public void enterProgram(STParser.ProgramContext ctx){ }

    @Override public void exitProgram(STParser.ProgramContext ctx) { } */

    @Override public void enterAll_decl(STParser.All_declContext ctx) { }

	@Override public void exitAll_decl(STParser.All_declContext ctx) { 
        GenAll_decl.exit(ctx);
    }

/* ///以下部分为所有的节点的用户操作
//////整体逻辑为
//////在enter()方法进入节点时，实例化对应的EMF模型对象存储至HashMap中
//////根据walker的遍历规则，完成enter()方法后，会进行递归，遍历子树直至叶子节点，再执行exit()方法
//////在exit()方法退出节点时，完成模型中的引用设置 */

	@Override public void enterExpression(STParser.ExpressionContext ctx) { 
        GenExpression.enter(ctx);
    }

	@Override public void exitExpression(STParser.ExpressionContext ctx) { 
        GenExpression.exit(ctx);
    }

    @Override public void enterStatements(STParser.StatementsContext ctx) { 
        GenStatements.enter(ctx);
    }

    @Override public void exitStatements(STParser.StatementsContext ctx) { 
        GenStatements.exit(ctx);
    }

    @Override public void enterStmt(STParser.StmtContext ctx) { 
        GenStmt.enter(ctx);
    }

    @Override public void exitStmt(STParser.StmtContext ctx) { 
        GenStmt.exit(ctx);
    }

    @Override public void enterSubprog_ctrl_stmt(STParser.Subprog_ctrl_stmtContext ctx) { 
        GenSubprog_ctrl_stmt.enter(ctx);
    }

	@Override public void exitSubprog_ctrl_stmt(STParser.Subprog_ctrl_stmtContext ctx) { 
        GenSubprog_ctrl_stmt.exit(ctx);
    }

    @Override public void enterSelection_stmt(STParser.Selection_stmtContext ctx) { 
        GenSelection_stmt.enter(ctx);
    }

	@Override public void exitSelection_stmt(STParser.Selection_stmtContext ctx) { 
        GenSelection_stmt.exit(ctx);
    }

    @Override public void enterIteration_stmt(STParser.Iteration_stmtContext ctx) { 
        GenIteration_stmt.enter(ctx);
    }

	@Override public void exitIteration_stmt(STParser.Iteration_stmtContext ctx) { 
        GenIteration_stmt.exit(ctx);
    }

    @Override public void enterIf_stmt(STParser.If_stmtContext ctx) { 
        GenIf_stmt.enter(ctx);
    }

    @Override public void exitIf_stmt(STParser.If_stmtContext ctx) { 
        GenIf_stmt.exit(ctx);
    }

    @Override public void enterElsif_stmt(STParser.Elsif_stmtContext ctx) { 
        GenElsif_stmt.enter(ctx);
    }

	@Override public void exitElsif_stmt(STParser.Elsif_stmtContext ctx) { 
        GenElsif_stmt.exit(ctx);
    }

	@Override public void enterElse_stmt(STParser.Else_stmtContext ctx) { 
        GenElse_stmt.enter(ctx);
    }

	@Override public void exitElse_stmt(STParser.Else_stmtContext ctx) { 
        GenElse_stmt.exit(ctx);
    }

    @Override public void enterAssign_stmt(STParser.Assign_stmtContext ctx) { 
        GenAssign_stmt.enter(ctx);
    }

	@Override public void exitAssign_stmt(STParser.Assign_stmtContext ctx) { 
        GenAssign_stmt.exit(ctx);
    }

    @Override public void enterFor_stmt(STParser.For_stmtContext ctx) { 
        GenFor_stmt.enter(ctx);
    }

	@Override public void exitFor_stmt(STParser.For_stmtContext ctx) { 
        GenFor_stmt.exit(ctx);
    }

	@Override public void enterFor_list(STParser.For_listContext ctx) { 
        GenFor_list.enter(ctx);
    }

	@Override public void exitFor_list(STParser.For_listContext ctx) { 
        GenFor_list.exit(ctx);
    }

    @Override public void enterStart_expr(STParser.Start_exprContext ctx) { 
        GenStart_expr.enter(ctx);
    }

	@Override public void exitStart_expr(STParser.Start_exprContext ctx) { 
        GenStart_expr.exit(ctx);
    }

	@Override public void enterEnd_expr(STParser.End_exprContext ctx) { 
        GenEnd_expr.enter(ctx);
    }

	@Override public void exitEnd_expr(STParser.End_exprContext ctx) { 
        GenEnd_expr.exit(ctx);
    }

	@Override public void enterStep_expr(STParser.Step_exprContext ctx) { 
        GenStep_stmt.exit(ctx);
    }

    @Override public void enterWhile_stmt(STParser.While_stmtContext ctx) { 
        GenWhile_stmt.enter(ctx);
    }

	@Override public void exitWhile_stmt(STParser.While_stmtContext ctx) { 
        GenWhile_stmt.exit(ctx);
    }

	@Override public void enterRepeat_stmt(STParser.Repeat_stmtContext ctx) { 
        GenRepeat_stmt.enter(ctx);
    }

	@Override public void exitRepeat_stmt(STParser.Repeat_stmtContext ctx) { 
        GenRepeat_stmt.exit(ctx);
    }

    @Override public void enterExit_stmt(STParser.Exit_stmtContext ctx) { 
        GenExit_stmt.enter(ctx);
    }

	@Override public void exitExit_stmt(STParser.Exit_stmtContext ctx) { 
        GenExit_stmt.exit(ctx);
    }

	@Override public void enterContinue_stmt(STParser.Continue_stmtContext ctx) { 
        GenContinue_stmt.enter(ctx);
    }

	@Override public void exitContinue_stmt(STParser.Continue_stmtContext ctx) { 
        GenContinue_stmt.exit(ctx);
    }

    @Override public void enterReturn_stmt(STParser.Return_stmtContext ctx) { 
        GenReturn_stmt.enter(ctx);
    }

	@Override public void exitReturn_stmt(STParser.Return_stmtContext ctx) { 
        GenReturn_stmt.exit(ctx);
    }

    @Override public void enterCase_stmt(STParser.Case_stmtContext ctx) { 
        GenCase_stmt.enter(ctx);
    }

	@Override public void exitCase_stmt(STParser.Case_stmtContext ctx) { 
        GenCase_stmt.exit(ctx);
    }

    //case_slection对应ECore中的GroupStatement类
	@Override public void enterCase_selection(STParser.Case_selectionContext ctx) { 
        GenCase_selection.enter(ctx);
    }

	@Override public void exitCase_selection(STParser.Case_selectionContext ctx) { 
        GenCase_selection.exit(ctx);
    }

	@Override public void enterCase_list(STParser.Case_listContext ctx) { 
        GenCase_list.enter(ctx);
    }

	@Override public void exitCase_list(STParser.Case_listContext ctx) { 
        GenCase_list.exit(ctx);
    }

	@Override public void enterCase_list_elem(STParser.Case_list_elemContext ctx) { 
        GenCase_list_elem.enter(ctx);
    }

	@Override public void exitCase_list_elem(STParser.Case_list_elemContext ctx) { 
        GenCase_list_elem.exit(ctx);
    }

/* ////////////////////////////////////////////////////////////////////////
//////以下是关于constant的部分
//////ST.g4文件中的constant规则，对应语言模型ECore的Literal
////// */
    @Override public void enterConstant(STParser.ConstantContext ctx) { 
        GenConstant.enter(ctx);
    }

    @Override public void exitConstant(STParser.ConstantContext ctx) { 
        GenConstant.exit(ctx);
    }

    @Override public void enterNumeric_literal(STParser.Numeric_literalContext ctx) { 
        GenNumeric_literal.enter(ctx);
    }

    @Override public void exitNumeric_literal(STParser.Numeric_literalContext ctx) {
        GenNumeric_literal.exit(ctx);
    }

    @Override public void enterInt_literal(STParser.Int_literalContext ctx) { 
        GenInt_literal.enter(ctx);
    }

	@Override public void exitInt_literal(STParser.Int_literalContext ctx) { 
        GenInt_literal.exit(ctx);
    }

	@Override public void enterBool_literal(STParser.Bool_literalContext ctx) { 
        GenBool_literal.enter(ctx);
    }

	@Override public void exitBool_literal(STParser.Bool_literalContext ctx) { 
        GenBool_literal.exit(ctx);
    }

    @Override public void enterChar_literal(STParser.Char_literalContext ctx) { 
        GenChar_literal.enter(ctx);
    }

	@Override public void exitChar_literal(STParser.Char_literalContext ctx) { 
        GenChar_literal.exit(ctx);
    }

    @Override public void enterTime_literal(STParser.Time_literalContext ctx) { 
        GenTime_literal.enter(ctx);
    }

	@Override public void exitTime_literal(STParser.Time_literalContext ctx) { 
        GenTime_literal.exit(ctx);
    }

/* ////////////////////////////////////////////////////////
//////以下是关于type的部分
//////
//////
////// */
    @Override public void enterType_name(STParser.Type_nameContext ctx) { 
        GenType_name.enter(ctx);
    }

    @Override public void exitType_name(STParser.Type_nameContext ctx) { 
        GenType_name.exit(ctx);
    }

    @Override public void enterData_type_decl(STParser.Data_type_declContext ctx) { 
        GenData_type_decl.enter(ctx);
    }

    @Override public void exitData_type_decl(STParser.Data_type_declContext ctx) { 
        GenData_type_decl.exit(ctx);
    }

    @Override public void enterType_decl_list(STParser.Type_decl_listContext ctx) { 
        GenType_decl_list.enter(ctx);
    }

	@Override public void exitType_decl_list(STParser.Type_decl_listContext ctx) { 
        GenType_decl_list.exit(ctx);
    }

    @Override public void enterType_decl(STParser.Type_declContext ctx) { 
        GenType_decl.enter(ctx);
    }

    @Override public void exitType_decl(STParser.Type_declContext ctx) { 
        GenType_decl.exit(ctx);
    }

    @Override public void enterSubrange_type_decl(STParser.Subrange_type_declContext ctx) { 
        GenSubrange_type_decl.enter(ctx);
    }

	@Override public void exitSubrange_type_decl(STParser.Subrange_type_declContext ctx) {
        GenSubrange_type_decl.exit(ctx);
    }

	@Override public void enterSubrange_spec(STParser.Subrange_specContext ctx) { 
        GenSubrange_spec.enter(ctx);
    }

	@Override public void exitSubrange_spec(STParser.Subrange_specContext ctx) { 
        GenSubrange_spec.exit(ctx);
    }

    @Override public void enterSubrange(STParser.SubrangeContext ctx) { 
        GenSubrange.enter(ctx);
    }

	@Override public void exitSubrange(STParser.SubrangeContext ctx) { 
        GenSubrange.exit(ctx);
    }

    @Override public void enterEnum_type_decl(STParser.Enum_type_declContext ctx) { 
        GenEnum_type_decl.enter(ctx);
    }

	@Override public void exitEnum_type_decl(STParser.Enum_type_declContext ctx) { 
        GenEnum_type_decl.exit(ctx);
    }

	@Override public void enterEnum_spec(STParser.Enum_specContext ctx) { 
        GenEnum_spec.enter(ctx);
    }

	@Override public void exitEnum_spec(STParser.Enum_specContext ctx) { 
        GenEnum_spec.exit(ctx);
    }

	@Override public void enterQuote_value(STParser.Quote_valueContext ctx) { 
        GenQuote_value.enter(ctx);
    }

	@Override public void exitQuote_value(STParser.Quote_valueContext ctx) {
        GenQuote_value.exit(ctx);
    }

	@Override public void enterEnum_value(STParser.Enum_valueContext ctx) { 
        GenEnum_value.enter(ctx);
    }

	@Override public void exitEnum_value(STParser.Enum_valueContext ctx) { 
        GenEnum_value.exit(ctx);
    }

    @Override public void enterArray_type_decl(STParser.Array_type_declContext ctx) { 
        GenArray_type_decl.enter(ctx);
    }

	@Override public void exitArray_type_decl(STParser.Array_type_declContext ctx) { 
        GenArray_type_decl.exit(ctx);
    }

	@Override public void enterArray_spec(STParser.Array_specContext ctx) { 
        GenArray_spec.enter(ctx);
    }

	@Override public void exitArray_spec(STParser.Array_specContext ctx) { 
        GenArray_spec.exit(ctx);
    }

    @Override public void enterArray_elem_init_value(STParser.Array_elem_init_valueContext ctx) { 
        GenArray_elem_init_value.enter(ctx);
    }

	@Override public void exitArray_elem_init_value(STParser.Array_elem_init_valueContext ctx) { 
        GenArray_elem_init_value.exit(ctx);
    }

    @Override public void enterStruct_type_decl(STParser.Struct_type_declContext ctx) { 
        GenStruct_type_decl.enter(ctx);
    }

	@Override public void exitStruct_type_decl(STParser.Struct_type_declContext ctx) { 
        GenStruct_type_decl.exit(ctx);
    }

	@Override public void enterStruct_spec(STParser.Struct_specContext ctx) { 
        GenStruct_spec.enter(ctx);
    }

	@Override public void exitStruct_spec(STParser.Struct_specContext ctx) { 
        GenStruct_spec.exit(ctx);
    }

	@Override public void enterStruct_elem_decl(STParser.Struct_elem_declContext ctx) { 
        GenStruct_elem_decl.enter(ctx);
    }

	@Override public void exitStruct_elem_decl(STParser.Struct_elem_declContext ctx) { 
        GenStruct_elem_decl.exit(ctx);
    }

    @Override public void enterStruct_elem_name(STParser.Struct_elem_nameContext ctx) { 
        GenStruct_elem_name.enter(ctx);
    }

	@Override public void exitStruct_elem_name(STParser.Struct_elem_nameContext ctx) { 
        GenStruct_elem_name.exit(ctx);
    }


    @Override public void enterElem_type_name(STParser.Elem_type_nameContext ctx) { 
        GenElem_type_name.enter(ctx);
    }

    @Override public void exitElem_type_name(STParser.Elem_type_nameContext ctx) { 
        GenElem_type_name.exit(ctx);
    }

    @Override public void enterData_type_access(STParser.Data_type_accessContext ctx) { 
        GenData_type_access.enter(ctx);
    }

	@Override public void exitData_type_access(STParser.Data_type_accessContext ctx) { 
        GenData_type_access.exit(ctx);
    }

    @Override public void enterDerived_type_access(STParser.Derived_type_accessContext ctx) { 
        GenDerived_type_access.enter(ctx);
    }

    @Override public void exitDerived_type_access(STParser.Derived_type_accessContext ctx) { 
        GenDerived_type_access.exit(ctx);
    }

    @Override public void enterType_access(STParser.Type_accessContext ctx) { 
        GenType_access.enter(ctx);
    }

	@Override public void exitType_access(STParser.Type_accessContext ctx) { 
        GenType_access.exit(ctx);
    }

    @Override public void enterString_type_access(STParser.String_type_accessContext ctx) { 
        GenString_type_access.enter(ctx);
    }

	@Override public void exitString_type_access(STParser.String_type_accessContext ctx) { 
        GenString_type_access.exit(ctx);
    }



/* ////////////////////////////////////////////////////////
//////以下是关于variable的部分
//////all_var_decls节点会实例化为VariableDeclaration类的对象
//////子节点关联到父节点的实例化对象，然后在子节点中根据节点名来设置其中的section
////// */
    @Override public void enterAll_var_decls(STParser.All_var_declsContext ctx) { 
        GenAll_var_decls.enter(ctx);
    }

    @Override public void exitAll_var_decls(STParser.All_var_declsContext ctx) { 
        GenAll_var_decls.exit(ctx);
    }

    @Override public void enterIo_var_decls(STParser.Io_var_declsContext ctx) { 
        GenIo_var_decls.enter(ctx);
    }

    @Override public void exitIo_var_decls(STParser.Io_var_declsContext ctx) { 
        GenIo_var_decls.exit(ctx);
    }

    @Override public void enterVar_decls(STParser.Var_declsContext ctx) { 
        GenVar_decls.enter(ctx);
    }

    @Override public void exitVar_decls(STParser.Var_declsContext ctx) { 
        GenVar_decls.exit(ctx);
    }

    @Override public void enterVar_input_decls(STParser.Var_input_declsContext ctx) { 
        GenVar_input_decls.enter(ctx);
    }

    @Override public void exitVar_input_decls(STParser.Var_input_declsContext ctx) { 
        GenVar_input_decls.exit(ctx);
    }

    @Override public void enterVar_output_decls(STParser.Var_output_declsContext ctx) { 
        GenVar_output_decls.enter(ctx);
    }

    @Override public void exitVar_output_decls(STParser.Var_output_declsContext ctx) { 
        GenVar_output_decls.exit(ctx);
    }

    @Override public void enterVar_in_out_decls(STParser.Var_in_out_declsContext ctx) { 
        GenVar_in_out_decls.enter(ctx);
    }

    @Override public void exitVar_in_out_decls(STParser.Var_in_out_declsContext ctx) { 
            GenVar_in_out_decls.exit(ctx);
    }

    @Override public void enterVar_external_decls(STParser.Var_external_declsContext ctx) { 
        GenVar_external_decls.enter(ctx);
    }

    @Override public void exitVar_external_decls(STParser.Var_external_declsContext ctx) { 
        GenVar_external_decls.exit(ctx);
    }

    @Override public void enterVar_global_decls(STParser.Var_global_declsContext ctx) { 
        GenVar_global_decls.enter(ctx);
    }

    @Override public void exitVar_global_decls(STParser.Var_global_declsContext ctx) { 
        GenVar_global_decls.exit(ctx);
    }

    @Override public void enterVar_temp_decls(STParser.Var_temp_declsContext ctx) { 
        GenVar_temp_decls.enter(ctx);
    }

    @Override public void exitVar_temp_decls(STParser.Var_temp_declsContext ctx) { 
        GenVar_temp_decls.exit(ctx);
    }

    @Override public void enterVar_access_decls(STParser.Var_access_declsContext ctx) {
        GenVar_access_decls.enter(ctx);
    }

    @Override public void exitVar_access_decls(STParser.Var_access_declsContext ctx) { 
        GenVar_access_decls.exit(ctx);
    }

    @Override public void enterVariable_list(STParser.Variable_listContext ctx) { 
        GenVariable_list.enter(ctx);
    }

	@Override public void exitVariable_list(STParser.Variable_listContext ctx) { 
        GenVariable_list.exit(ctx);
    }

    @Override public void enterVariable_name(STParser.Variable_nameContext ctx) { 
        GenVariable_name.enter(ctx);
    }

	@Override public void exitVariable_name(STParser.Variable_nameContext ctx) { 
        GenVariable_name.exit(ctx);
    }

    @Override public void enterVariable(STParser.VariableContext ctx) { 
        GenVariable.enter(ctx);
    }

	@Override public void exitVariable(STParser.VariableContext ctx) { 
        GenVariable.exit(ctx);
    }

    @Override public void enterSymbolic_variable(STParser.Symbolic_variableContext ctx) { 
        GenSymbolic_variable.enter(ctx);
    }

	@Override public void exitSymbolic_variable(STParser.Symbolic_variableContext ctx) {
        GenSymbolic_variable.exit(ctx);
    }

	@Override public void enterSubscript_list(STParser.Subscript_listContext ctx) { 
        GenSubscript_list.enter(ctx);
    }

	@Override public void exitSubscript_list(STParser.Subscript_listContext ctx) { 
        GenSubscript_list.exit(ctx);
    }

/* ////////////////////////////////////////////////////////
//////以下是关于initiate的部分
//////
//////
////// */
    @Override public void enterDecl_common_part(STParser.Decl_common_partContext ctx) {
        GenDecl_common_part.enter(ctx);
    }

    @Override public void exitDecl_common_part(STParser.Decl_common_partContext ctx) {
        GenDecl_common_part.exit(ctx);
    }

    @Override public void enterSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        GenSimple_spec_init.enter(ctx);
    }

    @Override public void exitSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
        GenSimple_spec_init.exit(ctx);
    }

    @Override public void enterStruct_spec_init(STParser.Struct_spec_initContext ctx) { 
        GenStruct_spec_init.enter(ctx);
    }

	@Override public void exitStruct_spec_init(STParser.Struct_spec_initContext ctx) { 
        GenStruct_spec_init.exit(ctx);
    }

	@Override public void enterStruct_init(STParser.Struct_initContext ctx) { 
        GenStruct_init.enter(ctx);
    }

	@Override public void exitStruct_init(STParser.Struct_initContext ctx) { 
        GenStruct_init.exit(ctx);
    }

	@Override public void enterStruct_elem_init(STParser.Struct_elem_initContext ctx) { 
        GenStruct_elem_init.enter(ctx);
    }

	@Override public void exitStruct_elem_init(STParser.Struct_elem_initContext ctx) { 
        GenStruct_elem_init.exit(ctx);
    }

    @Override public void enterSubrange_spec_init(STParser.Subrange_spec_initContext ctx) { 
        GenSubrange_spec_init.enter(ctx);
    }

	@Override public void exitSubrange_spec_init(STParser.Subrange_spec_initContext ctx) { 
        GenSubrange_spec_init.exit(ctx);
    }

    @Override public void enterEnum_spec_init(STParser.Enum_spec_initContext ctx) { 
        GenEnum_spec_init.enter(ctx);
    }

	@Override public void exitEnum_spec_init(STParser.Enum_spec_initContext ctx) { 
        GenEnum_spec_init.exit(ctx);
    }

    @Override public void enterArray_spec_init(STParser.Array_spec_initContext ctx) { 
        GenArray_spec_init.enter(ctx);
    }

	@Override public void exitArray_spec_init(STParser.Array_spec_initContext ctx) { 
        GenArray_spec_init.exit(ctx);
    }

	@Override public void enterArray_init(STParser.Array_initContext ctx) { 
        GenArray_init.enter(ctx);
    }

	@Override public void exitArray_init(STParser.Array_initContext ctx) { 
        GenArray_init.exit(ctx);
    }


/* ////////////////////////////////////////////////////////
//////以下是关于POU的部分
//////
//////
////// */
	@Override public void enterProg_decl(STParser.Prog_declContext ctx) { 
        GenProg_decl.enter(ctx);
    }

	@Override public void exitProg_decl(STParser.Prog_declContext ctx) { 
        GenProg_decl.exit(ctx);
    }

	@Override public void enterProg_name(STParser.Prog_nameContext ctx) { 
        GenProg_name.enter(ctx);
    }

	@Override public void exitProg_name(STParser.Prog_nameContext ctx) { 
        GenProg_name.exit(ctx);
    }

    //对于method的作用域，method内部声明只属于method，但是method是可以调用fb_decl中的声明
    //所以method_decl在创建namespace时，需要同步父节点fb_decl的namespace，否者会出现变量未声明报错
    @Override public void enterMethod_decl(STParser.Method_declContext ctx) { 
        GenMethod_decl.enter(ctx);
    }

	@Override public void exitMethod_decl(STParser.Method_declContext ctx) { 
        GenMethod_decl.exit(ctx);
    }

    @Override public void enterMethod_name(STParser.Method_nameContext ctx) { 
        GenMethod_name.enter(ctx);
    }

	@Override public void exitMethod_name(STParser.Method_nameContext ctx) { 
        GenMethod_name.exit(ctx);
    }

    @Override public void enterFunc_decl(STParser.Func_declContext ctx) { 
        GenFunc_decl.enter(ctx);
    }

	@Override public void exitFunc_decl(STParser.Func_declContext ctx) { 
        GenFunc_decl.exit(ctx);
    } 

	@Override public void enterFunc_name(STParser.Func_nameContext ctx) { 
        GenFunc_name.enter(ctx);
    }

	@Override public void exitFunc_name(STParser.Func_nameContext ctx) { 
        GenFunc_name.exit(ctx);
    }

	@Override public void enterDerived_func_name(STParser.Derived_func_nameContext ctx) { 
        GenDerived_func_name.enter(ctx);
    }

	@Override public void exitDerived_func_name(STParser.Derived_func_nameContext ctx) { 
        GenDerived_func_name.exit(ctx);
    }

	@Override public void enterFunc_call(STParser.Func_callContext ctx) { 
        GenFunc_call.enter(ctx);
    }

	@Override public void exitFunc_call(STParser.Func_callContext ctx) { 
        GenFunc_call.exit(ctx);
    }

	@Override public void enterInvocation(STParser.InvocationContext ctx) { 
        GenInvocation.enter(ctx);
    }

	@Override public void exitInvocation(STParser.InvocationContext ctx) { 
        GenInvocation.exit(ctx);
    }

    @Override public void enterFb_instance_name(STParser.Fb_instance_nameContext ctx) { 
        GenFb_instance_name.enter(ctx);
    }

	@Override public void exitFb_instance_name(STParser.Fb_instance_nameContext ctx) { 
        GenFb_instance_name.exit(ctx);
    }

    @Override public void enterParam_assign(STParser.Param_assignContext ctx) { 
        GenParam_assign.enter(ctx);
    }

	@Override public void exitParam_assign(STParser.Param_assignContext ctx) { 
        GenParam_assign.exit(ctx);
    }

    @Override public void enterFb_decl(STParser.Fb_declContext ctx) { 
        GenFb_decl.enter(ctx);
    }

	@Override public void exitFb_decl(STParser.Fb_declContext ctx) { 
        GenFb_decl.exit(ctx);
    }

    @Override public void enterDerived_fb_name(STParser.Derived_fb_nameContext ctx) { 
        GenDerived_fb_name.enter(ctx);
    }

    @Override public void exitDerived_fb_name(STParser.Derived_fb_nameContext ctx) { 
        GenDerived_fb_name.exit(ctx);
    }


}