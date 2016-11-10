package ${basePackage}.dao;

import org.springframework.stereotype.Repository;
import me.yoruichi.mis.BaseDao;
import ${basePackage}.${className};

@Repository
public class ${className}Dao extends BaseDao<${className}>{

    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}
