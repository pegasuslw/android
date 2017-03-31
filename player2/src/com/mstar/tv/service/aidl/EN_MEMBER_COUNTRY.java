package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MEMBER_COUNTRY implements Parcelable
{
	E_AUSTRALIA, // /< Australia
	E_AUSTRIA, // /< Austria
	E_BELGIUM, // /< Belgium
	E_BULGARIA, // /< Bulgaria
	E_CROATIA, // /< Croatia
	E_CZECH, // /< Czech
	E_DENMARK, // /< Denmark
	E_FINLAND, // /< Finland
	E_FRANCE, // /< France
	E_GERMANY, // /< Germany
	E_GREECE, // /< Greece
	E_HUNGARY, // /< Hungary
	E_ITALY, // /< Italy
	E_LUXEMBOURG, // /< Luxembourg
	E_NETHERLANDS, // /< Netherland
	E_NORWAY, // /< Norway
	E_POLAND, // /< Poland
	E_PORTUGAL, // /< Portugal
	E_RUMANIA, // /< Rumania
	E_RUSSIA, // /< Russia
	E_SERBIA, // /< Serbia
	E_SLOVENIA, // /< Slovenia
	E_SPAIN, // /< Spain
	E_SWEDEN, // /< Sweden
	E_SWITZERLAND, // /< Switzerland
	E_UK, // /< UK
	E_NEWZEALAND, // /< New Zealand
	E_ARAB, // /< Arab
	E_ESTONIA, // /< Estonia
	E_HEBREW, // /< Hebrew
	E_LATVIA, // /< Latvia
	E_SLOVAKIA, // /< Slovakia
	E_TURKEY, // /< Turkey
	E_IRELAND, // /< Ireland
	E_CHINA, // /< China
	E_TAIWAN, // /< Taiwan
	E_BRAZIL, // /< Brazil
	/* ATSC Country Start */
	E_CANADA, // /< Canada
	E_MEXICO, // /< Mexico
	E_US, // /< United States
	E_SOUTHKOREA, // /< South Korea
	/* ATSC Country End */
	E_OTHERS, // /< Others
	// ------------------------------------
	E_COUNTRY_NUM;
	public static final Parcelable.Creator<EN_MEMBER_COUNTRY> CREATOR = new Parcelable.Creator<EN_MEMBER_COUNTRY>()
			{
				public EN_MEMBER_COUNTRY createFromParcel(Parcel in)
				{
					return EN_MEMBER_COUNTRY.values()[in.readInt()];
				}

				public EN_MEMBER_COUNTRY[] newArray(int size)
				{
					return new EN_MEMBER_COUNTRY[size];
				}
			};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.ordinal());
	}

}

