package org.eg.confessionwall.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {
    /**
     * 将传入的文件名转换为文件
     *
     * @param path
     * @param imgNameList
     * @return
     */
    public static List<File> name2file(String path, List<String> imgNameList) {
        List<File> imgList=new ArrayList<>();
        for (String imgName:imgNameList){
            File img=new File(path,imgName);
            imgList.add(img);
        }
        return imgList;
    }
}
