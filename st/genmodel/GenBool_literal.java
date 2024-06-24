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

public class GenBool_literal {
    public static void enter(STParser.Bool_literalContext ctx){

    }

    public static void exit(STParser.Bool_literalContext ctx){ 
        try{
            Literal emf = ResourceBuilder.elemFactory.createLiteral();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setType(LiteralType.BOOLEAN);
            String value = ctx.getText();
            emf.setValue(value);
        } catch(Exception exception){
            System.err.println("Error In exitBool_literal!!!");
        }
    }
}
