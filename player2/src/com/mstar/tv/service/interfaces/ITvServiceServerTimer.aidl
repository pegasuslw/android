package com.mstar.tv.service.interfaces;

import com.mstar.tv.service.aidl.ST_Time;
import com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE;
import com.mstar.tv.service.aidl.ST_OnTime_TVDes;

/** @hide */
interface ITvServiceServerTimer
{
	ST_Time getCurTimer();
	
	boolean setOnTimer(in ST_Time time);
	
	ST_Time getOnTimer();
	
	boolean setOffTimer(in ST_Time time);
	
	ST_Time getOffTimer();
	
	boolean setOffTimerEnable(boolean bEnable);

	boolean isOffTimerEnable();
	
	boolean setOnTimerEnable(boolean bEnable);
	
	boolean isOnTimerEnable();
	
	boolean setSleepMode(in EN_SLEEP_TIME_STATE eMode);
	
	EN_SLEEP_TIME_STATE getSleepMode();
	
	boolean setOnTimeEvent(in ST_OnTime_TVDes stEvent);
		
	ST_OnTime_TVDes getOnTimeEvent();
}