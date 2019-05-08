package ${basePackage}.controller;

import ${basePackage}.service.${className}Service;
import ${basePackage}.model.req.${className}Req;
import ${basePackage}.model.entity.${className};
import ${basePackage}.util.ObjectConverter;
import ${basePackage}.client.model.BaseResponse;
import ${basePackage}.client.model.ao.*;
import static ${basePackage}.client.model.BaseResponse.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.jcabi.aspects.Loggable;
import java.util.List;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/${className}")
@Api(description = "${className}相关操作")
@Slf4j
public class ${className}Controller {

    @Autowired
    private ${className}Service i${className}Service;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部${className}的接口", response = ${className}Req.class)
    @Loggable(trim = false, name = "${basePackage}.controller.${className}Controller")
    public BaseResponse<${className}ListAo> get${className}s() throws Exception {
        List<${className}> res = i${className}Service.queryAll();
        List<${className}Ao> list = res.stream().map(o -> ObjectConverter.convertFrom(o, ${className}Ao.builder().build())).collect(Collectors.toList());
        return success(${className}ListAo.builder().list(list).build());
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "获取分页${className}的接口", response = ${className}Req.class)
    @Loggable(trim = false, name = "${basePackage}.controller.${className}Controller")
    public BaseResponse<${className}PageAo> get${className}s(@NotNull @RequestBody ${className}Req req, @RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        List<${className}> res = i${className}Service.queryPage(req, page, size);
        List<${className}Ao> list = res.stream().map(o -> ObjectConverter.convertFrom(o, ${className}Ao.builder().build())).collect(Collectors.toList());
        Long totalCount = i${className}Service.count(req);
        return success(${className}PageAo.builder().list(list).page(page).pageSize(size).totalCount(totalCount).build());
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    @ApiOperation(value = "获取单个${className}的接口", response = ${className}Req.class)
    @Loggable(trim = false, name = "${basePackage}.controller.${className}Controller")
    public BaseResponse<${className}Ao> get${className}(@NotNull @RequestBody ${className}Req req) throws Exception {
        ${className} res = i${className}Service.query(req);
        if (Objects.isNull(res)) {
            return success();
        }
        return success(ObjectConverter.convertFrom(res, ${className}Ao.builder().build()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加一个${className}", response = ${className}Req.class)
    @Loggable(trim = false, name = "${basePackage}.controller.${className}Controller")
    public BaseResponse add${className}(@NotNull @RequestBody ${className}Req req) throws Exception {
        i${className}Service.saveOne(req);
        return success();
    }
}
