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

public class GenCase_stmt {
    public static void enter(STParser.Case_stmtContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                System.err.println("no namespace in parentNode in case_stmt");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterCase_stmt!!!");
        }
    }

    public static void exit(STParser.Case_stmtContext ctx){ 
        try{
            Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);
            CaseStatement emf = ResourceBuilder.stmtFactory.createCaseStatement();
            emf.setNamespace(namespaceEmf);
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setTestString("case_stmt_emf");
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr == "expression"){
                    emf.setSelector((Expression)Traversal.getChildEmf(ctx, i));
                }
                else if(childNodeStr == "case_selection"){ 
                    emf.getGroupStatement().add((GroupStatement)Traversal.getChildEmf(ctx, i));
                }
                else if(childNodeStr == "stmt_list"){
                    emf.setElseStatement((StatementBody)Traversal.getChildEmf(ctx, i));
                }
                else{ }
            }
        } catch(Exception exception){
            System.err.println("Error In exitCase_stmt!!!");
        }
    }
}
