package ${basePackage}.client.model.ao;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import java.util.List;
import java.io.Serializable;

<#list importTypes as importType>
        ${importType}
</#list>

@Data
@Builder
public class ${className}ListAo implements Serializable {

    private List<${className}Ao> list;

    @Tolerate
    public ${className}ListAo () {
    }

}

