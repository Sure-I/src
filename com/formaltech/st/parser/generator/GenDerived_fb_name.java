package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenDerived_fb_name {
    public static void enter(STParser.Derived_fb_nameContext ctx){

    }

    public static void exit(STParser.Derived_fb_nameContext ctx){ 
        try{
            String fbName = ctx.getText();
            if(ResourceBuilder.mapTypeEmf.get(fbName) == null){
                FunctionBlock emf = ResourceBuilder.declFactory.createFunctionBlock();
                ResourceBuilder.mapEmf.put(ctx, emf);
            }
            else{
                System.err.println("function block " + fbName + " already exsit!!!");
                System.exit(0);
            }

        } catch(Exception exception){
            System.err.println("Error in exitDerived_fb_name!!!");
        }
    }
}
