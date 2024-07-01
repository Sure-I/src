package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenReturn_stmt {
    public static void enter(STParser.Return_stmtContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterReturn_stmt!!!");
        }
    }

    public static void exit(STParser.Return_stmtContext ctx){ 
        try{
            Return emf = ResourceBuilder.stmtFactory.createReturn();
            ResourceBuilder.mapEmf.put(ctx, emf);  
            for(int i = 0; i < ctx.getChildCount(); i++){
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(i)) == "expression"){
                    emf.setExpression(((Expression)ResourceBuilder.mapEmf.get(ctx.getChild(i))));
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitReturn_stmt!!!");
        }
    }
}
