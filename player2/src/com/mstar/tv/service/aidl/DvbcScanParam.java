package com.mstar.tv.service.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class DvbcScanParam implements Parcelable{
	public short u16SymbolRate;
	public EN_CAB_CONSTEL_TYPE QAM_Type;
	public int u32NITFrequency;
	public static final Parcelable.Creator<DvbcScanParam> CREATOR = new Parcelable.Creator<DvbcScanParam>()
    		{
    			public DvbcScanParam createFromParcel(Parcel in)
    			{
    				return new DvbcScanParam(in);
    			}

    			public DvbcScanParam[] newArray(int size)
    			{
    				return new DvbcScanParam[size];
    			}
    		};
    private DvbcScanParam(Parcel in) 
    {  
    	u16SymbolRate = (short) in.readInt();   
    	QAM_Type = EN_CAB_CONSTEL_TYPE.values()[in.readInt()];
    	u32NITFrequency = in.readInt(); 
     }
   
	public DvbcScanParam() {
		super();
		u16SymbolRate = 0;   
    	QAM_Type = null;
    	u32NITFrequency = 0; 
		// TODO Auto-generated constructor stub
	}

	public DvbcScanParam(short u16SymbolRate, EN_CAB_CONSTEL_TYPE qAM_Type,
			int u32nitFrequency) {
		super();
		this.u16SymbolRate = u16SymbolRate;
		QAM_Type = qAM_Type;
		u32NITFrequency = u32nitFrequency;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(u16SymbolRate);
		dest.writeInt(QAM_Type.ordinal());
		dest.writeInt(u32NITFrequency);
	}

}
