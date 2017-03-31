/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/mstar/tv/service/interfaces/ITvServiceServerPip.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerPip extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerPip
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerPip";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerPip interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerPip asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerPip))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerPip)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerPip.Stub.Proxy(obj);
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
case TRANSACTION_checkPipSupport:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.checkPipSupport(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_checkPipSupportOnSubSrc:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.checkPipSupportOnSubSrc(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enablePipTV:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mstar.tv.service.aidl.EN_PIP_RETURN _result = this.enablePipTV(_arg0, _arg1, _arg2);
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
case TRANSACTION_setPipSubwindow:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setPipSubwindow(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enablePipMM:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mstar.tv.service.aidl.EN_PIP_RETURN _result = this.enablePipMM(_arg0, _arg1);
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
case TRANSACTION_disablePip:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.disablePip();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSubWindowSourceList:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
com.mstar.tv.service.aidl.IntArrayList _result = this.getSubWindowSourceList(_arg0);
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
case TRANSACTION_getMainWindowSourceList:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.IntArrayList _result = this.getMainWindowSourceList();
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
case TRANSACTION_setPipDisplayFocusWindow:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SCALER_WIN _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SCALER_WIN.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setPipDisplayFocusWindow(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_checkPopSupport:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.checkPopSupport(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enablePopTV:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mstar.tv.service.aidl.EN_PIP_RETURN _result = this.enablePopTV(_arg0, _arg1);
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
case TRANSACTION_enablePopMM:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_PIP_RETURN _result = this.enablePopMM(_arg0);
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
case TRANSACTION_disablePop:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.disablePop();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_checkTravelingModeSupport:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.checkTravelingModeSupport(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableTravelingModeTV:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mstar.tv.service.aidl.EN_PIP_RETURN _result = this.enableTravelingModeTV(_arg0, _arg1);
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
case TRANSACTION_enableTravelingModeMM:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_PIP_RETURN _result = this.enableTravelingModeMM(_arg0);
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
case TRANSACTION_disableTravelingMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.disableTravelingMode();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getIsPipOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getIsPipOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setPipOnFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setPipOnFlag(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enable3dDualView:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _arg3;
if ((0!=data.readInt())) {
_arg3 = com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _result = this.enable3dDualView(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_disable3dDualView:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.disable3dDualView();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isPipModeEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPipModeEnabled();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPipSubwindow:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _result = this.getPipSubwindow();
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
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerPip
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
/// Check PIP support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

@Override public boolean checkPipSupport(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_checkPipSupport, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Check PIP support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

@Override public boolean checkPipSupportOnSubSrc(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_checkPipSupportOnSubSrc, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Set PIP mode with main/sub inputsource, the sub inputsource must be tv inputsource
/// with the position and size of the output of the sub input source
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @param ptDispWin            \b IN: the setting x, y, w, h of display window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePipTV(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE dispWin) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_PIP_RETURN _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((dispWin!=null)) {
_data.writeInt(1);
dispWin.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enablePipTV, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_PIP_RETURN.CREATOR.createFromParcel(_reply);
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
//------------------------------------------------------------------------------
/// Set pip sub window size and position
/// @param dispWin            \b IN: the setting x, y, w, h of display window
/// @return                 \b TRUE: success, or FALSE: failure.
//------------------------------------------------------------------------------

@Override public boolean setPipSubwindow(com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE dispWin) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((dispWin!=null)) {
_data.writeInt(1);
dispWin.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setPipSubwindow, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Set PIP mode with main/MM inputsource
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// with the position and size of the output of the sub input source
/// @param ptDispWin            \b IN: the setting x, y, w, h of display window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePipMM(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE dispWin) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_PIP_RETURN _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((dispWin!=null)) {
_data.writeInt(1);
dispWin.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enablePipMM, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_PIP_RETURN.CREATOR.createFromParcel(_reply);
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
//-------------------------------------------------------------------------------------------------
/// Disable Pip and set main inputsource to full screen
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean disablePip() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disablePip, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Get SubWindow Source List
/// @param ispip           \b IN: choose enter pip or pop mode
/// @return                           \b Array for Integer              
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.IntArrayList getSubWindowSourceList(boolean ispip) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.IntArrayList _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((ispip)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_getSubWindowSourceList, _data, _reply, 0);
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
//-------------------------------------------------------------------------------------------------
/// Get MainWindow Source List
/// @param ispip           \b IN: choose enter pip or pop mode
/// @return                           \b Array for Integer              
//-------------------------------------------------------------------------------------------------  

@Override public com.mstar.tv.service.aidl.IntArrayList getMainWindowSourceList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.IntArrayList _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMainWindowSourceList, _data, _reply, 0);
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
//-------------------------------------------------------------------------------------------------
/// set Pip Display Focus Window
/// @param enScalerWindow           \b IN: which window need focus
/// @return                          s             
//-------------------------------------------------------------------------------------------------

@Override public void setPipDisplayFocusWindow(com.mstar.tv.service.aidl.EN_SCALER_WIN enScalerWindow) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((enScalerWindow!=null)) {
_data.writeInt(1);
enScalerWindow.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setPipDisplayFocusWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//-------------------------------------------------------------------------------------------------
/// Check POP support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

@Override public boolean checkPopSupport(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_checkPopSupport, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Set POP mode with main/sub inputsource, the sub inputsource must be tv inputsource
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePopTV(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_PIP_RETURN _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enablePopTV, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_PIP_RETURN.CREATOR.createFromParcel(_reply);
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
//-------------------------------------------------------------------------------------------------
/// Set POP mode with main/MM inputsource
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePopMM(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_PIP_RETURN _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enablePopMM, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_PIP_RETURN.CREATOR.createFromParcel(_reply);
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
//-------------------------------------------------------------------------------------------------
/// Disable Pop and set main inputsource to full screen
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean disablePop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disablePop, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Check traveling mode support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will transmit to VE for encoding
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

@Override public boolean checkTravelingModeSupport(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_checkTravelingModeSupport, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Set Traveling mode with main/sub inputsource, the sub inputsource must be tv inputsource
/// The sub inputsource will transmit to the remote cellphone/PAD
/// if input sub input source equal to the main inputsource, the main inputsource do traveling mode,
/// else the chosen sub inputsource do traveling mode
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.EN_PIP_RETURN enableTravelingModeTV(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_PIP_RETURN _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enableTravelingModeTV, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_PIP_RETURN.CREATOR.createFromParcel(_reply);
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
//-------------------------------------------------------------------------------------------------
/// Set Traveling mode with main/MM inputsource
/// The MM will transmit to the remote cellphone/PAD
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

@Override public com.mstar.tv.service.aidl.EN_PIP_RETURN enableTravelingModeMM(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_PIP_RETURN _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enableTravelingModeMM, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_PIP_RETURN.CREATOR.createFromParcel(_reply);
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
//-------------------------------------------------------------------------------------------------
/// Disable ENableTravelingMode
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean disableTravelingMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disableTravelingMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Check is pip on or not
/// @return                 \b TRUE: on, or FALSE: off.
//-------------------------------------------------------------------------------------------------

@Override public boolean getIsPipOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getIsPipOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// set pip flag
/// @param pipOnFlag           \b IN: set pipon flag true or false
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean setPipOnFlag(boolean pipOnFlag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((pipOnFlag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setPipOnFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// enable 3d Dual View
/// @param eMainInputSrc           \b IN: main window input source
/// @param eSubInputSrc           \b IN: sub window input source
/// @param mainWin           \b IN: main window size
/// @param subWin           \b IN: sub window size
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean enable3dDualView(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE mainWin, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE subWin) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMainInputSrc!=null)) {
_data.writeInt(1);
eMainInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((eSubInputSrc!=null)) {
_data.writeInt(1);
eSubInputSrc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((mainWin!=null)) {
_data.writeInt(1);
mainWin.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((subWin!=null)) {
_data.writeInt(1);
subWin.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_enable3dDualView, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// disable 3d Dual View
/// @param null           
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean disable3dDualView() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disable3dDualView, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//-------------------------------------------------------------------------------------------------
/// Judge is Pip mode enabled
/// @param null           
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

@Override public boolean isPipModeEnabled() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPipModeEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE getPipSubwindow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPipSubwindow, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE.CREATOR.createFromParcel(_reply);
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
}
static final int TRANSACTION_checkPipSupport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_checkPipSupportOnSubSrc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_enablePipTV = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setPipSubwindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_enablePipMM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_disablePip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getSubWindowSourceList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getMainWindowSourceList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setPipDisplayFocusWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_checkPopSupport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_enablePopTV = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_enablePopMM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_disablePop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_checkTravelingModeSupport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_enableTravelingModeTV = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_enableTravelingModeMM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_disableTravelingMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getIsPipOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_setPipOnFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_enable3dDualView = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_disable3dDualView = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_isPipModeEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getPipSubwindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
}
//-------------------------------------------------------------------------------------------------
/// Check PIP support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

public boolean checkPipSupport(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Check PIP support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

public boolean checkPipSupportOnSubSrc(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Set PIP mode with main/sub inputsource, the sub inputsource must be tv inputsource
/// with the position and size of the output of the sub input source
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @param ptDispWin            \b IN: the setting x, y, w, h of display window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePipTV(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE dispWin) throws android.os.RemoteException;
//------------------------------------------------------------------------------
/// Set pip sub window size and position
/// @param dispWin            \b IN: the setting x, y, w, h of display window
/// @return                 \b TRUE: success, or FALSE: failure.
//------------------------------------------------------------------------------

public boolean setPipSubwindow(com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE dispWin) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Set PIP mode with main/MM inputsource
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// with the position and size of the output of the sub input source
/// @param ptDispWin            \b IN: the setting x, y, w, h of display window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePipMM(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE dispWin) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Disable Pip and set main inputsource to full screen
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean disablePip() throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Get SubWindow Source List
/// @param ispip           \b IN: choose enter pip or pop mode
/// @return                           \b Array for Integer              
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.IntArrayList getSubWindowSourceList(boolean ispip) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Get MainWindow Source List
/// @param ispip           \b IN: choose enter pip or pop mode
/// @return                           \b Array for Integer              
//-------------------------------------------------------------------------------------------------  

public com.mstar.tv.service.aidl.IntArrayList getMainWindowSourceList() throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// set Pip Display Focus Window
/// @param enScalerWindow           \b IN: which window need focus
/// @return                          s             
//-------------------------------------------------------------------------------------------------

public void setPipDisplayFocusWindow(com.mstar.tv.service.aidl.EN_SCALER_WIN enScalerWindow) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Check POP support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

public boolean checkPopSupport(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Set POP mode with main/sub inputsource, the sub inputsource must be tv inputsource
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePopTV(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Set POP mode with main/MM inputsource
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.EN_PIP_RETURN enablePopMM(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Disable Pop and set main inputsource to full screen
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean disablePop() throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Check traveling mode support main/sub inputsource combination or not
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will transmit to VE for encoding
/// @return                 \b TRUE: support, or FALSE: not support
//-------------------------------------------------------------------------------------------------

public boolean checkTravelingModeSupport(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Set Traveling mode with main/sub inputsource, the sub inputsource must be tv inputsource
/// The sub inputsource will transmit to the remote cellphone/PAD
/// if input sub input source equal to the main inputsource, the main inputsource do traveling mode,
/// else the chosen sub inputsource do traveling mode
/// @param  eMainInputSrc       \b IN: Inputsource that will display on main window
/// @param  eSubInputSrc       \b IN: Inputsource that will display on sub window
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.EN_PIP_RETURN enableTravelingModeTV(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Set Traveling mode with main/MM inputsource
/// The MM will transmit to the remote cellphone/PAD
/// @return                 \b EN_PIP_RETURN
//-------------------------------------------------------------------------------------------------

public com.mstar.tv.service.aidl.EN_PIP_RETURN enableTravelingModeMM(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Disable ENableTravelingMode
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean disableTravelingMode() throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Check is pip on or not
/// @return                 \b TRUE: on, or FALSE: off.
//-------------------------------------------------------------------------------------------------

public boolean getIsPipOn() throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// set pip flag
/// @param pipOnFlag           \b IN: set pipon flag true or false
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean setPipOnFlag(boolean pipOnFlag) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// enable 3d Dual View
/// @param eMainInputSrc           \b IN: main window input source
/// @param eSubInputSrc           \b IN: sub window input source
/// @param mainWin           \b IN: main window size
/// @param subWin           \b IN: sub window size
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean enable3dDualView(com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eMainInputSrc, com.mstar.tv.service.aidl.EN_INPUT_SOURCE_TYPE eSubInputSrc, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE mainWin, com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE subWin) throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// disable 3d Dual View
/// @param null           
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean disable3dDualView() throws android.os.RemoteException;
//-------------------------------------------------------------------------------------------------
/// Judge is Pip mode enabled
/// @param null           
/// @return                 \b TRUE: success, or FALSE: failure.
//-------------------------------------------------------------------------------------------------

public boolean isPipModeEnabled() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.VIDEO_WINDOW_TYPE getPipSubwindow() throws android.os.RemoteException;
}
