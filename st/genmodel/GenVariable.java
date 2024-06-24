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

public class GenVariable {
    public static void enter(STParser.VariableContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else if(Traversal.getParentEmf(ctx) instanceof Statement){
                Namespace namespaceEmf = ((Statement)Traversal.getParentEmf(ctx)).getNamespace();
                ResourceBuilder.mapEmf.put(ctx, namespaceEmf);
            }
        } catch(Exception exception){
            System.err.println("Error In enterVariable!!!");
        }
    }

    public static void exit(STParser.VariableContext ctx){ 
        try{
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "symbolic_variable":
                    Traversal.setFromChildEmf(ctx, 0);
                    break;
                default: ;
            }
        } catch(Exception exception){
            System.err.println("Error In exitVariable!!!");
        }
    }
}
