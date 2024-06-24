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

public class GenArray_spec_init {
    public static void enter(STParser.Array_spec_initContext ctx){
        try{
            ArraySpecInit emf = ResourceBuilder.initFactory.createArraySpecInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterArray_spec_init!!!");
        }
    }

    public static void exit(STParser.Array_spec_initContext ctx){ 
        try{
            ArraySpecInit emf = (ArraySpecInit)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "array_spec"){
                    ArrayTypeDecl typeDeclEmf = (ArrayTypeDecl)Traversal.getChildEmf(ctx, i);
                    emf.setNoNameType(typeDeclEmf);
                    if(typeDeclEmf.getType() == null){ }
                    else{
                        emf.setType(typeDeclEmf.getType());
                    }
                }
            }

/*             for(int i = 0; i < emf.getValue().getElements().size(); i++){
                System.out.println(((LiteralExpression)emf.getValue().getElements().get(i).getExpression()).getLiteral().getValue());
            } */
        } catch(Exception exception){
            System.err.println("Error In exitArray_spec_init!!!");
        }
    }
}
