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

public class GenAssign_stmt {
    public static void enter(STParser.Assign_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in assign_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterAssign_stmt!!!");
        }
    }

    public static void exit(STParser.Assign_stmtContext ctx){ 
        try{
            String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(0));
            switch(childNodeStr){
                case "variable": 
                    AssignmentStatement emf0 = ResourceBuilder.stmtFactory.createAssignmentStatement();
                    Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);
                    emf0.setNamespace(namespaceEmf);
                    emf0.setTestString("assign_stmt_emf");
                    ResourceBuilder.mapEmf.put(ctx, emf0);
                    for(int i = 0; i < ctx.getChildCount(); i++){
                        EObject childEmf = Traversal.getChildEmf(ctx, i);
                        if(childEmf instanceof Variable){
                            Variable childEmf0 = (Variable)childEmf;
                            emf0.setVariable(childEmf0);
                        }
                        else if(childEmf instanceof Expression){
                            Expression childEmf1 = (Expression)childEmf;
                            emf0.setExpression(childEmf1);
                        }
                        else if(childEmf instanceof QuoteValue){
                            QuoteValue childEmf1 = (QuoteValue)childEmf;
                            emf0.setQuoteValue(childEmf1);
                        }
                        else{ }
                    }
                    break;
                case "ref_assign": 

                    break;
                case "assignment_attempt": 

                    break;
            }
        } catch(Exception exception){
            System.err.println("Error In exitAssign_stmt!!!");
        }
    }
}
