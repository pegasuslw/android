#!/bin/bash

#1  进入bin目录

echo -----------进入bin目录--------------
cd /home/pegasus/tmp/pvr/bin 
ls -l *.apk
if [ 0 -ne $? ];then
    echo 没有生成apk
    exit
fi
cp /home/pegasus/tmp/pvr/bin/PvrPlayer_ui3.0.apk /home/pegasus/tcl_signature/

#2. 
echo ----------进行签名---------------
cd /home/pegasus/tcl_signature/
./sign.sh PvrPlayer_ui3.0.apk

ls -l  PvrPlayer_ui3.0_sign.apk
if [ 0 -ne $? ];then
    echo 签名不成功
    exit
fi

#3 push

echo --------进行安装-------------
RESULT=`adb devices | sed '1d'`
if [ "" = "$RESULT" ];
    then adb connect 10.120.142.106 
    sleep 1
fi
adb install -r PvrPlayer_ui3.0_sign.apk

