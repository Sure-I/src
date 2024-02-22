// Generated from .//ST.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link STParser}.
 */
public interface STListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link STParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(STParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(STParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#all_decl}.
	 * @param ctx the parse tree
	 */
	void enterAll_decl(STParser.All_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#all_decl}.
	 * @param ctx the parse tree
	 */
	void exitAll_decl(STParser.All_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#namespace_decl}.
	 * @param ctx the parse tree
	 */
	void enterNamespace_decl(STParser.Namespace_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#namespace_decl}.
	 * @param ctx the parse tree
	 */
	void exitNamespace_decl(STParser.Namespace_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#namespace_elements}.
	 * @param ctx the parse tree
	 */
	void enterNamespace_elements(STParser.Namespace_elementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#namespace_elements}.
	 * @param ctx the parse tree
	 */
	void exitNamespace_elements(STParser.Namespace_elementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#namespace_h_name}.
	 * @param ctx the parse tree
	 */
	void enterNamespace_h_name(STParser.Namespace_h_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#namespace_h_name}.
	 * @param ctx the parse tree
	 */
	void exitNamespace_h_name(STParser.Namespace_h_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#namespace_name}.
	 * @param ctx the parse tree
	 */
	void enterNamespace_name(STParser.Namespace_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#namespace_name}.
	 * @param ctx the parse tree
	 */
	void exitNamespace_name(STParser.Namespace_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#using_directive}.
	 * @param ctx the parse tree
	 */
	void enterUsing_directive(STParser.Using_directiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#using_directive}.
	 * @param ctx the parse tree
	 */
	void exitUsing_directive(STParser.Using_directiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#prog_decl}.
	 * @param ctx the parse tree
	 */
	void enterProg_decl(STParser.Prog_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#prog_decl}.
	 * @param ctx the parse tree
	 */
	void exitProg_decl(STParser.Prog_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#prog_name}.
	 * @param ctx the parse tree
	 */
	void enterProg_name(STParser.Prog_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#prog_name}.
	 * @param ctx the parse tree
	 */
	void exitProg_name(STParser.Prog_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void enterFunc_decl(STParser.Func_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void exitFunc_decl(STParser.Func_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#func_name}.
	 * @param ctx the parse tree
	 */
	void enterFunc_name(STParser.Func_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#func_name}.
	 * @param ctx the parse tree
	 */
	void exitFunc_name(STParser.Func_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#std_func_name}.
	 * @param ctx the parse tree
	 */
	void enterStd_func_name(STParser.Std_func_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#std_func_name}.
	 * @param ctx the parse tree
	 */
	void exitStd_func_name(STParser.Std_func_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#derived_func_name}.
	 * @param ctx the parse tree
	 */
	void enterDerived_func_name(STParser.Derived_func_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#derived_func_name}.
	 * @param ctx the parse tree
	 */
	void exitDerived_func_name(STParser.Derived_func_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(STParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(STParser.Func_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#func_access}.
	 * @param ctx the parse tree
	 */
	void enterFunc_access(STParser.Func_accessContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#func_access}.
	 * @param ctx the parse tree
	 */
	void exitFunc_access(STParser.Func_accessContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#fb_decl}.
	 * @param ctx the parse tree
	 */
	void enterFb_decl(STParser.Fb_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#fb_decl}.
	 * @param ctx the parse tree
	 */
	void exitFb_decl(STParser.Fb_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#fb_name}.
	 * @param ctx the parse tree
	 */
	void enterFb_name(STParser.Fb_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#fb_name}.
	 * @param ctx the parse tree
	 */
	void exitFb_name(STParser.Fb_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#std_fb_name}.
	 * @param ctx the parse tree
	 */
	void enterStd_fb_name(STParser.Std_fb_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#std_fb_name}.
	 * @param ctx the parse tree
	 */
	void exitStd_fb_name(STParser.Std_fb_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#derived_fb_name}.
	 * @param ctx the parse tree
	 */
	void enterDerived_fb_name(STParser.Derived_fb_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#derived_fb_name}.
	 * @param ctx the parse tree
	 */
	void exitDerived_fb_name(STParser.Derived_fb_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#fb_instance_name}.
	 * @param ctx the parse tree
	 */
	void enterFb_instance_name(STParser.Fb_instance_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#fb_instance_name}.
	 * @param ctx the parse tree
	 */
	void exitFb_instance_name(STParser.Fb_instance_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#method_decl}.
	 * @param ctx the parse tree
	 */
	void enterMethod_decl(STParser.Method_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#method_decl}.
	 * @param ctx the parse tree
	 */
	void exitMethod_decl(STParser.Method_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#class_decl}.
	 * @param ctx the parse tree
	 */
	void enterClass_decl(STParser.Class_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#class_decl}.
	 * @param ctx the parse tree
	 */
	void exitClass_decl(STParser.Class_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#class_instance_name}.
	 * @param ctx the parse tree
	 */
	void enterClass_instance_name(STParser.Class_instance_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#class_instance_name}.
	 * @param ctx the parse tree
	 */
	void exitClass_instance_name(STParser.Class_instance_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#interface_decl}.
	 * @param ctx the parse tree
	 */
	void enterInterface_decl(STParser.Interface_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#interface_decl}.
	 * @param ctx the parse tree
	 */
	void exitInterface_decl(STParser.Interface_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#method_prototype}.
	 * @param ctx the parse tree
	 */
	void enterMethod_prototype(STParser.Method_prototypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#method_prototype}.
	 * @param ctx the parse tree
	 */
	void exitMethod_prototype(STParser.Method_prototypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#interface_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterInterface_spec_init(STParser.Interface_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#interface_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitInterface_spec_init(STParser.Interface_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#interface_value}.
	 * @param ctx the parse tree
	 */
	void enterInterface_value(STParser.Interface_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#interface_value}.
	 * @param ctx the parse tree
	 */
	void exitInterface_value(STParser.Interface_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#interface_name_list}.
	 * @param ctx the parse tree
	 */
	void enterInterface_name_list(STParser.Interface_name_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#interface_name_list}.
	 * @param ctx the parse tree
	 */
	void exitInterface_name_list(STParser.Interface_name_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#access_spec}.
	 * @param ctx the parse tree
	 */
	void enterAccess_spec(STParser.Access_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#access_spec}.
	 * @param ctx the parse tree
	 */
	void exitAccess_spec(STParser.Access_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(STParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(STParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(STParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(STParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#stmt_list}.
	 * @param ctx the parse tree
	 */
	void enterStmt_list(STParser.Stmt_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#stmt_list}.
	 * @param ctx the parse tree
	 */
	void exitStmt_list(STParser.Stmt_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(STParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(STParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssign_stmt(STParser.Assign_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssign_stmt(STParser.Assign_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#assignment_attempt}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_attempt(STParser.Assignment_attemptContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#assignment_attempt}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_attempt(STParser.Assignment_attemptContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#invocation}.
	 * @param ctx the parse tree
	 */
	void enterInvocation(STParser.InvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#invocation}.
	 * @param ctx the parse tree
	 */
	void exitInvocation(STParser.InvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subprog_ctrl_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSubprog_ctrl_stmt(STParser.Subprog_ctrl_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subprog_ctrl_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSubprog_ctrl_stmt(STParser.Subprog_ctrl_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#param_assign}.
	 * @param ctx the parse tree
	 */
	void enterParam_assign(STParser.Param_assignContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#param_assign}.
	 * @param ctx the parse tree
	 */
	void exitParam_assign(STParser.Param_assignContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#selection_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSelection_stmt(STParser.Selection_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#selection_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSelection_stmt(STParser.Selection_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(STParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(STParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#elsif_stmt}.
	 * @param ctx the parse tree
	 */
	void enterElsif_stmt(STParser.Elsif_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#elsif_stmt}.
	 * @param ctx the parse tree
	 */
	void exitElsif_stmt(STParser.Elsif_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#else_stmt}.
	 * @param ctx the parse tree
	 */
	void enterElse_stmt(STParser.Else_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#else_stmt}.
	 * @param ctx the parse tree
	 */
	void exitElse_stmt(STParser.Else_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#case_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCase_stmt(STParser.Case_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#case_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCase_stmt(STParser.Case_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#case_selection}.
	 * @param ctx the parse tree
	 */
	void enterCase_selection(STParser.Case_selectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#case_selection}.
	 * @param ctx the parse tree
	 */
	void exitCase_selection(STParser.Case_selectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#case_list}.
	 * @param ctx the parse tree
	 */
	void enterCase_list(STParser.Case_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#case_list}.
	 * @param ctx the parse tree
	 */
	void exitCase_list(STParser.Case_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#case_list_elem}.
	 * @param ctx the parse tree
	 */
	void enterCase_list_elem(STParser.Case_list_elemContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#case_list_elem}.
	 * @param ctx the parse tree
	 */
	void exitCase_list_elem(STParser.Case_list_elemContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#iteration_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIteration_stmt(STParser.Iteration_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#iteration_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIteration_stmt(STParser.Iteration_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(STParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(STParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#control_variable}.
	 * @param ctx the parse tree
	 */
	void enterControl_variable(STParser.Control_variableContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#control_variable}.
	 * @param ctx the parse tree
	 */
	void exitControl_variable(STParser.Control_variableContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#for_list}.
	 * @param ctx the parse tree
	 */
	void enterFor_list(STParser.For_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#for_list}.
	 * @param ctx the parse tree
	 */
	void exitFor_list(STParser.For_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#start_expr}.
	 * @param ctx the parse tree
	 */
	void enterStart_expr(STParser.Start_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#start_expr}.
	 * @param ctx the parse tree
	 */
	void exitStart_expr(STParser.Start_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#end_expr}.
	 * @param ctx the parse tree
	 */
	void enterEnd_expr(STParser.End_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#end_expr}.
	 * @param ctx the parse tree
	 */
	void exitEnd_expr(STParser.End_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#step_expr}.
	 * @param ctx the parse tree
	 */
	void enterStep_expr(STParser.Step_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#step_expr}.
	 * @param ctx the parse tree
	 */
	void exitStep_expr(STParser.Step_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(STParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(STParser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#repeat_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRepeat_stmt(STParser.Repeat_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#repeat_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRepeat_stmt(STParser.Repeat_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#exit_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExit_stmt(STParser.Exit_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#exit_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExit_stmt(STParser.Exit_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(STParser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(STParser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#data_type_access}.
	 * @param ctx the parse tree
	 */
	void enterData_type_access(STParser.Data_type_accessContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#data_type_access}.
	 * @param ctx the parse tree
	 */
	void exitData_type_access(STParser.Data_type_accessContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#elem_type_name}.
	 * @param ctx the parse tree
	 */
	void enterElem_type_name(STParser.Elem_type_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#elem_type_name}.
	 * @param ctx the parse tree
	 */
	void exitElem_type_name(STParser.Elem_type_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#string_Type_Name}.
	 * @param ctx the parse tree
	 */
	void enterString_Type_Name(STParser.String_Type_NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#string_Type_Name}.
	 * @param ctx the parse tree
	 */
	void exitString_Type_Name(STParser.String_Type_NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#derived_type_access}.
	 * @param ctx the parse tree
	 */
	void enterDerived_type_access(STParser.Derived_type_accessContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#derived_type_access}.
	 * @param ctx the parse tree
	 */
	void exitDerived_type_access(STParser.Derived_type_accessContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#string_type_access}.
	 * @param ctx the parse tree
	 */
	void enterString_type_access(STParser.String_type_accessContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#string_type_access}.
	 * @param ctx the parse tree
	 */
	void exitString_type_access(STParser.String_type_accessContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#type_access}.
	 * @param ctx the parse tree
	 */
	void enterType_access(STParser.Type_accessContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#type_access}.
	 * @param ctx the parse tree
	 */
	void exitType_access(STParser.Type_accessContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#type_name}.
	 * @param ctx the parse tree
	 */
	void enterType_name(STParser.Type_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#type_name}.
	 * @param ctx the parse tree
	 */
	void exitType_name(STParser.Type_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#data_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterData_type_decl(STParser.Data_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#data_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitData_type_decl(STParser.Data_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#type_decl_list}.
	 * @param ctx the parse tree
	 */
	void enterType_decl_list(STParser.Type_decl_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#type_decl_list}.
	 * @param ctx the parse tree
	 */
	void exitType_decl_list(STParser.Type_decl_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#type_decl}.
	 * @param ctx the parse tree
	 */
	void enterType_decl(STParser.Type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#type_decl}.
	 * @param ctx the parse tree
	 */
	void exitType_decl(STParser.Type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#simple_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterSimple_type_decl(STParser.Simple_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#simple_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitSimple_type_decl(STParser.Simple_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#simple_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterSimple_spec_init(STParser.Simple_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#simple_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitSimple_spec_init(STParser.Simple_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subrange_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterSubrange_type_decl(STParser.Subrange_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subrange_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitSubrange_type_decl(STParser.Subrange_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subrange_spec}.
	 * @param ctx the parse tree
	 */
	void enterSubrange_spec(STParser.Subrange_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subrange_spec}.
	 * @param ctx the parse tree
	 */
	void exitSubrange_spec(STParser.Subrange_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subrange}.
	 * @param ctx the parse tree
	 */
	void enterSubrange(STParser.SubrangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subrange}.
	 * @param ctx the parse tree
	 */
	void exitSubrange(STParser.SubrangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subrange_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterSubrange_spec_init(STParser.Subrange_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subrange_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitSubrange_spec_init(STParser.Subrange_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#enum_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterEnum_type_decl(STParser.Enum_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#enum_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitEnum_type_decl(STParser.Enum_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#named_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterNamed_spec_init(STParser.Named_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#named_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitNamed_spec_init(STParser.Named_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#enum_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterEnum_spec_init(STParser.Enum_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#enum_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitEnum_spec_init(STParser.Enum_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#enum_value_spec}.
	 * @param ctx the parse tree
	 */
	void enterEnum_value_spec(STParser.Enum_value_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#enum_value_spec}.
	 * @param ctx the parse tree
	 */
	void exitEnum_value_spec(STParser.Enum_value_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#enum_value}.
	 * @param ctx the parse tree
	 */
	void enterEnum_value(STParser.Enum_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#enum_value}.
	 * @param ctx the parse tree
	 */
	void exitEnum_value(STParser.Enum_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#array_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterArray_type_decl(STParser.Array_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#array_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitArray_type_decl(STParser.Array_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#array_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterArray_spec_init(STParser.Array_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#array_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitArray_spec_init(STParser.Array_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#array_spec}.
	 * @param ctx the parse tree
	 */
	void enterArray_spec(STParser.Array_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#array_spec}.
	 * @param ctx the parse tree
	 */
	void exitArray_spec(STParser.Array_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#array_init}.
	 * @param ctx the parse tree
	 */
	void enterArray_init(STParser.Array_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#array_init}.
	 * @param ctx the parse tree
	 */
	void exitArray_init(STParser.Array_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#array_elem_init}.
	 * @param ctx the parse tree
	 */
	void enterArray_elem_init(STParser.Array_elem_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#array_elem_init}.
	 * @param ctx the parse tree
	 */
	void exitArray_elem_init(STParser.Array_elem_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#array_elem_init_value}.
	 * @param ctx the parse tree
	 */
	void enterArray_elem_init_value(STParser.Array_elem_init_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#array_elem_init_value}.
	 * @param ctx the parse tree
	 */
	void exitArray_elem_init_value(STParser.Array_elem_init_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterStruct_type_decl(STParser.Struct_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitStruct_type_decl(STParser.Struct_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_spec}.
	 * @param ctx the parse tree
	 */
	void enterStruct_spec(STParser.Struct_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_spec}.
	 * @param ctx the parse tree
	 */
	void exitStruct_spec(STParser.Struct_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterStruct_spec_init(STParser.Struct_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitStruct_spec_init(STParser.Struct_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_decl}.
	 * @param ctx the parse tree
	 */
	void enterStruct_decl(STParser.Struct_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_decl}.
	 * @param ctx the parse tree
	 */
	void exitStruct_decl(STParser.Struct_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_elem_decl}.
	 * @param ctx the parse tree
	 */
	void enterStruct_elem_decl(STParser.Struct_elem_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_elem_decl}.
	 * @param ctx the parse tree
	 */
	void exitStruct_elem_decl(STParser.Struct_elem_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_elem_name}.
	 * @param ctx the parse tree
	 */
	void enterStruct_elem_name(STParser.Struct_elem_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_elem_name}.
	 * @param ctx the parse tree
	 */
	void exitStruct_elem_name(STParser.Struct_elem_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_init}.
	 * @param ctx the parse tree
	 */
	void enterStruct_init(STParser.Struct_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_init}.
	 * @param ctx the parse tree
	 */
	void exitStruct_init(STParser.Struct_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_elem_init}.
	 * @param ctx the parse tree
	 */
	void enterStruct_elem_init(STParser.Struct_elem_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_elem_init}.
	 * @param ctx the parse tree
	 */
	void exitStruct_elem_init(STParser.Struct_elem_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#str_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterStr_type_decl(STParser.Str_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#str_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitStr_type_decl(STParser.Str_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_type_decl}.
	 * @param ctx the parse tree
	 */
	void enterRef_type_decl(STParser.Ref_type_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_type_decl}.
	 * @param ctx the parse tree
	 */
	void exitRef_type_decl(STParser.Ref_type_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterRef_spec_init(STParser.Ref_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitRef_spec_init(STParser.Ref_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_spec}.
	 * @param ctx the parse tree
	 */
	void enterRef_spec(STParser.Ref_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_spec}.
	 * @param ctx the parse tree
	 */
	void exitRef_spec(STParser.Ref_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_name}.
	 * @param ctx the parse tree
	 */
	void enterRef_name(STParser.Ref_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_name}.
	 * @param ctx the parse tree
	 */
	void exitRef_name(STParser.Ref_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_value}.
	 * @param ctx the parse tree
	 */
	void enterRef_value(STParser.Ref_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_value}.
	 * @param ctx the parse tree
	 */
	void exitRef_value(STParser.Ref_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_addr}.
	 * @param ctx the parse tree
	 */
	void enterRef_addr(STParser.Ref_addrContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_addr}.
	 * @param ctx the parse tree
	 */
	void exitRef_addr(STParser.Ref_addrContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_assign}.
	 * @param ctx the parse tree
	 */
	void enterRef_assign(STParser.Ref_assignContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_assign}.
	 * @param ctx the parse tree
	 */
	void exitRef_assign(STParser.Ref_assignContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ref_deref}.
	 * @param ctx the parse tree
	 */
	void enterRef_deref(STParser.Ref_derefContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ref_deref}.
	 * @param ctx the parse tree
	 */
	void exitRef_deref(STParser.Ref_derefContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#all_var_decls}.
	 * @param ctx the parse tree
	 */
	void enterAll_var_decls(STParser.All_var_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#all_var_decls}.
	 * @param ctx the parse tree
	 */
	void exitAll_var_decls(STParser.All_var_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#io_var_decls}.
	 * @param ctx the parse tree
	 */
	void enterIo_var_decls(STParser.Io_var_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#io_var_decls}.
	 * @param ctx the parse tree
	 */
	void exitIo_var_decls(STParser.Io_var_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_decls(STParser.Var_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_decls(STParser.Var_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_input_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_input_decls(STParser.Var_input_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_input_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_input_decls(STParser.Var_input_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_output_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_output_decls(STParser.Var_output_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_output_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_output_decls(STParser.Var_output_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_in_out_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_in_out_decls(STParser.Var_in_out_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_in_out_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_in_out_decls(STParser.Var_in_out_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_external_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_external_decls(STParser.Var_external_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_external_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_external_decls(STParser.Var_external_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_global_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_global_decls(STParser.Var_global_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_global_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_global_decls(STParser.Var_global_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_temp_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_temp_decls(STParser.Var_temp_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_temp_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_temp_decls(STParser.Var_temp_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_access_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_access_decls(STParser.Var_access_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_access_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_access_decls(STParser.Var_access_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_local_decls}.
	 * @param ctx the parse tree
	 */
	void enterVar_local_decls(STParser.Var_local_declsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_local_decls}.
	 * @param ctx the parse tree
	 */
	void exitVar_local_decls(STParser.Var_local_declsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#loc_var_decl}.
	 * @param ctx the parse tree
	 */
	void enterLoc_var_decl(STParser.Loc_var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#loc_var_decl}.
	 * @param ctx the parse tree
	 */
	void exitLoc_var_decl(STParser.Loc_var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#loc_var_spec_init}.
	 * @param ctx the parse tree
	 */
	void enterLoc_var_spec_init(STParser.Loc_var_spec_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#loc_var_spec_init}.
	 * @param ctx the parse tree
	 */
	void exitLoc_var_spec_init(STParser.Loc_var_spec_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_local_partly_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_local_partly_decl(STParser.Var_local_partly_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_local_partly_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_local_partly_decl(STParser.Var_local_partly_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#loc_partly_var}.
	 * @param ctx the parse tree
	 */
	void enterLoc_partly_var(STParser.Loc_partly_varContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#loc_partly_var}.
	 * @param ctx the parse tree
	 */
	void exitLoc_partly_var(STParser.Loc_partly_varContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_spec}.
	 * @param ctx the parse tree
	 */
	void enterVar_spec(STParser.Var_specContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_spec}.
	 * @param ctx the parse tree
	 */
	void exitVar_spec(STParser.Var_specContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#decl_common_part}.
	 * @param ctx the parse tree
	 */
	void enterDecl_common_part(STParser.Decl_common_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#decl_common_part}.
	 * @param ctx the parse tree
	 */
	void exitDecl_common_part(STParser.Decl_common_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#unknown_decl}.
	 * @param ctx the parse tree
	 */
	void enterUnknown_decl(STParser.Unknown_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#unknown_decl}.
	 * @param ctx the parse tree
	 */
	void exitUnknown_decl(STParser.Unknown_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(STParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(STParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#variable_list}.
	 * @param ctx the parse tree
	 */
	void enterVariable_list(STParser.Variable_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#variable_list}.
	 * @param ctx the parse tree
	 */
	void exitVariable_list(STParser.Variable_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#variable_name}.
	 * @param ctx the parse tree
	 */
	void enterVariable_name(STParser.Variable_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#variable_name}.
	 * @param ctx the parse tree
	 */
	void exitVariable_name(STParser.Variable_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#multi_elem_var}.
	 * @param ctx the parse tree
	 */
	void enterMulti_elem_var(STParser.Multi_elem_varContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#multi_elem_var}.
	 * @param ctx the parse tree
	 */
	void exitMulti_elem_var(STParser.Multi_elem_varContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subscript_list}.
	 * @param ctx the parse tree
	 */
	void enterSubscript_list(STParser.Subscript_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subscript_list}.
	 * @param ctx the parse tree
	 */
	void exitSubscript_list(STParser.Subscript_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subscript}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(STParser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subscript}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(STParser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_variable}.
	 * @param ctx the parse tree
	 */
	void enterStruct_variable(STParser.Struct_variableContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_variable}.
	 * @param ctx the parse tree
	 */
	void exitStruct_variable(STParser.Struct_variableContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#struct_elem_select}.
	 * @param ctx the parse tree
	 */
	void enterStruct_elem_select(STParser.Struct_elem_selectContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#struct_elem_select}.
	 * @param ctx the parse tree
	 */
	void exitStruct_elem_select(STParser.Struct_elem_selectContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#var_access}.
	 * @param ctx the parse tree
	 */
	void enterVar_access(STParser.Var_accessContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#var_access}.
	 * @param ctx the parse tree
	 */
	void exitVar_access(STParser.Var_accessContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#symbolic_variable}.
	 * @param ctx the parse tree
	 */
	void enterSymbolic_variable(STParser.Symbolic_variableContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#symbolic_variable}.
	 * @param ctx the parse tree
	 */
	void exitSymbolic_variable(STParser.Symbolic_variableContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(STParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(STParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#numeric_literal}.
	 * @param ctx the parse tree
	 */
	void enterNumeric_literal(STParser.Numeric_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#numeric_literal}.
	 * @param ctx the parse tree
	 */
	void exitNumeric_literal(STParser.Numeric_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void enterInt_literal(STParser.Int_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void exitInt_literal(STParser.Int_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#real_literal}.
	 * @param ctx the parse tree
	 */
	void enterReal_literal(STParser.Real_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#real_literal}.
	 * @param ctx the parse tree
	 */
	void exitReal_literal(STParser.Real_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#real_literal_exponent}.
	 * @param ctx the parse tree
	 */
	void enterReal_literal_exponent(STParser.Real_literal_exponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#real_literal_exponent}.
	 * @param ctx the parse tree
	 */
	void exitReal_literal_exponent(STParser.Real_literal_exponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#bool_literal}.
	 * @param ctx the parse tree
	 */
	void enterBool_literal(STParser.Bool_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#bool_literal}.
	 * @param ctx the parse tree
	 */
	void exitBool_literal(STParser.Bool_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#typed_literal}.
	 * @param ctx the parse tree
	 */
	void enterTyped_literal(STParser.Typed_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#typed_literal}.
	 * @param ctx the parse tree
	 */
	void exitTyped_literal(STParser.Typed_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void enterChar_literal(STParser.Char_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void exitChar_literal(STParser.Char_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#time_literal}.
	 * @param ctx the parse tree
	 */
	void enterTime_literal(STParser.Time_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#time_literal}.
	 * @param ctx the parse tree
	 */
	void exitTime_literal(STParser.Time_literalContext ctx);
}