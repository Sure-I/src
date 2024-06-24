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

public class GenData_type_access {
    public static void enter(STParser.Data_type_accessContext ctx){

    }

    public static void exit(STParser.Data_type_accessContext ctx){ 
        try{
            String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(0));
            if(childNodeStr == "elem_type_name"){
                Type emf = (Type)ResourceBuilder.mapTypeEmf.get(ctx.getText());
                ResourceBuilder.mapEmf.put(ctx, emf);
            }
            else if(childNodeStr == "derived_type_access"){
                Traversal.setFromChildEmf(ctx, 0);
            }
            else{ }
        }catch(Exception exception){
            System.err.println("Error In exitData_type_access!!!");
        }
    }
}
