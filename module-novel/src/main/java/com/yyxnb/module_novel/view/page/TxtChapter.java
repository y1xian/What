package com.yyxnb.module_novel.view.page;

/**
 * Created by newbiechen on 17-7-1.
 */

public class TxtChapter{

    //章节所属的小说(网络)
    private String chapterId;
    //章节的链接(网络)
    private String link;

    //章节名(共用)
    private String title;

    //章节内容在文章中的起始位置(本地)
    private long start;
    //章节内容在文章中的终止位置(本地)
    private long end;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title/*.replaceAll("[#@$%&*()!.，,。？“”]+","")*/;
    }

    public void setTitle(String title) {
        this.title = title/*.replaceAll("[#@$%&*()!.，,。？“”]+","")*/;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "TxtChapter{" +
                "chapterId='" + chapterId + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
