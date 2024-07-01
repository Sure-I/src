package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.eclipse.emf.ecore.EObject;

public class GenCase_list {
    public static void enter(STParser.Case_listContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_list");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_list!!!");
        }
    }

    public static void exit(STParser.Case_listContext ctx){ 
        try{

        } catch(Exception exception){
            System.err.println("Error In exitCase_list!!!");
        }
    }
}
