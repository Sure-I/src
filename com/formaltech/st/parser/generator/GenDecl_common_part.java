package com.formaltech.st.parser.generator;

import com.formaltech.st.parser.pre.*;
import com.formaltech.st.api.Model;
import com.formaltech.st.api.basics.*;
import com.formaltech.st.api.types.*;
import com.formaltech.st.api.declarations.*;
import com.formaltech.st.api.expressions.*;
import com.formaltech.st.api.statements.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class GenDecl_common_part {
////decl_common_part的实例化对象为Initializer，实例化步骤在子节点中进行完成，本节点中进行关联
////在exit中完成容器declaration以及属性variableList的设定，并且完成VariableList对象和Variable对象的属性设置
    public static void enter(STParser.Decl_common_partContext ctx){
        try{

        } catch(Exception exception){
            System.err.println("Error In enterDecl_common_part!!!");
        }
    }   

    public static void exit(STParser.Decl_common_partContext ctx){ 
        try{

        } catch(Exception exception){
            System.err.println("Error In exitDecl_common_part!!!");
        }
    }
}
