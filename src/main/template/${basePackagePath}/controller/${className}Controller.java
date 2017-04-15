package ${basePackage}.controller;

import ${basePackage}.service.po.${className}Service;
import ${basePackage}.model.req.${className}Req;
import ${basePackage}.model.page.ResponseStruct;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${className}")
@Api(description = "${className}相关操作")
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Service i${className}Service;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部${className}的接口", response = ${className}Req.class)
    public ResponseStruct get${className}s() throws Exception {
        return succ(i${className}Service.queryAll());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "获取单个${className}的接口", response = ${className}Req.class)
    public ResponseStruct get${className}(
        @RequestBody ${className}Req req
    ) throws Exception {
        return succ(i${className}Service.query(req));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加一个${className}", response = ${className}Req.class)
    public ResponseStruct add${className}(
             @RequestBody ${className}Req req
    ) throws Exception {
        i${className}Service.saveOne(req);
        return succ();
    }
}
