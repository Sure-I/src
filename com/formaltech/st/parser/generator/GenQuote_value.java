package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ParseTree;

public class GenQuote_value {
    public static void enter(STParser.Quote_valueContext ctx){

    }

    public static void exit(STParser.Quote_valueContext ctx){ 
        try{
            QuoteValueOp emf = ResourceBuilder.exprFactory.createQuoteValueOp();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((Type)ResourceBuilder.mapTypeEmf.get(ctx.getChild(i).getText()));
                    //System.out.println(ctx.getChild(i).getText());
                }
                else if(childNodeStr == "enum_value"){

                    //System.out.println(emf.getEnumValue().getValue());
                }
                else{ }
            }
        } catch(Exception exception){
            System.err.println("Error In exitQuote_value!!!");
        }
    }
}
