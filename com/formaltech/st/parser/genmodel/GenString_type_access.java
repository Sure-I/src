package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

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
