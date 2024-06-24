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

public class GenReturn_stmt {
    public static void enter(STParser.Return_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in return_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterReturn_stmt!!!");
        }
    }

    public static void exit(STParser.Return_stmtContext ctx){ 
        try{
            ReturnStatement emf = ResourceBuilder.stmtFactory.createReturnStatement();
            ResourceBuilder.mapEmf.put(ctx, emf);  
            for(int i = 0; i < ctx.getChildCount(); i++){
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(i)) == "expression"){
                    emf.setExpression(((Expression)ResourceBuilder.mapEmf.get(ctx.getChild(i))));
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitReturn_stmt!!!");
        }
    }
}
