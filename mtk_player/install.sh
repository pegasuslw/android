#!/bin/bash

#1  进入bin目录

echo -----------进入bin目录--------------
cd ./bin 
BIN_PATH=`pwd`
APK_NAME=`ls *.apk`
if [ 0 -ne $? ];then
    echo 没有生成apk
    exit
fi
cp ${BIN_PATH}/${APK_NAME} /home/pegasus/tcl_signature/

#2. 
echo ----------进行签名---------------
cd /home/pegasus/tcl_signature/
./sign.sh ${APK_NAME} 

SIGN_APK_NAME=${APK_NAME%.apk}_sign.apk

ls -l  ${SIGN_APK_NAME}
if [ 0 -ne $? ];then
    echo 签名不成功
    exit
fi

#3 push

echo --------进行安装-------------
RESULT=`adb devices | sed '1d'`
if [ "" = "$RESULT" ];
    then adb connect 192.168.0.103
    sleep 1
fi
adb install -r ${SIGN_APK_NAME}

