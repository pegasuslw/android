package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_GET_PROGRAM_INFO implements Parcelable{
	
	// / Get fine tune frequency
    E_GET_FINE_TUNE,
    // / Get Program PLL number
    E_GET_PROGRAM_PLL_DATA,
    // / Get Program Audio Standard
    E_GET_AUDIO_STANDARD,
    // / Get Program Video Standard
    E_GET_VIDEO_STANDARD_OF_PROGRAM,
    // / Get Program DUAL AUDIO Selection
    E_GET_DUAL_AUDIO_SELECTED,
    // / Is Program need to skip
    E_IS_PROGRAM_SKIPPED,
    // / Is Program lock
    E_IS_PROGRAM_LOCKED,
    // / Is Program need to hide
    E_IS_PROGRAM_HIDE,
    // / Is Program need to AFT
    E_IS_AFT_NEED,
    // / Is Direct Tuned
    E_IS_DIRECT_TUNED,
    // / Get AFT Offset
    E_GET_AFT_OFFSET,
    // / Is realtime audio detection enable
    E_IS_REALTIME_AUDIO_DETECTION_ENABLE,
    // / Get station name
    // original is E_GET_STATION_NAME,
    // to achieve this capability use getAtvStationName()
    E_GET_DUMMY1,
    // / Get Sorting priority
    E_GET_SORTING_PRIORITY,
    // / Get Channel Index
    E_GET_CHANNEL_INDEX,
    // /Get ATV auto color
    // GET_ATV_AUTOCOLOR -> for isdb, remark
    /*
     * Get MISC Information E_GET_MISC original is E_GET_MISC, to achieve
     * this capability use getAtvProgramMisInfo(int programNo)
     */
    E_GET_DUMMY2;

  public static final Parcelable.Creator<EN_GET_PROGRAM_INFO> CREATOR = new Parcelable.Creator<EN_GET_PROGRAM_INFO>()
			{
				public EN_GET_PROGRAM_INFO createFromParcel(Parcel in)
				{
					return EN_GET_PROGRAM_INFO.values()[in.readInt()];
				}

				public EN_GET_PROGRAM_INFO[] newArray(int size)
				{
					return new EN_GET_PROGRAM_INFO[size];
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
