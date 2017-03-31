package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class PresentFollowingEventInfo implements Parcelable{

	 public DtvEventComponentInfo componentInfo;
	    public EpgEventInfo eventInfo;

	    public PresentFollowingEventInfo() {
	        componentInfo = new DtvEventComponentInfo();
	        eventInfo = new EpgEventInfo();
	    }
	
	    public PresentFollowingEventInfo(Parcel in) {
	    	 componentInfo =  DtvEventComponentInfo.CREATOR.createFromParcel(in);
		     eventInfo = EpgEventInfo.CREATOR.createFromParcel(in);
		}

		public static final Parcelable.Creator<PresentFollowingEventInfo> CREATOR = new Parcelable.Creator<PresentFollowingEventInfo>()

	    		{

	    			public PresentFollowingEventInfo createFromParcel(Parcel in)

	    			{

	    				return new PresentFollowingEventInfo(in);

	    			}



	    			public PresentFollowingEventInfo[] newArray(int size)

	    			{

	    				return new PresentFollowingEventInfo[size];

	    			}

	    		};
	    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		componentInfo.writeToParcel(dest, flags);
		eventInfo.writeToParcel(dest, flags);
	}

}
