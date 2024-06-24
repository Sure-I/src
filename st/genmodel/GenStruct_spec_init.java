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

public class GenStruct_spec_init {
    public static void enter(STParser.Struct_spec_initContext ctx){
        try{
            StructSpecInit emf = ResourceBuilder.initFactory.createStructSpecInit();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setTestString("struct_spec_init_emf");
        } catch(Exception exception){
            System.err.println("Error In enterStruct_spec_init!!!");
        }
    }

    public static void exit(STParser.Struct_spec_initContext ctx){ 
        try{
            StructSpecInit emf = (StructSpecInit)Traversal.getEmf(ctx);
            emf.setStructInit((StructInit)ResourceBuilder.mapEmf.get(ctx.getChild(2)));
        } catch(Exception exception){
            System.err.println("Error In exitStruct_spec_init!!!");
        }
    }
}
