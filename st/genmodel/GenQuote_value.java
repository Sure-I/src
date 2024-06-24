package st.genmodel;

import parser.*;
import st.pre.*;
import st.basics.*;
import st.elements.*;
import st.literals.*;
import st.initializer.*;
import st.types.*;
import st.variables.*;
import st.pous.*;
import st.declarations.*;
import st.expressions.*;
import st.statements.*;

public class GenQuote_value {
    public static void enter(STParser.Quote_valueContext ctx){

    }

    public static void exit(STParser.Quote_valueContext ctx){ 
        try{
            QuoteValue emf = ResourceBuilder.liteFactory.createQuoteValue();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setQuoteType((Type)ResourceBuilder.mapTypeEmf.get(ctx.getChild(i).getText()));
                    //System.out.println(ctx.getChild(i).getText());
                }
                else if(childNodeStr == "enum_value"){
                    emf.setEnumValue((Literal)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                    //System.out.println(emf.getEnumValue().getValue());
                }
                else{ }
            }
        } catch(Exception exception){
            System.err.println("Error In exitQuote_value!!!");
        }
    }
}
