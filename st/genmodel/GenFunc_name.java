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

public class GenFunc_name {
    public static void enter(STParser.Func_nameContext ctx){
        try{
            Namespace namespaceEmf = ((FunctionCall)Traversal.getParentEmf(ctx)).getNamespace();
            ResourceBuilder.mapEmf.put(ctx, namespaceEmf);
        } catch(Exception exception){
            System.err.println("Error in enterFunc_name!!!");
        }
    }

    public static void exit(STParser.Func_nameContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
            ((FunctionCall)Traversal.getParentEmf(ctx)).setFunction((Function)Traversal.getEmf(ctx));
            //System.out.println(((FunctionCall)Traversal.getParentEmf(ctx)).getFunction().getName());
        } catch(Exception exception){
            System.err.println("Error in exitFunc_name!!!");
        }
    }
}
