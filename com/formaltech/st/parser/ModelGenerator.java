package com.formaltech.st.parser;

import com.formaltech.st.parser.util.*;
import com.formaltech.st.api.Model;

public class ModelGenerator {

    public static void main(String[] args) throws Exception { 
        String filepath = args[0]; 

        try{
            Model model = STModelReader.load(filepath);

            System.out.println(model.getDeclarations().size());
        } catch(Exception exception){
            System.err.println("Error In ModelGenerator!!!");
        }

    }
}