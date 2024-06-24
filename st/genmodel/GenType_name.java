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

public class GenType_name {
    public static void enter(STParser.Type_nameContext ctx){

    }

    public static void exit(STParser.Type_nameContext ctx){ 
        try{
            String typeName = ctx.getText();
            String parentNodeStr = ResourceBuilder.mapNodeStr.get(ctx.getParent());
            switch(parentNodeStr){
                case "simple_type_decl":
    
                    break;
                case "subrange_type_decl":
                    if(ResourceBuilder.mapTypeEmf.get(typeName) == null){ 
                        SubrangeType emfSubrangeType = ResourceBuilder.typeFactory.createSubrangeType();
                        emfSubrangeType.setTypeDeclaration(((TypeDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent())));
                        ResourceBuilder.mapEmf.put(ctx, emfSubrangeType);
                        emfSubrangeType.setName(typeName);
                        ResourceBuilder.mapTypeEmf.put(typeName, emfSubrangeType);
                    }
                    else{
                        System.err.println("Error: < type '" + typeName + "' is already exist >");
                        System.exit(0);
                    }
                    break;
                case "enum_type_decl":
                    EnumType emfEnumType = ResourceBuilder.typeFactory.createEnumType();
                    emfEnumType.setTypeDeclaration(((TypeDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent())));
                    ResourceBuilder.mapEmf.put(ctx, emfEnumType);
                    emfEnumType.setName(typeName);
                    ResourceBuilder.mapTypeEmf.put(typeName, emfEnumType);
                    break;
                case "array_type_decl":
                    ArrayType emfArrayType = ResourceBuilder.typeFactory.createArrayType();
                    emfArrayType.setTypeDeclaration(((TypeDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent())));
                    ResourceBuilder.mapEmf.put(ctx, emfArrayType);
                    emfArrayType.setName(typeName);
                    ResourceBuilder.mapTypeEmf.put(typeName, emfArrayType);
                    break;
                case "struct_type_decl":
                    StructType emfStructType = ResourceBuilder.typeFactory.createStructType();
                    emfStructType.setTypeDeclaration(((TypeDeclaration)ResourceBuilder.mapEmf.get(ctx.getParent())));
                    ResourceBuilder.mapEmf.put(ctx, emfStructType);
                    emfStructType.setName(typeName);
                    ResourceBuilder.mapTypeEmf.put(typeName, emfStructType);
                    break;
                default:
                    if(ResourceBuilder.mapTypeEmf.get(typeName) == null){
                        System.err.println("Error: <type " + typeName + " is not declared!!!>");
                        System.exit(0);
                    }
                    else{
                        Type emf0 = (Type)ResourceBuilder.mapTypeEmf.get(typeName);
                        ResourceBuilder.mapEmf.put(ctx, emf0);
                    }
                    break;
            }
        } catch(Exception exception){
            System.err.println("Error In exitType_name!!!");
        }
    }
}
