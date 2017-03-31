/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/mtk_player/src/com/mstar/tv/service/interfaces/ITvServiceServerTimer.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerTimer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerTimer
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerTimer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerTimer interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerTimer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerTimer))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerTimer)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerTimer.Stub.Proxy(obj);
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
case TRANSACTION_getCurTimer:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_Time _result = this.getCurTimer();
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
case TRANSACTION_setOnTimer:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_Time _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.ST_Time.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setOnTimer(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getOnTimer:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_Time _result = this.getOnTimer();
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
case TRANSACTION_setOffTimer:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_Time _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.ST_Time.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setOffTimer(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getOffTimer:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_Time _result = this.getOffTimer();
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
case TRANSACTION_setOffTimerEnable:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setOffTimerEnable(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isOffTimerEnable:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isOffTimerEnable();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setOnTimerEnable:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setOnTimerEnable(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isOnTimerEnable:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isOnTimerEnable();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setSleepMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSleepMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSleepMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE _result = this.getSleepMode();
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
case TRANSACTION_setOnTimeEvent:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_OnTime_TVDes _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.ST_OnTime_TVDes.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setOnTimeEvent(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getOnTimeEvent:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_OnTime_TVDes _result = this.getOnTimeEvent();
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
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerTimer
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
@Override public com.mstar.tv.service.aidl.ST_Time getCurTimer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ST_Time _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurTimer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ST_Time.CREATOR.createFromParcel(_reply);
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
@Override public boolean setOnTimer(com.mstar.tv.service.aidl.ST_Time time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((time!=null)) {
_data.writeInt(1);
time.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setOnTimer, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.ST_Time getOnTimer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ST_Time _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOnTimer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ST_Time.CREATOR.createFromParcel(_reply);
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
@Override public boolean setOffTimer(com.mstar.tv.service.aidl.ST_Time time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((time!=null)) {
_data.writeInt(1);
time.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setOffTimer, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.ST_Time getOffTimer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ST_Time _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOffTimer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ST_Time.CREATOR.createFromParcel(_reply);
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
@Override public boolean setOffTimerEnable(boolean bEnable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((bEnable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setOffTimerEnable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isOffTimerEnable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isOffTimerEnable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setOnTimerEnable(boolean bEnable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((bEnable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setOnTimerEnable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isOnTimerEnable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isOnTimerEnable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setSleepMode(com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE eMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMode!=null)) {
_data.writeInt(1);
eMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSleepMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE getSleepMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSleepMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE.CREATOR.createFromParcel(_reply);
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
@Override public boolean setOnTimeEvent(com.mstar.tv.service.aidl.ST_OnTime_TVDes stEvent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((stEvent!=null)) {
_data.writeInt(1);
stEvent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setOnTimeEvent, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.ST_OnTime_TVDes getOnTimeEvent() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ST_OnTime_TVDes _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOnTimeEvent, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ST_OnTime_TVDes.CREATOR.createFromParcel(_reply);
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
static final int TRANSACTION_getCurTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setOnTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getOnTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setOffTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getOffTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setOffTimerEnable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isOffTimerEnable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setOnTimerEnable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_isOnTimerEnable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setSleepMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getSleepMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_setOnTimeEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getOnTimeEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public com.mstar.tv.service.aidl.ST_Time getCurTimer() throws android.os.RemoteException;
public boolean setOnTimer(com.mstar.tv.service.aidl.ST_Time time) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.ST_Time getOnTimer() throws android.os.RemoteException;
public boolean setOffTimer(com.mstar.tv.service.aidl.ST_Time time) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.ST_Time getOffTimer() throws android.os.RemoteException;
public boolean setOffTimerEnable(boolean bEnable) throws android.os.RemoteException;
public boolean isOffTimerEnable() throws android.os.RemoteException;
public boolean setOnTimerEnable(boolean bEnable) throws android.os.RemoteException;
public boolean isOnTimerEnable() throws android.os.RemoteException;
public boolean setSleepMode(com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE eMode) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_SLEEP_TIME_STATE getSleepMode() throws android.os.RemoteException;
public boolean setOnTimeEvent(com.mstar.tv.service.aidl.ST_OnTime_TVDes stEvent) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.ST_OnTime_TVDes getOnTimeEvent() throws android.os.RemoteException;
}
