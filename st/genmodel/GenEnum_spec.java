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

public class GenEnum_spec {
    public static void enter(STParser.Enum_specContext ctx){

    }

    public static void exit(STParser.Enum_specContext ctx){ 
        try{
            EObject parentEmf = Traversal.getParentEmf(ctx);
            if(parentEmf instanceof Declaration){
                Traversal.setFromParentEmf(ctx);
                EnumTypeDecl emf0 = (EnumTypeDecl)parentEmf;
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "enum_value"){
                            //System.out.println(((Literal)Traversal.getChildEmf(ctx, i)).getValue());
                            emf0.getEnumValue().add((Literal)Traversal.getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "named_spec"){ }
                    }
                }
            }
            else if(parentEmf instanceof Initializer){ 
                EnumTypeDecl emf1 = ResourceBuilder.declFactory.createEnumTypeDecl();
                ResourceBuilder.mapEmf.put(ctx, emf1);
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                    ((EnumInit)parentEmf).setType((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "enum_value"){
                            //System.out.println(((Literal)Traversal.getChildEmf(ctx, i)).getValue());
                            emf1.getEnumValue().add((Literal)Traversal.getChildEmf(ctx, i));
                        }
                    }
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In exitEnum_spec!!!");
        }
    }
}
