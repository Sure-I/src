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

public class GenStruct_init {
    public static void enter(STParser.Struct_initContext ctx){
        try{
            StructInit emf = ResourceBuilder.initFactory.createStructInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterStruct_init!!!");
        }
    }

    public static void exit(STParser.Struct_initContext ctx){ 
        try{
            StructInit emf = (StructInit)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "struct_elem_init"){
                    emf.getElemInit().add(((StructElemInit)ResourceBuilder.mapEmf.get(ctx.getChild(i))));
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitStruct_init!!!");
        }
    }
}
