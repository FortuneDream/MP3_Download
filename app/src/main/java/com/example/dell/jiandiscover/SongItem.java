package com.example.dell.jiandiscover;

/**
 * Created by dell on 2016/2/27.
 */
public class SongItem {
    private String title;
    private String singer;
    private String url;

    public SongItem(String title, String singer,String url) {
        this.title = title;
        this.singer = singer;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }
    public String getSinger() {
        return singer;
    }
    public String getTitle() {
        return title;
    }

}
