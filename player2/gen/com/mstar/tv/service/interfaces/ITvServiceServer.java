/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/mstar/tv/service/interfaces/ITvServiceServer.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServer
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServer interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServer))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServer)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServer.Stub.Proxy(obj);
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
case TRANSACTION_getAudioManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerAudio _result = this.getAudioManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getChannelManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerChannel _result = this.getChannelManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getCommonManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerCommon _result = this.getCommonManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getPictureManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerPicture _result = this.getPictureManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getPipManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerPip _result = this.getPipManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getS3DManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerS3D _result = this.getS3DManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_getTimerManager:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.interfaces.ITvServiceServerTimer _result = this.getTimerManager();
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_onPreKeyDown:
{
data.enforceInterface(DESCRIPTOR);
android.view.KeyEvent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.view.KeyEvent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.onPreKeyDown(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onKeyDown:
{
data.enforceInterface(DESCRIPTOR);
android.view.KeyEvent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.view.KeyEvent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.onKeyDown(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_shutdown:
{
data.enforceInterface(DESCRIPTOR);
this.shutdown();
reply.writeNoException();
return true;
}
case TRANSACTION_resume:
{
data.enforceInterface(DESCRIPTOR);
this.resume();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServer
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
@Override public com.mstar.tv.service.interfaces.ITvServiceServerAudio getAudioManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerAudio _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAudioManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerAudio.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.interfaces.ITvServiceServerChannel getChannelManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerChannel _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getChannelManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerChannel.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.interfaces.ITvServiceServerCommon getCommonManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerCommon _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCommonManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerCommon.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.interfaces.ITvServiceServerPicture getPictureManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerPicture _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPictureManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerPicture.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.interfaces.ITvServiceServerPip getPipManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerPip _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPipManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerPip.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.interfaces.ITvServiceServerS3D getS3DManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerS3D _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getS3DManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerS3D.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.interfaces.ITvServiceServerTimer getTimerManager() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.interfaces.ITvServiceServerTimer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTimerManager, _data, _reply, 0);
_reply.readException();
_result = com.mstar.tv.service.interfaces.ITvServiceServerTimer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onPreKeyDown(android.view.KeyEvent event) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((event!=null)) {
_data.writeInt(1);
event.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onPreKeyDown, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onKeyDown(android.view.KeyEvent event) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((event!=null)) {
_data.writeInt(1);
event.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onKeyDown, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void shutdown() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_shutdown, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getAudioManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getChannelManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getCommonManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getPictureManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getPipManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getS3DManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getTimerManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onPreKeyDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_onKeyDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_shutdown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_resume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
}
public com.mstar.tv.service.interfaces.ITvServiceServerAudio getAudioManager() throws android.os.RemoteException;
public com.mstar.tv.service.interfaces.ITvServiceServerChannel getChannelManager() throws android.os.RemoteException;
public com.mstar.tv.service.interfaces.ITvServiceServerCommon getCommonManager() throws android.os.RemoteException;
public com.mstar.tv.service.interfaces.ITvServiceServerPicture getPictureManager() throws android.os.RemoteException;
public com.mstar.tv.service.interfaces.ITvServiceServerPip getPipManager() throws android.os.RemoteException;
public com.mstar.tv.service.interfaces.ITvServiceServerS3D getS3DManager() throws android.os.RemoteException;
public com.mstar.tv.service.interfaces.ITvServiceServerTimer getTimerManager() throws android.os.RemoteException;
public boolean onPreKeyDown(android.view.KeyEvent event) throws android.os.RemoteException;
public boolean onKeyDown(android.view.KeyEvent event) throws android.os.RemoteException;
public void shutdown() throws android.os.RemoteException;
public void resume() throws android.os.RemoteException;
}
