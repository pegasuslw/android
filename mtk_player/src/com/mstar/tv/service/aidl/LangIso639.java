package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class LangIso639 implements Parcelable{

	private static final int E_ISO_LANG_MAX_LENGTH = 3;
    public char[] isoLangInfo = new char[E_ISO_LANG_MAX_LENGTH];
    public short audioMode;
    public short audioType;
    public boolean isValid;

    public LangIso639()
    {
        audioMode = 0;
        audioType = 0;
        isValid = false;
        for (int i = 0; i < E_ISO_LANG_MAX_LENGTH; i++)
        {
            isoLangInfo[i] = 0;
        }
    }
    
    private LangIso639(Parcel in)
    {
    	in.readCharArray(isoLangInfo);
        audioMode = (short) in.readInt();
        audioType = (short) in.readInt();
        isValid = in.readInt() == 0? false : true;
    }
    
    public static final Parcelable.Creator<LangIso639> CREATOR = new Parcelable.Creator<LangIso639>()
    		{
    			public LangIso639 createFromParcel(Parcel in)
    			{
    				return new LangIso639(in);
    			}

    			public LangIso639[] newArray(int size)
    			{
    				return new LangIso639[size];
    			}
    		};
	
    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeCharArray(isoLangInfo);
		dest.writeInt(audioMode);
		dest.writeInt(audioType);
		dest.writeInt(isValid?1 : 0);
	}

}
