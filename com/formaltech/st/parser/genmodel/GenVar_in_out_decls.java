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

public class GenVar_in_out_decls {
    public static void enter(STParser.Var_in_out_declsContext ctx){
        try{
            Traversal.setFromParentEmf(ctx);
            Variable emf = (Variable)Traversal.getEmf(ctx);
            emf.setSection(VariableSection.VAR_IN_OUT);
        } catch(Exception exception){
            System.err.println("Error In enterVar_in_out_decls!!!");
        }
    }

    public static void exit(STParser.Var_in_out_declsContext ctx){ 

    }
}