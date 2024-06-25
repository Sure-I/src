package st;

import st.pre.*;

public class ModelGenerator {

    public static void main(String[] args) throws Exception { 
        ResourceBuilder.filepath = args[0]; 
        ResourceBuilder.initResource();

        try{
            Traversal visitor = new Traversal();
            
            //模型实例化
            GenModelListener genModelListener = new GenModelListener();
            visitor.travelsal(genModelListener);

            //语义检查
            ModelCheckListener modelCheckListener = new ModelCheckListener();
            visitor.travelsal(modelCheckListener);
        } catch(Exception exception){
            System.err.println("Error In MainApplication!!!");
        }

    }
}