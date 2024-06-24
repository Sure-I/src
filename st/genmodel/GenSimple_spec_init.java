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

public class GenSimple_spec_init {
    public static void enter(STParser.Simple_spec_initContext ctx){
        try{
            SimpleInit emf = ResourceBuilder.initFactory.createSimpleInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setTestString("simple_spec_init_emf");
        } catch(Exception exception){
            System.err.println("Error In enterSimple_spec_init!!!");
        }
    }

    public static void exit(STParser.Simple_spec_initContext ctx){ 
        try{
            SimpleInit emf = (SimpleInit)Traversal.getEmf(ctx);

            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodestr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodestr == "elem_type_name"){
                    Type typeEmf = (Type)ResourceBuilder.mapTypeEmf.get(ctx.getChild(i).getText());
                    emf.setType(typeEmf);
                }
                else if(childNodestr == "expression"){
                    Expression exprEmf = (Expression)Traversal.getChildEmf(ctx, i);
                    emf.setValue(exprEmf);
                }
                else{ }
            }

        } catch(Exception exception){
            System.err.println("Error In exitSimple_spec_init!!!");
        }
    }
}
