package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;


public class GenIf_stmt {
    public static void enter(STParser.If_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in if_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterIf_stmt!!!");
        }
    }

    public static void exit(STParser.If_stmtContext ctx){ 
        IfBlock emf = ResourceBuilder.stmtFactory.createIfBlock();
        ResourceBuilder.mapEmf.put(ctx, emf);
        for(int i = 0; i < ctx.getChildCount(); i++){
            String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
            switch(childNodeStr){
                case "expression":
                    emf.setCondition((Expression)Traversal.getChildEmf(ctx, i));
                    break;
                case "stmt_list":

                    break;
                case "elsif_stmt":

                    break;
                case "else_stmt":

                    break;
                default: ;
            }
        }
    }
}
