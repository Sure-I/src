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

public class GenDerived_func_name {
    public static void enter(STParser.Derived_func_nameContext ctx){

    }

    public static void exit(STParser.Derived_func_nameContext ctx){ 
        try{
            String funName = ctx.getText();
            if((ResourceBuilder.mapNodeStr.get(ctx.getParent())).equals("func_decl")){
                Namespace namespaceEmf = ((FunctionDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent())).getNamespace();
                if(namespaceEmf.getDeclType().getLiteral().equals("GLOBAL")){
                    if(ResourceBuilder.mapGlobalFunEmf.get(funName) == null){
                        Function emf = ResourceBuilder.pouFactory.createFunction();
                        emf.setNamespace(namespaceEmf);
                        ResourceBuilder.mapEmf.put(ctx, emf);
                        emf.setName(funName);
                        ResourceBuilder.mapGlobalFunEmf.put(funName, emf);
                    }
                    else{
                        System.err.println(funName + " already exsit!!!");
                        System.exit(0);
                    }
                }
                else{
                    if(Traversal.getFunInNamespace(funName, namespaceEmf) == null){
                        Function emf = ResourceBuilder.pouFactory.createFunction();
                        emf.setNamespace(namespaceEmf);
                        namespaceEmf.getNamespace().getFunction().add(emf);
                        ResourceBuilder.mapEmf.put(ctx, emf);
                        emf.setName(funName);
                    }
                    else{
                        System.err.println(funName + " already exsit!!!");
                        System.exit(0);
                    }
                }
            }
            else if(ResourceBuilder.mapNodeStr.get(ctx.getParent()) == "func_name"){
                Namespace namespaceEmf = (Namespace)Traversal.getParentEmf(ctx);
                if(Traversal.getFunInNamespace(funName, namespaceEmf) == null){
                    if(ResourceBuilder.mapGlobalFunEmf.get(funName) == null){
                        System.err.println(funName + " does not declared!!!");
                        System.exit(0);
                    }
                    else{
                        ResourceBuilder.mapEmf.put(ctx, ResourceBuilder.mapGlobalFunEmf.get(funName));
                    }
                }
                else{
                    ResourceBuilder.mapEmf.put(ctx, Traversal.getFunInNamespace(funName, namespaceEmf));
                }

            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error in exitDerived_func_name!!!");
        }
    }
}
