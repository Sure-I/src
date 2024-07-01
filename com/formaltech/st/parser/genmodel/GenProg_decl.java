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

public class GenProg_decl {
    public static void enter(STParser.Prog_declContext ctx){
        try{
            Program emf = ResourceBuilder.declFactory.createProgram();
            ResourceBuilder.mapEmf.put(ctx, emf);
        }catch(Exception exception){
            System.err.println("Error in enterProg_decl!!!");
        }
    }

    public static void exit(STParser.Prog_declContext ctx){ 
        try{
            Program emf = (Program)(Traversal.getEmf(ctx));
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "prog_name":

                        break;
                    case "all_var_decls":
                        break;
                    case "statements":

                        break;
                    default:
                        break;
                }
            }
        }catch(Exception exception){
            System.err.println("Error in exitProg_decl!!!");
        }
    }
}
