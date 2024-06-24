/* ///封装walker，travelsal()只需要传入对应的listener即可
//////该类提供一些静态方法用来在父子节点中传递EMF模型对象 */
package st.pre;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.emf.ecore.EObject;

import parser.STListener;
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
import st.statements.*;

public final class Traversal {
    private ParseTreeWalker myWalker = new ParseTreeWalker();

    public void travelsal(STListener listener){
        myWalker.walk(listener, ResourceBuilder.tree);
    }

//////setFromChildEmf()方法，获取某个子节点的emf并关联
    public static void setFromChildEmf(ParserRuleContext ctx, int i){
        EObject emf = ResourceBuilder.mapEmf.get(ctx.getChild(i));
        ResourceBuilder.mapEmf.put(ctx, emf);
    }
//////setFromParentEmf()方法，获取父节点的emf并关联
    public static void setFromParentEmf(ParserRuleContext ctx){
        EObject emf = ResourceBuilder.mapEmf.get(ctx.getParent());
        ResourceBuilder.mapEmf.put(ctx, emf);
    }
//////getEmf()方法，获取节点自身的emf并返回该对象
    public static EObject getEmf(ParserRuleContext ctx){
        EObject emf = ResourceBuilder.mapEmf.get(ctx);
        return emf;
    }
//////getChildEmf()，获取第i个子节点的emf并返回该对象
    public static EObject getChildEmf(ParserRuleContext ctx, int i){
        EObject emf = ResourceBuilder.mapEmf.get(ctx.getChild(i));
        return emf;
    }
//////getChildEmf()，获取父节点的emf并返回该对象
    public static EObject getParentEmf(ParserRuleContext ctx){
        EObject emf = ResourceBuilder.mapEmf.get(ctx.getParent());
        return emf;
    }
//////getVarInNamespace(),获取Namespace中的Variable
    public static Variable getVarInNamespace(String varName, Namespace namespaceEmf){
        if(namespaceEmf.getDeclType().getLiteral() == "GLOBAl"){
            return (Variable)ResourceBuilder.mapGlobalVarEmf.get(varName);
        }
        POUDeclaration POUDecl = (POUDeclaration)(namespaceEmf.getNamespace()); 
        return getVarInDecl(varName, POUDecl);
    }
//////getVarInDecl(),获取POUDeclaration中的Variable
    public static Variable getVarInDecl(String varName, POUDeclaration POUDecl){
        for(int i = 0; i < POUDecl.getVariable().size(); i++){
            if(varName.equals(POUDecl.getVariable().get(i).getName())) 
                return POUDecl.getVariable().get(i);
        }

        return null;
    }
//////getFunInNamespace(),获取Namespace中的function
    public static Function getFunInNamespace(String funName, Namespace namespaceEmf){
        if(namespaceEmf.getDeclType().getLiteral() == "GLOBAl"){
            return (Function)ResourceBuilder.mapGlobalFunEmf.get(funName);
        }
        POUDeclaration POUDecl = (POUDeclaration)(namespaceEmf.getNamespace()); 
        for(int i = 0; i < POUDecl.getFunction().size(); i++){
            if(funName.equals(POUDecl.getFunction().get(i).getName()))
                return POUDecl.getFunction().get(i);
        }

        return null;
    }
//////getMethodInDecl(),获取FBDeclaration中的Method
    public static Method getMethodInDecl(String methodName, FunctionBlockDeclaration FBDeclEmf){
        for(int i = 0; i < FBDeclEmf.getMethod().size(); i++){
            if(methodName.equals(FBDeclEmf.getMethod().get(i).getName())){
                return FBDeclEmf.getMethod().get(i);
            }
        }

        return null;
    }
}
