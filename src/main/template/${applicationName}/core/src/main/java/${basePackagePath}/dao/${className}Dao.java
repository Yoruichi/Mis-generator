package ${basePackage}.dao;

import me.yoruichi.mis.BaseDao;
import org.springframework.stereotype.Repository;
import ${basePackage}.model.entity.${className};

@Repository
public class ${className}Dao extends BaseDao<${className}>{

    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}
