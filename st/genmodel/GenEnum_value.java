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

public class GenEnum_value {
    public static void enter(STParser.Enum_valueContext ctx){

    }

    public static void exit(STParser.Enum_valueContext ctx){ 
        try{
            Literal emf = ResourceBuilder.elemFactory.createLiteral();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setValue(ctx.getText());
        } catch(Exception exception){
            System.err.println("Error In exitEnum_value!!!");
        }
    }
}
