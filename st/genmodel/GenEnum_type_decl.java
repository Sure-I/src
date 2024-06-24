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

public class GenEnum_type_decl {
    public static void enter(STParser.Enum_type_declContext ctx){
        try{
            EnumTypeDecl emf = ResourceBuilder.declFactory.createEnumTypeDecl();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterEnum_type_decl!!!");
        }
    }

    public static void exit(STParser.Enum_type_declContext ctx){ 
        try{
            EnumTypeDecl emf = (EnumTypeDecl)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "type_name"){
                    emf.setType((EnumType)Traversal.getChildEmf(ctx, i));
                }
            }

            if(emf.getTypeAccess() == null){
                //System.out.println(emf.getType().getName() + ": new enum_type_decl");
            }
            else{
                //System.out.println(emf.getType().getName() + ": type_access->" + emf.getTypeAccess().getName());
            }
        } catch(Exception exception){
            System.err.println("Error In exitEnum_type_decl!!!");
        }
    }
}
