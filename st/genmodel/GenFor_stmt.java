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

public class GenFor_stmt {
    public static void enter(STParser.For_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in for_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterFor_stmt!!!");
        }
    }

    public static void exit(STParser.For_stmtContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 3);
            ForStatement emf = (ForStatement)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
                switch(childNodeStr){
                    case "control_variable":
                        emf.setControlVariable(childNode.getText());
                        break;
                    case "stmt_list":
                        emf.setStatementBody((StatementBody)ResourceBuilder.mapEmf.get(childNode));
                        break;
                    default: ;
                }
            }

            //System.out.println(((LiteralExpression)emf.getEndExpression()).getLiteral().getValue());
        } catch(Exception exception){
            System.err.println("Error In exitFor_stmt!!!");
        }
    }
}
