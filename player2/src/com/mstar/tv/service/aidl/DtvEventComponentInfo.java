package com.mstar.tv.service.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class DtvEventComponentInfo implements Parcelable{

	 // / Video type
    private int videoType;
    // / Service MHEG5 or not
    public boolean mheg5Service;
    // / Service Subtitle or not
    public boolean subtitleService;
    // / Service TTX or not
    public boolean teletextService;
    // / Service CC or not
    public boolean ccService;
    // / Service HD or not
    private int enHd;
    // / Service AD or not
    public boolean isAd;
    // / Service audio track number
    public short audioTrackNum;
    // / Service subtitle number
    public short subtitleNum;
    // / Service aspec ratio
    private int enAspectRatio;
    // / Service genre
    private int enGenreType;
    // / Service parental rating
    public short parentalRating;

    public DtvEventComponentInfo()
    {
        videoType = 0;
        mheg5Service = false;
        subtitleService = false;
        teletextService = false;
        ccService = false;
        enHd = 0;
        isAd = false;
        audioTrackNum = 0;
        subtitleNum = 0;
        enAspectRatio = 0;
        enGenreType = 0;
        parentalRating = 0;

    }

    public DtvEventComponentInfo(Parcel in) {
    	videoType = in.readInt();
        mheg5Service = in.readInt() == 1? true : false;
        subtitleService = in.readInt() == 1? true : false;
        teletextService = in.readInt() == 1? true : false;
        ccService = in.readInt() == 1? true : false;
        enHd = in.readInt();
        isAd = in.readInt() == 1? true : false;
        audioTrackNum = (short) in.readInt();
        subtitleNum = (short) in.readInt();
        enAspectRatio = in.readInt();
        enGenreType = in.readInt();
        parentalRating = (short) in.readInt();
	}

	public int getVideoType()
    {
        return this.videoType;
    }

    public void setVideoType(int videoType)
    {
        this.videoType = videoType;
    }

    public int getDtvVideoQuality()
    {
        return this.enHd;
    }

    public void setDtvVideoQuality(int videQuality)
    {
        this.enHd = videQuality;
    }

    public int getAspectRatioCode()
    {
        return this.enAspectRatio;
    }

    public void setAspectRatioCode(int aspectRatio)
    {
        this.enAspectRatio = aspectRatio;
    }

    public int getGenreType()
    {
        return -1;
    }

    public void setGenreType(int genreType)
    {
        this.enGenreType = genreType;
    }
	
    public static final Parcelable.Creator<DtvEventComponentInfo> CREATOR = new Parcelable.Creator<DtvEventComponentInfo>()

    		{

    			public DtvEventComponentInfo createFromParcel(Parcel in)

    			{

    				return new DtvEventComponentInfo(in);

    			}



    			public DtvEventComponentInfo[] newArray(int size)

    			{

    				return new DtvEventComponentInfo[size];

    			}

    		};
    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(videoType); 
		dest.writeInt(mheg5Service?1:0);
		dest.writeInt(subtitleService?1:0); 
		dest.writeInt(teletextService?1:0);
		dest.writeInt(ccService?1:0);
		dest.writeInt(enHd);
		dest.writeInt(isAd?1:0);
		dest.writeInt(audioTrackNum);
		dest.writeInt(subtitleNum);
		dest.writeInt(enAspectRatio);
		dest.writeInt(enGenreType);
		dest.writeInt(parentalRating);
	}
	
	

}
