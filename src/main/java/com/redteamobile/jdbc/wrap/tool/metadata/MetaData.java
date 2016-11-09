package com.redteamobile.jdbc.wrap.tool.metadata;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库元数据相关操作
 * 给数从数据库获取元数据，并给数据模型赋值
 */
public class MetaData {
    /**
     * 获取数据库中的所有表的表名称
     *
     * @return
     */
    public static List<String> getAllTable(Connection conn)
            throws Exception {
        List<String> tableNames = Lists.newLinkedList();
        ResultSet rs = null;
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();//获取数据库元数据[DatabaseMetaData]
            //Oracle:select table_name from user_tables
            rs = dbMetaData
                    .getTables(null, null, null, null);//这里需要注意，前两个参数如果没有值，则必须为null，如果为空字符串，将查不出数据
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } finally {
            rs.close();
        }

        return tableNames;
    }

    /**
     * 获取指定表名的数据模型,包括表名称和表字段等信息
     *
     * @param tableName
     * @return
     */
    public static DataModelTable getTable(Connection conn, String tableName) throws Exception {
        DataModelTable dmTable = new DataModelTable();//数据库表的数据模型

        ResultSet rs = null;
        ResultSet columnRs = null;
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();//获取数据库元数据[DatabaseMetaData]
//			System.out.println("数据库产品名称："+dbMetaData.getDatabaseProductName());
            //这里需要注意，前两个参数如果没有值，则必须为null，如果为空字符串，将查不出数据，对于Oracle，必须为大写
            rs = dbMetaData.getTables(null, null, tableName, null);
            while (rs.next()) {
                dmTable.setSqlName(rs.getString("TABLE_NAME"));//1.设置表名称
                dmTable.setSqlType(rs.getString("TABLE_TYPE"));//2.设置表类型
                dmTable.setSqlComment(rs.getString("REMARKS"));//3.设置表注释
            }

            Map primaryKeys = new HashMap();//一个表的所有主键集合
            //获取指定表的主键
            ResultSet primaryKeysRs = dbMetaData
                    .getPrimaryKeys(null, null, tableName);

            while (primaryKeysRs.next()) {
                primaryKeys.put(primaryKeysRs.getString("COLUMN_NAME"), "pk");
            }
            //获取指定表的列
            columnRs = dbMetaData
                    .getColumns(null, null, tableName, null);
            List columns = new ArrayList();//列集合
            while (columnRs.next()) {
                DataModelColumn dmColumn = new DataModelColumn();
                dmColumn.setSqlName(columnRs.getString("COLUMN_NAME"));
                dmColumn.setSqlTypeName(columnRs.getString("TYPE_NAME"));
                dmColumn.setSqlTypeFlag(columnRs.getInt("DATA_TYPE"));//SQL type from java.sql.Types
                dmColumn.setSqlLength(columnRs.getString("COLUMN_SIZE"));
                dmColumn.setSqlBlank("yes".equalsIgnoreCase(columnRs.getString("IS_NULLABLE")));
                dmColumn.setSqlComment(columnRs.getString("REMARKS"));
                if (null != primaryKeys) {
                    if ("pk".equals(primaryKeys.get(columnRs.getString("COLUMN_NAME")))) {
                        dmColumn.setSqlPk(true);//设置该数据库字段为主键
                    } else {
                        dmColumn.setSqlPk(false);
                    }
                } else {
                    System.err.println("警告：" + tableName + "，没有找到主键信息！！！");
                }
                columns.add(dmColumn);
            }
            dmTable.setDataModelColumns(columns);
        } finally {
            rs.close();
            columnRs.close();
        }

        return dmTable;
    }
}
