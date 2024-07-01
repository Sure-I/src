package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;


public class GenData_type_decl {
    public static void enter(STParser.Data_type_declContext ctx){

    }

    public static void exit(STParser.Data_type_declContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 1);
        } catch(Exception exception){
            System.err.println("Error In exitData_type_decl!!!");
        }
    }
}