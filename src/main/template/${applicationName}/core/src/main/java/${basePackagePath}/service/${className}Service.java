package ${basePackage}.service.po;

import java.util.List;

import ${basePackage}.model.entity.${className};
import ${basePackage}.model.req.${className}Req;
import ${basePackage}.dao.${className}Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jcabi.aspects.Loggable;

@Service
public interface ${className}Service {

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public boolean saveOne(${className}Req req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public boolean saveOne(${className} req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public List<${className}> queryAll() throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public List<${className}> queryPage(${className}Req req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public ${className} query(${className}Req req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public ${className} query(${className} req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    public List<${className}> queryMany(${className} req) throws Exception;

}
