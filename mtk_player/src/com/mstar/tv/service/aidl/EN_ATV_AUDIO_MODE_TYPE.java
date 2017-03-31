package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ATV_AUDIO_MODE_TYPE implements Parcelable{
	
	 // /< Audio Mode Invalid
    E_ATV_AUDIOMODE_INVALID,
    // /< Audio Mode MONO
    E_ATV_AUDIOMODE_MONO,
    // /< Audio Mode Forced MONO
    E_ATV_AUDIOMODE_FORCED_MONO,
    // /< Audio Mode G Stereo
    E_ATV_AUDIOMODE_G_STEREO,
    // /< Audio Mode K Stereo
    E_ATV_AUDIOMODE_K_STEREO,
    // /< Audio Mode Mono SAP
    E_ATV_AUDIOMODE_MONO_SAP,
    // /< Audio Mode Stereo SAP
    E_ATV_AUDIOMODE_STEREO_SAP,
    // /< Audio Mode Dual A
    E_ATV_AUDIOMODE_DUAL_A,
    // /< Audio Mode Dual B
    E_ATV_AUDIOMODE_DUAL_B,
    // /< Audio Mode Dual AB
    E_ATV_AUDIOMODE_DUAL_AB,
    // /< Audio Mode NICAM MONO
    E_ATV_AUDIOMODE_NICAM_MONO,
    // /< Audio Mode NICAM Stereo
    E_ATV_AUDIOMODE_NICAM_STEREO,
    // /< Audio Mode NICAM DUAL A
    E_ATV_AUDIOMODE_NICAM_DUAL_A,
    // /< Audio Mode NICAM DUAL B
    E_ATV_AUDIOMODE_NICAM_DUAL_B,
    // /< Audio Mode NICAM DUAL AB
    E_ATV_AUDIOMODE_NICAM_DUAL_AB,
    // /< Audio Mode HIDEV MONO
    E_ATV_AUDIOMODE_HIDEV_MONO,
    // /< Audio Mode left left
    E_ATV_AUDIOMODE_LEFT_LEFT,
    // /< Audio Mode right right
    E_ATV_AUDIOMODE_RIGHT_RIGHT,
    // /< Audio Mode left right
    E_ATV_AUDIOMODE_LEFT_RIGHT,
    //   / < Audio Mode NUM>
    E_ATV_AUDIOMODE_NUM;

	public static final Parcelable.Creator<EN_ATV_AUDIO_MODE_TYPE> CREATOR = new Parcelable.Creator<EN_ATV_AUDIO_MODE_TYPE>()
			{
				public EN_ATV_AUDIO_MODE_TYPE createFromParcel(Parcel in)
				{
					return EN_ATV_AUDIO_MODE_TYPE.values()[in.readInt()];
				}

				public EN_ATV_AUDIO_MODE_TYPE[] newArray(int size)
				{
					return new EN_ATV_AUDIO_MODE_TYPE[size];
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
