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

public class GenFunc_decl {
    public static void enter(STParser.Func_declContext ctx){
        try{ 
            FunctionDeclaration emf = ResourceBuilder.declFactory.createFunctionDeclaration();
            Namespace namespaceEmf = ResourceBuilder.baseFactory.createNamespace();
            emf.setNamespace(namespaceEmf);

            String parentStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentStr){
                case "all_decl":
                    namespaceEmf.setDeclType(DeclType.GLOBAL);;
                    break;
                case "prog_decl":
                    namespaceEmf.setDeclType(DeclType.PROGRAM);
                    namespaceEmf.setNamespace((POUDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    break;
                default: break;
            }

            //System.out.println(emf.getNamespace().getDeclType());
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in enterFunc_decl!!!");
        }
    }

    public static void exit(STParser.Func_declContext ctx){ 
        try{
            FunctionDeclaration emf = (FunctionDeclaration)Traversal.getEmf(ctx);
            if(ResourceBuilder.mapTypeEmf.get("VOID") == null){
                ElementaryDataType emfVOID = ResourceBuilder.typeFactory.createElementaryDataType();
                emfVOID.setName("VOID");
                emfVOID.setType(PreDefinedEDType.VOID);
                ResourceBuilder.mapTypeEmf.put("VOID", emfVOID);
            }
            emf.setType((Type)ResourceBuilder.mapTypeEmf.get("VOID"));
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case "derived_func_name":
                        emf.setPOU((Function)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                        break;
                    case "data_type_access":
                        emf.setType((Type)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                        break;
                    case "using_directive":

                        break;
                    case "all_var_decls":

                        break;
                    case "method_decls":

                        break;
                    case "statements":

                        break;
                    default:

                        break;
                }
            }
        } catch(Exception exception){
            System.err.println("Error in exitFunc_decl!!!");
        }
    }
}
