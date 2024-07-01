package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ParseTree;

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
