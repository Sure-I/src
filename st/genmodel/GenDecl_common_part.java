package st.genmodel;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import parser.*;
import st.pre.*;
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

public class GenDecl_common_part {
////decl_common_part的实例化对象为Initializer，实例化步骤在子节点中进行完成，本节点中进行关联
////在exit中完成容器declaration以及属性variableList的设定，并且完成VariableList对象和Variable对象的属性设置
    public static void enter(STParser.Decl_common_partContext ctx){
        try{
            VariableDeclaration parentEmf = (VariableDeclaration)Traversal.getParentEmf(ctx);
            Namespace namespaceEmf = parentEmf.getNamespace();
            ResourceBuilder.mapEmf.put(ctx, namespaceEmf);
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getDeclType().getLiteral());
        } catch(Exception exception){
            System.err.println("Error In enterDecl_common_part!!!");
        }
    }   

    public static void exit(STParser.Decl_common_partContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 2);
            VariableInitializer emf = (VariableInitializer)ResourceBuilder.mapEmf.get(ctx); 


            //System.out.println(emf.getTestString());

            //initializer属于variableDeclaration，而且一个variableDeclaration中可能含有多个initializer，在此设定容器关系
            ParserRuleContext parentNode = ctx.getParent();
            VariableDeclaration parentEmf = (VariableDeclaration)ResourceBuilder.mapEmf.get(parentNode);
            emf.setDeclaration(parentEmf);
            parentEmf.getInitializer().add(emf);

            //设定variable_list的容器关系
            STParser.Variable_listContext variableListNode = (STParser.Variable_listContext)ctx.getChild(0);
            VariableList variableListEmf = (VariableList)ResourceBuilder.mapEmf.get(variableListNode);
            variableListEmf.setInitializer(emf);
            emf.setVariableList(variableListEmf);

            //同步设定variable_list和variable的类型
            for(int i = 0; i < variableListNode.getChildCount(); i++){
                ParseTree childNode = variableListNode.getChild(i);
                if(childNode instanceof STParser.Variable_nameContext){
                    Variable varEmf = (Variable)ResourceBuilder.mapEmf.get(childNode);
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
            System.err.println("Error In exitDecl_common_part!!!");
        }
    }
}
