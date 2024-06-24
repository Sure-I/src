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

public final class GenAll_decl {
    public static void enter(STParser.All_declContext ctx){

    }

    public static void exit(STParser.All_declContext ctx){
        try{
            AllDeclaration emf = ResourceBuilder.declFactory.createAllDeclaration();
            ResourceBuilder.mapEmf.put(ctx, emf);
            for(int i = 0; i < ctx.getChildCount(); i++){
                String childNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(i));
                if(childNodeStr.equals("data_type_decl")){
                    TypeDeclList typeDeclListEmf = (TypeDeclList)ResourceBuilder.mapEmf.get(ctx.getChild(i));
                    for(int j = 0; j < typeDeclListEmf.getTypeDeclatation().size(); j++){
                        emf.getDeclaration().add(typeDeclListEmf.getTypeDeclatation().get(j));
                    }
                }
                else{
                    emf.getDeclaration().add((Declaration)ResourceBuilder.mapEmf.get(ctx.getChild(i)));
                }
            }
            //System.out.println(emf.getDeclaration().size());
        }catch(Exception exception){
            System.err.println("Error in exitAll_decl!!!");
        }
    }
}
