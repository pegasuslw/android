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
package com.tcl.common.mediaplayer.aidl;
import android.os.Parcel;
import android.os.Parcelable;

public class MediaBean implements Parcelable {
	
	public String mName;//显示的名字
	public String mPath;//播放路径
	public String mURLType;//需要forTv进行播放的可以设为“VOD”;
	public int mIsSaved;//是否已经收藏 0:no,1:yes
	public String mLycPath;//歌词地址；
	public int mVodType;//在线类型；1为在线影视，２为在线教育，其他值为不存在；用于收藏操作；	

	/******************************for xiangling ******************************************************/
    public String mMusicPosterImagePath;//专辑封面
    public String mSongsterName;//歌手名字
    public String mPoster; //所属专辑
    public String mGenre;//流派
    public long mMediaId;//本地电影海报索引

	
	
    public static final Parcelable.Creator < MediaBean > CREATOR = new Creator < MediaBean > () {

        public MediaBean createFromParcel(Parcel source) {
            MediaBean bean = new MediaBean();
            bean.mName = source.readString();
            bean.mPath = source.readString();
            bean.mURLType = source.readString();
            bean.mIsSaved = source.readInt();
            bean.mLycPath = source.readString();
            bean.mVodType = source.readInt();
            
            
            bean.mMusicPosterImagePath = source.readString();
            bean.mSongsterName = source.readString();
            bean.mPoster = source.readString();
            bean.mGenre = source.readString();
            bean.mMediaId = source.readLong();
            
            return bean;
        }

        public MediaBean[] newArray( int size) {
            return new MediaBean[size];
        }

    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( this.mName);
        dest.writeString( this.mPath);
        dest.writeString(this.mURLType);
        dest.writeInt(this.mIsSaved);
        dest.writeString(this.mLycPath);
        dest.writeInt(this.mVodType);
        
        dest.writeString(this.mMusicPosterImagePath);
        dest.writeString(this.mSongsterName);
        dest.writeString(this.mPoster);
        dest.writeString(this.mGenre);
        dest.writeLong(this.mMediaId);
    }

    public int describeContents() {
        return 0 ;
    }

} 

