package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/** @hide */
public class AudioInfo implements Parcelable{

	public LangIso639 isoLangInfo; // :30 ///< ISO Language Info
    public int audioPid; // :13 ///< Audio PID
    public short audioType; // : 3 ///< 0x01: MPEG, 0x02: AC-3, 0x03: MPEG4_AUD
    // WORD Reserved : 1; ///< Reserved
    public boolean broadcastMixAd;
    // /AAC component type
    public short aacType;
    // /AAC profile and level
    public short aacProfileAndLevel;

    public AudioInfo()
    {
        isoLangInfo = new LangIso639();
        audioPid = 0;
        audioType = 0;
        broadcastMixAd = false;
        aacType = 0;
        aacProfileAndLevel = 0;
    }
    private AudioInfo(Parcel in)
    {
        isoLangInfo = LangIso639.CREATOR.createFromParcel(in);
        audioPid = in.readInt();
        audioType = (short)in.readInt();
        broadcastMixAd = in.readInt() == 0 ? false : true;
        aacType = (short) in.readInt();
        aacProfileAndLevel = (short) in.readInt();
    }
    
    public static final Parcelable.Creator<AudioInfo> CREATOR = new Parcelable.Creator<AudioInfo>()
    		{
    			public AudioInfo createFromParcel(Parcel in)
    			{
    				return new AudioInfo(in);
    			}

    			public AudioInfo[] newArray(int size)
    			{
    				return new AudioInfo[size];
    			}
    		};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		isoLangInfo.writeToParcel(dest, 0);
		dest.writeInt(audioPid);
		dest.writeInt(audioType);
		dest.writeInt(broadcastMixAd? 1 : 0);
		dest.writeInt(aacType);
		dest.writeInt(aacProfileAndLevel);
	}

}
