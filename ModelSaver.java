import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class ModelSaver {
    // 创建 ResourceSet 对象
    ResourceSet resourceSet = new ResourceSetImpl();

    // 创建 Resource 对象，并关联到文件
    String modelURI = new String();
    modelURI = "C:\\Workbench\\Java_workbench\\st_test\\output.genmodel";
    Resource resource = resourceSet.createResource(URI.createURI(modelURI));

    if (resource != null) {
        // 将 GenModel 实例添加到 Resource 中
        resource.getContents().add();

        // 保存 Resource 到文件
        try {
            resource.save(Collections.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.err.println("Failed to create resource.");
}
