package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

public class GenStmt {
    public static void enter(STParser.StmtContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterStmt!!!");
        }
    }

    public static void exit(STParser.StmtContext ctx){ 
        Traversal.setFromChildEmf(ctx, 0);
        //System.out.println((Statement)Traversal.getEmf(ctx).getTestString());
    }
}
