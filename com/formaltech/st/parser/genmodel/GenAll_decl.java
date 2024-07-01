package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;

import org.antlr.v4.runtime.tree.RuleNode;

import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public final class GenAll_decl {
    public static void enter(STParser.All_declContext ctx){

    }

    public static void exit(STParser.All_declContext ctx){
        try{
            Model emf = ResourceBuilder.apiFactory.createModel();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                if(ctx.getChild(i) instanceof RuleNode){
                    emf.getSubmodels().add((Declaration)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                }
            }
            //System.out.println(emf.getDeclaration().size());
        }catch(Exception exception){
            System.err.println("Error in exitAll_decl!!!");
        }
    }
}
