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

public class GenMethod_name {
    public static void enter(STParser.Method_nameContext ctx){
        try{
            Namespace namespaceEmf = ((MethodDeclaration)Traversal.getParentEmf(ctx)).getNamespace();
            ResourceBuilder.mapEmf.put(ctx, namespaceEmf);
        } catch(Exception exception){
            System.err.println("Error in enterMethod_name!!!");
        }
    }

    public static void exit(STParser.Method_nameContext ctx){ 
        try{ 
            Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);
            Method emf = ResourceBuilder.pouFactory.createMethod();
            emf.setName(ctx.getText());
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setNamespace(namespaceEmf);

            namespaceEmf.getNamespace().getMethod().add(emf);
        } catch(Exception exception){
            System.err.println("Error in exitMethod_name!!!");
        }
    }
}
