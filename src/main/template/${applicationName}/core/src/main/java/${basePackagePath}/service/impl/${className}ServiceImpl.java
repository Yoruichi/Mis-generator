package ${basePackage}.service.impl;

import java.util.List;

import ${basePackage}.model.entity.${className};
import ${basePackage}.model.req.${className}Req;
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

    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public boolean saveOne(${className}Req req) throws Exception {
        i${className}Dao.insertOne(this.translateFromReq(req));
        return true;
    }

    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public List<${className}> queryAll() throws Exception {
        return i${className}Dao.selectMany(${className}.build());
    }

    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public ${className} query(${className}Req req) throws Exception {
        return i${className}Dao.select(this.translateFromReq(req));
    }

    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public ${className} query(${className} req) throws Exception {
        return i${className}Dao.select(req);
    }

    @Loggable(trim = false, name = "${basePackage}.service.impl.${className}ServiceImpl")
    public List<${className}> queryMany(${className} req) throws Exception {
        return i${className}Dao.selectMany(req);
    }

    private ${className} translateFromReq(${className}Req req) throws Exception {
        ${className} entity = ${className}.build()
        <#list dataModelColumns as column>
            .set${column.operName}(req.get${column.operName}())//${column.sqlComment}
        </#list>
        ;
        return entity;
    }
}
