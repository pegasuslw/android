/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/tcl/common/mediaplayer/aidl/BackGroundAudioCtrl.aidl
 */
package com.tcl.common.mediaplayer.aidl;
public interface BackGroundAudioCtrl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl
{
private static final java.lang.String DESCRIPTOR = "com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl interface,
 * generating a proxy if needed.
 */
public static com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl))) {
return ((com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl)iin);
}
return new com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl.Stub.Proxy(obj);
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
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
this.pause();
reply.writeNoException();
return true;
}
case TRANSACTION_getPlayingAudioIndex:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPlayingAudioIndex();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getPlayList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> _result = this.getPlayList();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_isMediaPlayerAready:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isMediaPlayerAready();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPlayListFromPath:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> _result = this.getPlayListFromPath(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_stop:
{
data.enforceInterface(DESCRIPTOR);
this.stop();
reply.writeNoException();
return true;
}
case TRANSACTION_start:
{
data.enforceInterface(DESCRIPTOR);
this.start();
reply.writeNoException();
return true;
}
case TRANSACTION_seekTo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.seekTo(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getCurrentPosition:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrentPosition();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDuration:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDuration();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isPlaying:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPlaying();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_reset:
{
data.enforceInterface(DESCRIPTOR);
this.reset();
reply.writeNoException();
return true;
}
case TRANSACTION_play:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> _arg0;
_arg0 = data.createTypedArrayList(com.tcl.common.mediaplayer.aidl.MediaBean.CREATOR);
int _arg1;
_arg1 = data.readInt();
this.play(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_playNext:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.playNext();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_playPrevious:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.playPrevious();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setPlayType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setPlayType(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_openOrCloseDeskTopLylic:
{
data.enforceInterface(DESCRIPTOR);
this.openOrCloseDeskTopLylic();
reply.writeNoException();
return true;
}
case TRANSACTION_doLylicAction:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.doLylicAction(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getLylicStatu:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getLylicStatu();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack _arg0;
_arg0 = com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack _arg0;
_arg0 = com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setVolume(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_release:
{
data.enforceInterface(DESCRIPTOR);
this.release();
reply.writeNoException();
return true;
}
case TRANSACTION_isDobby:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.isDobby(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isDTS:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.isDTS(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl
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
@Override public void pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getPlayingAudioIndex() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPlayingAudioIndex, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> getPlayList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPlayList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.tcl.common.mediaplayer.aidl.MediaBean.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isMediaPlayerAready() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isMediaPlayerAready, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> getPlayListFromPath(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_getPlayListFromPath, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.tcl.common.mediaplayer.aidl.MediaBean.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void stop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void start() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_start, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void seekTo(int aMsec) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(aMsec);
mRemote.transact(Stub.TRANSACTION_seekTo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getCurrentPosition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getDuration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDuration, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isPlaying() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPlaying, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void reset() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_reset, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void play(java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> mList, int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(mList);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean playNext() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_playNext, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean playPrevious() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_playPrevious, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setPlayType(int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_setPlayType, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void openOrCloseDeskTopLylic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_openOrCloseDeskTopLylic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void doLylicAction(boolean open) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((open)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_doLylicAction, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getLylicStatu() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLylicStatu, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void registerCallback(com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterCallback(com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setVolume(int vol) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vol);
mRemote.transact(Stub.TRANSACTION_setVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void release() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_release, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int isDobby(int audioTrack) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(audioTrack);
mRemote.transact(Stub.TRANSACTION_isDobby, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int isDTS(int audioTrack) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(audioTrack);
mRemote.transact(Stub.TRANSACTION_isDTS, _data, _reply, 0);
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
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getPlayingAudioIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getPlayList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isMediaPlayerAready = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getPlayListFromPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_start = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_seekTo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getCurrentPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getDuration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isPlaying = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_reset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_playNext = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_playPrevious = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_setPlayType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_openOrCloseDeskTopLylic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_doLylicAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getLylicStatu = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_getVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_setVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_release = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_isDobby = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_isDTS = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
}
public void pause() throws android.os.RemoteException;
public int getPlayingAudioIndex() throws android.os.RemoteException;
public java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> getPlayList() throws android.os.RemoteException;
public boolean isMediaPlayerAready() throws android.os.RemoteException;
public java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> getPlayListFromPath(java.lang.String path) throws android.os.RemoteException;
public void stop() throws android.os.RemoteException;
public void start() throws android.os.RemoteException;
public void seekTo(int aMsec) throws android.os.RemoteException;
public int getCurrentPosition() throws android.os.RemoteException;
public int getDuration() throws android.os.RemoteException;
public boolean isPlaying() throws android.os.RemoteException;
public void reset() throws android.os.RemoteException;
public void play(java.util.List<com.tcl.common.mediaplayer.aidl.MediaBean> mList, int index) throws android.os.RemoteException;
public boolean playNext() throws android.os.RemoteException;
public boolean playPrevious() throws android.os.RemoteException;
public void setPlayType(int type) throws android.os.RemoteException;
public void openOrCloseDeskTopLylic() throws android.os.RemoteException;
public void doLylicAction(boolean open) throws android.os.RemoteException;
public boolean getLylicStatu() throws android.os.RemoteException;
public void registerCallback(com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack callback) throws android.os.RemoteException;
public void unregisterCallback(com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack callback) throws android.os.RemoteException;
public int getVolume() throws android.os.RemoteException;
public void setVolume(int vol) throws android.os.RemoteException;
public void release() throws android.os.RemoteException;
public int isDobby(int audioTrack) throws android.os.RemoteException;
public int isDTS(int audioTrack) throws android.os.RemoteException;
}
