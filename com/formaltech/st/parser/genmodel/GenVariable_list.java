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

public class GenVariable_list {
    public static void enter(STParser.Variable_listContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterVariable_list!!!");
        }
    }

    public static void exit(STParser.Variable_listContext ctx){ 
        try{ 

        } catch(Exception exception){
            System.err.println("Error In exitVariable_list!!!");
        }
    }
}
