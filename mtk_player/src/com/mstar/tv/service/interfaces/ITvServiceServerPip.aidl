package com.mstar.tv.service.interfaces;
import com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE;
import com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE;
import com.mstar.tv.service.aidl.EN_PIP_RETURN;
import com.mstar.tv.service.aidl.IntArrayList;
import com.mstar.tv.service.aidl.EN_SCALER_WIN;

/** @hide */
interface ITvServiceServerPip
{
	//-------------------------------------------------------------------------------------------------
    /// Check PIP support main/sub inputsource combination or not
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
    /// @return                 \b TRUE: support, or FALSE: not support
    //-------------------------------------------------------------------------------------------------
    boolean checkPipSupport(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in EN_INPUT_SOURCE_TYPE eSubInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Check PIP support main/sub inputsource combination or not
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
    /// @return                 \b TRUE: support, or FALSE: not support
    //-------------------------------------------------------------------------------------------------
    boolean checkPipSupportOnSubSrc(in EN_INPUT_SOURCE_TYPE eMainInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Set PIP mode with main/sub inputsource, the sub inputsource must be tv inputsource
    /// with the position and size of the output of the sub input source
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
    /// @param ptDispWin            \b IN: the setting x, y, w, h of display window
    /// @return                 \b EN_PIP_RETURN
    //-------------------------------------------------------------------------------------------------
    EN_PIP_RETURN enablePipTV(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in EN_INPUT_SOURCE_TYPE eSubInputSrc, in VIDEO_WINDOW_TYPE dispWin);

    //------------------------------------------------------------------------------
    /// Set pip sub window size and position
    /// @param dispWin            \b IN: the setting x, y, w, h of display window
    /// @return                 \b TRUE: success, or FALSE: failure.
    //------------------------------------------------------------------------------
    boolean setPipSubwindow(in VIDEO_WINDOW_TYPE dispWin);

    //-------------------------------------------------------------------------------------------------
    /// Set PIP mode with main/MM inputsource
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// with the position and size of the output of the sub input source
    /// @param ptDispWin            \b IN: the setting x, y, w, h of display window
    /// @return                 \b EN_PIP_RETURN
    //-------------------------------------------------------------------------------------------------
    EN_PIP_RETURN enablePipMM(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in VIDEO_WINDOW_TYPE dispWin);

    //-------------------------------------------------------------------------------------------------
    /// Disable Pip and set main inputsource to full screen
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean disablePip();

    //-------------------------------------------------------------------------------------------------
    /// Get SubWindow Source List
    /// @param ispip           \b IN: choose enter pip or pop mode
    /// @return                           \b Array for Integer              
    //-------------------------------------------------------------------------------------------------
    IntArrayList getSubWindowSourceList(boolean ispip);
    //-------------------------------------------------------------------------------------------------
    /// Get MainWindow Source List
    /// @param ispip           \b IN: choose enter pip or pop mode
    /// @return                           \b Array for Integer              
    //-------------------------------------------------------------------------------------------------  
    IntArrayList getMainWindowSourceList();
    
    //-------------------------------------------------------------------------------------------------
    /// set Pip Display Focus Window
    /// @param enScalerWindow           \b IN: which window need focus
    /// @return                          s             
    //-------------------------------------------------------------------------------------------------
    void setPipDisplayFocusWindow(in EN_SCALER_WIN enScalerWindow);

    //-------------------------------------------------------------------------------------------------
    /// Check POP support main/sub inputsource combination or not
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
    /// @return                 \b TRUE: support, or FALSE: not support
    //-------------------------------------------------------------------------------------------------
    boolean checkPopSupport(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in EN_INPUT_SOURCE_TYPE eSubInputSrc);


    //-------------------------------------------------------------------------------------------------
    /// Set POP mode with main/sub inputsource, the sub inputsource must be tv inputsource
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
    /// @return                 \b EN_PIP_RETURN
    //-------------------------------------------------------------------------------------------------
    EN_PIP_RETURN enablePopTV(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in EN_INPUT_SOURCE_TYPE eSubInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Set POP mode with main/MM inputsource
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @return                 \b EN_PIP_RETURN
    //-------------------------------------------------------------------------------------------------
    EN_PIP_RETURN enablePopMM(in EN_INPUT_SOURCE_TYPE eMainInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Disable Pop and set main inputsource to full screen
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean disablePop();

    //-------------------------------------------------------------------------------------------------
    /// Check traveling mode support main/sub inputsource combination or not
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will transmit to VE for encoding
    /// @return                 \b TRUE: support, or FALSE: not support
    //-------------------------------------------------------------------------------------------------
    boolean checkTravelingModeSupport(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in EN_INPUT_SOURCE_TYPE eSubInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Set Traveling mode with main/sub inputsource, the sub inputsource must be tv inputsource
    /// The sub inputsource will transmit to the remote cellphone/PAD
    /// if input sub input source equal to the main inputsource, the main inputsource do traveling mode,
    /// else the chosen sub inputsource do traveling mode
    /// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
    /// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
    /// @return                 \b EN_PIP_RETURN
    //-------------------------------------------------------------------------------------------------
    EN_PIP_RETURN enableTravelingModeTV(in EN_INPUT_SOURCE_TYPE eMainInputSrc, in EN_INPUT_SOURCE_TYPE eSubInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Set Traveling mode with main/MM inputsource
    /// The MM will transmit to the remote cellphone/PAD
    /// @return                 \b EN_PIP_RETURN
    //-------------------------------------------------------------------------------------------------
    EN_PIP_RETURN enableTravelingModeMM(in EN_INPUT_SOURCE_TYPE eMainInputSrc);

    //-------------------------------------------------------------------------------------------------
    /// Disable ENableTravelingMode
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean disableTravelingMode();	
    
    //-------------------------------------------------------------------------------------------------
    /// Check is pip on or not
    /// @return                 \b TRUE: on, or FALSE: off.
    //-------------------------------------------------------------------------------------------------
    boolean getIsPipOn();
    
    //-------------------------------------------------------------------------------------------------
    /// set pip flag
    /// @param pipOnFlag           \b IN: set pipon flag true or false
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean setPipOnFlag(boolean pipOnFlag);
    //-------------------------------------------------------------------------------------------------
    /// enable 3d Dual View
    /// @param eMainInputSrc           \b IN: main window input source
    /// @param eSubInputSrc           \b IN: sub window input source
    /// @param mainWin           \b IN: main window size
    /// @param subWin           \b IN: sub window size
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean enable3dDualView(in EN_INPUT_SOURCE_TYPE eMainInputSrc,
			in EN_INPUT_SOURCE_TYPE eSubInputSrc,
			in VIDEO_WINDOW_TYPE mainWin, in VIDEO_WINDOW_TYPE subWin);
			
	//-------------------------------------------------------------------------------------------------
    /// disable 3d Dual View
    /// @param null           
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean disable3dDualView();
			
    //-------------------------------------------------------------------------------------------------
    /// Judge is Pip mode enabled
    /// @param null           
    /// @return                 \b TRUE: success, or FALSE: failure.
    //-------------------------------------------------------------------------------------------------
    boolean isPipModeEnabled();
			
    VIDEO_WINDOW_TYPE getPipSubwindow();
			
    
}