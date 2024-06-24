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

public class GenContinue_stmt {
    public static void enter(STParser.Continue_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in continue_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterContinue_stmt!!!");
        }
    }

    public static void exit(STParser.Continue_stmtContext ctx){ 
        try{
            ContinueStatement emf = ResourceBuilder.stmtFactory.createContinueStatement();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In exitContinue_stmt!!!");
        }
    }
}
