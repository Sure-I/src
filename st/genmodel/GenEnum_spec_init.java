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

public class GenEnum_spec_init {
    public static void enter(STParser.Enum_spec_initContext ctx){
        try{
            EnumInit emf = ResourceBuilder.initFactory.createEnumInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterEnum_spec_init!!!");
        }
    }

    public static void exit(STParser.Enum_spec_initContext ctx){ 
        try{
            EnumInit emf = (EnumInit)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "enum_spec"){
                    EnumTypeDecl typeDeclEmf = (EnumTypeDecl)Traversal.getChildEmf(ctx, i);
                    emf.setNoNameType(typeDeclEmf);
                    if(typeDeclEmf.getType() == null){ }
                    else{
                        emf.setType(typeDeclEmf.getType());
                    }
                }
                else if(childNodeStr == "quote_value"){
                    emf.setValue((QuoteValue)Traversal.getChildEmf(ctx, i));
                }
            }

            //System.out.println(emf.getValue());
        } catch(Exception exception){
            System.err.println("Error In exitEnum_spec_init!!!");
        }
    }
}
