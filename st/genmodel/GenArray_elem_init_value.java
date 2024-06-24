package st.genmodel;

import org.antlr.v4.runtime.tree.ParseTree;

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

public class GenArray_elem_init_value {
    public static void enter(STParser.Array_elem_init_valueContext ctx){

    }

    public static void exit(STParser.Array_elem_init_valueContext ctx){ 
        try{
            ArrayElemInit emf = ResourceBuilder.initFactory.createArrayElemInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
            switch(childNodeStr){
                case "expression":
                    emf.setType(((Expression)ResourceBuilder.mapEmf.get(childNode)).getType());
                    emf.setExpression((Expression)Traversal.getChildEmf(ctx, 0));
                    //System.out.println(((LiteralExpression)emf.getExpression()).getLiteral().getValue());
                    break;
                case "struct_init":
                    emf.setStruct((StructInit)Traversal.getChildEmf(ctx, 0));
                    break;
                case "array_init":
                    emf.setArray((ArrayInit)Traversal.getChildEmf(ctx, 0));
                    break;
            }
        }catch(Exception exception){
            System.err.println("Error In exitArray_elem_init_value!!!");
        }
    }
}
