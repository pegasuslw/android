package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class DtvSubtitleInfo implements Parcelable{
	    public MenuSubtitleService[] subtitleServices = new MenuSubtitleService[24];
	    public short currentSubtitleIndex;
	    public short subtitleServiceNumber;
	    public boolean subtitleOn;
	    public static final Parcelable.Creator<DtvSubtitleInfo> CREATOR = new Parcelable.Creator<DtvSubtitleInfo>()
	    		{
	    			public DtvSubtitleInfo createFromParcel(Parcel in)
	    			{
	    				return new DtvSubtitleInfo(in);
	    			}

	    			public DtvSubtitleInfo[] newArray(int size)
	    			{
	    				return new DtvSubtitleInfo[size];
	    			}
	    		};
	    	private DtvSubtitleInfo(Parcel in) 
	    	{  
	    		for(int i = 0; i < 24; i ++){
	    			subtitleServices[i] = MenuSubtitleService.CREATOR.createFromParcel(in);
	    		}
//	    		subtitleServices = new MenuSubtitleService[in.readInt()];
	    		this.currentSubtitleIndex = (short)in.readInt(); 
	    		this.subtitleServiceNumber = (short)in.readInt(); 
	    		subtitleOn = (in.readInt() == 1?true:false);
	    	 }
	    public DtvSubtitleInfo(MenuSubtitleService[] subtitleServices,
	    		short currentSubtitleIndex,short subtitleServiceNumber,boolean subtitleOn)
	    {
	        this.subtitleServices = subtitleServices;
	        this.currentSubtitleIndex = currentSubtitleIndex;
	        this.subtitleServiceNumber = subtitleServiceNumber;
	        this.subtitleOn = subtitleOn;

	    }

		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			dest.writeParcelableArray(subtitleServices, 0);
			dest.writeInt(currentSubtitleIndex);
			dest.writeInt(subtitleServiceNumber);
			dest.writeInt(subtitleOn?1:0);
			
		}
}
