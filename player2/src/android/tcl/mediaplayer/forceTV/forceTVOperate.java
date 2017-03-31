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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.InputSource;

import com.tcl.common.mediaplayer.utils.Utils;

import android.util.Log;
public class forceTVOperate  {
	
	private final String TAG = "ForceTV";
	public forceTVOperate(){
		
	}
	/********************************* *********************************
	 * 打开客户端
	 * @return
	 ******************************************************************/
	public boolean openForceTVTask()
    {
    	String[] cmd ={"/data/forcetv_daemon", "-f v2 -o 9906 -b 30"};
    	Runtime runtime = Runtime.getRuntime();
        try {
			runtime.exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	return true;
    }
	/********************************* *********************************
	 * 检查客户端启动是否成功
	 * @return
	 ******************************************************************/
    public  Boolean testForceTVTask()
    {
    	//GetForcetvInfo_process a = null;
    	ForcetvCMD forcetvcmd = new ForcetvCMD();
    	//FileID fileid = new FileID();
    	ParselXml_DOM parselxml1 = new ParselXml_DOM();
    	/*get process info*/
    	parselxml1.decodeGetProcessInfo(forcetvcmd.XML_FORCETV_PROCESS_INFO);
    	if(parselxml1.getStatus() == null)
    		return false ;
    	if(parselxml1.getStatus().length() == 0 )
    		return false ;
    	else 
    		return true ;
    	
    	
    }
    
    public static  void stopAllChannels()
    {
    	//String cmd_url = "http://127.0.0.1:9906/cmd.xml?cmd=stop_all_chan";
    	String cmd_url = "http://127.0.0.1:9906/cmd.xml?cmd=stop_chan&id=";
    	Utils.printLog("ForceTV", "stopAllChannels  "+cmd_url);
    	URL url;
		try {
			url = new URL(cmd_url);
			InputSource is = new InputSource(new InputStreamReader(url.openStream(), 
				"UTF-8"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void switchChannel(String url_forcetv)
    {
		URL url;
		try {
			url = new URL(url_forcetv);
			InputSource is = new InputSource(new InputStreamReader(url.openStream(), 
				"UTF-8"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
    
    public String  readyToPlay(String mID , String mServer)
    {
    	String mAddress ="" ,mPlayPath ="";
    	if(mID.trim().length() >0 && mServer.trim().length() >0  )
    	{
    		stopAllChannels();
    		mAddress = "http://127.0.0.1:9906/cmd.xml?cmd=switch_chan&id=" + mID + "&server="+mServer;
    		switchChannel(mAddress);
    		getChanInfoListener(mID);
    		mPlayPath = "http://127.0.0.1:9906/" + mID;
    	}
    	return mPlayPath;
    }
    
    public void  getChanInfoListener(String mID  )
    {

		// TODO Auto-generated method stub
		ForcetvCMD forcetvcmd = new ForcetvCMD();

		ParselXml_DOM parselxml = new ParselXml_DOM();

		parselxml.decodeGetChanDataInfo(forcetvcmd.XML_FORCETV_CHAN_DATA_INFO + mID);

		Log.i(TAG, "ID = " + mID + " , Status = " + parselxml.getStatus() + " , VOD = " + parselxml.get_type() + " , chanID =" + parselxml.GetChanInfo());
    	
    };
    // 检测foecetv是否已启动
	public boolean checkForceTVIsStart() 
	{
		Process process = null;
//		if(process ==null)
//			return true ;
		try {
			process = Runtime.getRuntime().exec("ps");
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String forcetv_client = "forcetv_daemon", line = "", PID_name = "", pid = "";
			boolean flag = false;
			int first_blank = 0, last_blank = 0, process_cnt = 0;
			int pid_id[] = new int[1000];
			//Log.i(TAG, "line = " + forcetv_client); 
			Log.i(TAG, "line = " +  "start"); 
			while ((line = input.readLine()) != null) {
				
				if(line.trim().length()==0)
					break;
				line = line.trim().toLowerCase();
				PID_name = line.substring(line.length() - forcetv_client.length(), line.length());
				first_blank = 0;
				last_blank = 0;
				flag = false;
				if (PID_name.equalsIgnoreCase(forcetv_client)) {
					// ///////////////////////////////////////////
					for (int i = 0; i < line.length(); i++) {
						if ((line.substring(i, i + 1)).equals(" ")) {
							if (first_blank == 0)
								flag = true;
							else {
								if (first_blank > 0) {
									last_blank = i;
									break;
								}
							}
						} else {
							if (flag) {
								if (first_blank == 0)
									first_blank = i;
								flag = false;
							}
						}
						if (first_blank > 0 && last_blank > 0)
							break;
					}
					pid = line.substring(first_blank, last_blank);
					pid.trim();
					pid_id[process_cnt] = Integer.parseInt(pid);// '1265 '
					// /////////////////////////////////////////////////////////////
					process_cnt++;
				}
				line = "";
			}// end while ((line = input.readLine()) != null)
			Log.i(TAG, "line = " +  "over"); 
			if (process_cnt == 0) {
				input.close();
				return false;
			} 
			else if (process_cnt == 1) {
				input.close();
				return true;
			}
			else if (process_cnt > 1) // 如果有多个客户端打开，杀死进程
			{
				for (int i = 0; i < process_cnt; i++) {
					android.os.Process.killProcess(pid_id[i]);
				}
				input.close();
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}