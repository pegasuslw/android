package com.tcl.multiscreeninteractiontv;

interface IPlayerCallback {
   void dmr_play(String uri,String name,String player,String album);//播放
   void dmr_pause();//暂停
   void dmr_pauseToResume();//由暂停状态恢复播放
   void dmr_stop();//停止
   void dmr_seek(long time);//seek搜寻播放
   void dmr_setMute(boolean mute);//设置静音
   boolean dmr_getMute();//获取静音状态
   void dmr_setVolume(int volume);//设置音量
   int dmr_getVolume();//获取音量
   long dmr_getMediaDuration();//获取播放文件时长
   long dmr_getCurPlayPosition();//获取当前播放位置
   void dmr_setPlayingName(String str);//设置播放的音视频名称
   void dmr_playList(in List<String> list);//播放List
}