package ${basePackage}.client.model.ao;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

<#list importTypes as importType>
        ${importType}
</#list>

@Data
@Builder
public class ${className}Ao implements Serializable {

    <#list dataModelColumns as column>
    private ${column.javaType} ${column.fieldName};
    </#list>

    @Tolerate
    public ${className}Ao () {
    }

}

