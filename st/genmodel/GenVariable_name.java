package st.genmodel;

import org.antlr.v4.runtime.ParserRuleContext;

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

public class GenVariable_name {
    public static void enter(STParser.Variable_nameContext ctx){

    }

    public static void exit(STParser.Variable_nameContext ctx){ 
        try{
            ParserRuleContext parentNode = ctx.getParent();
            String parentNodeStr = ResourceBuilder.mapNodeStr.get(parentNode);
            String varName = ctx.getText();
            switch(parentNodeStr){
                case "variable_list":
                    VariableList parentEmf0 = (VariableList)ResourceBuilder.mapEmf.get(parentNode);
                    Namespace namespaceEmf0 = parentEmf0.getNamespace();
                    if((namespaceEmf0.getDeclType().getLiteral()).equals("GLOBAL")){
                        if(ResourceBuilder.mapGlobalVarEmf.get(varName) == null){
                            Variable emf0 = ResourceBuilder.elemFactory.createVariable();
                            ResourceBuilder.mapGlobalVarEmf.put(varName, emf0);
                            emf0.setNamespace(namespaceEmf0);
                            emf0.setVariableList(parentEmf0);
                            
                            ResourceBuilder.mapEmf.put(ctx, emf0);

                            String name = ctx.getText();
                            emf0.setName(name);
                        }
                        else{
                            System.err.println("Error: <" + ctx.getText() + " is already exist!!!>");
                            System.exit(0);
                        }
                    }
                    else{ 
                        //System.out.println(namespaceEmf0.getNamespace().getPOU().getName());
                        if(Traversal.getVarInNamespace(varName, namespaceEmf0) == null){
                            Variable emf0 = ResourceBuilder.elemFactory.createVariable();
                            namespaceEmf0.getNamespace().getVariable().add(emf0);
                            emf0.setNamespace(namespaceEmf0);
                            emf0.setVariableList(parentEmf0);
                            
                            ResourceBuilder.mapEmf.put(ctx, emf0);

                            String name = ctx.getText();
                            emf0.setName(name);
                        }
                        else{
                            System.err.println("Error: <" + ctx.getText() + " is already exist!!!>");
                            System.exit(0);
                        }
                    }
                    break;
                case "symbolic_variable":
                    Namespace namespaceEmf1 = (Namespace)Traversal.getParentEmf(ctx);
                    if((Traversal.getVarInNamespace(varName, namespaceEmf1) == null) && (ResourceBuilder.mapGlobalVarEmf.get(varName) == null)){
                        System.err.println("Error: <" + ctx.getText() + " is not declared!!!>");
                        System.exit(0);
                    }               
                    Variable emf1 = Traversal.getVarInNamespace(varName, namespaceEmf1);
                    ResourceBuilder.mapEmf.put(ctx, emf1);
                    break;
                case "loc_var_decl":
                    
                    break;
                case "loc_partly_var":
                    
                    break;
                case "param_assign":
                    if((ResourceBuilder.mapNodeStr.get(ctx.getParent().getParent())).equals("func_call")){
                        Function emfFunc = ((FunctionCall)ResourceBuilder.mapEmf.get(ctx.getParent().getParent())).getFunction();
                        FunctionDeclaration emfFunDecl = (FunctionDeclaration)emfFunc.getDeclaration();
                        if(Traversal.getVarInDecl(varName, emfFunDecl) == null){
                            System.err.println("Error: <" + ctx.getText() + " is not declared!!!>");
                            System.exit(0);
                        }
                        Variable emf3 = Traversal.getVarInDecl(varName, emfFunDecl);
                        ResourceBuilder.mapEmf.put(ctx, emf3);
                    }
                    else if((ResourceBuilder.mapNodeStr.get(ctx.getParent().getParent())).equals("invocation")){
                        Invocation emfInvo = (Invocation)ResourceBuilder.mapEmf.get(ctx.getParent().getParent());
                        MethodDeclaration methodDecl = (MethodDeclaration)emfInvo.getMethod().getDeclaration();
                        if(Traversal.getVarInDecl(varName, methodDecl) == null){
                            System.err.println("Error: <" + ctx.getText() + " is not declared!!!>");
                            System.exit(0);
                        }
                        Variable emf3 = Traversal.getVarInDecl(varName, methodDecl);
                        ResourceBuilder.mapEmf.put(ctx, emf3);
                    }
                    else{ }
                    break;
                default: ;
            }
        } catch(Exception exception){
            System.err.println("Error In exitVariable_name!!!");
        }
    }
}
