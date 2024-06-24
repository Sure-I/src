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

public class GenDerived_fb_name {
    public static void enter(STParser.Derived_fb_nameContext ctx){

    }

    public static void exit(STParser.Derived_fb_nameContext ctx){ 
        try{
            String fbName = ctx.getText();
            if(ResourceBuilder.mapTypeEmf.get(fbName) == null){
                FunctionBlockType emf = ResourceBuilder.typeFactory.createFunctionBlockType();
                ResourceBuilder.mapEmf.put(ctx, emf);
                emf.setName(fbName);
                emf.setDeclaration((FunctionBlockDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent()));
                ResourceBuilder.mapTypeEmf.put(fbName, emf);
            }
            else{
                System.err.println("function block " + fbName + " already exsit!!!");
                System.exit(0);
            }

        } catch(Exception exception){
            System.err.println("Error in exitDerived_fb_name!!!");
        }
    }
}
