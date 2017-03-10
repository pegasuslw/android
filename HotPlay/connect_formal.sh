#!/bin/bash

adb shell rm /data/data/com.tcl.hotplay/httptest.txt
adb shell killall -9 com.tcl.hotplay
adb shell am start -n com.tcl.hotplay/.MainActivity

adb shell ls /data/data/com.tcl.hotplay/httptest.txt
