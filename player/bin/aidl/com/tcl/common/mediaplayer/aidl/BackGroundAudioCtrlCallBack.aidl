package com.tcl.common.mediaplayer.aidl;

interface BackGroundAudioCtrlCallBack{


 void onAudioPlayChanged(int index);
 void onAudioPlayPrepared();	
 void onAudioPlaySeekComplete(int currentPosition);
 void onAudioPlayBufferingUpdate(int percent);
 void onAudioPlayError(int errCode);
 void onAudioPlayInfoNotify(int infoCode);
 void onRemoveIndex(int index);
 void onLycChangeSuccess(boolean res);
 void onAudioPlayComplete();


}