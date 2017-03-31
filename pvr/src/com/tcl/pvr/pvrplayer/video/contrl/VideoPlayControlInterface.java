/**
 * ------------------------------------------------------------------
 *  Copyright (c) 2011 TCL, All Rights Reserved.
 *  -----------------------------------------------------------------
 *  
 * @author bailing /qiaobl@tcl.com/2011.09.21
 * @JDK version: 1.6
 * @brief: provide the play and control of video and audio;
 * @version:v1.0
 */
package com.tcl.pvr.pvrplayer.video.contrl;

import java.util.List;

import com.tcl.tvmanager.vo.DtvPvrMediaFileInfo;

import android.view.SurfaceHolder;


public interface VideoPlayControlInterface {

	public void pause();

	public int getPlayingVideoIndex();

	//public List<MediaBean> getPlayList();

	public boolean isMediaPlayerPrepared();

	public boolean closeMediaControl();
	public void stop();

	public void start();

	public void seekTo(int aMsec);

	public void maltiSpeedPlayNext();

	public void maltiSpeedPlayPrevious();

	public int getCurrentPosition();

	public int getDuration();

	public boolean isPlaying();

	public void reset();

	public void play(List<String> mList, int index);

	public boolean playNext();

	public boolean playPrevious();

	public boolean releaseMediaPlayer();

	public void setPlayType(int type);
	
	public int getPlayType();

	public void registerCallback(VideoPlayControlCallback callback);

	public void unregisterCallback();
	
	public boolean isVOD();	
	
	
	public int getSubtitleNms();
	public int getAudioTrackNms();
	public void setSubtitleNms(short nums);
	public void setAudioTrackNms(short nums);
	public int getCurrentSubtitleNms();
	public int getCurrentAudioTrackNms();
	public String getCurrentAudioTrackName();
	public String getCurrentSubtitleText();
	public void setSubtiteSurface(SurfaceHolder holder);
	
	public boolean isFinishInit();
	public void setFinishInit(boolean isfinish);
	
	public int isDobby(int audioTrack);
	public int isDTS(int audioTrack);
	public int[] getMediaInfo();
	
}
