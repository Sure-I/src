package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenCase_list_elem {
    public static void enter(STParser.Case_list_elemContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_list_elem");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_list_elem!!!");
        }
    }

    public static void exit(STParser.Case_list_elemContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        } catch(Exception exception){
            System.err.println("Error In exitCase_list_elem!!!");
        }
    }
}
