/* ///针对BaseListener类进行继承并重载方法，该Listener用于对生成的EMF模型进行校验
//////主要逻辑为
//////该类的构造器，先通过GenModelListener对输入文件的解析树实例化为EMF
//////将GenModelListener中的各个EMF的HashMap提取出
//////再通过walker进行一次遍历，该类中对于每个节点的enter()和eixt()方法对的重载为校验功能

/////////////已编写规则条目如下，<>规则写在GenModelListener中，[]规则写在ModelCheckListener中
//////*<作用域错误> 于GenModelListener的enterRuleContext*/
//////*<使用未声明变量> 于exitVariable_name*/
//////*<新声明变量名已存在> 于exitVariable_name*/
//////*<使用未声明自定义类型> 于exitType_name*/
//////*<声明自类型名已存在> 于exitType_name*/
//////*<调用不存在的函数> 于derived_func_name*/
//////*<实例化的功能块名称不存在> */
//////*<使用了未定义的接口>*/
//////*<指定为基础的功能块不存在或不是功能块>*/
//////*<调用未实例化的功能块> */
//////*[接口指定的方法之一尚未由实现的功能块提供]*/
//////*[常量值超过数据类型范围]*/
//////*[expression操作数类型不匹配] 于exitExpression*/
//////*[赋值语句中变量与表达式类型不匹配]  于exitAssign_stmt*/
//////*[enum类型变量赋值不在定义中] 于exitQuote_value */
//////*[enum类型变量初始化问题] 于exitEnum_spec_init  */
//////*[数组的初始化，分配值与数组大小不符合] */
//////*[数组访问下标超过数组大小] */
//////*[不是数组的数据类型用 '[]' 索引] */
//////*<访问非struct成员> 于exitSymbolic_variable*/
//////*[struct初始化不匹配] 于exitStruct_spec_init */
//////*[for循环计数器变量未正确初始化]*/
//////*[未指定字符串的长度，'(' 之后的字符串大小未定义]*/
//////*[在函数调用中定义了一个局部变量] 于exitFunc_call*/
//////*[局部变量在函数调用中作为输出处理] 于exitFunc_call*/
//////*[传递给被调用函数的参数过多或过少] 于exitFunc_call*/
//////*[传递给被调用函数的参数不匹配] 于exitFunc_call*/


/*/////////////////
    关于作用域的校验，应该定义一种方法，输入参数为当前引用数据，和当前作用域，对比当前引用数据的可作用域
    那么应该如何在生成EMF模型时为其添加可作用域，是否应该引入一棵作用域树来记录整体的作用域
    需要考虑作用域的元素有，变量，type，method，function，function block
    type、funtion和funtion block一般都是声明为全局作用域
    method一般在funtion block内部进行声明
    变量的作用域则更为灵活

    其实一个是考虑作用域
    另一个是考虑成员，最复杂的情况是fb中的struct，或者不同fb中相同名称的method
/////////////////*/

///////////////////////////////////////////////////////////////////////////////////////////////// */
import java.util.ArrayList;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

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

public class ModelCheckListener extends STBaseListener{
    //从genModelListener中提出的HaspMap存放在下列属性中
    public Map<ParseTree, EObject> mapEmf = new HashMap<>();
    public Map<ParseTree, String> mapNodeStr = new HashMap<>();
    public Map<String, EObject> mapTypeEmf = new HashMap<>();
    public Map<String, EObject> mapGlobalVarEmf = new HashMap<>();
    public Map<String, EObject> mapGlobalFunEmf = new HashMap<>();
    public Map<String, EObject> mapGlobalFBEmf = new HashMap<>();

    //////getEmf()方法，获取节点自身的emf并返回该对象
    private EObject getEmf(ParserRuleContext ctx){
        EObject emf = mapEmf.get(ctx);
        return emf;
    }

    //////compareType()，用来对比输入参数的类型是否匹配，确认是否能进行运算
    private Boolean compareType(Type type, Expression expr){
        Boolean result;
        if(expr instanceof LiteralExpression){
            if(expr.getType().getName() == "INTEGER"){
                switch(type.getName()){
                    case "SINT": result = true; break;
                    case "INT": result = true; break;
                    case "DINT": result = true; break;
                    case "LINT": result = true; break;
                    case "USINT": result = true; break;
                    case "UINT": result = true; break;
                    case "UDINT": result = true; break;
                    case "ULINT": result = true; break;
                    default: result = false; break;
                }
            }
            else if(expr.getType().getName() == "SINGLE_BYTE_CHAR"){
                switch(type.getName()){
                    case "STRING": result = true; break;
                    case "WSTRING": result = true; break;
                    case "CHAR": result = true; break;
                    case "WCHAR": result = true; break;
                    default: result = false; break;
                }
            }
            else if(expr.getType().getName() == "REAL"){
                switch(type.getName()){
                    case "REAL": result = true; break;
                    default: result = false; break;
                }
            }
            else{ result = false; }
        }
        else{ 
            if(type.getName() == expr.getType().getName()){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    private Boolean compareType(Expression expr1, Expression expr2){
        Boolean result;
        if(expr2 instanceof LiteralExpression){
            if(expr2.getType().getName() == "INTEGER"){
                switch(expr1.getType().getName()){
                    case "SINT": result = true; break;
                    case "INT": result = true; break;
                    case "DINT": result = true; break;
                    case "LINT": result = true; break;
                    case "USINT": result = true; break;
                    case "UINT": result = true; break;
                    case "UDINT": result = true; break;
                    case "ULINT": result = true; break;
                    default: result = false; break;
                }
            }
            else if(expr2.getType().getName() == "SINGLE_BYTE_CHAR"){
                switch(expr1.getType().getName()){
                    case "STRING": result = true; break;
                    case "WSTRING": result = true; break;
                    case "CHAR": result = true; break;
                    case "WCHAR": result = true; break;
                    default: result = false; break;
                }
            }
            else if(expr2.getType().getName() == "REAL"){
                switch(expr1.getType().getName()){
                    case "REAL": result = true; break;
                    default: result = false; break;
                }
            }
            else{ result = false; }
        }
        else{ 
            if(expr1.getType().getName() == expr2.getType().getName()){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    private Boolean compareType(Variable var, Expression expr){
        Boolean result;
        if(expr instanceof LiteralExpression){
            switch(expr.getType().getName()){
                case"INTEGER":
                    switch(var.getType().getName()){
                        case "SINT": result = true; break;
                        case "INT": result = true; break;
                        case "DINT": result = true; break;
                        case "LINT": result = true; break;
                        case "USINT": result = true; break;
                        case "UINT": result = true; break;
                        case "UDINT": result = true; break;
                        case "ULINT": result = true; break;
                        default: result = false; break;
                    }
                break;
                case"SINGLE_BYTE_CHAR":
                    switch(var.getType().getName()){
                        case "STRING": result = true; break;
                        case "WSTRING": result = true; break;
                        case "CHAR": result = true; break;
                        case "WCHAR": result = true; break;
                        default: result = false; break;
                    }
                break;
                case"REAL":
                    switch(var.getType().getName()){
                        case "REAL": result = true; break;
                        default: result = false; break;
                    }
                break;
                default: result = false; break;
                }
        }
        else{ 
            if(var.getType().getName() == expr.getType().getName()){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    @Override public void exitExpression(STParser.ExpressionContext ctx) { 
        Expression exprEmf = (Expression)getEmf(ctx);
        if(exprEmf instanceof BinaryExpression){
            BinaryExpression emf = (BinaryExpression)exprEmf;
            if(compareType(emf.getFirstExpression(), emf.getSecondExpression())){ }
            else{
                System.err.println("[ Type is not compatible in: " + ctx.getText() + " ]");
                System.exit(0);
            }
        }
    }

    @Override public void exitAssign_stmt(STParser.Assign_stmtContext ctx) { 
        try{ 
            AssignmentStatement emf = (AssignmentStatement)getEmf(ctx);
            if(emf.getExpression() != null){ 
                if(compareType(emf.getVariable(), emf.getExpression())){ }
                else{
                    System.err.println("[ Type is not compatible in: " + ctx.getText() + " ]");
                    System.exit(0);
                }
            }
            else if(emf.getQuoteValue() != null){
                if(emf.getQuoteValue().getQuoteType().getName() == emf.getVariable().getType().getName()){ }
                else{
                    System.err.println("[ Type is not compatible in: " + ctx.getText() + " ]");
                    System.exit(0);
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In ModelCheck Assign_stmt!!! '" +  ctx.getText() + "': Can not Get type");
        }
    }

    //通过比对实际传入参数与function所定义的参数，判定参数传入的正确性
    @Override public void exitFunc_call(STParser.Func_callContext ctx) { 
        try{
            FunctionCall emf = (FunctionCall)getEmf(ctx);
            Function emfFunction = emf.getFunction();
            FunctionDeclaration emfFuncDecl = (FunctionDeclaration)emfFunction.getDeclaration();

            EList<Parameter> funParam = emf.getParameter();

            EList<Variable> inputVariables = ECollections.newBasicEList();
            for(int i = 0; i < emfFuncDecl.getVariable().size(); i++){
                if(emfFuncDecl.getVariable().get(i).getVariableList().getInitializer().getDeclaration().getSection() == VariableSection.VAR_INPUT)
                inputVariables.add(emfFuncDecl.getVariable().get(i));
            }

            if(funParam.size() == inputVariables.size()){
                for(int i = 0; i < funParam.size(); i++){
                    for(int j = 0; j < inputVariables.size(); j++){
                        if(funParam.get(i).getName().equals(inputVariables.get(j).getName())) break;
                        if(j == (inputVariables.size()-1)){
                            System.err.println(" [ Error: parameters does not match definition in function '" + ctx.getText() + "' ]");
                            System.exit(0);
                        }
                    }
                }
                for(int i = 0; i < inputVariables.size(); i++){
                    for(int j = 0; j < funParam.size(); j++){
                        if(inputVariables.get(i).getName().equals(funParam.get(j).getName())) break;
                        if(j == (funParam.size()-1)){
                            System.err.println(" [ Error: parameters does not match definition in function '" + ctx.getText() + "' ]");
                            System.exit(0);
                        }
                    }
                }
            }
            else{
                System.err.println(" [ Error: parameters does not match definition in function '" + ctx.getText() + "' ]");
                System.exit(0);
            }


        } catch(Exception exception){
            System.err.println("Error in ModelCheck Func_call!!!");
        }
    }


    @Override public void exitEnum_spec_init(STParser.Enum_spec_initContext ctx) { 
        try{

            EnumInit emf = (EnumInit)getEmf(ctx);
            EnumTypeDecl declEmf;
            if(emf.getType() == null){
                declEmf = (EnumTypeDecl)emf.getNoNameType();
            }
            else{
                EnumType typeEmf = (EnumType)emf.getType();
                declEmf = (EnumTypeDecl)typeEmf.getTypeDeclaration();
            }

            QuoteValue valueEmf = (QuoteValue)mapEmf.get(ctx.getChild(2));
            String valueString = valueEmf.getEnumValue().getValue();
            //System.out.println(valueString);

            for(int i = 0; i < declEmf.getEnumValue().size(); i++){
                //System.out.println(declEmf.getEnumValue().get(i).getValue());
                String existValue = declEmf.getEnumValue().get(i).getValue();
                if(valueString.equals(existValue)) break;
                if(i == (declEmf.getEnumValue().size() - 1)){
                    System.err.println("Error: " + ctx.getParent().getText() + " [ '" + valueString + "' does not match any value in enum type ]");
                }
            }
        } catch(Exception exception){
            System.err.println("Error in ModelCheck Enum_spec_init");
        }

    }

    @Override public void exitStruct_spec_init(STParser.Struct_spec_initContext ctx) { 
        try{ 
            StructSpecInit emf = (StructSpecInit)mapEmf.get(ctx);
            StructTypeDecl emfTypeDecl = (StructTypeDecl)mapEmf.get(ctx.getChild(0));
            StructInit emfStructInit = emf.getStructInit();
            for(int i = 0; i < emfStructInit.getElemInit().size(); i++){
                String elemName = emfStructInit.getElemInit().get(i).getName();
                //System.out.println(elemName);
                for(int j = 0; j < emfTypeDecl.getElement().size(); j++){
                    String existName = emfTypeDecl.getElement().get(j).getName();
                    if(elemName.equals(existName)){
                        //System.out.println(existName);
                        break;
                    }
                    if(j == (emfTypeDecl.getElement().size() -1)){
                        System.err.println("Error: " + ctx.getParent().getText() + " [ '" + elemName + "' does not match any element in struct type ]");
                    }
                }
            }
        } catch(Exception exception){
            System.err.println("Error in ModelCheck Struct_spec_init");
        }
    }

}
