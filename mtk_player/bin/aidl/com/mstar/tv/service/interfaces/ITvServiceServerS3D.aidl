package com.mstar.tv.service.interfaces;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT;
import com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH;
import com.mstar.tv.service.aidl.EN_ThreeDType;

/** @hide */
interface ITvServiceServerS3D
{
    
    /**
	 * 
	 * set self adaptive detect
	 * 
	 * @param EN_ThreeD_Video_SELFADAPTIVE_DETECT
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean setSelfAdaptiveDetect(
	        in EN_ThreeD_Video_SELFADAPTIVE_DETECT selfAdaptiveDetect);

	/**
	 * 
	 * get self adaptive detect
	 * 
	 * @return EN_ThreeD_Video_SELFADAPTIVE_DETECT
	 * 
	 */
	EN_ThreeD_Video_SELFADAPTIVE_DETECT getSelfAdaptiveDetect();
	
	/**
	 * 
	 * set self adaptive level
	 * 
	 * @param EN_ThreeD_Video_SELFADAPTIVE_LEVEL
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean setSelfAdaptiveLevel(
	        in EN_ThreeD_Video_SELFADAPTIVE_LEVEL selfAdaptiveLevel);

	/**
	 * 
	 * get self adaptive level
	 * 
	 * @return EN_ThreeD_Video_SELFADAPTIVE_LEVEL
	 * 
	 */
	EN_ThreeD_Video_SELFADAPTIVE_LEVEL getSelfAdaptiveLevel();
	
	/**
	 * 
	 * set display format
	 * 
	 * @param EN_ThreeD_Video_DISPLAYFORMAT
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean setDisplayFormat(
	        in EN_ThreeD_Video_DISPLAYFORMAT displayFormat);

	/**
	 * 
	 * get display format
	 * 
	 * @return EN_ThreeD_Video_DISPLAYFORMAT
	 * 
	 */
	EN_ThreeD_Video_DISPLAYFORMAT getDisplayFormat();

	/**
	 * 
	 * set display 3dto2d mode
	 * 
	 * @param EN_ThreeD_Video_3DTO2D
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean set3DTo2D(
	        in EN_ThreeD_Video_3DTO2D displayMode);

	/**
	 * 
	 * get display 3dto2d mode
	 * 
	 * @return EN_ThreeD_Video_3DTO2D
	 * 
	 */
	EN_ThreeD_Video_3DTO2D getDisplay3DTo2DMode();	
	
	/**
	 * 
	 * set 3D depth mode
	 * 
	 * @param EN_ThreeD_Video_3DDEPTH
	 *            (level 0-31)
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean set3DDepthMode(int mode3DDepth);

	/**
	 * 
	 * get 3D depth mode
	 * 
	 * @return EN_ThreeD_Video_3DDEPTH
	 * 
	 */
	int get3DDepthMode();

	/**
	 * 
	 * set 3D offset mode
	 * 
	 * @param EN_ThreeD_Video_3DOFFSET
	 *            (level 0-31)
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean set3DOffsetMode(int mode3DOffset);

	/**
	 * 
	 * get 3D offset mode
	 * 
	 * @return EN_ThreeD_Video_3DOFFSET
	 * 
	 */
	int get3DOffsetMode();

	/**
	 * 
	 * set auto start mode
	 * 
	 * @param EN_ThreeD_Video_AUTOSTART
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean setAutoStartMode(
	        in EN_ThreeD_Video_AUTOSTART autoStartMode);

	/**
	 * 
	 * get auto start mode
	 * 
	 * @return EN_ThreeD_Video_AUTOSTART
	 * 
	 */
	EN_ThreeD_Video_AUTOSTART getAutoStartMode();

	/**
	 * 
	 * set 3D output aspect mode
	 * 
	 * @param EN_ThreeD_Video_3DOUTPUTASPECT
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean set3DOutputAspectMode(
	        in EN_ThreeD_Video_3DOUTPUTASPECT outputAspectMode);

	/**
	 * 
	 * get 3D output aspect mode
	 * 
	 * @return EN_ThreeD_Video_3DOUTPUTASPECT
	 */
	EN_ThreeD_Video_3DOUTPUTASPECT get3DOutputAspectMode();

	/**
	 * 
	 * set LR view switch enable or disable
	 * 
	 * @param EN_ThreeD_Video_LRVIEWSWITCH
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean setLRViewSwitch(
	        in EN_ThreeD_Video_LRVIEWSWITCH LRViewSwitchMode);

	/**
	 * 
	 * get LR view switch status (enable or disable)
	 * 
	 * @return EN_ThreeD_Video_LRVIEWSWITCH
	 */
	EN_ThreeD_Video_LRVIEWSWITCH getLRViewSwitch();

    /**
     * 
     * set 3d display format for UI
     * 
     * @param 3d display format index
     * 
     * @return none
     */
	void  setDisplayFormatForUI(int threedDisplayFormatIdx);
	
    /**
     * 
     * get current 3d format index
     * 
     * @return current 3d format index
     */
	int getCurrent3dFormatIndex();
	
	}