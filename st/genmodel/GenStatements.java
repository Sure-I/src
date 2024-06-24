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

public class GenStatements {
    public static void enter(STParser.StatementsContext ctx){
        try{
            StatementBody emf = ResourceBuilder.stmtFactory.createStatementBody();
            Namespace namespaceEmf = ResourceBuilder.baseFactory.createNamespace();

            emf.setNamespace(namespaceEmf);

            String parentStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentStr){
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
                default: 
                    if(ResourceBuilder.mapEmf.get(ctx.getParent()) instanceof Namespace){
                        emf.setNamespace((Namespace)ResourceBuilder.mapEmf.get(ctx.getParent()));
                    }
                    else{

                    }
                    break;
            }

            ResourceBuilder.mapEmf.put(ctx, emf);
        } catch(Exception exception){
            System.err.println("Error In enterStatements!!!");
        }
    }

    public static void exit(STParser.StatementsContext ctx){ 
        try{
            StatementBody emf = (StatementBody)Traversal.getEmf(ctx);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr.equals("stmt")){
                    Statement childEmf0 = (Statement)Traversal.getChildEmf(ctx, i);
                    childEmf0.setTestString("stmt"+ i);
                    emf.getStatement().add(childEmf0);
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitStatements!!!");
        }
    }
}
