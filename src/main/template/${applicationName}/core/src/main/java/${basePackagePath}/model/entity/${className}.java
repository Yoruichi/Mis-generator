package ${basePackage}.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import me.yoruichi.mis.BasePo;
import java.io.Serializable;
import me.yoruichi.mis.Exclude;

<#list importTypes as importType>
${importType}
</#list>
/**
 *
 * ${sqlComment}
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ${className} extends BasePo implements Serializable {

    <#list dataModelColumns as column>
    /**
     * ${column.sqlComment}
     */
    @Exclude
    public static final String ${column.fieldName}Name="${column.fieldName}";
    </#list>

    <#list dataModelColumns as column>
    /**
     * ${column.sqlComment}
     */
    private ${column.javaType} ${column.fieldName};
    </#list>

    @Tolerate
    public ${className}() {
    }

}

