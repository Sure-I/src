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

public class GenDerived_type_access {
    public static void enter(STParser.Derived_type_accessContext ctx){

    }

    public static void exit(STParser.Derived_type_accessContext ctx){ 
        try{
            Traversal.setFromChildEmf(ctx, 0);
        }catch(Exception exception){
            System.err.println("Error In exitDerived_type_access!!!");
        }
    }
}
