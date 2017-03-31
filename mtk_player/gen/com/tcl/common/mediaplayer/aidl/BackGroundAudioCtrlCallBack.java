/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/mtk_player/src/com/tcl/common/mediaplayer/aidl/BackGroundAudioCtrlCallBack.aidl
 */
package com.tcl.common.mediaplayer.aidl;
public interface BackGroundAudioCtrlCallBack extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack
{
private static final java.lang.String DESCRIPTOR = "com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack interface,
 * generating a proxy if needed.
 */
public static com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack))) {
return ((com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack)iin);
}
return new com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack.Stub.Proxy(obj);
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
case TRANSACTION_onAudioPlayChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onAudioPlayChanged(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAudioPlayPrepared:
{
data.enforceInterface(DESCRIPTOR);
this.onAudioPlayPrepared();
reply.writeNoException();
return true;
}
case TRANSACTION_onAudioPlaySeekComplete:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onAudioPlaySeekComplete(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAudioPlayBufferingUpdate:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onAudioPlayBufferingUpdate(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAudioPlayError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onAudioPlayError(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAudioPlayInfoNotify:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onAudioPlayInfoNotify(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onRemoveIndex:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onRemoveIndex(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onLycChangeSuccess:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.onLycChangeSuccess(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAudioPlayComplete:
{
data.enforceInterface(DESCRIPTOR);
this.onAudioPlayComplete();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack
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
@Override public void onAudioPlayChanged(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_onAudioPlayChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAudioPlayPrepared() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onAudioPlayPrepared, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAudioPlaySeekComplete(int currentPosition) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(currentPosition);
mRemote.transact(Stub.TRANSACTION_onAudioPlaySeekComplete, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAudioPlayBufferingUpdate(int percent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(percent);
mRemote.transact(Stub.TRANSACTION_onAudioPlayBufferingUpdate, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAudioPlayError(int errCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errCode);
mRemote.transact(Stub.TRANSACTION_onAudioPlayError, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAudioPlayInfoNotify(int infoCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(infoCode);
mRemote.transact(Stub.TRANSACTION_onAudioPlayInfoNotify, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onRemoveIndex(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_onRemoveIndex, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onLycChangeSuccess(boolean res) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((res)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onLycChangeSuccess, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAudioPlayComplete() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onAudioPlayComplete, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onAudioPlayChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onAudioPlayPrepared = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onAudioPlaySeekComplete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onAudioPlayBufferingUpdate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onAudioPlayError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onAudioPlayInfoNotify = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onRemoveIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onLycChangeSuccess = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_onAudioPlayComplete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
}
public void onAudioPlayChanged(int index) throws android.os.RemoteException;
public void onAudioPlayPrepared() throws android.os.RemoteException;
public void onAudioPlaySeekComplete(int currentPosition) throws android.os.RemoteException;
public void onAudioPlayBufferingUpdate(int percent) throws android.os.RemoteException;
public void onAudioPlayError(int errCode) throws android.os.RemoteException;
public void onAudioPlayInfoNotify(int infoCode) throws android.os.RemoteException;
public void onRemoveIndex(int index) throws android.os.RemoteException;
public void onLycChangeSuccess(boolean res) throws android.os.RemoteException;
public void onAudioPlayComplete() throws android.os.RemoteException;
}
