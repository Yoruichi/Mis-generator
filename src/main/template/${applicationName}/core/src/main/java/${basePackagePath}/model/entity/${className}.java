package ${basePackage}.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

import com.redteamobile.mis.BasePo;
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

