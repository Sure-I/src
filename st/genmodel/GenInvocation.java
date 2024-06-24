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

public class GenInvocation {
    public static void enter(STParser.InvocationContext ctx){
        try{
            Invocation emf = ResourceBuilder.exprFactory.createInvocation();
            Namespace namespaceEmf = (Namespace)Traversal.getParentEmf(ctx);
            emf.setNamespace(namespaceEmf);
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in enterInvocation!!!");
        }
    }

    public static void exit(STParser.InvocationContext ctx){ 
        try{
            Invocation emf = (Invocation)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case"fb_instance_name":
                        FBInstanceName FBInstanceNameEmf = (FBInstanceName)Traversal.getChildEmf(ctx, i);
                        emf.setInstanceName(FBInstanceNameEmf);
                        break;
                    case"param_assign":
                        emf.getParameter().add((Parameter)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                        break;
                    default: break;
                }
            }
        } catch(Exception exception){
            System.err.println("Error in exitInvocation!!!");
        }
    }
}
