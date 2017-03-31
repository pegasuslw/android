package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class RfInfo implements Parcelable{

	

    /**
     * RF signal number
     */
    public short rfPhyNum;
    /**
     * RF signal frequency
     */
    public int frequency;
    /**
     * RF signal VHF or not
     */
    public boolean isVHF;
    /**
     * RF signal name
     */
    public String rfName;

    public RfInfo()
    {
        rfPhyNum = 0;
        frequency = 0;
        isVHF = false;
        rfName = "";
    }
    private RfInfo(Parcel in)
    {
        rfPhyNum = (short) in.readInt();
        frequency = in.readInt();
        isVHF = in.readInt() == 0 ? false : true;
        rfName = in.readString();
    }
    public static final Parcelable.Creator<RfInfo> CREATOR = new Parcelable.Creator<RfInfo>()
    		{
    			public RfInfo createFromParcel(Parcel in)
    			{
    				return new RfInfo(in);
    			}

    			public RfInfo[] newArray(int size)
    			{
    				return new RfInfo[size];
    			}
    		};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(rfPhyNum);
		dest.writeInt(frequency);
		dest.writeInt(isVHF? 1 : 0);
		dest.writeString(rfName);
	}

}
