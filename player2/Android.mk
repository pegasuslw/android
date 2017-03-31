 LOCAL_PATH :=$(call my-dir)
 include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

ifeq ($(HAS_TVOS_ADDON),yes)
LOCAL_JAVA_LIBRARIES += com.tcl.tvos.addon \
                        android.tclwidget \
                        com.tcl.tvmanager 
else
LOCAL_JAVA_LIBRARIES += com.tcl.os.system \
                com.tcl.devicemanager  \
                com.tcl.net.ethernet  \
                com.tcl.media  \
                com.tcl.net.pppoe  \
                com.tcl.seeker  \
                com.tcl.os.storage  \
                com.tcl.device.authentication  \
                com.tcl.deviceinfo \
                android.tclwidget \
                com.tcl.tvmanager 
endif
$(info LOCAL_JAVA_LIBRARIES = $(LOCAL_JAVA_LIBRARIES))
LOCAL_STATIC_JAVA_LIBRARIES := ComMediaPlayer_UsageStatsUtil

LOCAL_SRC_FILES := $(call all-java-files-under, src) \
	src/com/tcl/common/mediaplayer/aidl/BackGroundAudioCtrlCallBack.aidl \
	src/com/tcl/common/mediaplayer/aidl/BackGroundAudioCtrl.aidl \
	src/com/tcl/multiscreeninteractiontv/IDLNAService.aidl \
    src/com/tcl/multiscreeninteractiontv/IPhotoCallback.aidl \
	src/com/tcl/multiscreeninteractiontv/IPlayerCallback.aidl 
     
	
LOCAL_PACKAGE_NAME := ComMediaPlayer
LOCAL_CERTIFICATE := platform
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := ComMediaPlayer_UsageStatsUtil:libs/UsageStatsUtil.jar

include $(BUILD_MULTI_PREBUILT)
include $(call all-makefiles-under,$(LOCAL_PATH))
