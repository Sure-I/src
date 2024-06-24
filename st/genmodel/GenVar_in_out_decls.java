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

public class GenVar_in_out_decls {
    public static void enter(STParser.Var_in_out_declsContext ctx){
        try{
            Traversal.setFromParentEmf(ctx);
            VariableDeclaration emf = (VariableDeclaration)Traversal.getEmf(ctx);
            emf.setSection(VariableSection.VAR_IN_OUT);
        } catch(Exception exception){
            System.err.println("Error In enterVar_in_out_decls!!!");
        }
    }

    public static void exit(STParser.Var_in_out_declsContext ctx){ 

    }
}
