/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/tcl/multiscreeninteractiontv/IPhotoCallback.aidl
 */
package com.tcl.multiscreeninteractiontv;
public interface IPhotoCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.tcl.multiscreeninteractiontv.IPhotoCallback
{
private static final java.lang.String DESCRIPTOR = "com.tcl.multiscreeninteractiontv.IPhotoCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.tcl.multiscreeninteractiontv.IPhotoCallback interface,
 * generating a proxy if needed.
 */
public static com.tcl.multiscreeninteractiontv.IPhotoCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.tcl.multiscreeninteractiontv.IPhotoCallback))) {
return ((com.tcl.multiscreeninteractiontv.IPhotoCallback)iin);
}
return new com.tcl.multiscreeninteractiontv.IPhotoCallback.Stub.Proxy(obj);
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
this.dmr_play(_arg0);
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
case TRANSACTION_dmr_setPlayingName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.dmr_setPlayingName(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_picZoomIn:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_picZoomIn();
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_picZoomOut:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_picZoomOut();
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_picRotateLeft:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_picRotateLeft();
reply.writeNoException();
return true;
}
case TRANSACTION_dmr_picRotateRight:
{
data.enforceInterface(DESCRIPTOR);
this.dmr_picRotateRight();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.tcl.multiscreeninteractiontv.IPhotoCallback
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
@Override public void dmr_play(java.lang.String uri) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(uri);
mRemote.transact(Stub.TRANSACTION_dmr_play, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//开始播放图片

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
//停止图片播放

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
//设置播放的图片名称

@Override public void dmr_picZoomIn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_picZoomIn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//放大当前播放图片

@Override public void dmr_picZoomOut() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_picZoomOut, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//缩小当前播放图片

@Override public void dmr_picRotateLeft() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_picRotateLeft, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//当前播放图片左旋转

@Override public void dmr_picRotateRight() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dmr_picRotateRight, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_dmr_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_dmr_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_dmr_setPlayingName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_dmr_picZoomIn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_dmr_picZoomOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_dmr_picRotateLeft = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_dmr_picRotateRight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public void dmr_play(java.lang.String uri) throws android.os.RemoteException;
//开始播放图片

public void dmr_stop() throws android.os.RemoteException;
//停止图片播放

public void dmr_setPlayingName(java.lang.String str) throws android.os.RemoteException;
//设置播放的图片名称

public void dmr_picZoomIn() throws android.os.RemoteException;
//放大当前播放图片

public void dmr_picZoomOut() throws android.os.RemoteException;
//缩小当前播放图片

public void dmr_picRotateLeft() throws android.os.RemoteException;
//当前播放图片左旋转

public void dmr_picRotateRight() throws android.os.RemoteException;
}
