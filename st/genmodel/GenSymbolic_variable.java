package st.genmodel;

import org.antlr.v4.runtime.tree.ParseTree;

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

public class GenSymbolic_variable {
    public static void enter(STParser.Symbolic_variableContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("Error: no Namespace in ParentNode in enterSymbolic_variable!!!");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterSymbolic_variable!!!");
        }
    }

    public static void exit(STParser.Symbolic_variableContext ctx){ 
        try{
            ParseTree childNode = ctx.getChild(0);
            String childNodeStr = ResourceBuilder.mapNodeStr.get(childNode);
            if(childNodeStr.equals("symbolic_variable")){ 
                SubscriptVariable emf = ResourceBuilder.varFactory.createSubscriptVariable();
                ResourceBuilder.mapEmf.put(ctx, emf);
                for(int i = 0; i < ctx.getChildCount(); i++){
                    childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                    if(childNodeStr.equals("symbolic_variable")){
                        emf.setVariable((Variable)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                    }
                    else if(childNodeStr.equals("subscript_list")){
                        emf.setSubscript((SubscriptList)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                    }
                }
                Variable varEmf = emf.getVariable();
                emf.setName(varEmf.getName());
                if(varEmf.getType() == null){
                    //emf.setType(((ArrayTypeDecl)((VariableInitializer)varEmf.getVariableList().getInitializer()).getNoNameType()).getInsideType());
                }
                else{
                    emf.setType(((ArrayTypeDecl)(((UserDefinedDataType)varEmf.getType()).getTypeDeclaration())).getInsideType());
                }
            }
            else if(childNodeStr.equals("variable_name")){ 
                Traversal.setFromChildEmf(ctx, 0); 
            }
            else if(childNodeStr.equals("ref_deref")){ 

            }
            else if(childNodeStr.equals("fb_instance_name")){ 
                Variable emf = ((FBInstanceName)Traversal.getChildEmf(ctx, 0)).getElemVar();
                ResourceBuilder.mapEmf.put(ctx, emf);
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In exitSymbolic_variable!!!");
        }
    }
}
