package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;


public class GenFunc_decl {
    public static void enter(STParser.Func_declContext ctx){
        try{ 
            Function emf = ResourceBuilder.declFactory.createFunction();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in enterFunc_decl!!!");
        }
    }

    public static void exit(STParser.Func_declContext ctx){ 
        try{

        } catch(Exception exception){
            System.err.println("Error in exitFunc_decl!!!");
        }
    }
}
