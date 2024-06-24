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

public class GenInt_literal {
    public static void enter(STParser.Int_literalContext ctx){

    }

    public static void exit(STParser.Int_literalContext ctx){ 
        try{
            NumericLiteral emf = ResourceBuilder.liteFactory.createNumericLiteral();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setType(LiteralType.INTEGER);
            String value = ctx.getText();
            //System.out.println(value);
            emf.setValue(value);
        } catch(Exception exception){
            System.err.println("Error In exitInt_literal!!!");
        }
    }
}
