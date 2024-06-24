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

public class GenStruct_spec {
    public static void enter(STParser.Struct_specContext ctx){

    }

    public static void exit(STParser.Struct_specContext ctx){ 
        try{
            EObject parentEmf = Traversal.getParentEmf(ctx);
            if(parentEmf instanceof Declaration){
                Traversal.setFromParentEmf(ctx);
                StructTypeDecl emf0 = (StructTypeDecl)parentEmf;
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    emf0.setTypeAccess((Type)Traversal.getChildEmf(ctx, 0));
                }
                else{ 
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        if(ResourceBuilder.mapNodeStr.get(ctx.getChild(i)) == "struct_elem_decl"){
                            emf0.getElement().add((StructElemDecl)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                        }
                    }
                }
            }
            else if(parentEmf instanceof Initializer){ 
                if(ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "type_access"){
                    StructType type = (StructType)Traversal.getChildEmf(ctx, 0);
                    StructTypeDecl emf0 = (StructTypeDecl)type.getTypeDeclaration();
                    ResourceBuilder.mapEmf.put(ctx, emf0);
                    
                    ((StructSpecInit)parentEmf).setType((Type)Traversal.getChildEmf(ctx, 0));
                }
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In exitStruct_spec!!!");
        }
    }
}
