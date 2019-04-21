package com.senti.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDeal {
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

    public List<String> getPresentJava(String filepath){
        String prefix=filepath+"\\";
        List<File> files = getFiles(filepath);
        List<String> paths=getJavaFilePath(files);
        List<String> res=new ArrayList<>();

        for(String path:paths) {
            res.add((path.replace(prefix, "")).replace("\\","/"));
        }
        return res;

    }

    public Map<String,List<String>> getJavaFileContent(String filepath){
        Map<String,List<String>> res=new HashMap<>();
        List<File> files = getFiles(filepath);
        List<String> paths = getJavaFilePath(files);

        List<String> lines=null;
        for(String path:paths){
            lines=getFileLines(path);
            path=path.replace(filepath, "").substring(1);
            res.put(path, lines);
        }

        return res;
    }

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
