package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenAll_var_decls {
    public static void enter(STParser.All_var_declsContext ctx){
        try{ 
            Variable emf = ResourceBuilder.declFactory.createVariable();
            ResourceBuilder.mapEmf.put(ctx, emf);
        }catch(Exception exception){
            System.err.println("Error In enterAll_var_decls!!!");
        }
    }

    public static void exit(STParser.All_var_declsContext ctx){ 
        try{ 
            //VariableDeclaration emf = (VariableDeclaration)Traversal.getEmf(ctx);
            //System.out.println(emf.getNamespace().getDeclType());
            //System.out.println(emf.getNamespace().getNamespace().getPOU().getName());
            //System.out.println(((VariableDeclaration)ResourceBuilder.mapEmf.get(ctx)).getTestString());

        }catch(Exception exception){
            System.err.println("Error In exitAll_var_decls!!!");
        }
    }
}
