package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenDerived_type_access {
    public static void enter(STParser.Derived_type_accessContext ctx){

    }

    public static void exit(STParser.Derived_type_accessContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        }catch(Exception exception){
            System.err.println("Error In exitDerived_type_access!!!");
        }
    }
}
