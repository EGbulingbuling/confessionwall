package org.eg.confessionwall.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageUtil {
    @Getter
    private int totalCount;//总数
    @Setter
    @Getter
    private int pageSize;//每页显示数量
    @Setter
    @Getter
    private int currpageNum;//当前页
    @Setter
    @Getter
    private int pageCount;//总页数
    @Setter
    @Getter
    private int prePage;//上一页
    @Setter
    @Getter
    private int nextPage;//下一页
    @Setter
    @Getter
    private boolean hasPrePage;//是否有上一页
    @Setter
    @Getter
    private boolean hasNextPage;//是否有下一页
    @Setter
    @Getter
    private int firstPage;//第一页
    @Setter
    @Getter
    private int lastPage;//最后一页
    @Setter
    @Getter
    private int currentcount;//当前从第多少条数据开始显示

    public PageUtil() {
            
    }
    public PageUtil(int currpageNum,int pageSize){
        this.currpageNum=currpageNum;
        this.pageSize=pageSize;
        currentcount =(this.currpageNum-1)*pageSize;
    }

    public void setTotalCount(int totalCount) {
        pageCount = (int) Math.ceil(1.0*totalCount/pageSize);
        this.totalCount = totalCount;
        firstPage=1;
        lastPage=pageCount;
        if(currpageNum>1){  //判断是不是第一页
            /*--不是第一页 则有上一页 ，也有第一页--*/
            hasPrePage=true;
            prePage = currpageNum-1;
        }else {
            hasPrePage=false;
            prePage = currpageNum-1;
        }
        if(currpageNum<pageCount){//判断是不是最后一页
            /*--不是最后一页 则有上一页 ，也有最后一页--*/
            hasNextPage=true;
            nextPage=currpageNum+1;
        }else {
            hasNextPage=false;
            nextPage=currpageNum+1;
        }
    }

    public void nextPage(){
        if(currpageNum < pageCount){
            currpageNum++;
            currentcount =(currpageNum-1)*pageSize;
            prePage++;
            hasPrePage=true;
        }
        if(currpageNum<pageCount){//判断是不是最后一页
            /*--不是最后一页 则有上一页 ，也有最后一页--*/
            hasNextPage=true;
            nextPage=currpageNum+1;
        }else {
            hasNextPage=false;
            nextPage=currpageNum+1;
        }
    }
}