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

public class GenType_decl {
    public static void enter(STParser.Type_declContext ctx){

    }

    public static void exit(STParser.Type_declContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        } catch(Exception exception){
            System.err.println("Error in exitType_decl!!!");
        }
    }
}
