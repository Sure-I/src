package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;


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
