package me.yoruichi.mis.generator.util;

import java.sql.*;

/**
 * JDBC实用工具类
 */
public class JdbcUtil {
    private static Connection connection;

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String url, String username, String password)
            throws Exception {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    /**
     * 执行sql查询，返回字符串类型的结果
     *
     * @param sql
     * @return
     */
    private static String execQuery(Connection connection, String sql) throws Exception {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = connection.createStatement();
            rs = s.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } finally {
            if (s != null)
                s.close();
            if (rs != null) rs.close();
        }
    }

    public static String getTableCount(Connection connection, String table) throws Exception {
        String result = "0";
        try {
            String sql = "select count(distinct name) " +
                    "from " + table + " where tel is not null or phone is not null";
            result = execQuery(connection, sql);
        } catch (Exception e) {
            throw new Exception("错误结构的表：" + table, e);
        }
        return result;
    }

}
