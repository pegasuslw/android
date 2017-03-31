package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MWE_TYPE implements Parcelable
{
	
//	 E_OFF,
//     // /optimize
//     E_OPTIMIZE,
//     // /enhance
//     E_ENHANCE,
//     // / side by side
//     E_SIDE_BY_SIDE,
//     // /dynamic compare
//     E_DYNAMICCOMPARE,
//     // /center based scale
//     E_CENTERBASEDSCALE,
//     // /square move
//     E_SQUAREMOVE,
//     // / the number of MWE type
////     E_DEFAULT,
//     // / the MAX
//     E_NUM;
	
	 // / off
    E_OFF,
    // /optimize
    E_OPTIMIZE,
    // /enhance
    E_ENHANCE,
    // / side by side
    E_SIDE_BY_SIDE,
    // /dynamic compare
    E_DYNAMICCOMPARE,
    // /center based scale
    E_CENTERBASEDSCALE,
    // /square move
    E_SQUAREMOVE,
    ///for customer
    E_EN_MS_MWE_CUSTOMER1,
    ///for customer
    E_EN_MS_MWE_CUSTOMER2,
    ///for customer
    E_EN_MS_MWE_CUSTOMER3,
    ///for customer
    E_EN_MS_MWE_CUSTOMER4,
    ///for customer
    E_EN_MS_MWE_CUSTOMER5,
    ///for customer
    E_EN_MS_MWE_CUSTOMER6,
    // / the number of MWE type
    // / the MAX
    E_NUM;

	public static final Parcelable.Creator<EN_MWE_TYPE> CREATOR = new Parcelable.Creator<EN_MWE_TYPE>()
              {
				public EN_MWE_TYPE createFromParcel(Parcel in)
				{
					return EN_MWE_TYPE.values()[in.readInt()];
				}

				public EN_MWE_TYPE[] newArray(int size)
				{
					return new EN_MWE_TYPE[size];
				}
			};
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.ordinal());		
	}
}	

	