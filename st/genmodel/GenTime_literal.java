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

public class GenTime_literal {
    public static void enter(STParser.Time_literalContext ctx){

    }

    public static void exit(STParser.Time_literalContext ctx){ 
        try{
            TimeLiteral emf = ResourceBuilder.liteFactory.createTimeLiteral();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setType(LiteralType.TIME);
            String value = ctx.getText();
            emf.setValue(value);
        } catch(Exception exception){
            System.err.println("Error In exitTime_literal!!!");
        }
    }
}
