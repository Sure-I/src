package st.genmodel;

import org.eclipse.emf.ecore.EObject;

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

public class GenCase_list {
    public static void enter(STParser.Case_listContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_list");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_list!!!");
        }
    }

    public static void exit(STParser.Case_listContext ctx){ 
        try{
            Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);
            GroupStatement emf = ResourceBuilder.stmtFactory.createGroupStatement();
            emf.setNamespace(namespaceEmf);
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                EObject childEmf = Traversal.getChildEmf(ctx, i);
                if(childEmf instanceof Expression){
                    emf.getExprLabel().add((Expression)childEmf);
                }
                else if(childEmf instanceof SubrangeType){
                    emf.getSubrangeLabel().add((SubrangeType)childEmf);
                }
                else{ }
            }
        } catch(Exception exception){
            System.err.println("Error In exitCase_list!!!");
        }
    }
}
