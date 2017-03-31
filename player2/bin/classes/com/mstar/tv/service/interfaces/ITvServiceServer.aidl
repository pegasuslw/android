package com.mstar.tv.service.interfaces;

import com.mstar.tv.service.interfaces.ITvServiceServerAudio;
import com.mstar.tv.service.interfaces.ITvServiceServerChannel;
import com.mstar.tv.service.interfaces.ITvServiceServerCommon;
import com.mstar.tv.service.interfaces.ITvServiceServerPicture;
import com.mstar.tv.service.interfaces.ITvServiceServerPip;
import com.mstar.tv.service.interfaces.ITvServiceServerS3D;
import com.mstar.tv.service.interfaces.ITvServiceServerTimer;
import android.view.KeyEvent;

/** @hide */
interface ITvServiceServer{

    ITvServiceServerAudio getAudioManager();
    ITvServiceServerChannel getChannelManager();
    ITvServiceServerCommon getCommonManager();
    ITvServiceServerPicture getPictureManager();
    ITvServiceServerPip getPipManager();
    ITvServiceServerS3D getS3DManager();
    ITvServiceServerTimer getTimerManager();
    boolean onPreKeyDown(in KeyEvent event);
    boolean onKeyDown(in KeyEvent event);
    
    void shutdown();
    void resume();
}