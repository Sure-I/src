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

public class GenFunc_call {
    public static void enter(STParser.Func_callContext ctx){
        try{
            FunctionCall emf = ResourceBuilder.exprFactory.createFunctionCall();
            Namespace namespaceEmf = (Namespace)Traversal.getParentEmf(ctx);
            emf.setNamespace(namespaceEmf);
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in enterFunc_call!!!");
        }
    }

    public static void exit(STParser.Func_callContext ctx){ 
        try{
            FunctionCall emf = (FunctionCall)Traversal.getEmf(ctx);
            for(int i =0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "func_name":
                        Function emfFunction = (Function)Traversal.getChildEmf(ctx, 0);
                        emf.setFunction(emfFunction);
                        emf.setType(((FunctionDeclaration)(emfFunction.getDeclaration())).getType());
                        break;
                    case "param_assign":
                        emf.getParameter().add((Parameter)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
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
}
