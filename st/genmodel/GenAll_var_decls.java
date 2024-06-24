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

public class GenAll_var_decls {
    public static void enter(STParser.All_var_declsContext ctx){
        try{ 
            VariableDeclaration emf = ResourceBuilder.declFactory.createVariableDeclaration();
            
            Namespace namespaceEmf = ResourceBuilder.baseFactory.createNamespace();

            emf.setNamespace(namespaceEmf);

            String parentStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentStr){
                case "all_decl":
                    namespaceEmf.setDeclType(DeclType.GLOBAL);
                    break;
                case "prog_decl":
                    namespaceEmf.setDeclType(DeclType.PROGRAM);
                    namespaceEmf.setNamespace((POUDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    break;
                case "func_decl":
                    namespaceEmf.setDeclType(DeclType.FUNCTION);
                    namespaceEmf.setNamespace((POUDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    break;
                case "fb_decl":
                    namespaceEmf.setDeclType(DeclType.FB);
                    namespaceEmf.setNamespace((POUDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    break;
                case "method_decl":
                    namespaceEmf.setDeclType(DeclType.METHOD);
                    namespaceEmf.setNamespace((POUDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    break;
                case "class_decl":
                
                    break;
                default: break;
            }

            ResourceBuilder.mapEmf.put(ctx, emf);
        }catch(Exception exception){
            System.err.println("Error In enterAll_var_decls!!!");
        }
    }

    public static void exit(STParser.All_var_declsContext ctx){ 
        try{ 
            //VariableDeclaration emf = (VariableDeclaration)Traversal.getEmf(ctx);
            //System.out.println(emf.getNamespace().getDeclType());
            //System.out.println(emf.getNamespace().getNamespace().getPOU().getName());
            //System.out.println(((VariableDeclaration)ResourceBuilder.mapEmf.get(ctx)).getTestString());

        }catch(Exception exception){
            System.err.println("Error In exitAll_var_decls!!!");
        }
    }
}
