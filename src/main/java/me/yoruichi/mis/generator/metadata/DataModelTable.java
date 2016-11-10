package me.yoruichi.mis.generator.metadata;

import me.yoruichi.mis.generator.util.StringUtil;

import java.util.*;

/**
 * 数据库表的数据模型
 */
public class DataModelTable {
    private String tableNameLowerCase;//数据库中的表名称，包含前缀，并且全小写，add sides template
    private String sqlName;//数据库中的表名称
    private String sqlType;//数据库中的表类型，table或view
    private String sqlComment;//数据库中的表注释

    /**
     * 1.对表名称的注释
     *
     * @return
     */
    public String getSqlComment() {//这里需要处理，否则数据库中没有注释，返回null，在进行模板操作的时候，可能会出现空指针异常
        //去除字符串中的空格,回车,换行符,制表符,注释需要在一行显示，否则在生成的目标文件时，会自动换行，出现错误
        if ("".equals(sqlComment) || null == sqlComment) {
//			System.err.println("警告："+sqlName+",没有注释信息！！！");
        }
        return ("".equals(sqlComment) || null == sqlComment) ?
                "no comment" :
                sqlComment.replaceAll("\\s*|\t|\r|\n", "");
    }

    private List dataModelColumns = new ArrayList();//该表对应的字段集合

    private String finalName;//最终的表名
    private String finalComment;//最终的表注释

    private Set<String> importTypes;//import的java类型

    /**
     * 2.根据该表中的字段类型，获取在Java代码中，需要导入的基本类型代码
     *
     * @return
     */
    public Set<String> getImportTypes() {//返回Set，因为其内容不重复
        Set<String> imports = new HashSet<String>();
        for (int i = 0; i < dataModelColumns.size(); i++) {
            DataModelColumn dm = (DataModelColumn) dataModelColumns.get(i);
            if ("BigDecimal".equals(dm.getJavaType())) {
                imports.add("import java.math.BigDecimal;");
            }
            if ("Date".equals(dm.getJavaType())) {
                imports.add("import java.util.Date;");
            }
            if ("Time".equals(dm.getJavaType())) {
                imports.add("import java.sql.Time;");
            }
            if ("Timestamp".equals(dm.getJavaType())) {
                imports.add("import java.sql.Timestamp;");
            }
        }
        return imports;
    }

    private String tablePrefix;

    /**
     * 3.获取表前缀，这里的表前缀的含义是，表xx_yy，对应的前缀是xx
     *
     * @return
     */
    public String getTablePrefix() {
        String tableName = this.getSqlName();
        if (null != tableName) {
            String[] table = tableName.split("_");
            tablePrefix = table[0];
            return table[0];
        } else {
            return "";
        }
    }

    /**
     * 4.数据库中的表名称，包含前缀，并且全小写
     * add sides template
     *
     * @return
     */
    public String getTableNameLowerCase() {
        return sqlName.toLowerCase();
    }

    /**
     * 5.数据库中的表名称，包含前缀，并且全大写
     * add sides template
     *
     * @return
     */
    public String getTableNameUpperCase() {
        return sqlName.toUpperCase();
    }

    private String className;//生成的类名称    例如：DataDictionary

    private String basePackagePath;
    private String basePackage;
    private String classDefinationName;//类定义的名称    例如：dataDictionary

    public String getClassDefinationName() {//将表名称转换为小写，去掉表的前缀（前缀必须小写），并且以_分隔，然后将分隔后的，第一个首字母小写，第二个起的字符串，首字母大写
        sqlName = sqlName.toLowerCase();
        String sqlNameNoPrefix = "";
        String[] splitName = sqlName.split("_");
        String finalClassName = splitName[0];
        for (int i = 1; i < splitName.length; i++) {//这里i从1开始遍历
            finalClassName += StringUtil.firstUpper(splitName[i]);
        }
        return finalClassName;
    }

    public String getClassName() {
        return StringUtil.firstUpper(getClassDefinationName());
    }

    public String getClassNameUpperAll() {//类名全部大写 DATADICTIONARY
        return getClassDefinationName().toUpperCase();
    }


    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public void setSqlComment(String sqlComment) {
        this.sqlComment = sqlComment;
    }

    public String getFinalName() {
        return sqlName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }

    public String getFinalComment() {
        return sqlComment;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List getDataModelColumns() {
        return dataModelColumns;
    }

    public void setDataModelColumns(List dataModelColumns) {
        this.dataModelColumns = dataModelColumns;
    }

    public String getBasePackagePath() {
        return this.basePackage.replaceAll("\\.", "/");
    }

    public String getBasePackage() {
        return basePackage;
    }

    public DataModelTable setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }
}
