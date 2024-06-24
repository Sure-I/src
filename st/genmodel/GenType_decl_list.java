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

public class GenType_decl_list {
    public static void enter(STParser.Type_decl_listContext ctx){

    }

    public static void exit(STParser.Type_decl_listContext ctx){ 
        try{
            TypeDeclList emf = ResourceBuilder.declFactory.createTypeDeclList();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_decl"){
                    emf.getTypeDeclatation().add((TypeDeclaration)Traversal.getChildEmf(ctx, i));
                }
            }

            //System.out.println(emf.getTypeDeclatation().size() + " declaration in type_decl_list");
        } catch(Exception exception){
            System.err.println("Error In exitType_decl_list!!!");
        }
    }
}
