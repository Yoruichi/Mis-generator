# MIS Generator

## 使用说明
    usage :
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

## 从源码编译
   1. 获取源码

    git clone

   2. 编译

    mvn clean package -DskipTests

   3. 解压目标文件

    cd target
    tar -zxvf mis-0.0.1-bundle.tar.gz

   4. 执行bin目录下的Mis-Generator

    cd Mis-Generator
    ./bin/Mis-Generator

## 下载可执行文件
   点击[这里](https://github.com/Yoruichi/Mis-generator/blob/master/mis-generator-0.0.1-bundle.tar.gz)下载

## MIS
   详情参见[这里](/Yoruichi/MIS "MIS")
     
