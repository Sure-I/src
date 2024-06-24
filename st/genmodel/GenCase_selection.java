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

public class GenCase_selection {
    public static void enter(STParser.Case_selectionContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_selection");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_selection!!!");
        }
    }

    public static void exit(STParser.Case_selectionContext ctx){ 
        try{ 
            Traversal.setFromChildEmf(ctx, 0);
            GroupStatement emf = (GroupStatement)Traversal.getEmf(ctx);
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "stmt_list"){
                    emf.setStatementBody((StatementBody)Traversal.getChildEmf(ctx, i));
                }
                else{ }
            }

            //System.out.println(((LiteralExpression)emf.getExprLabel().get(0)).getLiteral().getValue());
        } catch(Exception exception){
            System.err.println("Error In enterCase_selection!!!");
        }
    }
}
