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

public class GenElse_stmt {
    public static void enter(STParser.Else_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in else_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterElse_stmt!!!");
        }
    }

    public static void exit(STParser.Else_stmtContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 1);
        } catch(Exception exception){
            System.err.println("Error In exitElse_stmt!!!");
        }
    }
}
