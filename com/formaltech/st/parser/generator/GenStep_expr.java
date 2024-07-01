package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenStep_expr {
    public static void enter(STParser.Step_exprContext ctx){

    }

    public static void exit(STParser.Step_exprContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        }catch(Exception exception){
            System.err.println("Error In exitStep_expr!!!");
        }
    }
}
