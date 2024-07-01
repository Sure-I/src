package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.ecore.EObject;

public class GenElem_type_name {
    public static void enter(STParser.Elem_type_nameContext ctx){

    }

    public static void exit(STParser.Elem_type_nameContext ctx){ 
        try{
            String name = ctx.getText();
            if(ResourceBuilder.mapTypeEmf.get(name) == null){
                Type emf = ResourceBuilder.declFactory.createType();
                emf.setName(name);
                ResourceBuilder.mapEmf.put(ctx, emf);
                ResourceBuilder.mapTypeEmf.put(name, emf);
            }
            else{

            }
        } catch(Exception exception){
            System.err.println("Error In exitElem_type_nmae!!!");
        }

    }
}
