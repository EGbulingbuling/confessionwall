package org.eg.confessionwall.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadUtil {
    /**
     * 上传文件
     * @param file 文件
     * @param path 路径 一定要在结尾加“/”
     * @return 文件url
     */
    public static String uploadSimpleFile(MultipartFile file,String path) throws IOException {
        String filename = file.getOriginalFilename();
        String[] names = filename.split("\\.");
        String tempNum = (int) (Math.random() * 100000) + "";
        String uploadFileName = tempNum + System.currentTimeMillis() + "." + names[names.length - 1];
        return uploadFile(file,path,uploadFileName);
    }

    /**
     * 上传文件
     * @param file 文件
     * @param path 路径
     * @param fileName 文件名
     * @return
     */
    public static String uploadFile(MultipartFile file,String path,String fileName) throws IOException {
        File targetFile = new File("/usr/local/server/"+path,fileName);
        file.transferTo(targetFile);
        String fileUrl="http://39.96.93.83:80/"+path+fileName;

//        File targetFile = new File("D:/server/"+path,fileName);
//        file.transferTo(targetFile);
//        String fileUrl="http://localhost:8080/"+path+fileName;

        return fileUrl;
    }
}
