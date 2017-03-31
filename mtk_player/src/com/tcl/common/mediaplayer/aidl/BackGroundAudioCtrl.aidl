package com.tcl.common.mediaplayer.aidl;

import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack;
interface BackGroundAudioCtrl{
	
 void pause();	
	
 int getPlayingAudioIndex();
	
List<MediaBean> getPlayList();
	
 boolean isMediaPlayerAready();

List<MediaBean> getPlayListFromPath(String path);
 void stop();

 void start();

 void seekTo(int aMsec);

 int getCurrentPosition();
	
 int getDuration();
	
boolean isPlaying();
	
void reset();
	
void play(in List<MediaBean> mList,in int index);
	
 boolean playNext();
	
 boolean playPrevious();
	
 void setPlayType(int type);
	
void openOrCloseDeskTopLylic();
	
 void doLylicAction(boolean open);
	
boolean getLylicStatu();
	
 void registerCallback(BackGroundAudioCtrlCallBack callback);
	
void unregisterCallback(BackGroundAudioCtrlCallBack callback);

int getVolume();

void setVolume(int vol);
void release();
int isDobby(int audioTrack);
int isDTS(int audioTrack);



}