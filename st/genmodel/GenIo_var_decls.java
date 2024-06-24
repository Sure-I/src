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

public class GenIo_var_decls {
    public static void enter(STParser.Io_var_declsContext ctx){
        try{
            Traversal.setFromParentEmf(ctx);
        } catch(Exception exception){
            System.err.println("Error In enterIo_var_decls!!!");
        }
    }

    public static void exit(STParser.Io_var_declsContext ctx){ 

    }
}
