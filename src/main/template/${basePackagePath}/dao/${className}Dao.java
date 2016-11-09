package ${basePackage}.dao;

import org.springframework.stereotype.Repository;
import com.redteamobile.jdbc.wrap.base.BaseDao;
import ${basePackage}.${className};

@Repository
public class ${className}Dao extends BaseDao<${className}>{

    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}
