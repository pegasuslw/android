/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/mstar/tv/service/interfaces/ITvServiceServerTvManager.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerTvManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerTvManager
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerTvManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerTvManager interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerTvManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerTvManager))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerTvManager)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerTvManager.Stub.Proxy(obj);
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
case TRANSACTION_onDtvReadyPopupDialog:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.onDtvReadyPopupDialog(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onScartMuteOsdMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.onScartMuteOsdMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onSignalLock:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.onSignalLock(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onSignalUnlock:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.onSignalUnlock(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onUnityEvent:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.onUnityEvent(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerTvManager
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
@Override public boolean onDtvReadyPopupDialog(int arg0, int arg1, int arg2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(arg0);
_data.writeInt(arg1);
_data.writeInt(arg2);
mRemote.transact(Stub.TRANSACTION_onDtvReadyPopupDialog, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onScartMuteOsdMode(int arg0) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(arg0);
mRemote.transact(Stub.TRANSACTION_onScartMuteOsdMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onSignalLock(int arg0) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(arg0);
mRemote.transact(Stub.TRANSACTION_onSignalLock, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onSignalUnlock(int arg0) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(arg0);
mRemote.transact(Stub.TRANSACTION_onSignalUnlock, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onUnityEvent(int arg0, int arg1, int arg2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(arg0);
_data.writeInt(arg1);
_data.writeInt(arg2);
mRemote.transact(Stub.TRANSACTION_onUnityEvent, _data, _reply, 0);
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
static final int TRANSACTION_onDtvReadyPopupDialog = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onScartMuteOsdMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onSignalLock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onSignalUnlock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onUnityEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public boolean onDtvReadyPopupDialog(int arg0, int arg1, int arg2) throws android.os.RemoteException;
public boolean onScartMuteOsdMode(int arg0) throws android.os.RemoteException;
public boolean onSignalLock(int arg0) throws android.os.RemoteException;
public boolean onSignalUnlock(int arg0) throws android.os.RemoteException;
public boolean onUnityEvent(int arg0, int arg1, int arg2) throws android.os.RemoteException;
}
