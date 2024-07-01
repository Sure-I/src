package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ParseTree;

public class GenVariable {
    public static void enter(STParser.VariableContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterVariable!!!");
        }
    }

    public static void exit(STParser.VariableContext ctx){ 
        try{
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "symbolic_variable":
                    Traversal.setFromChildEmf(ctx, 0);
                    break;
                default: ;
            }
        } catch(Exception exception){
            System.err.println("Error In exitVariable!!!");
        }
    }
}
