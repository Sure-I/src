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

public class GenProg_name {
    public static void enter(STParser.Prog_nameContext ctx){

    }

    public static void exit(STParser.Prog_nameContext ctx){ 
        try{
            Program emf = ResourceBuilder.pouFactory.createProgram();
            emf.setName(ctx.getText());
            emf.setDeclaration(((ProgramDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent())));
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in exitProg_name!!!");
        }
    }
}
