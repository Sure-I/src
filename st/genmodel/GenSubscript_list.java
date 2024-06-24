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

public class GenSubscript_list {
    public static void enter(STParser.Subscript_listContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("Error: no Namespace in ParentNode in enterSubscript_list!!!");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterSubscript_list!!!");
        }
    }

    public static void exit(STParser.Subscript_listContext ctx){ 
        try{
            SubscriptList emf = ResourceBuilder.varFactory.createSubscriptList();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr.equals("expression")){
                    emf.getSubscript().add((Expression)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitSubscript_list!!!");
        }
    }
}
