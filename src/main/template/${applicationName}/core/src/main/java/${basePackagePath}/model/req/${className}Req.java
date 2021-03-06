package ${basePackage}.model.req;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

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
public class ${className}Req implements Serializable {

    <#list dataModelColumns as column>
    private ${column.javaType} ${column.fieldName};
    </#list>

    @Tolerate
    public ${className}Req() {
    }

}

