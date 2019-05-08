package me.yoruichi.mis.generator.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件相关实用工具类
 */
public class FileUtil {
    /**
     * 复制文件或者目录
     *
     * @param resFilePath
     * @param distFolder
     * @throws IOException： <br>date：2013-9-23 下午2:57:26
     *                      <br>version：V1.0.0
     *                      <br>ModifyRecord：
     */
    public static void copyFile(String resFilePath, String distFolder) throws IOException {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            FileUtils.copyDirectoryToDirectory(resFile, distFile);
        } else if (resFile.isFile()) {
            FileUtils.copyFileToDirectory(resFile, distFile, true);
        }
    }

    /**
     * 获取相对于baseDir的文件路径,
     * 例如:
     * baseDir=d:\file
     * file=d:\file\a\b
     * 返回a\b
     */
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file))
            return "";
        if (baseDir.getParentFile() == null)
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
    }

    /**
     * 删除指定的目录，以及子目录
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(String dir) throws IOException {
        File deleteDir = new File(dir);
        if (deleteDir.exists()) {
            FileUtils.forceDelete(deleteDir);
        }

    }

    /**
     * 获取目标文件输出路径
     *
     * @param targetFilename
     * @return
     */
    public static File getTargetOutputFilePath(String targetDir, String targetFilename) {
        File outputFile = new File(targetDir, targetFilename);
        outputFile.getParentFile().mkdirs();
        return outputFile;
    }


    /**
     * 将file以及子目录下的所有模板文件存到List集合中
     *
     * @param file
     * @param collector
     * @throws IOException
     */
    public static void listFiles(File file, List collector) throws IOException {
        collector.add(file);
        if ((!file.isHidden() && file.isDirectory()) && !isIgnoreFile(file)) {//如果不是隐藏文件，并且是目录，并且不是被忽略的文件
            File[] subFiles = file.listFiles();//获取该目录下的所有文件
            for (int i = 0; i < subFiles.length; i++) {
                listFiles(subFiles[i], collector);
            }
        }
    }

    /**
     * 判断文件是否是忽略的文件
     *
     * @param file
     * @return
     */
    public static boolean isIgnoreFile(File file) {
        List<String> ignoreList = new ArrayList();
        ignoreList.add(".svn");
        ignoreList.add(".ignore");
        for (int i = 0; i < ignoreList.size(); i++) {
            if (file.getName().endsWith(ignoreList.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取两个对应目录中，重复的文件
     *
     * @param srcFile
     * @param desFile
     * @return
     * @throws IOException： <br>date：2013-9-23 下午4:01:40
     *                      <br>version：V1.0.0
     *                      <br>ModifyRecord：
     */
    public static List FileDuplicate(File srcFile, File desFile) throws IOException {
        List src = new ArrayList();
        List des = new ArrayList();
        List ret = new ArrayList();
        FileUtil.listFiles(srcFile, src);
        FileUtil.listFiles(desFile, des);
        for (int i = 0; i < src.size(); i++) {
            String srcFileName = FileUtil.getRelativePath(srcFile, (File) src.get(i));
            for (int j = 0; j < des.size(); j++) {
                String desFileName = FileUtil.getRelativePath(desFile, (File) des.get(j));
                if (srcFileName.equals(desFileName)) {
                    if (srcFileName.indexOf(".") != -1) {
                        ret.add(desFileName);
                    }
                }
            }
        }
        return ret;
    }

}
