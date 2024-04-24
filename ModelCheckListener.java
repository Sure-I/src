/* ///针对BaseListener类进行继承并重载方法，该Listener用于对生成的EMF模型进行校验
//////主要逻辑为
//////该类的构造器，先通过GenModelListener对输入文件的解析树实例化为EMF
//////将GenModelListener中的各个EMF的HashMap提取出
//////再通过walker进行一次遍历，该类中对于每个节点的enter()和eixt()方法对的重载为校验功能

/////////////已编写规则条目如下，<>规则写在GenModelListener中，[]规则写在ModelCheckListener中
//////*<使用未声明标识符，比如变量、自定义类型> */
//////*<调用不存在的函数或功能块> */
//////*[expression操作数类型不匹配] */
//////*<enum类型变量赋值不在定义中> */
//////* */
///////////////////////////////////////////////////////////////////////////////////////////////// */
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.emf.ecore.EObject;

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
    public Map<String, EObject> mapVarEmf = new HashMap<>();
    public Map<String, EObject> mapTypeEmf = new HashMap<>();


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
        AssignmentStatement emf = (AssignmentStatement)getEmf(ctx);
        if(emf.getExpression() == null){ }
        else{
            if(compareType(emf.getVariable(), emf.getExpression())){ }
            else{
                System.err.println("[ Type is not compatible in: " + ctx.getText() + " ]");
                System.exit(0);
            }
        }
    }

    @Override public void exitSimple_spec_init(STParser.Simple_spec_initContext ctx) { 
/*         SimpleInit emf = (SimpleInit)getEmf(ctx);
        if(emf.getValue() == null){ }
        else{
            if(compareType(emf.getType(), emf.getValue())){ }
            else{
                System.err.println("Type is not compatible in: " + ctx.getText());
                System.exit(0);
            }
        } */
    }

}
