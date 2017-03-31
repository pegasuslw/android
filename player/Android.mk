 LOCAL_PATH :=$(call my-dir)
 include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional


ifeq ($(TCL_PRODUCT_PLATFORM),mt5658)
LOCAL_STATIC_JAVA_LIBRARIES := VideoPlayer_Mtkmmp \
						VideoPlayer_TclMedia
endif
LOCAL_STATIC_JAVA_LIBRARIES += VideoPlayer_JsonScene \
						VideoPlayer_XiriFeedback \
						VideoPlayer_XiriScene

ifeq ($(HAS_TVOS_ADDON),yes)
LOCAL_JAVA_LIBRARIES += com.tcl.tvos.addon \
                        android.tclwidget \
                        com.tcl.tvmanager 
else
ifeq ($(TCL_PRODUCT_PLATFORM),mt5658)
LOCAL_JAVA_LIBRARIES += com.tcl.os.system \
                com.tcl.seeker  \
                com.tcl.device.authentication  \
                com.tcl.deviceinfo \
                android.tclwidget \
                com.tcl.tvmanager
else
LOCAL_JAVA_LIBRARIES += com.tcl.os.system \
                com.tcl.media  \
                com.tcl.seeker  \
                com.tcl.device.authentication  \
                com.tcl.deviceinfo \
                android.tclwidget \
                com.tcl.tvmanager
endif
endif
$(info LOCAL_JAVA_LIBRARIES = $(LOCAL_JAVA_LIBRARIES))



LOCAL_SRC_FILES := $(call all-java-files-under, src) \
	src/com/tcl/common/mediaplayer/aidl/BackGroundAudioCtrlCallBack.aidl \
	src/com/tcl/common/mediaplayer/aidl/BackGroundAudioCtrl.aidl \
	src/com/tcl/multiscreeninteractiontv/IDLNAService.aidl \
    src/com/tcl/multiscreeninteractiontv/IPhotoCallback.aidl \
	src/com/tcl/multiscreeninteractiontv/IPlayerCallback.aidl 
     
LOCAL_STATIC_JAVA_LIBRARIES += ComMediaPlayer_UsageStatsUtil \
VideoPlayer_appconfig
	
LOCAL_PACKAGE_NAME := VideoPlayer
LOCAL_CERTIFICATE := platform
LOCAL_PROGUARD_ENABLED := disabled
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
ifeq ($(TCL_PRODUCT_PLATFORM),mt5658)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := VideoPlayer_Mtkmmp:libs/com.mediatek.mmp.jar \
					VideoPlayer_TclMedia:libs/com.tcl.media.jar  
endif
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += ComMediaPlayer_UsageStatsUtil:libs/UsageStatsUtil.jar \
VideoPlayer_appconfig:libs/com.tcl.appconfiger.jar \
VideoPlayer_JsonScene:libs/JsonScene.jar \
VideoPlayer_XiriFeedback:libs/XiriFeedback.jar \
VideoPlayer_XiriScene:libs/XiriScene.jar


include $(BUILD_MULTI_PREBUILT)
include $(call all-makefiles-under,$(LOCAL_PATH))
