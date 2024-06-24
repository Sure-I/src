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

public class GenStruct_elem_decl {
    public static void enter(STParser.Struct_elem_declContext ctx){
        try{
            StructElemDecl emf = ResourceBuilder.declFactory.createStructElemDecl();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterStruct_elem_decl!!!");
        }
    }

    public static void exit(STParser.Struct_elem_declContext ctx){ 
        try{
            StructElemDecl emf = (StructElemDecl)Traversal.getEmf(ctx);
            String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(2));
            switch(childNodeStr){
                case "data_type_access":
                    emf.setType((Type)ResourceBuilder.mapEmf.get(ctx.getChild(2)));
                    break;
                default:
                    emf.setNoNameType((TypeDeclaration)ResourceBuilder.mapEmf.get(ctx.getChild(2)));
                    //System.out.println(emf.getNoNameType().getTestString());
                break;
            }
        } catch(Exception exception){
            System.err.println("Error In exitStruct_elem_decl!!!");
        }
    }
}
