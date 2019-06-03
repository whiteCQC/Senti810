package com.senti.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  本地文件的操作
 */
public class FileDeal {

    /**
     *
     * @param path
     * @return 获得该路径下的所有File
     */
    private List<File> getFiles(String path){
        List<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if(file.isDirectory()){
            File []files = file.listFiles();
            for(File fileIndex:files){
                if(fileIndex.isDirectory()){
                    fileList.addAll(getFiles(fileIndex.getPath()));
                }else
                    fileList.add(fileIndex);
            }
        }
        return fileList;
    }

    /**
     *
     * @param files
     * @return File取出FileList中的java的文件路径
     */
    private List<String> getJavaFilePath(List<File> files) {
        List<String> paths=new ArrayList<String>();
        for(int i=0;i<files.size();i++) {
            String path=files.get(i).getPath();
            if(path.endsWith(".java")){
                paths.add(path);
            }
        }

        return paths;
    }

    /**
     *
     * @param path
     * @return 读取指定路径的文件中的内容
     */
    private List<String> getFileLines(String path){
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
            String line = null;

            while ((line = br.readLine()) != null)
                lines.add(line);

            br.close();
        } catch (IOException e) {
            System.out.println("error happens when reading file");
        }
        return lines;
    }

    /**
     *
     * @param filepath
     * @return 获得当前的所有java文件的相对路径，路径中的分隔符为“/”
     */
    public List<String> getPresentJava(String filepath){
        String prefix=filepath+"/";
        List<File> files = getFiles(filepath);
        List<String> paths=getJavaFilePath(files);
        List<String> res=new ArrayList<>();

        for(String path:paths) {
            res.add(path.replace("\\","/").replace(prefix, ""));
        }
        return res;

    }

    /**
     *
     * @param filepath
     * @return 获得指定路径下的java文件的代码内容，key为java文件的相对路径，路径中的分隔符为“/”
     */
    public Map<String,List<String>> getJavaFileContent(String filepath){
        Map<String,List<String>> res=new HashMap<>();
        List<File> files = getFiles(filepath);
        List<String> paths = getJavaFilePath(files);

        List<String> lines=null;
        for(String path:paths){
            lines=getFileLines(path);
            path=path.replace("\\","/").replace(filepath, "").substring(1);//路径分隔符的转换
            res.put(path, lines);
        }

        return res;
    }

    /**
     *
     * 删除指定的文件，若是文件夹则整个删除
     */
    public void deleteDirectory(File file) {
        if (file.isFile()) {// 表示该文件不是文件夹
            file.delete();
        } else {
            // 首先得到当前的路径
            String[] childFilePaths = file.list();
            for (String childFilePath : childFilePaths) {
                File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);
                deleteDirectory(childFile);
            }
            file.delete();
        }
    }
}
