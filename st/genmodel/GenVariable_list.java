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

public class GenVariable_list {
    public static void enter(STParser.Variable_listContext ctx){
        try{
            VariableList emf = ResourceBuilder.varFactory.createVariableList();
            emf.setNamespace((Namespace)Traversal.getParentEmf(ctx));
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterVariable_list!!!");
        }
    }

    public static void exit(STParser.Variable_listContext ctx){ 
        try{ 
            VariableList emf = (VariableList)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                if(childNode instanceof STParser.Variable_nameContext){
                    Variable varEmf = (Variable)ResourceBuilder.mapEmf.get(childNode);
                    emf.getVariable().add(varEmf);
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitVariable_list!!!");
        }
    }
}
