package com.formaltech.st.parser;

import com.formaltech.st.parser.pre.*;

public class ModelGenerator {

    public static void main(String[] args) throws Exception { 
        ResourceBuilder.filepath = args[0]; 
        ResourceBuilder.initResource();

        try{
            Traversal visitor = new Traversal();
            
            //模型实例化
            GenModelListener genModelListener = new GenModelListener();
            visitor.travelsal(genModelListener);

        } catch(Exception exception){
            System.err.println("Error In ModelGenerator!!!");
        }

    }
}