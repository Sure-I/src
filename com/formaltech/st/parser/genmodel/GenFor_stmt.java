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

public class GenFor_stmt {
    public static void enter(STParser.For_stmtContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterFor_stmt!!!");
        }
    }

    public static void exit(STParser.For_stmtContext ctx){ 
        try{

        } catch(Exception exception){
            System.err.println("Error In exitFor_stmt!!!");
        }
    }
}