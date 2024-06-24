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

public class GenType_access {
    public static void enter(STParser.Type_accessContext ctx){

    }

    public static void exit(STParser.Type_accessContext ctx){ 
        try{
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    Type emf = (Type)ResourceBuilder.mapTypeEmf.get(ctx.getChild(i).getText());
                    ResourceBuilder.mapEmf.put(ctx, emf);
                }
            }
        }catch(Exception exception){
            System.err.println("Error In exitType_access!!!");
        }
    }
}
