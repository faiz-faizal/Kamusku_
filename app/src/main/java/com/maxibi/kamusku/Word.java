package com.maxibi.kamusku;

/**
 * Created by User on 8/14/2017.
 */

public class Word {
    String bm, bi, bookmark;
    int id;

    public Word( String bm, String bi, int id, String bookmark)
    {
        this.bm = bm;
        this.bi = bi;
        this.id = id;
        this.bookmark = bookmark;
    }

    //getter
    public String getBm(){  return bm;}

    //setter
    public void setBm(String bm){   this.bm = bm;}

    public String getBi(){  return bi;}

    public void setBi(String bi){   this.bi = bi;}

    public String getBookmark() { return bookmark; }

    public void setBookmark(String bookmark){ this.bookmark = bookmark; }
}
