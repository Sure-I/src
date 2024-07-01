package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.eclipse.emf.ecore.EObject;

public class GenAssign_stmt {
    public static void enter(STParser.Assign_stmtContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterAssign_stmt!!!");
        }
    }

    public static void exit(STParser.Assign_stmtContext ctx){ 
        try{
            String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(0));
            switch(childNodeStr){
                case "variable": 
                    Assignment emf0 = ResourceBuilder.stmtFactory.createAssignment();
                    ResourceBuilder.mapEmf.put(ctx, emf0);
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        EObject childEmf = Traversal.getChildEmf(ctx, i);
                        if(childEmf instanceof Variable){
                            Variable childEmf0 = (Variable)childEmf;
                            emf0.setVariable(childEmf0);
                        }
                        else if(childEmf instanceof Expression){
                            Expression childEmf1 = (Expression)childEmf;
                            emf0.setExpression(childEmf1);
                        }
                        else{ }
                    }
                    break;
                case "ref_assign": 

                    break;
                case "assignment_attempt": 

                    break;
            }
        } catch(Exception exception){
            System.err.println("Error In exitAssign_stmt!!!");
        }
    }
}
