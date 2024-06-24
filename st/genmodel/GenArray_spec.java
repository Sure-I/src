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

public class GenArray_spec {
    public static void enter(STParser.Array_specContext ctx){

    }

    public static void exit(STParser.Array_specContext ctx){ 
        try{
            EObject parentEmf = Traversal.getParentEmf(ctx);
            if(parentEmf instanceof ArrayTypeDecl){
                Traversal.setFromParentEmf(ctx);
                ArrayTypeDecl emf0 = (ArrayTypeDecl)parentEmf;
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "subrange"){
                            emf0.getSubrange().add((Subrange)Traversal.getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "data_type_access"){
                            emf0.setInsideType((Type)Traversal.getChildEmf(ctx, i));
                        }
                        else{ }
                    }
                }
            }
            else if(parentEmf instanceof Initializer){ 
                ArrayTypeDecl emf1 = ResourceBuilder.declFactory.createArrayTypeDecl();
                emf1.setTestString("array_type_decl testString");
                ResourceBuilder.mapEmf.put(ctx, emf1);
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                    ((ArraySpecInit)parentEmf).setType((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "subrange"){
                            emf1.getSubrange().add((Subrange)Traversal.getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "data_type_access"){
                            emf1.setInsideType((Type)Traversal.getChildEmf(ctx, i));
                        }
                        else{ }
                    }
                }
            }
            else if(parentEmf instanceof StructElemDecl){
                ArrayTypeDecl emf1 = ResourceBuilder.declFactory.createArrayTypeDecl();
                emf1.setTestString("array_type_decl testString");
                ResourceBuilder.mapEmf.put(ctx, emf1);
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf1.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                        if(childNodeStr == "subrange"){
                            emf1.getSubrange().add((Subrange)Traversal.getChildEmf(ctx, i));
                        }
                        else if(childNodeStr == "data_type_access"){
                            emf1.setInsideType((Type)Traversal.getChildEmf(ctx, i));
                        }
                        else{ }
                    }
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitArray_spec!!!");
        }
    }
}
