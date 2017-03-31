package com.mstar.tv.service.aidl;



import android.os.Parcel;
import android.os.Parcelable;

public class EpgEventInfo implements Parcelable{

	public int startTime; // / EPG event start time

    public int endTime; // / EPG event end time, for time of offset time change
                        // is between start and end

    public int durationTime; // / EPG event duration time
    // / EPG interface function status for return value

    public String name; // / EPG event info name

    public int eventId; // / EPG event id in db

    public boolean isScrambled; // / EPG event is scrambled or not

    public short genre; // / EPG event genre

    public short parentalRating; // / EPG event rating

    public String description; // / EPG event description

    public int originalStartTime; // / EPG original start time

    protected int functionStatus; // / EPG function status

    public EpgEventInfo()
    {
        startTime = 0;
        endTime = 0;
        durationTime = 0;
        name = "";
        eventId = 0;
        isScrambled = false;
        genre = 0;
        parentalRating = 0;
        description = "";
        originalStartTime = 0;
        functionStatus = 0;
    }

    public EpgEventInfo(Parcel in) {
    	startTime = in.readInt();
        endTime = in.readInt();
        durationTime = in.readInt();
        name = in.readString();
        eventId = in.readInt();
        isScrambled = in.readInt() == 1 ? true : false;
        genre = (short) in.readInt();
        parentalRating = (short) in.readInt();
        description = in.readString();
        originalStartTime = in.readInt();
        functionStatus = in.readInt();
	}

	public int getEpgFunctionStatus()
    {

        return this.functionStatus;
    }

    public void setEpgFunctionStatus(int status)
    {
        functionStatus = status;
    }
	
	
    public static final Parcelable.Creator<EpgEventInfo> CREATOR = new Parcelable.Creator<EpgEventInfo>()

    		{

    			public EpgEventInfo createFromParcel(Parcel in)

    			{

    				return new EpgEventInfo(in);

    			}



    			public EpgEventInfo[] newArray(int size)

    			{

    				return new EpgEventInfo[size];

    			}

    		};
    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(startTime);
		dest.writeInt(endTime);
		dest.writeInt(durationTime);
		dest.writeString(name);
        dest.writeInt(eventId);
        dest.writeInt(isScrambled?1:0);
        dest.writeInt(genre);
        dest.writeInt(parentalRating);
        dest.writeString(description);
        dest.writeInt(originalStartTime);
        dest.writeInt(functionStatus);
	}

}
