package st.genmodel;

import org.eclipse.emf.ecore.EObject;

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

public class GenSubrange_spec {
    public static void enter(STParser.Subrange_specContext ctx){

    }

    public static void exit(STParser.Subrange_specContext ctx){ 
        try{
            EObject parentEmf = Traversal.getParentEmf(ctx);
            if(parentEmf instanceof Declaration){
                Traversal.setFromParentEmf(ctx);
                SubrangeTypeDecl emf0 = (SubrangeTypeDecl)parentEmf;
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    emf0.setBaseType((ElementaryDataType)Traversal.getChildEmf(ctx, 0));
                    emf0.setSubrange((Subrange)Traversal.getChildEmf(ctx, 2));
                }
            }
            else if(parentEmf instanceof Initializer){ 
                SubrangeTypeDecl emf1 = ResourceBuilder.declFactory.createSubrangeTypeDecl();
                ResourceBuilder.mapEmf.put(ctx, emf1);
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                    ((SubrangeInit)parentEmf).setType((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    emf1.setBaseType((ElementaryDataType)Traversal.getChildEmf(ctx, 0));
                    emf1.setSubrange((Subrange)Traversal.getChildEmf(ctx, 2));
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In exitSubrange_spec!!!");
        }
    }
}
