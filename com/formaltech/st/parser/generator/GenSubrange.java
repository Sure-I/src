package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ParseTree;

public class GenSubrange {
    public static void enter(STParser.SubrangeContext ctx){

    }

    public static void exit(STParser.SubrangeContext ctx){ 
        try{

        } catch(Exception exception){
            System.err.println("Error In Subrange!!!");
        }
    }
}
