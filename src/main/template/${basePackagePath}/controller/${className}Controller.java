package ${basePackage}.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ${basePackage}.dao.${className}Dao;
import ${basePackage}.model.entity.${className};
import ${basePackage}.model.page.ResponseStruct;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${className}")
@Api(description = "${className}相关操作")
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Dao i${className}Dao;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部${className}的接口", response = ${className}.class)
    public ResponseStruct get${className}s() throws Exception {
        return succ(i${className}Dao.selectMany(${className}.build()));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "获取分页${className}的接口", response = ${className}.class)
    public ResponseStruct get${className}sByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) throws Exception {
        if (pageNum <= 0) pageNum = 1;
        if (pageSize <= 0) pageSize = 10;

        return succ(i${className}Dao.selectMany(
        (${className}) ${className}.build().setLimit(pageSize).setIndex((pageNum - 1) * pageSize)));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加一个${className}", response = ${className}.class)
    public ResponseStruct add${className}(
            @ApiParam(required = true, value = "{\n"
                    + "\t\"property\":\"value.\"\n"
                    + "}") @RequestBody JsonNode jsonNode
    ) throws Exception {
        ${className} entity = new ObjectMapper().readValue(jsonNode.toString(), ${className}.class);
        i${className}Dao.insertOne(entity);
        return succ(entity);
    }
}
