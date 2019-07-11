package mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

public class JacksonDateFormatAnnotation extends PluginAdapter {

    public static final String JACKSON_DATE_ANNOTATION_ENABLE = "jacksonDateFormat.enable";
    public static final String JACKSON_DATE_ANNOTATION_FORMAT = "jacksonDateFormat.format";

    public static final String DATE_TYPE ="Date";

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        String enable = properties.getProperty(JACKSON_DATE_ANNOTATION_ENABLE);
        String format = properties.getProperty(JACKSON_DATE_ANNOTATION_FORMAT);

        if (StringUtility.isTrue(enable)) {
            String annotation = String.format("@JsonFormat( pattern=\"%s\",timezone = \"GMT+8\")", format);
            FullyQualifiedJavaType fullyQualifiedJavaType = field.getType();
            System.out.println(fullyQualifiedJavaType.getShortNameWithoutTypeArguments());
            if (DATE_TYPE.equals(fullyQualifiedJavaType.getShortNameWithoutTypeArguments())){
                topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonFormat");
                field.addAnnotation(annotation);
            }
        }

        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}
