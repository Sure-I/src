package st.genmodel;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

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

public class GenArray_init {
    public static void enter(STParser.Array_initContext ctx){

    }

    public static void exit(STParser.Array_initContext ctx){ 
        try{
            ArrayInit emf = ResourceBuilder.initFactory.createArrayInit();
            if(ctx.getChild(1) instanceof TerminalNode){
                int count = Integer.parseInt(ctx.getChild(1).getText());
                ArrayElemInit childEmf = null;
                for(int i = 0; i < ctx.getChildCount(); i++){
                    String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                    if(childNodeStr == "array_elem_init_value"){
                        childEmf = (ArrayElemInit)Traversal.getChildEmf(ctx, i);
                    }
                    else{ }
                }
                for(int i = 0; i < count; i++){
                    emf.getElement().add(childEmf);
                }
            }
            else if(ctx.getChild(1) instanceof RuleContext){
                for(int i = 0; i < ctx.getChildCount(); i++){
                    String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                    if(childNodeStr == "array_elem_init_value"){
                        emf.getElement().add((ArrayElemInit)Traversal.getChildEmf(ctx, i));
                    }
                    else{ }
                }
            }
            else{ }
        }catch(Exception exception){
            System.err.println("Array_init!!!");
        }
    }
}
