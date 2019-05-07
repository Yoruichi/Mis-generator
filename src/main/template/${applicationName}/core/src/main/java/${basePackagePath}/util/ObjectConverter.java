package ${basePackage}.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Yoruichi
 * @Date: 2019/4/25 3:22 PM
 */
public class ObjectConverter {
    private static final Logger logger = LoggerFactory.getLogger(ObjectConverter.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final DateTimeFormatter BASE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static <T> T convertFrom(String json, TypeReference<T> tTypeReference) throws IOException {
        return OBJECT_MAPPER.readValue(json, tTypeReference);
    }

    public static <T> T convertFrom(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, new TypeReference<T>() {
        });
    }

    public static <T> T convertFrom(JsonNode json) throws IOException {
        return OBJECT_MAPPER.readValue(json.toString(), new TypeReference<T>() {
        });
    }

    public static BigDecimal convertFrom(Integer value) {
        if (Objects.isNull(value)) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(value.intValue());
    }

    public static <T, R> T convertFrom(R resource, T target) {
        ArrayList<Field> targetFields = Lists.newArrayList(target.getClass().getDeclaredFields());
        Map<String, Field> resourceFieldsMap = Lists.newArrayList(resource.getClass().getDeclaredFields())
                .stream().collect(Collectors.toMap(f -> f.getName(), f -> f));
        for (Field field : targetFields) {
            if (resourceFieldsMap.containsKey(field.getName())) {
                Field resourceField = resourceFieldsMap.get(field.getName());
                if (field.getType().equals(resourceField.getType())) {
                    field.setAccessible(true);
                    resourceField.setAccessible(true);
                    try {
                        field.set(target, resourceField.get(resource));
                    } catch (IllegalAccessException e) {
                        logger.warn("Set field {} for target {} with value {} failed.", field.getName(), target, resource);
                    }
                }
            }
        }
        return target;
    }

}
