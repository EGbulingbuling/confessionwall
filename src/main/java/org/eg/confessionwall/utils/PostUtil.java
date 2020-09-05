package org.eg.confessionwall.utils;

import org.eg.confessionwall.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostUtil {
    /**
     * 处理content内容
     * 找出帖子内容里所有的<img>，并取出所有url中的文件名，并替换帖子内容里<img>的url
     * @param post
     * @param content
     * @return
     */
    public static List<String> setContent(Post post, String content) {
        List<String> FileNameList=getFileName(content);
        String contentReplacement=replaceImgUrl(content);
        post.setPostContent(contentReplacement);
        return FileNameList;
    }

    /**
     * 截取content的摘要
     * 将content的内容选取前  个字符截取出来作为摘要
     * 如果包含图片则将图片改为 [图片]
     * @param post
     * @param content
     */
    public static void setAbstract(Post post, String content,String contentText) {
        Pattern p=Pattern.compile("(<img.*?src=[\"|\'].*?[\"|\']?\\s.*?>)");
        Matcher m=p.matcher(content);
        String abstractStr="";
        while(m.find()){
            abstractStr+="[图片]";
        }
        if (contentText.length()>36){
            abstractStr+=contentText.substring(0,36);
        }else {
            abstractStr+=contentText;
        }
        post.setPostAbstract(abstractStr);
    }

    /**
     * 找出帖子内容里所有的<img>，并取出所有url中的文件名
     * @param content
     * @return
     */
    public static List<String> getFileName(String content) {
        List<String> fileNameList=new ArrayList<>();
        Pattern p=Pattern.compile("<img.*?src=[\"|\'](.*?)[\"|\']?\\s.*?>");
        Matcher m=p.matcher(content);
        while (m.find()){
            String[] names=m.group(1).split("/");
            fileNameList.add(names[names.length-1]);
        }
        return fileNameList;
    }

    /**
     * 替换帖子内容里<img>的url
     * 将存放在缓存里的图片地址替换为正式存储图片的地址
     * @param content
     * @return
     */
    public static String replaceImgUrl(String content) {
        StringBuffer sb=new StringBuffer();
        Pattern p=Pattern.compile("<img.*?src=[\"|\'](.*?)[\"|\']?\\s.*?>");
        Matcher m=p.matcher(content);
        while (m.find()){
            String[] names=m.group(1).split("/");
            m.appendReplacement(sb, "<img src=\"http://39.96.93.83:80/img/"+names[names.length-1]+"\" style=\"max-width: 100%;\">");
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
