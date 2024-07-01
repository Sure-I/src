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

public class GenSubrange_type_decl {
    public static void enter(STParser.Subrange_type_declContext ctx){
        try{
            Subrange emf = ResourceBuilder.typeFactory.createSubrange();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterSubrange_type_decl!!!");
        }
    }

    public static void exit(STParser.Subrange_type_declContext ctx){ 
        try{ 

        } catch(Exception exception){
            System.err.println("Error In exitSubrange_type_decl!!!");
        }
    }
}
