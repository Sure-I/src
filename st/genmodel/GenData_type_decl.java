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

public class GenData_type_decl {
    public static void enter(STParser.Data_type_declContext ctx){

    }

    public static void exit(STParser.Data_type_declContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 1);
        } catch(Exception exception){
            System.err.println("Error In exitData_type_decl!!!");
        }
    }
}
