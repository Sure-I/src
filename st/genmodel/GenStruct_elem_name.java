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

public class GenStruct_elem_name {
    public static void enter(STParser.Struct_elem_nameContext ctx){

    }

    public static void exit(STParser.Struct_elem_nameContext ctx){ 
        try{
            Traversal.setFromParentEmf(ctx);
            if(Traversal.getEmf(ctx) instanceof StructElemDecl){ 
                StructElemDecl emf = (StructElemDecl)Traversal.getEmf(ctx);
                emf.setName(ctx.getText());
            }
            else if(Traversal.getEmf(ctx) instanceof StructElemInit){
                StructElemInit emf = (StructElemInit)Traversal.getEmf(ctx);
                emf.setName(ctx.getText());
            }
        } catch(Exception exception){
            System.err.println("Error In exitStruct_elem_name!!!");
        }
    }
}
