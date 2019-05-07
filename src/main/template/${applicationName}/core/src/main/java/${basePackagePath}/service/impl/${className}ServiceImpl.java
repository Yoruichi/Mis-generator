package ${basePackage}.service.impl;

import java.util.List;

import ${basePackage}.model.entity.${className};
import ${basePackage}.dao.${className}Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.jcabi.aspects.Loggable;

@Slf4j
@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Dao i${className}Dao;

    @Override
    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public List<${className}> queryAll() throws Exception {
        return i${className}Dao.selectMany(${className}.builder().build());
    }

    @Override
    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public List<${className}> queryPage(${className}Req req, int page, int pageSize) throws Exception {
        int idx = pageSize * (page - 1);
        ${className} condition = translateFromReq(req);
        condition.setIndex(idx).setLimit(pageSize);
        return i${className}Dao.selectMany(condition);
    }

    @Override
    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public ${className} query(${className}Req req) throws Exception {
        return i${className}Dao.select(translateFromReq(req));
    }

    @Override
    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public List<${className}> queryMany(${className}Req req) throws Exception {
        return i${className}Dao.selectMany(translateFromReq(req));
    }

    private ${className} translateFromReq(${className}Req req) throws Exception {
        return ${className}.builder()
        <#list dataModelColumns as column>
            .${column.fieldName}(req.get${column.operName}())
        </#list>
        .build();
    }
}
