package com.mstar.tv.service.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class ProgramInfoQueryCriteria implements Parcelable{
	 // / program information of program index
    public int queryIndex;
    // / program information of program number
    public int number;
    // / Program service type
    protected short serviceType;
    public static final Parcelable.Creator<ProgramInfoQueryCriteria> CREATOR = new Parcelable.Creator<ProgramInfoQueryCriteria>()
    		{
    			public ProgramInfoQueryCriteria createFromParcel(Parcel in)
    			{
    				return new ProgramInfoQueryCriteria(in);
    			}

    			public ProgramInfoQueryCriteria[] newArray(int size)
    			{
    				return new ProgramInfoQueryCriteria[size];
    			}
    		};
       private ProgramInfoQueryCriteria(Parcel in)
      {
    	     this.queryIndex = in.readInt();
    	     this.number = in.readInt();
    	     this.serviceType = (short)in.readInt();
      }  
    public ProgramInfoQueryCriteria(int queryIndex,int number,short serviceType)
    {
        this.queryIndex = queryIndex;
        this.number = number;
        this.serviceType = serviceType;
                                                                               // as
                                                                               // E_SERVICETYPE_INVALID
    }

    public EN_MEMBER_SERVICE_TYPE getServiceType()
    {
        return EN_MEMBER_SERVICE_TYPE.values()[serviceType];
    }

    public void setServiceType(EN_MEMBER_SERVICE_TYPE eServiceType)
    {
        this.serviceType = (short) eServiceType.ordinal();
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
		dest.writeInt(serviceType);
	}

}
