package st.genmodel;

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

public class GenParam_assign {
    public static void enter(STParser.Param_assignContext ctx){
        try{
            String parentNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentNodeStr){
                case"func_call":
                    FunctionCall parentEmf0 = (FunctionCall)Traversal.getParentEmf(ctx);
                    Namespace namespaceEmf0 = parentEmf0.getNamespace();
                    ResourceBuilder.mapEmf.put(ctx, namespaceEmf0);
                    break;
                case"invocation":
                    break;    
            }
        } catch(Exception exception){
            System.err.println("Error in enterParam_assign!!!");
        }
    }

    public static void exit(STParser.Param_assignContext ctx){ 
        try{
            Parameter emf = ResourceBuilder.exprFactory.createParameter();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "variable_name":
                        emf.setName(((Variable)ResourceBuilder.mapEmf.get(ctx.getChild(i))).getName());
                        //System.out.println(emf.getName());
                        break;
                    case "expression":
                        emf.setExpression((Expression)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
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
