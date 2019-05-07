package ${basePackage}.dao;

import org.springframework.stereotype.Repository;
import com.redteamobile.mis.BaseDao;
import ${basePackage}.model.entity.${className};

@Repository
public class ${className}Dao extends BaseDao<${className}>{

    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}
