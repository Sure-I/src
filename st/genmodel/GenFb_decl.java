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

public class GenFb_decl {
    public static void enter(STParser.Fb_declContext ctx){
        try{ 
            FunctionBlockDeclaration emf = ResourceBuilder.declFactory.createFunctionBlockDeclaration();
            Namespace namespaceEmf = ResourceBuilder.baseFactory.createNamespace();
            emf.setNamespace(namespaceEmf);

            namespaceEmf.setDeclType(DeclType.GLOBAL);
            ResourceBuilder.mapEmf.put(ctx, emf);

/*             String parentStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentStr){
                case "all_decl":
                    namespaceEmf.setDeclType(DeclType.GLOBAL);;
                    break;
                case "prog_decl":
                    namespaceEmf.setDeclType(DeclType.PROGRAM);
                    namespaceEmf.setNamespace((POUDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    break;
                default: break;
            } */

            //System.out.println(emf.getNamespace().getDeclType());
        } catch(Exception exception){
            System.err.println("Error in enterFunc_decl!!!");
        }
    }

    public static void exit(STParser.Fb_declContext ctx){ 

    }
}
