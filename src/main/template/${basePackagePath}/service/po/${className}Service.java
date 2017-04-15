package ${basePackage}.service.po;

import java.util.List;

import ${basePackage}.model.entity.${className};
import ${basePackage}.model.req.${className}Req;
import ${basePackage}.dao.${className}Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ${className}Service {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ${className}Dao i${className}Dao;

    public boolean saveOne(${className}Req req) throws Exception {
        i${className}Dao.insertOne(this.translateFromReq(req));
        return true;
    }

    public List<${className}> queryAll() throws Exception {
        return i${className}Dao.selectMany(${className}.build());
    }
    
    public ${className} query(${className}Req req) throws Exception {
        return i${className}Dao.select(this.translateFromReq(req));
    }
    
    public ${className} query(${className} req) throws Exception {
        return i${className}Dao.select(req);
    }
    
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
