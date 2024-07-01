package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ParseTree;

public class GenProg_name {
    public static void enter(STParser.Prog_nameContext ctx){

    }

    public static void exit(STParser.Prog_nameContext ctx){ 
        try{
            Program emf = ResourceBuilder.declFactory.createProgram();
            emf.setName(ctx.getText());
            ResourceBuilder.mapEmf.put(ctx, emf);

            String parentNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentNodeStr){
                case "program_decl":
                    ResourceBuilder.mapEmf.put(ctx.getParent(), emf);
                    break;
                default:
                    break;
            }
        } catch(Exception exception){
            System.err.println("Error in exitProg_name!!!");
        }
    }
}
