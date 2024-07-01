package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.RuleContext;

public class GenNumeric_literal {
    public static void enter(STParser.Numeric_literalContext ctx){

    }

    public static void exit(STParser.Numeric_literalContext ctx){ 
        try{

        } catch(Exception exception){
            System.err.println("Error In exitNumeric_literal!!!");
        }
    }
}
