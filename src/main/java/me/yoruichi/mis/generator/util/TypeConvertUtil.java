package me.yoruichi.mis.generator.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换工具类，将数据库类型转换为java类型
 */
public class TypeConvertUtil {
    private static Map<Integer, String> typeMap = new HashMap<Integer, String>();
//key是java.sql.Types中的值，value是对应的java类型

    static {
        typeMap.put(Types.TINYINT, "java.lang.Byte");//
        typeMap.put(Types.SMALLINT, "java.lang.Integer");//
        typeMap.put(Types.INTEGER, "java.lang.Integer");//
        typeMap.put(Types.BIGINT, "java.lang.Long");//
        typeMap.put(Types.REAL, "java.lang.Float");//
        typeMap.put(Types.FLOAT, "java.lang.Double");//
        typeMap.put(Types.DOUBLE, "java.lang.Double");//
        typeMap.put(Types.DECIMAL, "java.lang.Long");//需要import java.math.BigDecimal;
        typeMap.put(Types.NUMERIC, "java.lang.Integer");//
        typeMap.put(Types.BIT, "java.lang.Boolean");//
        typeMap.put(Types.CHAR, "java.lang.String");//
        typeMap.put(Types.VARCHAR, "java.lang.String");//
        typeMap.put(Types.LONGVARCHAR, "java.lang.String");//
        typeMap.put(Types.BINARY, "byte[]");//
        typeMap.put(Types.VARBINARY, "byte[]");//
        typeMap.put(Types.LONGVARBINARY, "byte[]");//
        typeMap.put(Types.DATE, "java.util.Date");//import java.sql.Date;
        typeMap.put(Types.TIME, "java.sql.Time");//import java.sql.Time;
        typeMap.put(Types.TIMESTAMP, "java.sql.Timestamp");//import java.sql.Timestamp;
        typeMap.put(Types.JAVA_OBJECT, "java.lang.Object");//
        typeMap.put(Types.BLOB, "byte[]");//
        typeMap.put(Types.CLOB, "java.lang.String");//
        typeMap.put(Types.OTHER, "java.lang.Object");//
        typeMap.put(Types.BOOLEAN, "java.lang.Boolean");
    }

    /**
     * 根据java.sql.Type中的类型，获取相应的java类型
     *
     * @param sqlType
     * @return
     */
    public static String getJavaType(int sqlType) {
        String javaType = (String) typeMap.get(sqlType);
        return javaType;
    }

}
