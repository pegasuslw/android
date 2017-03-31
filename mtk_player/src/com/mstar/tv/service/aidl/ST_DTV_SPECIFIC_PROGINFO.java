package com.mstar.tv.service.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class ST_DTV_SPECIFIC_PROGINFO implements Parcelable{
	// Res. :Show resolution, SD,HD ,
	// / Service name
	public String m_sServiceName;
	// / Program number
	public int m_u32Number;
	// / Width
	public short m_u16Width;
	// / Height
	public short m_u16Height;
	// / Framerate
	public short m_u16FrameRate; // framerate x 1000
	// / Interlace or not
	public boolean m_bInterlace;
	// FIXME :
	// / Color sys : PAL,SCAM ,NTSC ?
	// / sound sys : Audio, standard,L,R
	// / MTS : mono, Stereo, MtsSap, MtsDUAL_A/B/AB, NICAM, NICAM_STEREO,
	// NICAM_DUAL_A/B,
	// / MtsRIGHT_RIGHTKK
	// / input Text : Radio,Data,air TV
	// / Audio : mpeg,dolby
	public int m_eServiceType;
	// / Audio info
	public AudioInfo m_stAudioInfo;
	// / Video type
	public int m_eVideoType;
	// / Service MHEG5 or not
	public boolean m_bMHEG5Service;
	// / Service Subtitle or not
	public boolean m_bSubtitleService;
	// / Service TTX or not
	public boolean m_bTTXService;
	// / Service CC or not
	public boolean m_bCCService;
	// MHEG : mheg,video, data only
	// Narration :
	// Subtitle :
	// Audiolang :
	// Teletext :
	// Epg info page :
	// / Service HD or not
	public boolean m_bHD;
	// / Service AD or not
	public boolean m_bAD;
	// / Service audio track number
	public byte m_u8AudioTrackNum;
	// / Service subtitle number
	public byte m_u8SubtitleNum;
	// / Service aspec ratio
	// EN_ASPECT_RATIO_CODE m_enAspectRatio;
	// / Service genre
	// MW_EN_EPG_MAIN_GENRE_TYPE m_enGenreType;
	// / Service parental rating
	// U8 m_u8ParentalRating;
			
	private ST_DTV_SPECIFIC_PROGINFO(Parcel in){
		m_sServiceName = in.readString();
		  m_u32Number = in.readInt();
		  m_u16Width = (short) in.readInt();
		  m_u16Height = (short) in.readInt();
		  m_u16FrameRate = (short) in.readInt(); // framerate x 1000
		  m_bInterlace = in.readInt() == 1? true : false;
		 m_eServiceType = in.readInt();
		 m_stAudioInfo = AudioInfo.CREATOR.createFromParcel(in);
		  m_eVideoType = in.readInt();
		  m_bMHEG5Service = in.readInt() == 1? true : false;
		  m_bSubtitleService = in.readInt() == 1? true : false;
		  m_bTTXService = in.readInt() == 1? true : false;
		  m_bCCService = in.readInt() == 1? true : false;
		  m_bHD = in.readInt() == 1? true : false;
		  m_bAD = in.readInt() == 1? true : false;
		  m_u8AudioTrackNum = in.readByte();
		  m_u8SubtitleNum = in.readByte();
	}
	 public static final Parcelable.Creator<ST_DTV_SPECIFIC_PROGINFO> CREATOR = new Parcelable.Creator<ST_DTV_SPECIFIC_PROGINFO>()
	    		{
	    			public ST_DTV_SPECIFIC_PROGINFO createFromParcel(Parcel in)
	    			{
	    				return new ST_DTV_SPECIFIC_PROGINFO(in);
	    			}

	    			public ST_DTV_SPECIFIC_PROGINFO[] newArray(int size)
	    			{
	    				return new ST_DTV_SPECIFIC_PROGINFO[size];
	    			}
	    		};		
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(m_sServiceName); 
		dest.writeInt(m_u32Number);
		dest.writeInt(m_u16Width);
		dest.writeInt(m_u16Height);  
		dest.writeInt(m_u16FrameRate);  
		dest.writeInt(m_bInterlace? 1 : 0);
		dest.writeInt(m_eServiceType); 
		 m_stAudioInfo.writeToParcel(dest, flags);
		 dest.writeInt(m_eVideoType); 
		 dest.writeInt(m_bMHEG5Service? 1 : 0); 
		 dest.writeInt(m_bSubtitleService? 1 : 0);
		 dest.writeInt(  m_bTTXService ? 1 : 0);
		 dest.writeInt(  m_bCCService ? 1 : 0);
		 dest.writeInt(  m_bHD ? 1 : 0);
		 dest.writeInt(  m_bAD ? 1 : 0);
		dest.writeByte(m_u8AudioTrackNum); 
		dest.writeByte(  m_u8SubtitleNum );
	}

}
