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

public class GenStruct_elem_init {
    public static void enter(STParser.Struct_elem_initContext ctx){
        try{
            StructElemInit emf = ResourceBuilder.initFactory.createStructElemInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterStruct_elem_init!!!");
        }
    }

    public static void exit(STParser.Struct_elem_initContext ctx){ 
        try{
            StructElemInit emf = (StructElemInit)Traversal.getEmf(ctx);
            String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(2));
            EObject childEmf = ResourceBuilder.mapEmf.get(ctx.getChild(2));
            switch(childNodeStr){
                case"expression":
                    emf.setExpression((Expression)childEmf);
                break;
                case"array_init":
                    emf.setArray((ArrayInit)childEmf);
                break;
                case"struct_init":
                    emf.setStruct((StructInit)childEmf);
                break;
                case"ref_value":
                
                break;
                default:
                
                break;
            }

        } catch(Exception exception){
            System.err.println("Error In exitStruct_elem_init!!!");
        }
    }
}
