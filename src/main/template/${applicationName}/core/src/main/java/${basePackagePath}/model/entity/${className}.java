package ${basePackage}.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import me.yoruichi.mis.BasePo;
import java.io.Serializable;

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
public class ${className} extends BasePo implements Serializable {

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

