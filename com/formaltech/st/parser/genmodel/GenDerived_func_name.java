package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenDerived_func_name {
    public static void enter(STParser.Derived_func_nameContext ctx){

    }

    public static void exit(STParser.Derived_func_nameContext ctx){ 
        try{
            String funName = ctx.getText();

        } catch(Exception exception){
            System.err.println("Error in exitDerived_func_name!!!");
        }
    }
}