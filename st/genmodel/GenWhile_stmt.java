package st.genmodel;

import org.antlr.v4.runtime.tree.ParseTree;

import parser.*;
import parser.STParser.While_stmtContext;
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

public class GenWhile_stmt {
    public static void enter(STParser.While_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in while_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterWhile_stmt!!!");
        }
    }

    public static void exit(STParser.While_stmtContext ctx){ 
        try{
            WhileStatement emf = ResourceBuilder.stmtFactory.createWhileStatement();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
                switch(childNodeStr){
                    case "expression":
                        emf.setCondition((Expression)Traversal.getChildEmf(ctx, i));
                        break;
                    case "stmt_list":
                        emf.setStatementBody((StatementBody)Traversal.getChildEmf(ctx, i));
                        break;
                    default: ;
                }
            }
        }catch(Exception exception){
            System.err.println("Error In exitWhile_stmt!!!");
        }
    }
}
