package st.genmodel;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
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

public class GenElem_type_name {
    public static void enter(STParser.Elem_type_nameContext ctx){

    }

    public static void exit(STParser.Elem_type_nameContext ctx){ 
        try{
            ParseTree childNode = ctx.getChild(0);
            if(childNode instanceof TerminalNode){
                String typeName = ctx.getText();
                //System.out.println(typeName);
                if(ResourceBuilder.mapTypeEmf.get(typeName) == null){
                    ElementaryDataType emf = ResourceBuilder.typeFactory.createElementaryDataType();
                    ResourceBuilder.mapEmf.put(ctx, emf);
                    ResourceBuilder.mapTypeEmf.put(typeName, emf);
                    switch(typeName){
                        case "BOOL":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.BOOL);
                            break;
                        case "SINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.SINT);
                            break;
                        case "INT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.INT);
                            break;
                        case "DINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.DINT);
                            break;
                        case "LINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LINT);
                            break;
                        case "USINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.USINT);
                            break;
                        case "UINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.UINT);
                            break;
                        case "UDINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.UDINT);
                            break;
                        case "ULINT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.ULINT);
                            break;
                        case "REAL":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.REAL);
                            break;
                        case "LREAL":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LREAL);
                            break;
                        case "TIME":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.TIME);
                            break;
                        case "LTIME":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LTIME);
                            break;
                        case "DATE":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.DATE);
                            break;
                        case "LDATE":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LDATE);
                            break;
                        case "TOD":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.TOD);
                            break;
                        case "LTOD":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LTOD);
                            break;
                        case "DT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.DT);
                            break;
                        case "LDT":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LDT);
                            break;
                        case "CHAR":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.CHAR);
                            break;
                        case "WCHAR":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.WCHAR);
                            break;
                        case "BYTE":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.BYTE);
                            break;
                        case "WORD":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.WORD);
                            break;
                        case "DWORD":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.DWORD);
                            break;
                        case "LWORD":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.LWORD);
                            break;
    /*                     case "":
                            emf.setName(typeName);
                            emf.setType(PreDefinedEDType.);
                            break; */
                        default: //System.out.println("elem_type_name Error!");
                    }
                }
                else{
                    ElementaryDataType emf = (ElementaryDataType)ResourceBuilder.mapTypeEmf.get(typeName);
                    ResourceBuilder.mapEmf.put(ctx, emf);
                }
            }
            else{
                String typeName = childNode.getText();
                if(ResourceBuilder.mapTypeEmf.get(typeName) == null){
                    switch (typeName) {
                        case "STRING":
                            ElementaryDataType emf0 = ResourceBuilder.typeFactory.createElementaryDataType();
                            ResourceBuilder.mapEmf.put(ctx, emf0);
                            ResourceBuilder.mapEmf.put(childNode, emf0);
                            ResourceBuilder.mapTypeEmf.put(typeName, emf0);
                            emf0.setName(typeName);
                            emf0.setType(PreDefinedEDType.STRING);
                            break;
                        case "WSTRING":
                            ElementaryDataType emf1 = ResourceBuilder.typeFactory.createElementaryDataType();
                            ResourceBuilder.mapEmf.put(ctx, emf1);
                            ResourceBuilder.mapEmf.put(childNode, emf1);
                            ResourceBuilder.mapTypeEmf.put(typeName, emf1);
                            emf1.setName(typeName);
                            emf1.setType(PreDefinedEDType.WSTRING);
                            break;                      
                        default:
                            UserDefinedDataType emf2 = ResourceBuilder.typeFactory.createUserDefinedDataType();
                            TypeDeclaration emf3 = ResourceBuilder.declFactory.createTypeDeclaration();
                            emf2.setTypeDeclaration(emf3);
                            ResourceBuilder.mapEmf.put(ctx, emf2);
                            ResourceBuilder.mapEmf.put(childNode, emf2);
                            ResourceBuilder.mapTypeEmf.put(typeName, emf2);
                            emf2.setName(typeName);
                    }
                }
                else{
                    EObject emf = ResourceBuilder.mapTypeEmf.get(typeName);
                    ResourceBuilder.mapEmf.put(ctx, emf);
                    ResourceBuilder.mapEmf.put(childNode, emf);
                }
            }
        } catch(Exception exception){
            System.err.println("Error In exitElem_type_nmae!!!");
        }

    }
}
