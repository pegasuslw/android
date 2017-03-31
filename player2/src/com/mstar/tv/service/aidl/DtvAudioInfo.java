package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class DtvAudioInfo implements Parcelable{

	public short audioLangNum;
    public AudioInfo[] audioInfos = new AudioInfo[16];
    public short currentAudioIndex;

    public DtvAudioInfo()
    {
        audioLangNum = 0;
        currentAudioIndex = 0;
        for (int i = 0; i < audioInfos.length; i++)
        {
            audioInfos[i] = new AudioInfo();
        }
    }
    
    public DtvAudioInfo(Parcel in)
    {
        audioLangNum = (short) in.readInt();
        for (int i = 0; i < /*audioInfos.length*/audioLangNum; i++)
        {
            audioInfos[i] = AudioInfo.CREATOR.createFromParcel(in);
        }
        currentAudioIndex = (short) in.readInt();
    }
	 public static final Parcelable.Creator<DtvAudioInfo> CREATOR = new Parcelable.Creator<DtvAudioInfo>()
	    		{
	    			public DtvAudioInfo createFromParcel(Parcel in)
	    			{
	    				return new DtvAudioInfo(in);
	    			}

	    			public DtvAudioInfo[] newArray(int size)
	    			{
	    				return new DtvAudioInfo[size];
	    			}
	    		};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(audioLangNum);
	//	dest.writeParcelableArray(audioInfos, 0);
	for(int i = 0;i<audioLangNum;i++){audioInfos[i].writeToParcel( dest,0);}
		dest.writeInt(currentAudioIndex);
		
	}

}
