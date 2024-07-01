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

public class GenType_decl {
    public static void enter(STParser.Type_declContext ctx){

    }

    public static void exit(STParser.Type_declContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        } catch(Exception exception){
            System.err.println("Error in exitType_decl!!!");
        }
    }
}
