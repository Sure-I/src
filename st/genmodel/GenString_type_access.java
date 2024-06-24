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

public class GenString_type_access {
    public static void enter(STParser.String_type_accessContext ctx){

    }

    public static void exit(STParser.String_type_accessContext ctx){ 
        try{
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "string_Type_Name"){
                    Type emf = (Type)ResourceBuilder.mapTypeEmf.get(ctx.getChild(i).getText());
                    ResourceBuilder.mapEmf.put(ctx, emf);
                }
            }
        }catch(Exception exception){
            System.err.println("Error In exitString_type_access!!!");
        }
    }
}
