/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/mtk_player/src/com/mstar/tv/service/interfaces/ITvServiceServerCommon.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerCommon extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerCommon
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerCommon";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerCommon interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerCommon asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerCommon))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerCommon)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerCommon.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getSourceList:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.IntArrayList _result = this.getSourceList();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_ForbidDetection:
{
data.enforceInterface(DESCRIPTOR);
this.ForbidDetection();
reply.writeNoException();
return true;
}
case TRANSACTION_AllowDetection:
{
data.enforceInterface(DESCRIPTOR);
this.AllowDetection();
reply.writeNoException();
return true;
}
case TRANSACTION_StartSourceDetection:
{
data.enforceInterface(DESCRIPTOR);
this.StartSourceDetection();
reply.writeNoException();
return true;
}
case TRANSACTION_StopSourceDetection:
{
data.enforceInterface(DESCRIPTOR);
this.StopSourceDetection();
reply.writeNoException();
return true;
}
case TRANSACTION_EnableAutoSourceSwitch:
{
data.enforceInterface(DESCRIPTOR);
this.EnableAutoSourceSwitch();
reply.writeNoException();
return true;
}
case TRANSACTION_DisableAutoSourceSwitch:
{
data.enforceInterface(DESCRIPTOR);
this.DisableAutoSourceSwitch();
reply.writeNoException();
return true;
}
case TRANSACTION_GetInputSourceStatus:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.BoolArrayList _result = this.GetInputSourceStatus();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_GetCurrentInputSource:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _result = this.GetCurrentInputSource();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_SetInputSource:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.SetInputSource(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isSignalStable:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSignalStable();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getVideoInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_VIDEO_INFO _result = this.getVideoInfo();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_programUp:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.programUp();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_programDown:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.programDown();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_openSurfaceView:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.openSurfaceView(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_setSurfaceView:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.setSurfaceView(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_closeSurfaceView:
{
data.enforceInterface(DESCRIPTOR);
this.closeSurfaceView();
reply.writeNoException();
return true;
}
case TRANSACTION_setOsdLanguage:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setOsdLanguage(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getOsdLanguage:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE _result = this.getOsdLanguage();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_setPowerOnSource:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setPowerOnSource(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPowerOnSource:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE _result = this.getPowerOnSource();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_upgrade:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.upgrade(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPresentFollowingEventInfo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
boolean _arg2;
_arg2 = (0!=data.readInt());
int _arg3;
_arg3 = data.readInt();
com.mstar.tv.service.aidl.PresentFollowingEventInfo _result = this.getPresentFollowingEventInfo(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getGpioDeviceStatus:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getGpioDeviceStatus(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setGpioDeviceStatus:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _result = this.setGpioDeviceStatus(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enterSleepMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _arg1;
_arg1 = (0!=data.readInt());
this.enterSleepMode(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_rebootSystem:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.rebootSystem(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_standbySystem:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.standbySystem(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setDeskDisplayMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setDeskDisplayMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setHotkeyEnableOrNot:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setHotkeyEnableOrNot(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setInputSourceFast:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setInputSourceFast(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerCommon
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//-------------------------------------------------------------------------------------------------
/// Get SubWindow Source List
/// @return                           \b Array for Integer              
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.IntArrayList getSourceList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.IntArrayList _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSourceList, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.IntArrayList.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void ForbidDetection() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ForbidDetection, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void AllowDetection() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_AllowDetection, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void StartSourceDetection() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_StartSourceDetection, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void StopSourceDetection() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_StopSourceDetection, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void EnableAutoSourceSwitch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_EnableAutoSourceSwitch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void DisableAutoSourceSwitch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_DisableAutoSourceSwitch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public com.mstar.tv.service.aidl.BoolArrayList GetInputSourceStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.BoolArrayList _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetInputSourceStatus, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.BoolArrayList.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE GetCurrentInputSource() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurrentInputSource, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void SetInputSource(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE source) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((source!=null)) {
_data.writeInt(1);
source.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_SetInputSource, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isSignalStable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSignalStable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//boolean isHdmiSignalMode();

@Override public com.mstar.tv.service.aidl.ST_VIDEO_INFO getVideoInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ST_VIDEO_INFO _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVideoInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ST_VIDEO_INFO.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean programUp() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_programUp, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean programDown() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_programDown, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void openSurfaceView(int x, int y, int width, int height) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(x);
_data.writeInt(y);
_data.writeInt(width);
_data.writeInt(height);
mRemote.transact(Stub.TRANSACTION_openSurfaceView, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSurfaceView(int x, int y, int width, int height) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(x);
_data.writeInt(y);
_data.writeInt(width);
_data.writeInt(height);
mRemote.transact(Stub.TRANSACTION_setSurfaceView, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void closeSurfaceView() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_closeSurfaceView, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean setOsdLanguage(com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE eLang) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eLang!=null)) {
_data.writeInt(1);
eLang.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setOsdLanguage, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE getOsdLanguage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOsdLanguage, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
    *  set power on source
    *
    */
@Override public boolean setPowerOnSource(com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE eSour) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eSour!=null)) {
_data.writeInt(1);
eSour.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setPowerOnSource, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE getPowerOnSource() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPowerOnSource, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean upgrade(java.lang.String name, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_upgrade, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.PresentFollowingEventInfo getPresentFollowingEventInfo(int serviceType, int serviceNo, boolean present, int descriptionType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.PresentFollowingEventInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(serviceType);
_data.writeInt(serviceNo);
_data.writeInt(((present)?(1):(0)));
_data.writeInt(descriptionType);
mRemote.transact(Stub.TRANSACTION_getPresentFollowingEventInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.PresentFollowingEventInfo.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getGpioDeviceStatus(int pinId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pinId);
mRemote.transact(Stub.TRANSACTION_getGpioDeviceStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setGpioDeviceStatus(int pinId, boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pinId);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setGpioDeviceStatus, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void enterSleepMode(boolean bMode, boolean bNoSignalPwDn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((bMode)?(1):(0)));
_data.writeInt(((bNoSignalPwDn)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enterSleepMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void rebootSystem(java.lang.String pwd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pwd);
mRemote.transact(Stub.TRANSACTION_rebootSystem, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void standbySystem(java.lang.String pwd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pwd);
mRemote.transact(Stub.TRANSACTION_standbySystem, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setDeskDisplayMode(java.lang.String mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(mode);
mRemote.transact(Stub.TRANSACTION_setDeskDisplayMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setHotkeyEnableOrNot(boolean isEnable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isEnable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setHotkeyEnableOrNot, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setInputSourceFast(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE source, boolean isStore) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((source!=null)) {
_data.writeInt(1);
source.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((isStore)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setInputSourceFast, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getSourceList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_ForbidDetection = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_AllowDetection = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_StartSourceDetection = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_StopSourceDetection = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_EnableAutoSourceSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_DisableAutoSourceSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_GetInputSourceStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_GetCurrentInputSource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_SetInputSource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isSignalStable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getVideoInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_programUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_programDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_openSurfaceView = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_setSurfaceView = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_closeSurfaceView = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_setOsdLanguage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getOsdLanguage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setPowerOnSource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getPowerOnSource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_upgrade = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getPresentFollowingEventInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_getGpioDeviceStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_setGpioDeviceStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_enterSleepMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_rebootSystem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_standbySystem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_setDeskDisplayMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_setHotkeyEnableOrNot = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_setInputSourceFast = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
}
//-------------------------------------------------------------------------------------------------
/// Get SubWindow Source List
/// @return                           \b Array for Integer              
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.IntArrayList getSourceList() throws android.os.RemoteException;
public void ForbidDetection() throws android.os.RemoteException;
public void AllowDetection() throws android.os.RemoteException;
public void StartSourceDetection() throws android.os.RemoteException;
public void StopSourceDetection() throws android.os.RemoteException;
public void EnableAutoSourceSwitch() throws android.os.RemoteException;
public void DisableAutoSourceSwitch() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.BoolArrayList GetInputSourceStatus() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE GetCurrentInputSource() throws android.os.RemoteException;
public void SetInputSource(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE source) throws android.os.RemoteException;
public boolean isSignalStable() throws android.os.RemoteException;
//boolean isHdmiSignalMode();

public com.mstar.tv.service.aidl.ST_VIDEO_INFO getVideoInfo() throws android.os.RemoteException;
public boolean programUp() throws android.os.RemoteException;
public boolean programDown() throws android.os.RemoteException;
public void openSurfaceView(int x, int y, int width, int height) throws android.os.RemoteException;
public void setSurfaceView(int x, int y, int width, int height) throws android.os.RemoteException;
public void closeSurfaceView() throws android.os.RemoteException;
public boolean setOsdLanguage(com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE eLang) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MEMBER_LANGUAGE getOsdLanguage() throws android.os.RemoteException;
/**
    *  set power on source
    *
    */
public boolean setPowerOnSource(com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE eSour) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_TIME_ON_TIME_SOURCE getPowerOnSource() throws android.os.RemoteException;
public boolean upgrade(java.lang.String name, java.lang.String value) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.PresentFollowingEventInfo getPresentFollowingEventInfo(int serviceType, int serviceNo, boolean present, int descriptionType) throws android.os.RemoteException;
public int getGpioDeviceStatus(int pinId) throws android.os.RemoteException;
public boolean setGpioDeviceStatus(int pinId, boolean enable) throws android.os.RemoteException;
public void enterSleepMode(boolean bMode, boolean bNoSignalPwDn) throws android.os.RemoteException;
public void rebootSystem(java.lang.String pwd) throws android.os.RemoteException;
public void standbySystem(java.lang.String pwd) throws android.os.RemoteException;
public void setDeskDisplayMode(java.lang.String mode) throws android.os.RemoteException;
public void setHotkeyEnableOrNot(boolean isEnable) throws android.os.RemoteException;
public void setInputSourceFast(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE source, boolean isStore) throws android.os.RemoteException;
}
