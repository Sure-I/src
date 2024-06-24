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

public class GenSubrange_type_decl {
    public static void enter(STParser.Subrange_type_declContext ctx){
        try{
            SubrangeTypeDecl emf = ResourceBuilder.declFactory.createSubrangeTypeDecl();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterSubrange_type_decl!!!");
        }
    }

    public static void exit(STParser.Subrange_type_declContext ctx){ 
        try{ 
            SubrangeTypeDecl emf = (SubrangeTypeDecl)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((SubrangeType)Traversal.getChildEmf(ctx, i));
                }
            }

            if(emf.getTypeAccess() == null){
                //System.out.println(emf.getType().getName() + ": new subrange_type_decl");
                //System.out.println(((LiteralExpression)emf.getSubrange().getLowerBound()).getLiteral().getValue());
            }
            else{
                //System.out.println(emf.getType().getName() + ": type_access->" + emf.getTypeAccess().getName());
            }
        } catch(Exception exception){
            System.err.println("Error In exitSubrange_type_decl!!!");
        }
    }
}
