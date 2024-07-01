package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenStatements {
    public static void enter(STParser.StatementsContext ctx){
        try{
            Statements emf = ResourceBuilder.stmtFactory.createStatements();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterStatements!!!");
        }
    }

    public static void exit(STParser.StatementsContext ctx){ 
        try{
            Statements emf = (Statements)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr.equals("stmt")){
                    Statement childEmf0 = (Statement)Traversal.getChildEmf(ctx, i);
                    emf.getStatements().add(childEmf0);
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitStatements!!!");
        }
    }
}
