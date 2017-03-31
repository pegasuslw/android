/**
 * ------------------------------------------------------------------
 *  Copyright (c) 2011 TCL, All Rights Reserved.
 *  -----------------------------------------------------------------
 *  
 * @author bailing /qiaobl@tcl.com/2011.09.21
 * @JDK version: 1.6
 * @brief: provide the play and control of video and audio;
 * @version:v1.0
 */
package com.tcl.common.mediaplayer.lyric;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author hadeslee
 */
/**
 * This class implements item for playlist.
 */
public class PlayListItem implements Serializable {

    private static final long serialVersionUID = 20071213L;
    //private static Logger log = Logger.getLogger(PlayListItem.class.getName());
    protected String name = "";
    protected String displayName = "";
    protected String location = "";
    protected boolean isFile = true;
    protected long seconds = -1;
    protected boolean isSelected = false; // 
    private String track;
    private String genre;
    private boolean isRead;//是否读过标签了,免得每次都去读
    private File lyricFile;//这个项目所关联的歌词文件
    protected static ExecutorService es = Executors.newSingleThreadExecutor();
    private int offset;//存在这里的歌曲偏移量，以保存下来，但是又不需要去写LRC文件

    protected PlayListItem() {
    }

    /**
     * 是否初始化过了，如果没有初始化过的话，则
     * 用它来搜歌词是会出问题的
     * @return
     */
    public boolean isInited() {
        return isRead;
    }

    /**
     * Contructor for playlist item.
     *
     * @param name     Song name to be displayed
     * @param location File or URL
     * @param seconds  Time length
     * @param isFile   true for File instance
     */
    public PlayListItem(String name, String location, long seconds, boolean isFile) {
        this.name = name;
        this.seconds = seconds;
        this.isFile = isFile;
        this.location = location;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public File getLyricFile() {
        return lyricFile;
    }

    public void setLyricFile(File lyricFile) {
        this.lyricFile = lyricFile;
    }


    public void setDuration(long sec) {
        seconds = sec;
    }



    
    /**
     * Returns item name such as (hh:mm:ss) Title - Artist if available.
     *
     * @return
     */
    public String getFormattedName() {
        if (displayName == null) {
            return name;
        } // Name extracted from TagInfo or stream title.
        else {
            return displayName;
        }
    }

 

    /**
     * Returns true if item to play is coming for a file.
     *
     * @return
     */
    public boolean isFile() {
        return isFile;
    }

    /**
     * Set File flag for playslit item.
     *
     * @param b
     */
    public void setFile(boolean b) {
        isFile = b;
    }

    /**
     * Returns playtime in seconds. If tag info is available then its playtime will be returned.
     *
     * @return playtime
     */
   


    public void setSelected(boolean mode) {
        isSelected = mode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Reads file comments/tags.
     *
     * @param l
     */
    public void setLocation(String l) {
        this.location = l;
    }



  
}
