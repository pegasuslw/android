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
package android.tcl.mediaplayer.forcetv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class ParselXml_DOM {
	
	
	private String TAG = "DOMParsel";
	private DocumentBuilderFactory domfac;
	public String sReason = "";
	//process info
	String process_id = null;
	String process_reason = null;
	String process_ret = null;
	String process_mem_cahce = null;
	public String strVOD;
	
	public ParselXml_DOM()
	{
		domfac = DocumentBuilderFactory.newInstance();
		process_id = null;
		process_reason = null;
		process_ret = null;
		process_mem_cahce = null;
	}
	/*get process info and decode it
	 * @param CMDurl: get info url
	 * */
	private String ProcessID = null;
	public void decodeGetProcessInfo(String CMDurl)
	{
		sReason = null;
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
//			InputStream is = new FileInputStream(xmlurl);
			Log.v(TAG, "xml url is : " +CMDurl);  
			URL url = new URL(CMDurl);
			InputSource is = new InputSource(new InputStreamReader(url.openStream(), "UTF-8"));
			Document doc = dombuilder.parse(is);
			Element root =  doc.getDocumentElement();
			NodeList books = root.getChildNodes();                     
			Log.v(TAG,"nodelist num is:" + books.getLength());
			if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    Log.v(TAG,"nodelist type :" + book.getNodeType());
                    if (book.getNodeType() == Node.ELEMENT_NODE) {
                        if(i == 0)
                        {
                    	String ret = book.getAttributes().getNamedItem(
                                "ret").getNodeValue();                        
                    	process_ret = ret;
                    	Log.v(TAG,"returnvalue  is:" + process_ret);
                        //l_playlistname.add(name);
                        String net_status =  book.getAttributes().getNamedItem(
                        "reason").getNodeValue(); 
                        sReason = net_status;
                        Log.v(TAG, "reason: " +net_status); 
                        process_reason = net_status;
                        }else{
                        	String process_id1 =  book.getAttributes().getNamedItem(
                            "pid").getNodeValue(); 
                            Log.v(TAG, "process ID: " +process_id1); 
                            process_id = process_id1;
                            String men_cahce =  book.getAttributes().getNamedItem(
                            "mem_max").getNodeValue(); 
                            Log.v(TAG, "men_max memery: " +men_cahce); 
                            process_mem_cahce = men_cahce;
                        }
                     
                    }
                }
              //  return Info_process;
			}
			//return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
	}	
	public String GetProcessID()
	{
		return process_id;
	}
	
	/*
	 *  Decode the xml to get Chandatainfo.
	 *  @Param CMDurl:decode xml url;
	 * */
	private String ChanInfo = null;
	public void decodeGetChanDataInfo(String CMDurl)
	{
		sReason = "";
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			Log.v(TAG, "xml url is : " +CMDurl);  
			URL url = new URL(CMDurl);
			InputSource is = new InputSource(new InputStreamReader(url.openStream(), "UTF-8"));
			Document doc = dombuilder.parse(is);
			Element root =  doc.getDocumentElement();
			NodeList books = root.getChildNodes();                     
			Log.v(TAG,"nodelist num is:" + books.getLength());
			if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    Log.v(TAG,"nodelist type is:" + book.getNodeType());
                    if (book.getNodeType() == Node.ELEMENT_NODE) {
                        if(i == 0)
                        {
                    	String ret = book.getAttributes().getNamedItem(
                                "ret").getNodeValue();                        
                    	process_ret = ret;
                        String net_status =  book.getAttributes().getNamedItem(
                        "reason").getNodeValue(); 
                        sReason = net_status;
                        Log.v(TAG, "reason: " +net_status); 
                        process_reason = net_status;
                        }else if(i == 1){
                        	String chan_id =  book.getAttributes().getNamedItem(
                            "id").getNodeValue(); 
                            Log.v(TAG, "chan file ID : " +chan_id); 
                            
                            ChanInfo =  chan_id;
                            
                            String vod_num =  book.getAttributes().getNamedItem(
                            "vod").getNodeValue(); 
                            Log.v(TAG, "chan file ID: " +vod_num); 
                            
                            if(vod_num.equalsIgnoreCase("0"))
                            strVOD = "直播";
                            else
                            	strVOD = "点播";
                            
                            ChanInfo += " , vod =" + vod_num;
                            
                           // Log.v("ggggggggggggggggggggggggggggggggg", "chan file byterate : " +vod_num); 
                            
                            
                            String bytera =  book.getAttributes().getNamedItem(
                            "byterate").getNodeValue(); 
                            Log.v(TAG, "chan file byterate : " +bytera); 
                            ChanInfo += "/" + bytera;
                            String avg_size =  book.getAttributes().getNamedItem(
                            "avg_packet_size").getNodeValue(); 
                            Log.v(TAG, "mchan file avg packet size: " +avg_size); 
                            ChanInfo += "/" + avg_size;
                            String filesize =  book.getAttributes().getNamedItem(
                            "file_size").getNodeValue(); 
                            Log.v(TAG, "chan file: " +filesize); 
                            ChanInfo += "/" + filesize;
                            
                            
                            for (Node node = book.getFirstChild(); node != null;
                            node = node.getNextSibling())
                            {
                            	Log.v(TAG,"getnode name: " + node.getNodeName());
                                if(node.getNodeType()==Node.ELEMENT_NODE){
                            	String  timebegin = node.getAttributes().getNamedItem(
                    			"begin").getNodeValue();
                            	Log.v(TAG,"begin time :" + timebegin);
                            	ChanInfo += "begin time :" + timebegin;
                            	String timeend = node.getAttributes().getNamedItem(
                    			"end").getNodeValue();
                            	Log.v(TAG,"end time : " + timeend);
                            	ChanInfo += "end time :" + timeend;
                            	String timeplay = node.getAttributes().getNamedItem(
                            	"play").getNodeValue();
                            	Log.v(TAG,"play time: " + timeplay);
                            	ChanInfo += "play time :" + timeplay;
                            	String cache_time = node.getAttributes().getNamedItem(
                            	"cache_time").getNodeValue();
                            	Log.v(TAG,"cache_time: " + cache_time);
                            	ChanInfo += "cache_time :" + cache_time;
                            	return;
                                }
                            }
                        }                    
                    }
                }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}	
	public String GetChanInfo()
	{
		return ChanInfo;
	}
	/*get p2p info and decode it 
	 * @param url:url*/
	public void getChanP2pinfo(String path)
	{
		sReason = "";
		try{
			DocumentBuilder dombuilder;dombuilder = domfac.newDocumentBuilder();
			Log.v(TAG, "xml url : " +path);  
			URL url = new URL(path);
			InputSource is = new InputSource(new InputStreamReader(url.openStream(), "UTF-8"));
			Document doc;
			doc = dombuilder.parse(is);
			Element root =  doc.getDocumentElement();
			NodeList books = root.getChildNodes(); 
			Log.v(TAG,"nodelist num :" + books.getLength());
			if(books != null)
			{
				for(int i = 0; i < books.getLength(); i++)
				{
					Node book = books.item(i);
                    Log.v(TAG,"nodelist type is:" + book.getNodeType());
                    if (book.getNodeType() == Node.ELEMENT_NODE) {
                        if(i == 0)
                        {
                    	String ret = book.getAttributes().getNamedItem(
                                "ret").getNodeValue();                        
                    	process_ret = ret;
                        String net_status =  book.getAttributes().getNamedItem(
                        "reason").getNodeValue(); 
                        sReason = net_status;
                        Log.v(TAG, "Connet http server: " +net_status); 
                        process_reason = net_status;
                        }else if(i == 1){
                        	String chan_id =  book.getAttributes().getNamedItem(
                            "id").getNodeValue(); 
                            Log.v(TAG, "chan file ID : " +chan_id); 
                            for (Node node = book.getFirstChild(); node != null;
                            node = node.getNextSibling())
                            {
                            	Log.v(TAG,"getnode name: " + node.getNodeName());
                                if(node.getNodeType()==Node.ELEMENT_NODE){
                                	String  lasttime = node.getAttributes().getNamedItem(
                                	"lasttime").getNodeValue();
                                	Log.v(TAG,"last time :" + lasttime);
                                	String flowbyte = node.getAttributes().getNamedItem(
                                	"flowbyte").getNodeValue();
                                	Log.v(TAG,"flowbyte : " + flowbyte);
                                	String flowpack = node.getAttributes().getNamedItem(
                                	"flowpack").getNodeValue();
                                	Log.v(TAG,"flowpack : " + flowpack);
//                                	for(Node cnode = node.getFirstChild(); cnode != null;
//                                 	cnode = cnode.getNextSibling())
//                                	{
//                                		Log.v(TAG,"getnode name: " + node.getNodeName());
//                                        if(node.getNodeType()==Node.ELEMENT_NODE){
//                                        	String  sip = node.getAttributes().getNamedItem(
//                                        	"ip").getNodeValue();
//                                        	Log.v(TAG,"begin time :" + sip);
//                                        	String port = node.getAttributes().getNamedItem(
//                                        	"port").getNodeValue();
//                                        	Log.v(TAG,"port : " + flowbyte);
//                                        	String cflowbyte = node.getAttributes().getNamedItem(
//                                        	"flowpack").getNodeValue();
//                                        	Log.v(TAG,"current IP flowpack: " + cflowbyte);
//                                        	String cflowpack = node.getAttributes().getNamedItem(
//                                        	"flowpack").getNodeValue();
//                                        	Log.v(TAG,"current IP flowpack: " + cflowpack);
//                                        }
//                                	}
                                }
                            }                    
                        }
                    }
				}
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getStatus()
	{
		return sReason;
	}
	public String get_type()
	{
		return strVOD ;
	}
}
