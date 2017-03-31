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
package android.tcl.mediaplayer.forceTV;

public class ForcetvCMD {
	public final String XML_FORCETV_PROCESS_INFO = "http://127.0.0.1:9906/cmd.xml?cmd=query_process_info";
	public final String XML_FORCETV_CHAN_DATA_INFO = 
		"http://127.0.0.1:9906/cmd.xml?cmd=query_chan_data_info&id=";
	
	public final String XML_FORCETV_P2P_INFO = 
		"http://127.0.0.1:9906/cmd.xml?cmd=query_chan_p2p_info&id=";
}