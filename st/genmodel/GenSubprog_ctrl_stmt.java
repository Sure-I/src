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

public class GenSubprog_ctrl_stmt {
    public static void enter(STParser.Subprog_ctrl_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else if(Traversal.getParentEmf(ctx) instanceof StatementBody){
                Namespace namespaceEmf = ((StatementBody)Traversal.getParentEmf(ctx)).getNamespace();
                ResourceBuilder.mapEmf.put(ctx, namespaceEmf);
            }
        } catch(Exception exception){
            System.err.println("Error In enterSubprog_crtl_stmt!!!");
        }
    }

    public static void exit(STParser.Subprog_ctrl_stmtContext ctx){ 
        try{
            SubprogStatement emf = ResourceBuilder.stmtFactory.createSubprogStatement();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In exitSubprog_crtl_stmt!!!");
        }
    }
}
