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

public class GenProg_decl {
    public static void enter(STParser.Prog_declContext ctx){
        try{
            ProgramDeclaration emf = ResourceBuilder.declFactory.createProgramDeclaration();
            Namespace namespaceEmf = ResourceBuilder.baseFactory.createNamespace();
            emf.setNamespace(namespaceEmf);
            namespaceEmf.setDeclType(DeclType.GLOBAL);
            ResourceBuilder.mapEmf.put(ctx, emf);
        }catch(Exception exception){
            System.err.println("Error in enterProg_decl!!!");
        }
    }

    public static void exit(STParser.Prog_declContext ctx){ 
        try{
            ProgramDeclaration emf = (ProgramDeclaration)Traversal.getEmf(ctx);
            emf.setPOU((Program)ResourceBuilder.mapEmf.get(ctx.getChild(1)));
        }catch(Exception exception){
            System.err.println("Error in exitProg_decl!!!");
        }
    }
}
