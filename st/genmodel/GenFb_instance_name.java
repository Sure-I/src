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

public class GenFb_instance_name {
    public static void enter(STParser.Fb_instance_nameContext ctx){
        try{
            if(ResourceBuilder.mapEmf.get(ctx.getParent()) instanceof Namespace){
                Traversal.setFromParentEmf(ctx);
            }
            else{
                Namespace getEmf = ((Invocation)Traversal.getParentEmf(ctx)).getNamespace();
                ResourceBuilder.mapEmf.put(ctx, getEmf);
            }

        } catch(Exception exception){
            System.err.println("Error in enterFb_instance_name!!!");
        }
    }

    public static void exit(STParser.Fb_instance_nameContext ctx){ 
        try{
            FBInstanceName emf = ResourceBuilder.exprFactory.createFBInstanceName();
            Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);
            ResourceBuilder.mapEmf.put(ctx, emf);

            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                switch(childNodeStr){
                    case"namespace_name":
                        emf.getVarName().add(ctx.getChild(i).getText());
                        break;
                    case"fb_elem_name":
                        emf.setName(ctx.getChild(i).getText());
                        break;
                    default: break;
                }
            }

            String varName = emf.getVarName().get(0);

            if(Traversal.getVarInNamespace(varName, namespaceEmf) == null){
                System.err.println("Error: <" + varName + " is not declared!!!>");
            }
            else{ emf.setInstance(Traversal.getVarInNamespace(varName, namespaceEmf)); }

            FunctionBlockDeclaration FBDeclEmf = (FunctionBlockDeclaration)(((FunctionBlockType)emf.getInstance().getType()).getDeclaration());
            String elemName = emf.getName();
            if(Traversal.getVarInDecl(elemName, FBDeclEmf) == null){
                if(Traversal.getMethodInDecl(elemName, FBDeclEmf) == null){
                    System.err.println("Error: <" + elemName + " is not declared!!!>");
                }
                else{
                    ((Invocation)ResourceBuilder.mapEmf.get(ctx.getParent())).setMethod(Traversal.getMethodInDecl(elemName, FBDeclEmf));
                    emf.setMethod(Traversal.getMethodInDecl(elemName, FBDeclEmf));
                }
            }
            else{
                emf.setElemVar(Traversal.getVarInDecl(elemName, FBDeclEmf));
                emf.setType(emf.getElemVar().getType());
            }

            
        } catch(Exception exception){
            System.err.println("Error in exitFb_instance_name!!!");
        }
    }
}
