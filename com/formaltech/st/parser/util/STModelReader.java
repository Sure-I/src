package com.formaltech.st.parser.util;

import com.formaltech.st.api.Model;
import com.formaltech.st.parser.pre.GenModelListener;
import com.formaltech.st.parser.pre.ResourceBuilder;
import com.formaltech.st.parser.pre.Traversal;

/**
 * 模型加载器
 * 根据文件后缀选用合适的加载器，输出语言模型
 */
public class STModelReader {

    public static Model load(final String filePath) throws Exception {
        ResourceBuilder.filepath = filePath; 
        ResourceBuilder.initResource();

        try{
            //模型实例化
            Traversal visitor = new Traversal();
            GenModelListener genModelListener = new GenModelListener();
            visitor.travelsal(genModelListener);

        } catch(Exception exception){
            System.err.println("Error In STModelReader!!!");
        }

        return ResourceBuilder.model;

    }

}
