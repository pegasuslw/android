/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player/src/com/tcl/multiscreeninteractiontv/IDLNAService.aidl
 */
package com.tcl.multiscreeninteractiontv;
public interface IDLNAService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.tcl.multiscreeninteractiontv.IDLNAService
{
private static final java.lang.String DESCRIPTOR = "com.tcl.multiscreeninteractiontv.IDLNAService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.tcl.multiscreeninteractiontv.IDLNAService interface,
 * generating a proxy if needed.
 */
public static com.tcl.multiscreeninteractiontv.IDLNAService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.tcl.multiscreeninteractiontv.IDLNAService))) {
return ((com.tcl.multiscreeninteractiontv.IDLNAService)iin);
}
return new com.tcl.multiscreeninteractiontv.IDLNAService.Stub.Proxy(obj);
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
case TRANSACTION_registerPlayerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.tcl.multiscreeninteractiontv.IPlayerCallback _arg0;
_arg0 = com.tcl.multiscreeninteractiontv.IPlayerCallback.Stub.asInterface(data.readStrongBinder());
this.registerPlayerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterPlayerCallback:
{
data.enforceInterface(DESCRIPTOR);
this.unregisterPlayerCallback();
reply.writeNoException();
return true;
}
case TRANSACTION_registerPhotoCallback:
{
data.enforceInterface(DESCRIPTOR);
com.tcl.multiscreeninteractiontv.IPhotoCallback _arg0;
_arg0 = com.tcl.multiscreeninteractiontv.IPhotoCallback.Stub.asInterface(data.readStrongBinder());
this.registerPhotoCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterPhotoCallback:
{
data.enforceInterface(DESCRIPTOR);
this.unregisterPhotoCallback();
reply.writeNoException();
return true;
}
case TRANSACTION_setPlayStatus:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setPlayStatus(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getRecordAddr:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getRecordAddr();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_isHaveNewPCM:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isHaveNewPCM();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.tcl.multiscreeninteractiontv.IDLNAService
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
@Override public void registerPlayerCallback(com.tcl.multiscreeninteractiontv.IPlayerCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerPlayerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//注册音视频播放器回调接口

@Override public void unregisterPlayerCallback() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unregisterPlayerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//取消音视频播放器回调注册

@Override public void registerPhotoCallback(com.tcl.multiscreeninteractiontv.IPhotoCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerPhotoCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//注册图片播放回调接口

@Override public void unregisterPhotoCallback() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unregisterPhotoCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//取消图片播放回调注册

@Override public void setPlayStatus(java.lang.String status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(status);
mRemote.transact(Stub.TRANSACTION_setPlayStatus, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//设置当前播放状态:播放-PLAYING, 停止-STOPPED, 暂停-PAUSED_PLAYBACK, 正在传输中-TRANSITIONING

@Override public java.lang.String getRecordAddr() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRecordAddr, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//为语音助手留的接口，可以访问保存的文件路径

@Override public boolean isHaveNewPCM() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isHaveNewPCM, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_registerPlayerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterPlayerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerPhotoCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unregisterPhotoCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setPlayStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getRecordAddr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isHaveNewPCM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public void registerPlayerCallback(com.tcl.multiscreeninteractiontv.IPlayerCallback cb) throws android.os.RemoteException;
//注册音视频播放器回调接口

public void unregisterPlayerCallback() throws android.os.RemoteException;
//取消音视频播放器回调注册

public void registerPhotoCallback(com.tcl.multiscreeninteractiontv.IPhotoCallback cb) throws android.os.RemoteException;
//注册图片播放回调接口

public void unregisterPhotoCallback() throws android.os.RemoteException;
//取消图片播放回调注册

public void setPlayStatus(java.lang.String status) throws android.os.RemoteException;
//设置当前播放状态:播放-PLAYING, 停止-STOPPED, 暂停-PAUSED_PLAYBACK, 正在传输中-TRANSITIONING

public java.lang.String getRecordAddr() throws android.os.RemoteException;
//为语音助手留的接口，可以访问保存的文件路径

public boolean isHaveNewPCM() throws android.os.RemoteException;
}
