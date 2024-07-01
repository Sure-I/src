package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenIo_var_decls {
    public static void enter(STParser.Io_var_declsContext ctx){
        try{
            Traversal.setFromParentEmf(ctx);
        } catch(Exception exception){
            System.err.println("Error In enterIo_var_decls!!!");
        }
    }

    public static void exit(STParser.Io_var_declsContext ctx){ 

    }
}
