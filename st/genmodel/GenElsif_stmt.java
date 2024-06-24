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

public class GenElsif_stmt {
    public static void enter(STParser.Elsif_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in elseif_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterElseif_stmt!!!");
        }
    }

    public static void exit(STParser.Elsif_stmtContext ctx){ 
        try{
            IfStatement emf = ResourceBuilder.stmtFactory.createIfStatement();
            emf.setTestString("if_stmt_emf");
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "expression":
                        emf.setCondition((Expression)Traversal.getChildEmf(ctx, i));
                        break;
                    case "stmt_list":
                        emf.setThenStatement((StatementBody)Traversal.getChildEmf(ctx, i));
                        break;
                    default: //System.out.println("elsif_stmt error!!!");
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitElseif_stmt!!!");
        }
    }
}
