package mybatis.generator.plugins;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;


/**
 * @author Created by rui.zhang on 2018/4/9.
 * @version ver1.0
 * email misterchangray@hotmail.com
 * description 根据数据库注释对实体类增加swagger2文档注解
 */
public class GeneratorSwagger2Doc extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        String classAnnotation = "@ApiModel";
        if(!topLevelClass.getAnnotations().contains(classAnnotation)) {
            topLevelClass.addAnnotation(classAnnotation);
        }

        String apiModelAnnotationPackage =  properties.getProperty("apiModelAnnotationPackage");
        String apiModelPropertyAnnotationPackage = properties.getProperty("apiModelPropertyAnnotationPackage");
        if(null == apiModelAnnotationPackage) {
            apiModelAnnotationPackage = "io.swagger.annotations.ApiModel";
        }
        if(null == apiModelPropertyAnnotationPackage) {
            apiModelPropertyAnnotationPackage = "io.swagger.annotations.ApiModelProperty";
        }

        topLevelClass.addImportedType(apiModelAnnotationPackage);

        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            topLevelClass.addImportedType(apiModelPropertyAnnotationPackage);
            field.addAnnotation("@ApiModelProperty(value=\"" + introspectedColumn.getJavaProperty() + introspectedColumn.getRemarks() + "\")");
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
