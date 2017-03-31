package com.mstar.tv.service.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class MenuSubtitleService implements Parcelable{
	// / the subtitle language
    public int eLanguage;
    // / the subtitle type
    public int enSubtitleType; // one of DtvType.E_SUBTITLING_TYPE_
    // / how many subtitle services it refers
    public short refCount;
    // / the ISO 639 subtitle string code
    public char[] stringCodes = new char[4];
    public static final Parcelable.Creator<MenuSubtitleService> CREATOR = new Parcelable.Creator<MenuSubtitleService>()
    		{
    			public MenuSubtitleService createFromParcel(Parcel in)
    			{
    				return new MenuSubtitleService(in);
    			}

    			public MenuSubtitleService[] newArray(int size)
    			{
    				return new MenuSubtitleService[size];
    			}
    		};
    private MenuSubtitleService(Parcel in)
    		{
    			this.eLanguage = in.readInt();;
    			this.enSubtitleType = in.readInt();
    			this.refCount = (short)in.readInt();
    			this.stringCodes = in.readString().toCharArray();
    		}  
    public MenuSubtitleService(int eLanguage,int enSubtitleType,short refCount,char[] stringCodes)
     {
        this.eLanguage = eLanguage;
        this.enSubtitleType = enSubtitleType;
        this.refCount = 0;
        this.stringCodes = stringCodes;
    }

    public EN_MS_LANGUAGE getLanguage()
    {
        return EN_MS_LANGUAGE.values()[eLanguage];
    }

    public void setLanguage(EN_MS_LANGUAGE language)
    {
        eLanguage = language.ordinal();
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.eLanguage);
		dest.writeInt(this.enSubtitleType);
		dest.writeInt(this.refCount);
		dest.writeCharArray(this.stringCodes);
	}

}
