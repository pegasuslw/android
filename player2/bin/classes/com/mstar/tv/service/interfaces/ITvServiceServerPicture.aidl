package com.mstar.tv.service.interfaces;
import com.mstar.tv.service.aidl.EN_MS_PICTURE;
import com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type;
import com.mstar.tv.service.aidl.EN_MS_VIDEOITEM;
import com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP;
import com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA;
import com.mstar.tv.service.aidl.EN_MS_MPEG_NR;
import com.mstar.tv.service.aidl.EN_MS_NR;
import com.mstar.tv.service.aidl.EN_MWE_TYPE;

/** @hide */
interface ITvServiceServerPicture
{

	boolean setPictureModeIdx(in EN_MS_PICTURE ePicMode);
	
	EN_MS_PICTURE getPictureModeIdx();
	
	boolean setVideoArc(in MAPI_VIDEO_ARC_Type eArcIdx);
	
	MAPI_VIDEO_ARC_Type getVideoArc();
	
	boolean execVideoItem(in EN_MS_VIDEOITEM eIndex, int value);

	int getVideoItem(in EN_MS_VIDEOITEM eIndex);

	boolean setBacklight(int value);

	 int getBacklight();

	 boolean setColorTempIdx(in EN_MS_COLOR_TEMP eColorTemp);

	 EN_MS_COLOR_TEMP getColorTempIdx();

	 boolean setColorTempPara(in T_MS_COLOR_TEMPEX_DATA stColorTemp);

	 T_MS_COLOR_TEMPEX_DATA getColorTempPara();

	 boolean setMpegNR(in EN_MS_MPEG_NR eMpNRIdx);

	 EN_MS_MPEG_NR getMpegNR();

	 boolean setNR(in EN_MS_NR eNRIdx);

	 EN_MS_NR getNR();
	 
	 boolean setMWEStatus(in EN_MWE_TYPE eMWE);
	 
	 EN_MWE_TYPE getMWEStatus();
 	int getPCHPos();
    
	 boolean setPCHPos(int hpos);
	 
	 int getPCVPos();
	 
	 boolean setPCVPos(int vpos);
	 
	 int getPCClock();
	 
	 boolean setPCClock(int clock);
	 
	 int getPCPhase();
	 
	 boolean setPCPhase(int phase);
	 
	 void setMFC(int iMfcMode);
	 int getMFC(); 
	 
	 /**
      *  set  pc auto adjust
      *
     **/
	 boolean ExecAutoPc();
	 int getDlcAverageLuma();
	 boolean freezeImage();
	 boolean unFreezeImage();
	 boolean setSwingLevel(int swingLevel);
	 int getDlcHistogramMax();
	 int getDlcHistogramMin();
}