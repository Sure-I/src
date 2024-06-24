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

public class GenMethod_decl {
    public static void enter(STParser.Method_declContext ctx){
        try{
            MethodDeclaration emf = ResourceBuilder.declFactory.createMethodDeclaration();
            Namespace namespaceEmf = ResourceBuilder.baseFactory.createNamespace();
            namespaceEmf.setDeclType(DeclType.FB);
            emf.setNamespace(namespaceEmf);
            FunctionBlockDeclaration FBEmf = (FunctionBlockDeclaration)Traversal.getParentEmf(ctx);
            namespaceEmf.setNamespace(FBEmf);
    
            for(int i = 0; i < FBEmf.getVariable().size(); i++){
                emf.getVariable().add(FBEmf.getVariable().get(i));
            }
    
            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error in enterMethod_decl!!!");
        }
    }

    public static void exit(STParser.Method_declContext ctx){ 
        try{
            MethodDeclaration emf = (MethodDeclaration)Traversal.getEmf(ctx);
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
                    case "method_name":
                        emf.setPOU((Method)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                        break;
                    case "data_type_access":
                        emf.setType((Type)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                        break;
                    case "using_directive":

                        break;
                    case "all_var_decls":

                        break;
                    case "statements":

                        break;
                    default:

                        break;
                }
            }

        } catch(Exception exception){
            System.err.println("Error in exitMethod_decl!!!");
        }
    }
}
