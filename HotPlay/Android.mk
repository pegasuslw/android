LOCAL_PATH:= $(call my-dir)
##################################################
include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES :=  \
				hotplay_android-support-v4:libs/android-support-v4.jar\
				hotplay_android-support-v7-recyclerview:libs/android-support-v7-recyclerview.jar \
				hotplay_glide:libs/glide-3.7.0.jar \
				hotplay_glide-okhttp3-integration:libs/glide-okhttp3-integration-1.4.0.jar \
				hotplay_gson:libs/gson-2.8.0.jar\
				hotplay_okhttp:libs/okhttp-3.4.2.jar \
				hotplay_okio:libs/okio-1.11.0.jar \
				hotplay_PlatformInterfaceLib:libs/PlatformInterfaceLib.jar \
				hotplay_tclrecyclerviewlib:libs/tclrecyclerviewlib.jar \
				hotplay_vodwait:libs/vodwait.jar
include $(BUILD_MULTI_PREBUILT)

#################################################
include $(CLEAR_VARS)

$(info LOCAL_JAVA_LIBRARIES = $(LOCAL_JAVA_LIBRARIES))
$(info HAS_TVOS_ADDON = $(HAS_TVOS_ADDON))

LOCAL_SRC_FILES := $(call all-java-files-under, src)
##################################################
	
LOCAL_STATIC_JAVA_LIBRARIES := \
		hotplay_android-support-v4 \
		hotplay_android-support-v7-recyclerview \
		hotplay_glide \
		hotplay_glide-okhttp3-integration \
		hotplay_gson \
		hotplay_okhttp \
		hotplay_okio \
		hotplay_PlatformInterfaceLib \
		hotplay_tclrecyclerviewlib\
		hotplay_vodwait\
##################################################

LOCAL_PACKAGE_NAME := HotPlay
LOCAL_MULTILIB := 32

LOCAL_PROGUARD_ENABLED := disabled
LOCAL_MODULE_OPTION := APPS

LOCAL_CERTIFICATE := platform
LOCAL_MODULE_TAGS := optional
LOCAL_DEX_PREOPT := true
include $(BUILD_PACKAGE)

