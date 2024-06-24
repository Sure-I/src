package st.genmodel;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

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

public class GenExpression {
    public static void enter(STParser.ExpressionContext ctx){
        try{
            if(Traversal.getParentEmf(ctx) instanceof Namespace) Traversal.setFromParentEmf(ctx);
            else{
                //System.err.println("no namespace in parentNode in Expression");
            }
            //System.out.println(((Namespace)Traversal.getEmf(ctx)).getNamespace().getPOU().getName());
        } catch(Exception exception){
            System.err.println("Error In enterExpression!!!");
        }
    }

/* ///exit节点时对子节点进行判断，确定是哪一种expression
//////实例化emf并且配置属性中的reference和attribute */

    public static void exit(STParser.ExpressionContext ctx){ 
        try{
            ParseTree childNode = ctx.getChild(0);
            Namespace namespaceEmf = (Namespace)Traversal.getEmf(ctx);

            if(childNode instanceof ErrorNode){ 

            }
            else if(childNode instanceof TerminalNode){
                TerminalNode tNode = (TerminalNode)childNode;
                UnaryExpression emf = ResourceBuilder.exprFactory.createUnaryExpression();
                emf.setNamespace(namespaceEmf);
                ResourceBuilder.mapEmf.put(ctx, emf);
                emf.setTestString("unary_expr_emf");

                emf.setExpression( (Expression)Traversal.getChildEmf(ctx, 1) );
                emf.setType(emf.getExpression().getType());
                String nodeStr = ResourceBuilder.mapNodeStr.get(tNode);
                switch(nodeStr){
                    case "(":
                        //System.out.println("PARETHESES");
                        emf.setOperator(UnaryOperator.PARENTHESES);
                        break;
                    case "^":
                        emf.setOperator(UnaryOperator.DEREFERENCE);
                        break;
                    case "-":
                        emf.setOperator(UnaryOperator.NEGATION);
                        break;
                    case "+":
                        emf.setOperator(UnaryOperator.UNRAYPLUS);
                        break;
                    case "NOT":
                        emf.setOperator(UnaryOperator.COMPLEMENT);
                        break;
                    default: //System.out.println("expression error!!!");
                }
            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "expression" ){
                BinaryExpression emf = ResourceBuilder.exprFactory.createBinaryExpression();
                emf.setNamespace(namespaceEmf);
                ResourceBuilder.mapEmf.put(ctx, emf);

                emf.setTestString("binary_expr_emf");
                emf.setFirstExpression( (Expression)Traversal.getChildEmf(ctx, 0) );
                emf.setSecondExpression( (Expression)Traversal.getChildEmf(ctx, 2) );
                emf.setType(emf.getFirstExpression().getType());

                String nodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(1));
                switch(nodeStr){
                    case "**":
                        emf.setOperator(BinaryOperator.EXPONENTIATION);
                        break;
                    case "*":
                        emf.setOperator(BinaryOperator.MULTIPLY);
                        break;
                    case "/":
                        emf.setOperator(BinaryOperator.DIVIDE);
                        break;
                    case "MOD":
                        emf.setOperator(BinaryOperator.MODULO);
                        break;
                    case "+":
                        emf.setOperator(BinaryOperator.ADD);
                        break;
                    case "-":
                        emf.setOperator(BinaryOperator.SUBSTRACT);
                        break;
                    case "=":
                        emf.setOperator(BinaryOperator.EQUAL);
                        break;
                    case "<>":
                        emf.setOperator(BinaryOperator.INEQUAL);
                        break;
                    case "<":
                        emf.setOperator(BinaryOperator.LESS_THAN);
                        break;
                    case "<=":
                        emf.setOperator(BinaryOperator.LESS_THAN_OR_EQUAL);
                        break;
                    case ">":
                        emf.setOperator(BinaryOperator.GREATER_THAN);
                        break;
                    case ">=":
                        emf.setOperator(BinaryOperator.GTEATER_THAN_OR_EQUAL);
                        break;
                    case "&":
                        emf.setOperator(BinaryOperator.BOOLEAN_AND);
                        break;
                    case "AND":
                        emf.setOperator(BinaryOperator.BOOLEAN_AND);
                        break;
                    case "XOR":
                        emf.setOperator(BinaryOperator.BOOLEAN_EXCLUSIVE_OR);
                        break;
                    case "OR":
                        emf.setOperator(BinaryOperator.BOOLEAN_OR);
                        break;
                    default: //System.out.println("expression error!!!");
                }

            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "constant" ){
                LiteralExpression emf = ResourceBuilder.exprFactory.createLiteralExpression();
                emf.setNamespace(namespaceEmf);
                ResourceBuilder.mapEmf.put(ctx, emf);
                emf.setTestString("constant_expr_emf");
                emf.setLiteral( (Literal)Traversal.getChildEmf(ctx, 0) );
                //System.out.println(ctx.getText() + ": " + emf.getLiteral().getType().getLiteral());
                if(emf.getLiteral().getType() == LiteralType.INTEGER){
                    if(ResourceBuilder.mapTypeEmf.get("INTEGER") == null){
                        TypeDeclaration typeDeclEmf = ResourceBuilder.declFactory.createTypeDeclaration();
                        UserDefinedDataType typeEmf = ResourceBuilder.typeFactory.createUserDefinedDataType();
                        typeDeclEmf.setType(typeEmf);
                        typeEmf.setTypeDeclaration(typeDeclEmf);
                        typeEmf.setName("INTEGER");
                        ResourceBuilder.mapTypeEmf.put("INTEGER", typeEmf);
                        emf.setType(typeEmf);
                    }
                    else{emf.setType((Type)ResourceBuilder.mapTypeEmf.get("INTEGER"));}
                }
                else if(emf.getLiteral().getType() == LiteralType.SINGLE_BYTE_CHAR){
                    if(ResourceBuilder.mapTypeEmf.get("SINGLE_BYTE_CHAR") == null){
                        TypeDeclaration typeDeclEmf = ResourceBuilder.declFactory.createTypeDeclaration();
                        UserDefinedDataType typeEmf = ResourceBuilder.typeFactory.createUserDefinedDataType();
                        typeDeclEmf.setType(typeEmf);
                        typeEmf.setTypeDeclaration(typeDeclEmf);
                        typeEmf.setName("SINGLE_BYTE_CHAR");
                        ResourceBuilder.mapTypeEmf.put("SINGLE_BYTE_CHAR", typeEmf);
                        emf.setType(typeEmf);
                    }
                    else{emf.setType((Type)ResourceBuilder.mapTypeEmf.get("SINGLE_BYTE_CHAR"));}
                }
                else if(emf.getLiteral().getType() == LiteralType.REAL){
                    if(ResourceBuilder.mapTypeEmf.get("REAL") == null){
                        TypeDeclaration typeDeclEmf = ResourceBuilder.declFactory.createTypeDeclaration();
                        UserDefinedDataType typeEmf = ResourceBuilder.typeFactory.createUserDefinedDataType();
                        typeDeclEmf.setType(typeEmf);
                        typeEmf.setTypeDeclaration(typeDeclEmf);
                        typeEmf.setName("REAL");
                        ResourceBuilder.mapTypeEmf.put("REAL", typeEmf);
                        emf.setType(typeEmf);
                    }
                    else{emf.setType((Type)ResourceBuilder.mapTypeEmf.get("REAL"));}
                }
                else if(emf.getLiteral().getType() == LiteralType.BOOLEAN){
                    if(ResourceBuilder.mapTypeEmf.get("BOOLEAN") == null){
                        TypeDeclaration typeDeclEmf = ResourceBuilder.declFactory.createTypeDeclaration();
                        UserDefinedDataType typeEmf = ResourceBuilder.typeFactory.createUserDefinedDataType();
                        typeDeclEmf.setType(typeEmf);
                        typeEmf.setTypeDeclaration(typeDeclEmf);
                        typeEmf.setName("BOOLEAN");
                        ResourceBuilder.mapTypeEmf.put("BOOLEAN", typeEmf);
                        emf.setType(typeEmf);
                    }
                    else{emf.setType((Type)ResourceBuilder.mapTypeEmf.get("BOOLEAN"));}
                }
                else if(emf.getLiteral().getType() == LiteralType.TIME){
                    if(ResourceBuilder.mapTypeEmf.get("TIME") == null){
                        TypeDeclaration typeDeclEmf = ResourceBuilder.declFactory.createTypeDeclaration();
                        UserDefinedDataType typeEmf = ResourceBuilder.typeFactory.createUserDefinedDataType();
                        typeDeclEmf.setType(typeEmf);
                        typeEmf.setTypeDeclaration(typeDeclEmf);
                        typeEmf.setName("TIME");
                        ResourceBuilder.mapTypeEmf.put("TIME", typeEmf);
                        emf.setType(typeEmf);
                    }
                    else{emf.setType((Type)ResourceBuilder.mapTypeEmf.get("TIME"));}
                }
                else{}
            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "variable" ){
                VariableExpression emf = ResourceBuilder.exprFactory.createVariableExpression();
                emf.setNamespace(namespaceEmf);
                ResourceBuilder.mapEmf.put(ctx, emf);
                emf.setVariable((Variable)Traversal.getChildEmf(ctx, 0));
                emf.setType(emf.getVariable().getType());

                emf.setTestString("var_access_expr_emf");
            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "func_call" ){
                Traversal.setFromChildEmf(ctx, 0);
                FunctionCall emf = (FunctionCall)Traversal.getEmf(ctx);
                emf.setNamespace(namespaceEmf);
                emf.setTestString("func_call_expr_emf");
            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "quote_value" ){ 
                QuotevalueExpression emf = ResourceBuilder.exprFactory.createQuotevalueExpression();
                emf.setNamespace(namespaceEmf);
                ResourceBuilder.mapEmf.put(ctx, emf);
                QuoteValue valueEmf = (QuoteValue)ResourceBuilder.mapEmf.get(ctx.getChild(0));
                emf.setQuoteValue(valueEmf);
                emf.setType(valueEmf.getQuoteType());
            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In exitExpression!!!");
        }
    }
}
