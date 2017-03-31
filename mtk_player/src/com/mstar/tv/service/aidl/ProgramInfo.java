package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgramInfo implements Parcelable
{
    // / program information of program index
    public int queryIndex;
    // / program information of program number
    public int number;
    // / Major Channnel Number,Range 1 and 99
    public short majorNum;
    // / Minor Channel Number,Range 0 to 999
    public short minorNum;
    // / Program ID in db
    public int progId;
    // / Program favorite
    public short favorite;
    // / Program lock
    public boolean isLock;
    // / Program skip
    public boolean isSkip;
    // / Program scramble
    public boolean isScramble;
    // / Program delete
    public boolean isDelete;
    // / Program visible
    public boolean isVisible;
    // / Program Hide
    public boolean isHide;
    // / Program service type
    public short serviceType;
    // / Program screen mute status
    public int screenMuteStatus;
    // / Program name
    public String serviceName;

    // / Program's mux Frequency
    public int frequency;
    // / Program's transport ID
    public int transportStreamId;
    // / Program's service ID
    public int serviceId;
    // / Prgraom's antenna type
    public int antennaType;
    public static final Parcelable.Creator<ProgramInfo> CREATOR = new Parcelable.Creator<ProgramInfo>()
    		{
    			public ProgramInfo createFromParcel(Parcel in)
    			{
    				return new ProgramInfo(in);
    			}

    			public ProgramInfo[] newArray(int size)
    			{
    				return new ProgramInfo[size];
    			}
    		};
    private ProgramInfo(Parcel in)
   {
    	queryIndex = in.readInt();
        number = in.readInt();
        majorNum = (short)in.readInt();
        minorNum = (short)in.readInt();
        progId = in.readInt();
        favorite = (short)in.readInt();
        isLock = (in.readInt() == 1?true:false);
        isSkip = (in.readInt() == 1?true:false);
        isScramble = (in.readInt() == 1?true:false);
        isDelete = (in.readInt() == 1?true:false);
        isVisible = (in.readInt() == 1?true:false);
        isHide = (in.readInt() == 1?true:false);
        serviceType =(short)in.readInt();
        screenMuteStatus = in.readInt();
        serviceName = in.readString();
        frequency = in.readInt();
        transportStreamId = in.readInt();
        serviceId = in.readInt();
        antennaType = in.readInt();
    }  
    
    public ProgramInfo(int queryIndex, int number, short majorNum,
			short minorNum, int progId, short favorite, boolean isLock,
			boolean isSkip, boolean isScramble, boolean isDelete,
			boolean isVisible, boolean isHide, short serviceType,
			int screenMuteStatus, String serviceName, int frequency,
			int transportStreamId, int serviceId, int antennaType) {
		super();
		this.queryIndex = queryIndex;
		this.number = number;
		this.majorNum = majorNum;
		this.minorNum = minorNum;
		this.progId = progId;
		this.favorite = favorite;
		this.isLock = isLock;
		this.isSkip = isSkip;
		this.isScramble = isScramble;
		this.isDelete = isDelete;
		this.isVisible = isVisible;
		this.isHide = isHide;
		this.serviceType = serviceType;
		this.screenMuteStatus = screenMuteStatus;
		this.serviceName = serviceName;
		this.frequency = frequency;
		this.transportStreamId = transportStreamId;
		this.serviceId = serviceId;
		this.antennaType = antennaType;
	}

	public ProgramInfo(int queryIndex)
    {
        queryIndex = 0;
        number = 0;
        majorNum = 0;
        minorNum = 0;
        progId = 0;
        favorite = 0;
        isLock = false;
        isSkip = false;
        isScramble = false;
        isDelete = false;
        isVisible = false;
        isHide = false;
        serviceType = 0;
        screenMuteStatus = 0;
        serviceName = "";
        frequency = 0;
        transportStreamId = 0;
        serviceId = 0;
        antennaType = 0;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(queryIndex);
		dest.writeInt(number);
		dest.writeInt(majorNum);
		dest.writeInt(minorNum);
		dest.writeInt(progId);
		dest.writeInt(favorite);
		dest.writeInt(isLock?1:0);
		dest.writeInt(isSkip?1:0);
		dest.writeInt(isScramble?1:0);
		dest.writeInt(isDelete?1:0);
		dest.writeInt(isVisible?1:0);
		dest.writeInt(isHide?1:0);
		dest.writeInt(serviceType);
		dest.writeInt(screenMuteStatus);
		dest.writeString(serviceName);
		dest.writeInt(frequency);
		dest.writeInt(transportStreamId);
		dest.writeInt(serviceId);
		dest.writeInt(antennaType);
	}

}
