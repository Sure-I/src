package com.formaltech.st.parser.genmodel;

import parser.*;
import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

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

            if(childNode instanceof ErrorNode){ 

            }
            else if(childNode instanceof TerminalNode){
                TerminalNode tNode = (TerminalNode)childNode;
                UnaryOp emf = ResourceBuilder.exprFactory.createUnaryOp();
                ResourceBuilder.mapEmf.put(ctx, emf);

                emf.setOperand( (Expression)Traversal.getChildEmf(ctx, 1) );
                String nodeStr = ResourceBuilder.mapNodeStr.get(tNode);
                switch(nodeStr){
                    case "(":
                        //System.out.println("PARETHESES");
                        emf.setOperator("()");
                        break;
                    case "^":
                        emf.setOperator("^");
                        break;
                    case "-":
                        emf.setOperator("-");
                        break;
                    case "+":
                        emf.setOperator("+");
                        break;
                    case "NOT":
                        emf.setOperator("NOT");
                        break;
                    default: //System.out.println("expression error!!!");
                }
            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "expression" ){
                BinaryOp emf = ResourceBuilder.exprFactory.createBinaryOp();
                ResourceBuilder.mapEmf.put(ctx, emf);

                emf.getOperands().add( (Expression)Traversal.getChildEmf(ctx, 0) );
                emf.getOperands().add( (Expression)Traversal.getChildEmf(ctx, 2) );

                String nodeStr = ResourceBuilder.mapNodeStr.get(ctx.getChild(1));
                switch(nodeStr){
                    case "**":
                        emf.setOperator("**");
                        break;
                    case "*":
                        emf.setOperator("*");
                        break;
                    case "/":
                        emf.setOperator("/");
                        break;
                    case "MOD":
                        emf.setOperator("MOD");
                        break;
                    case "+":
                        emf.setOperator("+");
                        break;
                    case "-":
                        emf.setOperator("-");
                        break;
                    case "=":
                        emf.setOperator("=");
                        break;
                    case "<>":
                        emf.setOperator("<>");
                        break;
                    case "<":
                        emf.setOperator("<");
                        break;
                    case "<=":
                        emf.setOperator("<=");
                        break;
                    case ">":
                        emf.setOperator(">");
                        break;
                    case ">=":
                        emf.setOperator(">=");
                        break;
                    case "&":
                        emf.setOperator("&");
                        break;
                    case "AND":
                        emf.setOperator("AND");
                        break;
                    case "XOR":
                        emf.setOperator("XOR");
                        break;
                    case "OR":
                        emf.setOperator("OR");
                        break;
                    default: //System.out.println("expression error!!!");
                }

            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "constant" ){
                LiteralOp emf = ResourceBuilder.exprFactory.createLiteralOp();
                ResourceBuilder.mapEmf.put(ctx, emf);

            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "variable" ){

            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "func_call" ){

            }
            else if( ResourceBuilder.mapNodeStr.get(ctx.getChild(0)) == "quote_value" ){ 

            }
            else{ }
        } catch(Exception exception){
            System.err.println("Error In exitExpression!!!");
        }
    }
}
