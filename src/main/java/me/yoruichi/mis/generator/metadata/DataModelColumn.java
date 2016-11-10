package me.yoruichi.mis.generator.metadata;

import com.google.common.base.Strings;
import me.yoruichi.mis.generator.util.StringUtil;
import me.yoruichi.mis.generator.util.TypeConvertUtil;

/**
 * 数据库列的数据模型
 */
public class DataModelColumn {
    private String sqlName;//列名称    例如：user_flag
    private String sqlTypeName;//列类型名称
    private int sqlTypeFlag;//列类型标识
    private String sqlLength;//列长度
    private boolean sqlPk;//是否为主键
    private boolean sqlBlank;//是否为空
    private String sqlComment;//列注释

    public String getSqlComment() {
//		System.out.println(sqlName+"的注释是:"+sqlComment);
        //去除字符串中的空格,回车,换行符,制表符,注释需要在一行显示，否则在生成的目标文件时，会自动换行，出现错误
        return ("".equals(sqlComment) || null == sqlComment) ?
                "no comment" :
                sqlComment.replaceAll("\\s*|\t|\r|\n", "");
    }

    public String getJspTitle() throws Exception {
        String jspTitle = getSqlComment();
        if ("no comment".equals(jspTitle)) {//如果没有注释，就使用属性名称
            jspTitle = getFieldName();
        } else {//如果有注释，应截取一定长度
            if (jspTitle.length() > 5) {
                jspTitle = jspTitle.substring(0, 4);
            }
        }
        return jspTitle;
    }

    public String getSqlNameUpper() {
        return this.getSqlName().toUpperCase();
    }

    private String sqlDefaultValue;//列的默认值

    private String javaType;//该字段的数据库类型对应的java类型   java.lang.String
    private String javaTypeAll;
    private String fieldName;//该字段对应的java中的field，首字母小写的驼峰形式  例如：userFlag
    private String operName;//该字段对应的java中的setter和getter等操作的名称，首字母大写的驼峰形式   例如：UserFlag

    public String getJavaTypeAll() {
//		System.out.println("getJavaType:"+getFieldName()+":"+sqlName+":"+sqlTypeFlag);
        String javaType = TypeConvertUtil.getJavaType(sqlTypeFlag);
        //如果数据库定义的number,默认转换为Long
        //这里添加一个处理，如果number的长度小于10（主键的长度设置为10），就转换成Integer
        if (javaType.indexOf("java.lang.Long") == 0) {
            if (Integer.valueOf(sqlLength) < 10) {
                javaType = "java.lang.Integer";
            }
        }
        return javaType;
    }

    /**
     * 获取数据库字段类型对应的java类型，并且类型中去掉包名称，
     * 例如：java.lang.Integer,这里返回Integer
     * 主要应用在java类中的属性定义的时候
     *
     * @return： <br>date：2013-9-25 上午8:44:07
     * <br>version：V1.0.0
     * <br>ModifyRecord：
     */
    public String getJavaType() {
//		System.out.println("getJavaType:"+getFieldName()+":"+sqlName+":"+sqlTypeFlag);
        String javaType = TypeConvertUtil.getJavaType(sqlTypeFlag);
        //如果数据库定义的number,默认转换为Long
        //这里添加一个处理，如果number的长度小于10（主键的长度设置为10），就转换成Integer
        if (javaType.indexOf("java.lang.Long") == 0) {
            //System.err.println(sqlName+"的类型"+sqlTypeName+" 长度"+sqlLength);
            if (Integer.valueOf(sqlLength) < 10) {
                javaType = "java.lang.Integer";
            }
        }

        if (javaType.indexOf("java.lang") == 0) {//去掉java类型中的java.lang
            javaType = javaType.substring(10, javaType.length());
        }
        if (javaType.indexOf("java.math") == 0) {//去掉java类型中的java.math
            javaType = javaType.substring(10, javaType.length());
        }
        if (javaType.indexOf("java.util") == 0) {//去掉java类型中的java.util
            javaType = javaType.substring(10, javaType.length());
        }
        if (javaType.indexOf("java.sql") == 0) {//去掉java类型中的java.sql
            javaType = javaType.substring(9, javaType.length());
        }
        return javaType;
    }


    public String getFieldName()
            throws Exception {//将字段名称转换为小写，并且以_分隔，然后将分隔后的，第一个首字母小写，第二个起的字符串，首字母大写
        String sqlName = this.getSqlName().toLowerCase();
//		System.out.println("XXXXXXXXXXXXX:"+sqlName);
        String[] splitName = sqlName.split("_");//如果字段名称中，有两个连续的下划线，这里会出现异常
        String finalFieldName = splitName[0];
        for (int i = 1; i < splitName.length; i++) {//这里i从1开始遍历
            if (Strings.isNullOrEmpty(splitName[i]))
                throw new Exception("Are you crazy ?You named a column like '" + sqlName + "' ");
            finalFieldName += StringUtil.firstUpper(splitName[i]);
        }
        return finalFieldName;
    }

    public String getOperName() throws Exception {
        return StringUtil.firstUpper(getFieldName());
    }



    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getSqlLength() {
        return sqlLength;
    }

    public void setSqlLength(String sqlLength) {
        this.sqlLength = sqlLength;
    }

    public boolean isSqlPk() {
        return sqlPk;
    }

    public void setSqlPk(boolean sqlPk) {
        this.sqlPk = sqlPk;
    }

    public boolean isSqlBlank() {
        return sqlBlank;
    }

    public void setSqlBlank(boolean sqlBlank) {
        this.sqlBlank = sqlBlank;
    }

    public void setSqlComment(String sqlComment) {
        this.sqlComment = sqlComment;
    }

    public String getSqlDefaultValue() {
        return sqlDefaultValue;
    }

    public void setSqlDefaultValue(String sqlDefaultValue) {
        this.sqlDefaultValue = sqlDefaultValue;
    }

    public String getSqlTypeName() {
        return sqlTypeName;
    }

    public void setSqlTypeName(String sqlTypeName) {
        this.sqlTypeName = sqlTypeName;
    }

    public int getSqlTypeFlag() {
        return sqlTypeFlag;
    }

    public void setSqlTypeFlag(int sqlTypeFlag) {
        this.sqlTypeFlag = sqlTypeFlag;
    }


}
