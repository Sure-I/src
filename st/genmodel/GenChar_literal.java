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

public class GenChar_literal {
    public static void enter(STParser.Char_literalContext ctx){

    }

    public static void exit(STParser.Char_literalContext ctx){ 
        try{
            CharLiteral emf = ResourceBuilder.liteFactory.createCharLiteral();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setType(LiteralType.SINGLE_BYTE_CHAR);
            String value = ctx.getText();
            emf.setValue(value);
        } catch(Exception exception){
            System.err.println("Error In exitChar_literal!!!");
        }
    }
}
