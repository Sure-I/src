package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;


public class GenCase_stmt {
    public static void enter(STParser.Case_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_stmt!!!");
        }
    }

    public static void exit(STParser.Case_stmtContext ctx){ 
        try{
            Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);
            CaseBlock emf = ResourceBuilder.stmtFactory.createCaseBlock();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "expression"){
                    emf.setSelector((Expression)Traversal.getChildEmf(ctx, i));
                }
                else if(childNodeStr == "case_selection"){ 

                }
                else if(childNodeStr == "stmt_list"){

                }
                else{ }
            }
        } catch(Exception exception){
            System.err.println("Error In exitCase_stmt!!!");
        }
    }
}
