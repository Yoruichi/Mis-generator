package com.redteamobile.jdbc.wrap.tool;

import com.google.common.base.Strings;
import com.redteamobile.jdbc.wrap.tool.metadata.DataModelTable;
import com.redteamobile.jdbc.wrap.tool.metadata.MetaData;
import com.redteamobile.jdbc.wrap.tool.util.FileUtil;
import com.redteamobile.jdbc.wrap.tool.util.FreeMarker;
import com.redteamobile.jdbc.wrap.tool.util.JdbcUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yoruichi on 16/11/9.
 */
public class Generator {

    /**
     * usage :
     * -d  driver name (only mysql jdbc driver[com.mysql.jdbc.Driver] now)
     * -h  mysql host default 0.0.0.0
     * -P  mysql port default 3306
     * -url db connection url with parameters
     * -u  mysql user name
     * -p  password
     * -b  db name
     * -t  table name(s) split with ','
     * -a  package name
     * -template  template file path default current path template
     * -target  target file path default current path gen-java
     * -encode  default UTF-8
     *
     * @param args
     */
    public static void main(String[] args) {
        String usage = "usage : \n"
                + " -d  driver name (only mysql jdbc driver[com.mysql.jdbc.Driver] now)\n"
                + " -h  mysql host default 0.0.0.0\n"
                + " -P  mysql port default 3306\n"
                + " -url  db connection url with parameters\n"
                + " -u  mysql user name\n"
                + " -p  password\n"
                + " -b  db name\n"
                + " -t  table name(s) split with ',' \n"
                + " -template  template file path default current path template\n"
                + " -target  target file path default current gen-java\n"
                + " -encode  default UTF-8\n"
                + " -a  package name";
        if (args.length < 0 || args.length % 2 == 1) {
            System.out.println(usage);
            System.exit(-1);
        }
        String driver = "com.mysql.jdbc.Driver";
        String host = "localhost";
        String port = "3306";
        String url = null;
        String username = "";
        String password = "";
        String db = null;
        String tables = null;
        String templatePath = "template/";
        String targetPath = "gen-java/";
        String packageName = "";
        String encode = "UTF-8";

        for (int i = 0; i < args.length; i = i + 2) {
            String value = args[i + 1];
            switch (args[i]) {
                case "-d":
                    driver = value;
                    break;
                case "-h":
                    host = value;
                    break;
                case "-P":
                    port = value;
                    break;
                case "-url":
                    url = value;
                    break;
                case "-u":
                    username = value;
                    break;
                case "-p":
                    password = value;
                    break;
                case "-b":
                    db = value;
                    break;
                case "-t":
                    tables = value;
                    break;
                case "-template":
                    templatePath = value.endsWith("/") ? value : value + "/";
                    break;
                case "-target":
                    targetPath = value.endsWith("/") ? value + "gen-java/" : value + "/gen-java/";
                    break;
                case "-encode":
                    encode = value;
                    break;
                case "-a":
                    packageName = value;
                    break;
            }
        }
        if (Strings.isNullOrEmpty(driver)
                || Strings.isNullOrEmpty(username)
                || Strings.isNullOrEmpty(password)
                || Strings.isNullOrEmpty(packageName)
                ) {
            System.out.println(usage);
            System.exit(-1);
        }
        if (url == null && db == null) {
            System.out.println(usage);
            System.exit(-1);
        }
        if (url == null && db != null) {
            url = "jdbc:mysql://" + host + ":" + port + "/" + db;
        }
        List<String> tableNames = null;
        Connection conn = null;
        try {
            Class.forName(driver);//要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段
            conn = JdbcUtil.getConnection(url, username, password);//获取数据库连接
            if (Strings.isNullOrEmpty(tables)) {
                tableNames = MetaData.getAllTable(conn);
            } else {
                tableNames = Arrays.asList(tables.split(","));
            }
            FileUtil.deleteDir(targetPath);//删除目标文件的目录,清理
            for (String tableName : tableNames) {
                System.out.println("一共" + tableNames.size() + "个表。正在处理表:" + tableName);
                DataModelTable dmTable = MetaData.getTable(conn, tableName);//1.获取数据模型对象
                dmTable.setBasePackage(packageName);
                Configuration conf = FreeMarker
                        .getFreeMarkerConfiguration(encode, new File(templatePath));//2.获取模板引擎
                //3.获取所有模板文件
                List templateFiles = new ArrayList();
                FileUtil.listFiles(new File(templatePath),
                        templateFiles);//将该模板文件路径下的模板文件存放到模板文件集合中（templateFiles）

                //4.处理，先生成文件路径，然后生成目标文件
                for (int j = 0; j < templateFiles.size(); j++) {//遍历模板文件
                    File templateFile = (File) templateFiles.get(j);
                    System.out.println("一共" + templateFiles.size() + "个模板。正在处理模板:" + templateFile
                            .getAbsolutePath());
                    String templateRelativePath =
                            FileUtil.getRelativePath(new File(templatePath), templateFile);
                    //对目标路径的处理，首先获取模板的相对路径，然后使用模板引擎，将该路径处理为真实路径，最后根据该真实路径+输出根路径即可
                    if (templateRelativePath.trim().equals(""))
                        continue;
                    String outRelativePath = templateRelativePath;//输出相对路径，就是输出文件的相对路径
                    String targetFilename = FreeMarker.generateString(dmTable,
                            new Template("targetFilePath", new StringReader(outRelativePath),
                                    conf));//根据数据模型和模板文件路径，来生成目标文件路径

                    File targetOutputFilePath =
                            FileUtil.getTargetOutputFilePath(targetPath,
                                    targetFilename);//根据目标文件名称获取绝对输出文件名称
                    System.out.println("目标文件路径" + targetOutputFilePath.getAbsolutePath());
                    System.out.println("目标文件相对路径:" + templateRelativePath);

                    //过滤模板文件：是目录或隐藏的跳过，相对路径是空的跳过，以.include结尾的跳过
                    if (templateFile.isDirectory() || templateFile.isHidden())
                        continue;
                    if (templateFile.getName().toLowerCase().endsWith(".include")) {
                        continue;
                    }
                    //上面被过滤的在配置引擎中取不到，所以需要过滤
                    Template t = conf.getTemplate(templateRelativePath);//获取模板文件对象，注意传入相对于模板根目录的路径文件
                    t.setEncoding(encode);
                    if (targetOutputFilePath.exists()) {//如果文件存在，说明是追加的情况
                        String appendContext = FreeMarker.generateString(dmTable, t);
                        Writer out = new FileWriter(targetOutputFilePath, true);//第二个参数设置为true，表示追加
                        out.write(appendContext);
                        out.close();
                        continue;
                    }

                    FreeMarker.generateFile(dmTable, t, targetOutputFilePath, encode);
                }
            }
        } catch (Exception e) {
            System.out.println(
                    "Something's wrong.Please check parameters and try again.Or check error message below.");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
