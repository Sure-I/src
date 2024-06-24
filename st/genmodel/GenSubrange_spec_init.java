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

public class GenSubrange_spec_init {
    public static void enter(STParser.Subrange_spec_initContext ctx){
        try{
            SubrangeInit emf = ResourceBuilder.initFactory.createSubrangeInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterSubrange_spec_init!!!");
        }
    }

    public static void exit(STParser.Subrange_spec_initContext ctx){ 
        try{
            SubrangeInit emf = (SubrangeInit)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "subrange_spec"){
                    SubrangeTypeDecl typeDeclEmf = (SubrangeTypeDecl)Traversal.getChildEmf(ctx, i);
                    emf.setNoNameType(typeDeclEmf);
                    if(typeDeclEmf.getType() == null){ }
                    else{
                        emf.setType(typeDeclEmf.getType());
                    }
                }
                else if(childNodeStr == "expression"){
                    emf.setValue((Expression)Traversal.getChildEmf(ctx, i));
                }
                else{ }
            }

            //System.out.println(((LiteralExpression)emf.getValue()).getLiteral().getValue());
        } catch(Exception exception){
            System.err.println("Error In exitSubrange_spec_init!!!");
        }
    }
}
