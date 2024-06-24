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

public class GenStmt {
    public static void enter(STParser.StmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else if(Traversal.getParentEmf(ctx) instanceof StatementBody){
                Namespace namespaceEmf = ((StatementBody)Traversal.getParentEmf(ctx)).getNamespace();
                ResourceBuilder.mapEmf.put(ctx, namespaceEmf);
            }
        } catch(Exception exception){
            System.err.println("Error In enterStmt!!!");
        }
    }

    public static void exit(STParser.StmtContext ctx){ 
        Traversal.setFromChildEmf(ctx, 0);
        //System.out.println((Statement)Traversal.getEmf(ctx).getTestString());
    }
}
