package me.yoruichi.mis.generator.util;

import me.yoruichi.mis.generator.metadata.DataModelTable;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * 模板引擎相关的操作
 */
public class FreeMarker {
    /**
     * 获取模板引擎
     *
     * @return
     * @throws IOException
     */
    public static Configuration getFreeMarkerConfiguration(String encode, File templateFileRootDir)
            throws IOException {
        Configuration config = new Configuration();//1.创建freemarker的configuration

        //2.将模板文件根目录转换为freemarker的FileTemplateLoader类型
        FileTemplateLoader[] templateFileLoaders =
                new FileTemplateLoader[] {new FileTemplateLoader(templateFileRootDir)};

        MultiTemplateLoader multiTemplateLoader =
                new MultiTemplateLoader(templateFileLoaders);//多模板加载器

        //3.为freemarker的configuration对象设置参数
        config.setTemplateLoader(multiTemplateLoader);
        config.setDefaultEncoding(encode);//UTF-8

        return config;
    }

    /**
     * 根据数据模型和模板文件,来生成目标字符串
     *
     * @param dmTable
     * @param t
     * @return
     * @throws Exception
     */
    public static String generateString(DataModelTable dmTable, Template t) throws Exception {
        StringWriter out = new StringWriter();
        try {
            t.process(dmTable, out);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 临时，用于数据抓取的模板
     *
     * @param dmTable
     * @param t
     * @return
     * @throws Exception： <br>date：2013-6-27 上午11:02:33
     *                    <br>version：V1.0.0
     *                    <br>ModifyRecord：
     */
    public static String generateString(Map dmTable, Template t) throws Exception {
        StringWriter out = new StringWriter();
        try {
            t.process(dmTable, out);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据数据模型和模板文件，来生成目标文件
     *
     * @param dmTable
     * @param t
     * @param targetOutputFilePath
     * @throws Exception
     */
    public static void generateFile(DataModelTable dmTable, Template t, File targetOutputFilePath,
            String encode) throws Exception {
        Writer out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(targetOutputFilePath), encode));
        t.process(dmTable, out);
        out.close();
    }

    /**
     * 临时，用于数据抓取的模板
     *
     * @param dmTable
     * @param t
     * @param targetOutputFilePath
     * @throws Exception： <br>date：2013-6-27 上午11:03:01
     *                    <br>version：V1.0.0
     *                    <br>ModifyRecord：
     */
    public static void generateFile(Map dmTable, Template t, File targetOutputFilePath,
            String encode) throws Exception {
        Writer out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(targetOutputFilePath), encode));
        t.process(dmTable, out);
        out.close();
    }
}
