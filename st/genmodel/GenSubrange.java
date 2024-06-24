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

public class GenSubrange {
    public static void enter(STParser.SubrangeContext ctx){

    }

    public static void exit(STParser.SubrangeContext ctx){ 
        try{
            Subrange emf = ResourceBuilder.declFactory.createSubrange();
            ResourceBuilder.mapEmf.put(ctx, emf);
            emf.setLowerBound((Expression)Traversal.getChildEmf(ctx, 0));
            emf.setUpperBound((Expression)Traversal.getChildEmf(ctx, 2));
        } catch(Exception exception){
            System.err.println("Error In Subrange!!!");
        }
    }
}
