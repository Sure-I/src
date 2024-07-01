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

public class GenWhile_stmt {
    public static void enter(STParser.While_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in while_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterWhile_stmt!!!");
        }
    }

    public static void exit(STParser.While_stmtContext ctx){ 
        try{
            WhileBlock emf = ResourceBuilder.stmtFactory.createWhileBlock();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                ParseTree childNode = ctx.getChild(i);
                String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
                switch(childNodeStr){
                    case "expression":
                        emf.setCondition((Expression)Traversal.getChildEmf(ctx, i));
                        break;
                    case "stmt_list":
                        emf.setStatements((Statements)Traversal.getChildEmf(ctx, i));
                        break;
                    default: ;
                }
            }
        }catch(Exception exception){
            System.err.println("Error In exitWhile_stmt!!!");
        }
    }
}
