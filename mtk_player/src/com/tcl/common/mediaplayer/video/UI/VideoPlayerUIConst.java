package com.tcl.common.mediaplayer.video.UI;

public interface VideoPlayerUIConst {
	
	public static final String ATV_OSD_CLOSE = "TVAPP_OSDCLOSE";
	public static final String ATV_OSD_OPEN = "TVAPP_OSDOPEN";
	public static String KEY_OF_ADD_TYPE = "key_of_add_type";
	public static String KEY_OF_COVER_TYPE = "key_of_cover_type";
	public static String NOTIFY_VIDEO_3D = "com.android.tcl.videoexit";
	public static final int INTENET_TIMEOUT = 60000;
	
	public static final String MY_SPREFS_FILE = "VideoSettingFile";
	public static final String VEDIO_PLAY_TYPE = "VideoPlayType";
	public static final String VEDIO_NATURAL_LIGHT = "VideoNaturalLight";
	public static final String VEDIO_SAVED_BACKLIGHT = "VideoSaveBackLight";
	
	public static final String COLLECTION_OPERATE_DONE = "com.tcl.mediaplayer.collection.result";//通知收藏结果的广播；
    public static final String OPERATE_RESULT = "collection.result";//收藏结果参数字符串；
    public static final int Succ_COLLECTION = 1;//收藏成功；
    public static final int Fail_COLLECTION = 2;//收藏失败；
    public static final String COLLECTION_OPERATE = "com.tcl.mediaplayer.collection.operation";//通知开始收藏操作的广播；
    public static final String ADD_OR_DEL = "collection.operation";//收藏操作参数字符串；
    public static final int ADD_COLLECTION = 1;//收藏；
    public static final int DEL_COLLECTION = 2;//取消收藏；
    public static final String COLLECTION_ADDRESS = "collection.url";//收藏媒体地址参数字符串；
    public static final String COLLECTION_APP_MODE = "com.tcl.sis.onlinevideo.app_mode";//收藏媒体类型参数字符串；
    public static final int COLLECTION_APP_MODE_ONLINEMEDIA = 1; //在线影视；
    public static final int COLLECTION_APP_MODE_ONLINEDU = 2;//在线教育；
    
    
    
    public static final int SHOW_MENU_3D = 1;//为１表示菜单键中按下3d键需要再次弹出菜单；
    public static final int SHOW_VOLUME_3D = 2;// 为２表示按下3d遥控键，此时音量条在，则需要再次弹出音量条；
    public static final int NO_MENU_3D = 3;// 为３则表示按下3d遥控键，返回时不需要弹任何菜单；
    public static final int NO_3D= 0;//为其他值则表示未按３d键；
	
    public static final int CLICKED_VOLUME = 3;//为３表示菜单键中按下音量键，需要再次获得焦点；
    public static final int CLICKED_3D = 2;//为２表示菜单键中按下３D键，需要再次获得焦点；
    public static final int CLICKED_SAVE = 1;//为１表示菜单键中按下收藏键，需要再次获得焦点；
    public static final int CLICKED_NO = 0;//为０或者其他值表示菜单键中无需要弹出菜单的键按下；
    public static final int CLICKED_ADVANCE = 4;//为4表示菜单键中按下字幕和音轨键，需要再次获得焦点；
    public static final int CLICKED_IMAGE = 5;//为5表示菜单键中按下图像键，需要再次获得焦点；
    public static final int CLICKED_VIDEOINFO = 6;//为5表示菜单键中按下图像键，需要再次获得焦点；
}
