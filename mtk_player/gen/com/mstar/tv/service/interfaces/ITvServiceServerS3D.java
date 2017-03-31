/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/mtk_player/src/com/mstar/tv/service/interfaces/ITvServiceServerS3D.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerS3D extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerS3D
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerS3D";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerS3D interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerS3D asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerS3D))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerS3D)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerS3D.Stub.Proxy(obj);
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
case TRANSACTION_setSelfAdaptiveDetect:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSelfAdaptiveDetect(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSelfAdaptiveDetect:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT _result = this.getSelfAdaptiveDetect();
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
case TRANSACTION_setSelfAdaptiveLevel:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSelfAdaptiveLevel(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSelfAdaptiveLevel:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL _result = this.getSelfAdaptiveLevel();
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
case TRANSACTION_setDisplayFormat:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setDisplayFormat(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDisplayFormat:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT _result = this.getDisplayFormat();
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
case TRANSACTION_set3DTo2D:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.set3DTo2D(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDisplay3DTo2DMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D _result = this.getDisplay3DTo2DMode();
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
case TRANSACTION_set3DDepthMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.set3DDepthMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_get3DDepthMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.get3DDepthMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_set3DOffsetMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.set3DOffsetMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_get3DOffsetMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.get3DOffsetMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setAutoStartMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setAutoStartMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getAutoStartMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART _result = this.getAutoStartMode();
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
case TRANSACTION_set3DOutputAspectMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.set3DOutputAspectMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_get3DOutputAspectMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT _result = this.get3DOutputAspectMode();
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
case TRANSACTION_setLRViewSwitch:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setLRViewSwitch(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getLRViewSwitch:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH _result = this.getLRViewSwitch();
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
case TRANSACTION_setDisplayFormatForUI:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setDisplayFormatForUI(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getCurrent3dFormatIndex:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrent3dFormatIndex();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerS3D
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
/**
	 * 
	 * set self adaptive detect
	 * 
	 * @param EN_ThreeD_Video_SELFADAPTIVE_DETECT
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setSelfAdaptiveDetect(com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT selfAdaptiveDetect) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((selfAdaptiveDetect!=null)) {
_data.writeInt(1);
selfAdaptiveDetect.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSelfAdaptiveDetect, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get self adaptive detect
	 * 
	 * @return EN_ThreeD_Video_SELFADAPTIVE_DETECT
	 * 
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT getSelfAdaptiveDetect() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSelfAdaptiveDetect, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT.CREATOR.createFromParcel(_reply);
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
	 * 
	 * set self adaptive level
	 * 
	 * @param EN_ThreeD_Video_SELFADAPTIVE_LEVEL
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setSelfAdaptiveLevel(com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL selfAdaptiveLevel) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((selfAdaptiveLevel!=null)) {
_data.writeInt(1);
selfAdaptiveLevel.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSelfAdaptiveLevel, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get self adaptive level
	 * 
	 * @return EN_ThreeD_Video_SELFADAPTIVE_LEVEL
	 * 
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL getSelfAdaptiveLevel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSelfAdaptiveLevel, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL.CREATOR.createFromParcel(_reply);
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
	 * 
	 * set display format
	 * 
	 * @param EN_ThreeD_Video_DISPLAYFORMAT
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setDisplayFormat(com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT displayFormat) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((displayFormat!=null)) {
_data.writeInt(1);
displayFormat.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setDisplayFormat, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get display format
	 * 
	 * @return EN_ThreeD_Video_DISPLAYFORMAT
	 * 
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT getDisplayFormat() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplayFormat, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT.CREATOR.createFromParcel(_reply);
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
	 * 
	 * set display 3dto2d mode
	 * 
	 * @param EN_ThreeD_Video_3DTO2D
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean set3DTo2D(com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D displayMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((displayMode!=null)) {
_data.writeInt(1);
displayMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_set3DTo2D, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get display 3dto2d mode
	 * 
	 * @return EN_ThreeD_Video_3DTO2D
	 * 
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D getDisplay3DTo2DMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplay3DTo2DMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D.CREATOR.createFromParcel(_reply);
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
	 * 
	 * set 3D depth mode
	 * 
	 * @param EN_ThreeD_Video_3DDEPTH
	 *            (level 0-31)
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean set3DDepthMode(int mode3DDepth) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode3DDepth);
mRemote.transact(Stub.TRANSACTION_set3DDepthMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get 3D depth mode
	 * 
	 * @return EN_ThreeD_Video_3DDEPTH
	 * 
	 */
@Override public int get3DDepthMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_get3DDepthMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * set 3D offset mode
	 * 
	 * @param EN_ThreeD_Video_3DOFFSET
	 *            (level 0-31)
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean set3DOffsetMode(int mode3DOffset) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode3DOffset);
mRemote.transact(Stub.TRANSACTION_set3DOffsetMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get 3D offset mode
	 * 
	 * @return EN_ThreeD_Video_3DOFFSET
	 * 
	 */
@Override public int get3DOffsetMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_get3DOffsetMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * set auto start mode
	 * 
	 * @param EN_ThreeD_Video_AUTOSTART
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setAutoStartMode(com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART autoStartMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((autoStartMode!=null)) {
_data.writeInt(1);
autoStartMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setAutoStartMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get auto start mode
	 * 
	 * @return EN_ThreeD_Video_AUTOSTART
	 * 
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART getAutoStartMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAutoStartMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART.CREATOR.createFromParcel(_reply);
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
	 * 
	 * set 3D output aspect mode
	 * 
	 * @param EN_ThreeD_Video_3DOUTPUTASPECT
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean set3DOutputAspectMode(com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT outputAspectMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((outputAspectMode!=null)) {
_data.writeInt(1);
outputAspectMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_set3DOutputAspectMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get 3D output aspect mode
	 * 
	 * @return EN_ThreeD_Video_3DOUTPUTASPECT
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT get3DOutputAspectMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_get3DOutputAspectMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT.CREATOR.createFromParcel(_reply);
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
	 * 
	 * set LR view switch enable or disable
	 * 
	 * @param EN_ThreeD_Video_LRVIEWSWITCH
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setLRViewSwitch(com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH LRViewSwitchMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((LRViewSwitchMode!=null)) {
_data.writeInt(1);
LRViewSwitchMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setLRViewSwitch, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * get LR view switch status (enable or disable)
	 * 
	 * @return EN_ThreeD_Video_LRVIEWSWITCH
	 */
@Override public com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH getLRViewSwitch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLRViewSwitch, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH.CREATOR.createFromParcel(_reply);
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
     * 
     * set 3d display format for UI
     * 
     * @param 3d display format index
     * 
     * @return none
     */
@Override public void setDisplayFormatForUI(int threedDisplayFormatIdx) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(threedDisplayFormatIdx);
mRemote.transact(Stub.TRANSACTION_setDisplayFormatForUI, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 
     * get current 3d format index
     * 
     * @return current 3d format index
     */
@Override public int getCurrent3dFormatIndex() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrent3dFormatIndex, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_setSelfAdaptiveDetect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getSelfAdaptiveDetect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setSelfAdaptiveLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getSelfAdaptiveLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setDisplayFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getDisplayFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_set3DTo2D = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getDisplay3DTo2DMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_set3DDepthMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_get3DDepthMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_set3DOffsetMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_get3DOffsetMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_setAutoStartMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getAutoStartMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_set3DOutputAspectMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_get3DOutputAspectMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_setLRViewSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getLRViewSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_setDisplayFormatForUI = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_getCurrent3dFormatIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
}
/**
	 * 
	 * set self adaptive detect
	 * 
	 * @param EN_ThreeD_Video_SELFADAPTIVE_DETECT
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setSelfAdaptiveDetect(com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT selfAdaptiveDetect) throws android.os.RemoteException;
/**
	 * 
	 * get self adaptive detect
	 * 
	 * @return EN_ThreeD_Video_SELFADAPTIVE_DETECT
	 * 
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_DETECT getSelfAdaptiveDetect() throws android.os.RemoteException;
/**
	 * 
	 * set self adaptive level
	 * 
	 * @param EN_ThreeD_Video_SELFADAPTIVE_LEVEL
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setSelfAdaptiveLevel(com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL selfAdaptiveLevel) throws android.os.RemoteException;
/**
	 * 
	 * get self adaptive level
	 * 
	 * @return EN_ThreeD_Video_SELFADAPTIVE_LEVEL
	 * 
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_SELFADAPTIVE_LEVEL getSelfAdaptiveLevel() throws android.os.RemoteException;
/**
	 * 
	 * set display format
	 * 
	 * @param EN_ThreeD_Video_DISPLAYFORMAT
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setDisplayFormat(com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT displayFormat) throws android.os.RemoteException;
/**
	 * 
	 * get display format
	 * 
	 * @return EN_ThreeD_Video_DISPLAYFORMAT
	 * 
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_DISPLAYFORMAT getDisplayFormat() throws android.os.RemoteException;
/**
	 * 
	 * set display 3dto2d mode
	 * 
	 * @param EN_ThreeD_Video_3DTO2D
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean set3DTo2D(com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D displayMode) throws android.os.RemoteException;
/**
	 * 
	 * get display 3dto2d mode
	 * 
	 * @return EN_ThreeD_Video_3DTO2D
	 * 
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_3DTO2D getDisplay3DTo2DMode() throws android.os.RemoteException;
/**
	 * 
	 * set 3D depth mode
	 * 
	 * @param EN_ThreeD_Video_3DDEPTH
	 *            (level 0-31)
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean set3DDepthMode(int mode3DDepth) throws android.os.RemoteException;
/**
	 * 
	 * get 3D depth mode
	 * 
	 * @return EN_ThreeD_Video_3DDEPTH
	 * 
	 */
public int get3DDepthMode() throws android.os.RemoteException;
/**
	 * 
	 * set 3D offset mode
	 * 
	 * @param EN_ThreeD_Video_3DOFFSET
	 *            (level 0-31)
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean set3DOffsetMode(int mode3DOffset) throws android.os.RemoteException;
/**
	 * 
	 * get 3D offset mode
	 * 
	 * @return EN_ThreeD_Video_3DOFFSET
	 * 
	 */
public int get3DOffsetMode() throws android.os.RemoteException;
/**
	 * 
	 * set auto start mode
	 * 
	 * @param EN_ThreeD_Video_AUTOSTART
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setAutoStartMode(com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART autoStartMode) throws android.os.RemoteException;
/**
	 * 
	 * get auto start mode
	 * 
	 * @return EN_ThreeD_Video_AUTOSTART
	 * 
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_AUTOSTART getAutoStartMode() throws android.os.RemoteException;
/**
	 * 
	 * set 3D output aspect mode
	 * 
	 * @param EN_ThreeD_Video_3DOUTPUTASPECT
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean set3DOutputAspectMode(com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT outputAspectMode) throws android.os.RemoteException;
/**
	 * 
	 * get 3D output aspect mode
	 * 
	 * @return EN_ThreeD_Video_3DOUTPUTASPECT
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_3DOUTPUTASPECT get3DOutputAspectMode() throws android.os.RemoteException;
/**
	 * 
	 * set LR view switch enable or disable
	 * 
	 * @param EN_ThreeD_Video_LRVIEWSWITCH
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setLRViewSwitch(com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH LRViewSwitchMode) throws android.os.RemoteException;
/**
	 * 
	 * get LR view switch status (enable or disable)
	 * 
	 * @return EN_ThreeD_Video_LRVIEWSWITCH
	 */
public com.mstar.tv.service.aidl.EN_ThreeD_Video_LRVIEWSWITCH getLRViewSwitch() throws android.os.RemoteException;
/**
     * 
     * set 3d display format for UI
     * 
     * @param 3d display format index
     * 
     * @return none
     */
public void setDisplayFormatForUI(int threedDisplayFormatIdx) throws android.os.RemoteException;
/**
     * 
     * get current 3d format index
     * 
     * @return current 3d format index
     */
public int getCurrent3dFormatIndex() throws android.os.RemoteException;
}
