package com.mstar.tv.service.interfaces;
import com.mstar.tv.service.aidl.EN_ATV_MANUAL_TUNE_MODE;
import com.mstar.tv.service.aidl.EN_FIRST_SERVICE_INPUT_TYPE;
import com.mstar.tv.service.aidl.EN_FIRST_SERVICE_TYPE;
import com.mstar.tv.service.aidl.EN_FAVORITE_ID;
import com.mstar.tv.service.aidl.EN_GET_PROGRAM_INFO;
import com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD;
import com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE;
import com.mstar.tv.service.aidl.EN_SET_PROGRAM_INFO;
import com.mstar.tv.service.aidl.EN_ANTENNA_TYPE;
import com.mstar.tv.service.aidl.EN_PROGRAM_COUNT_TYPE;
import com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE;
import com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE;
import com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY;
import com.mstar.tv.service.aidl.EN_SET_PROGRAM_CTRL;
import com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE;
import com.mstar.tv.service.aidl.EN_TV_TS_STATUS;
import com.mstar.tv.service.aidl.EN_GET_PROGRAM_CTRL;
import com.mstar.tv.service.aidl.EN_ATV_AUDIO_MODE_TYPE;
import com.mstar.tv.service.aidl.DtvSubtitleInfo;
import com.mstar.tv.service.aidl.ProgramInfo;
import com.mstar.tv.service.aidl.ProgramInfoQueryCriteria;
import com.mstar.tv.service.aidl.EN_PROGRAM_INFO_TYPE;
import com.mstar.tv.service.aidl.DvbMuxInfo;
import com.mstar.tv.service.aidl.EN_MS_LANGUAGE;
import com.mstar.tv.service.aidl.DvbcScanParam;
import com.mstar.tv.service.aidl.EN_CAB_CONSTEL_TYPE;
import com.mstar.tv.service.aidl.RfInfo;
import com.mstar.tv.service.aidl.EnumInfoType;
import com.mstar.tv.service.aidl.DtvAudioInfo;
import com.mstar.tv.service.aidl.ST_DTV_SPECIFIC_PROGINFO;

/** @hide */
interface ITvServiceServerChannel
{
   boolean makeSourceAtv();
   
   boolean makeSourceDtv();
   
   boolean switchMSrvDtvRouteCmd(int u8DtvRoute);
   
   boolean atvSetManualTuningStart(int EventIntervalMs, int Frequency,
       in EN_ATV_MANUAL_TUNE_MODE eMode);
       
   boolean changeToFirstService(in EN_FIRST_SERVICE_INPUT_TYPE enInputType,
		        in EN_FIRST_SERVICE_TYPE enServiceType);
		        
	void atvSetManualTuningEnd();
	
   boolean atvSetAutoTuningStart(int EventIntervalMs,int FrequencyStart, int FrequencyEnd);
   
   void addProgramToFavorite(in EN_FAVORITE_ID favoriteId, int programNo,
        int programType, int programId);
        
	int atvGetCurrentFrequency();
	
	int atvGetProgramInfo(in EN_GET_PROGRAM_INFO Cmd, int u16Program,
        int u16Param2, String str);
        
     EN_ATV_SYSTEM_STANDARD atvGetSoundSystem(); 
     
     EN_AVD_VIDEO_STANDARD_TYPE atvGetVideoSystem();   
     
     int getCurrentChannelNumber();
     
     boolean atvSetAutoTuningEnd();
     
     boolean atvSetAutoTuningPause();
     
     boolean atvSetAutoTuningResume();
     
     int atvSetChannel(int ChannelNum, boolean bCheckBlock);
     
     boolean atvSetForceSoundSystem(in EN_ATV_SYSTEM_STANDARD eSoundSystem);
     
     boolean atvSetForceVedioSystem(in EN_AVD_VIDEO_STANDARD_TYPE eVideoSystem);
     
     
     boolean atvSetFrequency(int Frequency);
     
     
     int atvSetProgramInfo(in EN_SET_PROGRAM_INFO Cmd, int Program,
        int Param2, String str);
        
     boolean closeSubtitle();
         
     void deleteProgramFromFavorite(in EN_FAVORITE_ID favoriteId,
        int programNo, int programType, int programId);
        
     boolean dtvAutoScan();
     
     boolean dtvChangeManualScanRF(int RFNum);
     
     boolean dtvFullScan();
     
     
     String getAtvStationName(int programNo);
     
     int getProgramCount(in EN_PROGRAM_COUNT_TYPE programCountType);
     
     boolean programSel(int u32Number, in EN_MEMBER_SERVICE_TYPE u8ServiceType);
     
     void switchAudioTrack(int track);
     
     void setUserScanType(in EN_TUNING_SCAN_TYPE scantype);
     
     
     void setSystemCountry(in EN_MEMBER_COUNTRY mem_country);
     
     void setProgramName(int programNum, int programType,
        String porgramName);
        
    int setProgramCtrl(in EN_SET_PROGRAM_CTRL Cmd, int u16Param2,
        int u16Param3, String pVoid);
     
     void setProgramAttribute(in EN_PROGRAM_ATTRIBUTE enpa, int programNo,
        int pt, int pd, boolean bv);
        
      void sendDtvScaninfo();
      
      void sendAtvScaninfo();
      
      boolean programUp();
      
      boolean programReturn();
      
      boolean programDown();
      
      boolean openSubtitle(int index);
      
      void moveProgram(int progSourcePosition, int progTargetPosition);
      
      boolean isSignalStabled();
      
      EN_AVD_VIDEO_STANDARD_TYPE getVideoStandard();
      
      EN_TUNING_SCAN_TYPE getUserScanType();
      
      EN_TV_TS_STATUS GetTsStatus();
      
      boolean getProgramAttribute(in EN_PROGRAM_ATTRIBUTE enpa,
        int programNo, int pt, int pd, boolean bv);
        
      int getProgramCtrl(in EN_GET_PROGRAM_CTRL Cmd, int u16Param2,
        int u16Param3, String pVoid);
        
      String getProgramName(int progNo, in EN_MEMBER_SERVICE_TYPE progType,
        int progrID);
        
       EN_ATV_AUDIO_MODE_TYPE getSIFMtsMode();
       
       
       EN_MEMBER_COUNTRY getSystemCountry();
       
       DtvSubtitleInfo getSubtitleInfo();
       
       ProgramInfo getProgramInfo(in ProgramInfoQueryCriteria criteria,
        in EN_PROGRAM_INFO_TYPE programInfoType);
        
        int getMSrvDtvRoute();
        
        ProgramInfo getCurrProgramInfo();
        
        EN_ANTENNA_TYPE dtvGetAntennaType();
        
        boolean dtvManualScanFreq(int FrequencyKHz);
        
        boolean dtvManualScanRF(int RFNum);
        
        boolean dtvPauseScan();
        
        boolean dtvplayCurrentProgram();
        
        boolean dtvResumeScan();
        
        void dtvSetAntennaType(in EN_ANTENNA_TYPE type);
        
        boolean dtvStartManualScan();
        
        boolean DtvStopScan();
        
        DvbMuxInfo getCurrentMuxInfo();
        
        EN_MS_LANGUAGE getCurrentLanguageIndex(String languageCode);
        
        boolean dvbcgetScanParam(in DvbcScanParam sp);
        
         boolean dvbcsetScanParam(int u16SymbolRate,
        in EN_CAB_CONSTEL_TYPE enConstellation, int u32nitFrequency,
        int u32EndFrequncy, int u16NetworkID);
        
        boolean getCurrentProgramSpecificInfo(in ST_DTV_SPECIFIC_PROGINFO cResult);
        RfInfo dtvGetRFInfo(in EnumInfoType enInfoType, int RFChNum);
        DtvAudioInfo getAudioInfo();
        void setChannelChangeFreezeMode(boolean freezeMode);
}