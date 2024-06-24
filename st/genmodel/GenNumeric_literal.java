package st.genmodel;

import org.antlr.v4.runtime.RuleContext;

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

public class GenNumeric_literal {
    public static void enter(STParser.Numeric_literalContext ctx){

    }

    public static void exit(STParser.Numeric_literalContext ctx){ 
        try{
            if(ctx.getChild(0) instanceof RuleContext){
            Traversal.setFromChildEmf(ctx, 0);
            }
            else{
                NumericLiteral emf = ResourceBuilder.liteFactory.createNumericLiteral();
                ResourceBuilder.mapEmf.put(ctx, emf);
                emf.setType(LiteralType.REAL);
                String value = ctx.getText();
                emf.setValue(value);
            }
        } catch(Exception exception){
            System.err.println("Error In exitNumeric_literal!!!");
        }
    }
}
