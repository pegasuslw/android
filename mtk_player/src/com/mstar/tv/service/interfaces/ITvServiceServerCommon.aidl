package com.mstar.tv.service.interfaces;
import com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE;
import com.mstar.tv.service.aidl.ST_VIDEO_INFO;
import com.mstar.tv.service.aidl.BoolArrayList;
import com.mstar.tv.service.aidl.IntArrayList;
import com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE;
import com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE;
import com.mstar.tv.service.aidl.PresentFollowingEventInfo;
import com.mstar.tv.service.aidl.EN_ThreeD_OSD_TYPE;

/** @hide */
interface ITvServiceServerCommon
{
	//-------------------------------------------------------------------------------------------------
    /// Get SubWindow Source List
    /// @return                           \b Array for Integer              
    //-------------------------------------------------------------------------------------------------
    IntArrayList getSourceList();
	
	void ForbidDetection();
	
	void AllowDetection();
	
	void StartSourceDetection();
	
	void StopSourceDetection();
	
	void EnableAutoSourceSwitch();
	
	void DisableAutoSourceSwitch();
	
	BoolArrayList GetInputSourceStatus();
    EN_INPUT_SOURCE_TYPE GetCurrentInputSource();
    void SetInputSource(in EN_INPUT_SOURCE_TYPE source);
    boolean isSignalStable();
    //boolean isHdmiSignalMode();
    ST_VIDEO_INFO getVideoInfo();
    boolean programUp();
    boolean programDown();
    
	void openSurfaceView(int x, int y, int width, int height);

	 void setSurfaceView(int x, int y, int width, int height);

	 void closeSurfaceView();
    
    boolean setOsdLanguage(in EN_MEMBER_LANGUAGE eLang);
    EN_MEMBER_LANGUAGE getOsdLanguage();
    /**
    *  set power on source
    *
    **/
   	boolean setPowerOnSource(in EN_TIME_ON_TIME_SOURCE eSour);
    
    EN_TIME_ON_TIME_SOURCE getPowerOnSource();
    boolean upgrade(String name,String value);
    
    PresentFollowingEventInfo getPresentFollowingEventInfo(int serviceType, int serviceNo, boolean present,int descriptionType);
    int getGpioDeviceStatus(int pinId);
    boolean setGpioDeviceStatus(int pinId,boolean enable);
    void enterSleepMode(boolean bMode, boolean bNoSignalPwDn);
    void rebootSystem(String pwd);
    void standbySystem(String pwd);
    void setDeskDisplayMode(String mode);
    void setHotkeyEnableOrNot(boolean isEnable);
    void setInputSourceFast(in EN_INPUT_SOURCE_TYPE source, boolean isStore);
}