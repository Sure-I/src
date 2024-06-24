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

public class GenCase_list_elem {
    public static void enter(STParser.Case_list_elemContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_list_elem");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_list_elem!!!");
        }
    }

    public static void exit(STParser.Case_list_elemContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        } catch(Exception exception){
            System.err.println("Error In exitCase_list_elem!!!");
        }
    }
}
