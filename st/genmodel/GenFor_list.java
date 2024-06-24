package st.genmodel;

import org.antlr.v4.runtime.tree.ParseTree;

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

public class GenFor_list {
    public static void enter(STParser.For_listContext ctx){

    }

    public static void exit(STParser.For_listContext ctx){ 
                try{
            ForStatement emf = ResourceBuilder.stmtFactory.createForStatement();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setTestString("for_stmt_emf");
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
                switch(childNodeStr){
                    case "start_expr":
                        emf.setStartExpression((Expression)Traversal.getChildEmf(ctx, i));
                        break;
                    case "end_expr":
                        emf.setEndExpression((Expression)Traversal.getChildEmf(ctx, i));
                        break;
                    case "step_expr":
                        emf.setStepExpression((Expression)Traversal.getChildEmf(ctx, i));
                        break;
                    default: ;
                }
            }
        }catch(Exception exception){
            System.err.println("Error In exitFor_list!!!");
        }
    }
}
