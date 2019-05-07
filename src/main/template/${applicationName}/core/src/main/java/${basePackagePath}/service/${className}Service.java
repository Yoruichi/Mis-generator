package ${basePackage}.service.po;

import java.util.List;

import ${basePackage}.model.entity.${className};
import ${basePackage}.model.req.${className}Req;

import org.springframework.beans.factory.annotation.Autowired;
import com.jcabi.aspects.Loggable;

public interface ${className}Service {

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    boolean saveOne(${className} req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    List<${className}> queryAll() throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    List<${className}> queryPage(${className} req, int page, int pageSize) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    ${className} query(${className} req) throws Exception;

    @Loggable(trim = false, name = "${basePackage}.service.${className}Service")
    List<${className}> queryMany(${className} req) throws Exception;

}
