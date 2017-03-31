package com.mstar.tv.service.interfaces;

/** @hide */
interface ITvServiceServerTvManager
{
	boolean onDtvReadyPopupDialog(int arg0, int arg1, int arg2);
	boolean onScartMuteOsdMode(int arg0);
	boolean onSignalLock(int arg0);
	boolean onSignalUnlock(int arg0);
	boolean onUnityEvent(int arg0, int arg1, int arg2);
}