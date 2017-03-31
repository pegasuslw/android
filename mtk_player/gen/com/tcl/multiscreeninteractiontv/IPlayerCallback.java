/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/mtk_player/src/com/tcl/multiscreeninteractiontv/IPlayerCallback.aidl
 */
package com.tcl.multiscreeninteractiontv;
public interface IPlayerCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.tcl.multiscreeninteractiontv.IPlayerCallback
{
private static final java.lang.String DESCRIPTOR = "com.tcl.multiscreeninteractiontv.IPlayerCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.tcl.multiscreeninteractiontv.IPlayerCallback interface,
 * generating a proxy if needed.
 */
public static com.tcl.multiscreeninteractiontv.IPlayerCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.tcl.multiscreeninteractiontv.IPlayerCallback))) {
return ((com.tcl.multiscreeninteractiontv.IPlayerCallback)iin);
}
return new com.tcl.multiscreeninteractiontv.IPlayerCallback.Stub.Proxy(obj);
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
case TRANSACTION_dmr_play:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
this.dmr_play(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_pause:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_pause();
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_pauseToResume:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_pauseToResume();
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_stop:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_stop();
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_seek:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
this.dmr_seek(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_setMute:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.dmr_setMute(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_getMute:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dmr_getMute();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dmr_setVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.dmr_setVolume(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_getVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.dmr_getVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_dmr_getMediaDuration:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.dmr_getMediaDuration();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_dmr_getCurPlayPosition:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.dmr_getCurPlayPosition();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_dmr_setPlayingName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.dmr_setPlayingName(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_playList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
this.dmr_playList(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.tcl.multiscreeninteractiontv.IPlayerCallback
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
@Override public void dmr_play(java.lang.String uri, java.lang.String name, java.lang.String player, java.lang.String album) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(uri);
_data.writeString(name);
_data.writeString(player);
_data.writeString(album);
mRemote.transact(Stub.TRANSACTION_dmr_play, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//播放

@Override public void dmr_pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_pause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//暂停

@Override public void dmr_pauseToResume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_pauseToResume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//由暂停状态恢复播放

@Override public void dmr_stop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//停止

@Override public void dmr_seek(long time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(time);
mRemote.transact(Stub.TRANSACTION_dmr_seek, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//seek搜寻播放

@Override public void dmr_setMute(boolean mute) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((mute)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_dmr_setMute, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//设置静音

@Override public boolean dmr_getMute() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_getMute, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取静音状态

@Override public void dmr_setVolume(int volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(volume);
mRemote.transact(Stub.TRANSACTION_dmr_setVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//设置音量

@Override public int dmr_getVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_getVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取音量

@Override public long dmr_getMediaDuration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_getMediaDuration, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取播放文件时长

@Override public long dmr_getCurPlayPosition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_getCurPlayPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//获取当前播放位置

@Override public void dmr_setPlayingName(java.lang.String str) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(str);
mRemote.transact(Stub.TRANSACTION_dmr_setPlayingName, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//设置播放的音视频名称

@Override public void dmr_playList(java.util.List<java.lang.String> list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(list);
mRemote.transact(Stub.TRANSACTION_dmr_playList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_dmr_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_dmr_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_dmr_pauseToResume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_dmr_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_dmr_seek = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_dmr_setMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_dmr_getMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_dmr_setVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_dmr_getVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_dmr_getMediaDuration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_dmr_getCurPlayPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_dmr_setPlayingName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_dmr_playList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public void dmr_play(java.lang.String uri, java.lang.String name, java.lang.String player, java.lang.String album) throws android.os.RemoteException;
//播放

public void dmr_pause() throws android.os.RemoteException;
//暂停

public void dmr_pauseToResume() throws android.os.RemoteException;
//由暂停状态恢复播放

public void dmr_stop() throws android.os.RemoteException;
//停止

public void dmr_seek(long time) throws android.os.RemoteException;
//seek搜寻播放

public void dmr_setMute(boolean mute) throws android.os.RemoteException;
//设置静音

public boolean dmr_getMute() throws android.os.RemoteException;
//获取静音状态

public void dmr_setVolume(int volume) throws android.os.RemoteException;
//设置音量

public int dmr_getVolume() throws android.os.RemoteException;
//获取音量

public long dmr_getMediaDuration() throws android.os.RemoteException;
//获取播放文件时长

public long dmr_getCurPlayPosition() throws android.os.RemoteException;
//获取当前播放位置

public void dmr_setPlayingName(java.lang.String str) throws android.os.RemoteException;
//设置播放的音视频名称

public void dmr_playList(java.util.List<java.lang.String> list) throws android.os.RemoteException;
}
