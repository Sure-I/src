package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.codegen.model.decl.Decl;
import org.antlr.v4.runtime.tree.ParseTree;

public class GenType_name {
    public static void enter(STParser.Type_nameContext ctx){
        try{
            String name = ctx.getText();
            if(ResourceBuilder.mapTypeEmf.get(name) == null){
                Type emf = ResourceBuilder.declFactory.createType();
                emf.setName(name);
                ResourceBuilder.mapEmf.put(ctx, emf);
                ResourceBuilder.mapTypeEmf.put(name, emf);
            }
            else{
                Type emf = (Type)(ResourceBuilder.mapTypeEmf.get(name));
                ResourceBuilder.mapEmf.put(ctx, emf);
            }
        } catch(Exception exception){
            System.err.println("Error In enterType_name!!!");
        }
    }

    public static void exit(STParser.Type_nameContext ctx){ 
        try{
            
        } catch(Exception exception){
            System.err.println("Error In exitType_name!!!");
        }
    }
}
