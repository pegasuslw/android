 LOCAL_PATH :=$(call my-dir)
 include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

ifeq ($(TCL_PRODUCT_PLATFORM),mt5658)
LOCAL_STATIC_JAVA_LIBRARIES := PVRPlayer_Mtkmmp \
						PVRPlayer_TclMedia
endif
TVOS_PATH := $(ANDROID_BUILD_TOP)/$(call intermediates-dir-for,JAVA_LIBRARIES,com.tcl.tvos.addon,,COMMON)/classes.jar

HAS_TVOS_ADDON:=$(shell test -e $(TVOS_PATH) && echo yes)

ifeq ($(HAS_TVOS_ADDON),yes)
LOCAL_JAVA_LIBRARIES += com.tcl.tvos.addon \
                        android.tclwidget \
                        com.tcl.tvmanager 
else
LOCAL_JAVA_LIBRARIES += com.tcl.devicemanager  \
                com.tcl.media  \
                com.tcl.os.system \
                com.tcl.os.storage  \
                com.tcl.device.authentication  \
                com.tcl.deviceinfo \
                android.tclwidget \
                com.tcl.tvmanager 
endif


LOCAL_SRC_FILES := $(call all-java-files-under, src) \
     
	
LOCAL_PACKAGE_NAME := PvrPlayer
LOCAL_CERTIFICATE := platform
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)

ifeq ($(TCL_PRODUCT_PLATFORM),mt5658)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := PVRPlayer_Mtkmmp:libs/com.mediatek.mmp.jar \
					PVRPlayer_TclMedia:libs/com.tcl.media.jar  
endif
include $(BUILD_MULTI_PREBUILT)
include $(call all-makefiles-under,$(LOCAL_PATH))
