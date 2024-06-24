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

public class GenStep_stmt {
    public static void enter(STParser.Step_exprContext ctx){

    }

    public static void exit(STParser.Step_exprContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        }catch(Exception exception){
            System.err.println("Error In exitStep_expr!!!");
        }
    }
}
