/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/mstar/tv/service/interfaces/ITvServiceServerPicture.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerPicture extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerPicture
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerPicture";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerPicture interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerPicture asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerPicture))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerPicture)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerPicture.Stub.Proxy(obj);
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
case TRANSACTION_setPictureModeIdx:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_PICTURE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MS_PICTURE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setPictureModeIdx(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPictureModeIdx:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_PICTURE _result = this.getPictureModeIdx();
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
case TRANSACTION_setVideoArc:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setVideoArc(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getVideoArc:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type _result = this.getVideoArc();
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
case TRANSACTION_execVideoItem:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_VIDEOITEM _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MS_VIDEOITEM.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
boolean _result = this.execVideoItem(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getVideoItem:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_VIDEOITEM _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MS_VIDEOITEM.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.getVideoItem(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setBacklight:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setBacklight(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getBacklight:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBacklight();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setColorTempIdx:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setColorTempIdx(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getColorTempIdx:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP _result = this.getColorTempIdx();
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
case TRANSACTION_setColorTempPara:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setColorTempPara(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getColorTempPara:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA _result = this.getColorTempPara();
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
case TRANSACTION_setMpegNR:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_MPEG_NR _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MS_MPEG_NR.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setMpegNR(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getMpegNR:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_MPEG_NR _result = this.getMpegNR();
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
case TRANSACTION_setNR:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_NR _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MS_NR.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setNR(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getNR:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MS_NR _result = this.getNR();
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
case TRANSACTION_setMWEStatus:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MWE_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MWE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setMWEStatus(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getMWEStatus:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MWE_TYPE _result = this.getMWEStatus();
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
case TRANSACTION_getPCHPos:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPCHPos();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPCHPos:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPCHPos(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPCVPos:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPCVPos();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPCVPos:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPCVPos(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPCClock:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPCClock();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPCClock:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPCClock(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPCPhase:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPCPhase();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPCPhase:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPCPhase(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setMFC:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setMFC(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getMFC:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMFC();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_ExecAutoPc:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.ExecAutoPc();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDlcAverageLuma:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDlcAverageLuma();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_freezeImage:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.freezeImage();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_unFreezeImage:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.unFreezeImage();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setSwingLevel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setSwingLevel(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDlcHistogramMax:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDlcHistogramMax();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDlcHistogramMin:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDlcHistogramMin();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerPicture
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
@Override public boolean setPictureModeIdx(com.mstar.tv.service.aidl.EN_MS_PICTURE ePicMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((ePicMode!=null)) {
_data.writeInt(1);
ePicMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setPictureModeIdx, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_MS_PICTURE getPictureModeIdx() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MS_PICTURE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPictureModeIdx, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MS_PICTURE.CREATOR.createFromParcel(_reply);
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
@Override public boolean setVideoArc(com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type eArcIdx) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eArcIdx!=null)) {
_data.writeInt(1);
eArcIdx.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setVideoArc, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type getVideoArc() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVideoArc, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type.CREATOR.createFromParcel(_reply);
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
@Override public boolean execVideoItem(com.mstar.tv.service.aidl.EN_MS_VIDEOITEM eIndex, int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eIndex!=null)) {
_data.writeInt(1);
eIndex.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_execVideoItem, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getVideoItem(com.mstar.tv.service.aidl.EN_MS_VIDEOITEM eIndex) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eIndex!=null)) {
_data.writeInt(1);
eIndex.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getVideoItem, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setBacklight(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_setBacklight, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getBacklight() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBacklight, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setColorTempIdx(com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP eColorTemp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eColorTemp!=null)) {
_data.writeInt(1);
eColorTemp.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setColorTempIdx, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP getColorTempIdx() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getColorTempIdx, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP.CREATOR.createFromParcel(_reply);
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
@Override public boolean setColorTempPara(com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA stColorTemp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((stColorTemp!=null)) {
_data.writeInt(1);
stColorTemp.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setColorTempPara, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA getColorTempPara() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getColorTempPara, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA.CREATOR.createFromParcel(_reply);
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
@Override public boolean setMpegNR(com.mstar.tv.service.aidl.EN_MS_MPEG_NR eMpNRIdx) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMpNRIdx!=null)) {
_data.writeInt(1);
eMpNRIdx.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setMpegNR, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_MS_MPEG_NR getMpegNR() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MS_MPEG_NR _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMpegNR, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MS_MPEG_NR.CREATOR.createFromParcel(_reply);
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
@Override public boolean setNR(com.mstar.tv.service.aidl.EN_MS_NR eNRIdx) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eNRIdx!=null)) {
_data.writeInt(1);
eNRIdx.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setNR, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_MS_NR getNR() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MS_NR _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getNR, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MS_NR.CREATOR.createFromParcel(_reply);
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
@Override public boolean setMWEStatus(com.mstar.tv.service.aidl.EN_MWE_TYPE eMWE) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eMWE!=null)) {
_data.writeInt(1);
eMWE.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setMWEStatus, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_MWE_TYPE getMWEStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MWE_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMWEStatus, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MWE_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public int getPCHPos() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPCHPos, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setPCHPos(int hpos) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(hpos);
mRemote.transact(Stub.TRANSACTION_setPCHPos, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPCVPos() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPCVPos, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setPCVPos(int vpos) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vpos);
mRemote.transact(Stub.TRANSACTION_setPCVPos, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPCClock() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPCClock, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setPCClock(int clock) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clock);
mRemote.transact(Stub.TRANSACTION_setPCClock, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPCPhase() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPCPhase, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setPCPhase(int phase) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(phase);
mRemote.transact(Stub.TRANSACTION_setPCPhase, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setMFC(int iMfcMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iMfcMode);
mRemote.transact(Stub.TRANSACTION_setMFC, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getMFC() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMFC, _data, _reply, 0);
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
      *  set  pc auto adjust
      *
     */
@Override public boolean ExecAutoPc() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ExecAutoPc, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getDlcAverageLuma() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDlcAverageLuma, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean freezeImage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_freezeImage, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean unFreezeImage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unFreezeImage, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setSwingLevel(int swingLevel) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(swingLevel);
mRemote.transact(Stub.TRANSACTION_setSwingLevel, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getDlcHistogramMax() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDlcHistogramMax, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getDlcHistogramMin() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDlcHistogramMin, _data, _reply, 0);
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
static final int TRANSACTION_setPictureModeIdx = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getPictureModeIdx = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setVideoArc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getVideoArc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_execVideoItem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getVideoItem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setBacklight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getBacklight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setColorTempIdx = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getColorTempIdx = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_setColorTempPara = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getColorTempPara = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_setMpegNR = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getMpegNR = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_setNR = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getNR = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_setMWEStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getMWEStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getPCHPos = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setPCHPos = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getPCVPos = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setPCVPos = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getPCClock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setPCClock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_getPCPhase = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_setPCPhase = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_setMFC = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getMFC = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_ExecAutoPc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_getDlcAverageLuma = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_freezeImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_unFreezeImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_setSwingLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getDlcHistogramMax = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_getDlcHistogramMin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
}
public boolean setPictureModeIdx(com.mstar.tv.service.aidl.EN_MS_PICTURE ePicMode) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MS_PICTURE getPictureModeIdx() throws android.os.RemoteException;
public boolean setVideoArc(com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type eArcIdx) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.MAPI_VIDEO_ARC_Type getVideoArc() throws android.os.RemoteException;
public boolean execVideoItem(com.mstar.tv.service.aidl.EN_MS_VIDEOITEM eIndex, int value) throws android.os.RemoteException;
public int getVideoItem(com.mstar.tv.service.aidl.EN_MS_VIDEOITEM eIndex) throws android.os.RemoteException;
public boolean setBacklight(int value) throws android.os.RemoteException;
public int getBacklight() throws android.os.RemoteException;
public boolean setColorTempIdx(com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP eColorTemp) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MS_COLOR_TEMP getColorTempIdx() throws android.os.RemoteException;
public boolean setColorTempPara(com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA stColorTemp) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.T_MS_COLOR_TEMPEX_DATA getColorTempPara() throws android.os.RemoteException;
public boolean setMpegNR(com.mstar.tv.service.aidl.EN_MS_MPEG_NR eMpNRIdx) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MS_MPEG_NR getMpegNR() throws android.os.RemoteException;
public boolean setNR(com.mstar.tv.service.aidl.EN_MS_NR eNRIdx) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MS_NR getNR() throws android.os.RemoteException;
public boolean setMWEStatus(com.mstar.tv.service.aidl.EN_MWE_TYPE eMWE) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MWE_TYPE getMWEStatus() throws android.os.RemoteException;
public int getPCHPos() throws android.os.RemoteException;
public boolean setPCHPos(int hpos) throws android.os.RemoteException;
public int getPCVPos() throws android.os.RemoteException;
public boolean setPCVPos(int vpos) throws android.os.RemoteException;
public int getPCClock() throws android.os.RemoteException;
public boolean setPCClock(int clock) throws android.os.RemoteException;
public int getPCPhase() throws android.os.RemoteException;
public boolean setPCPhase(int phase) throws android.os.RemoteException;
public void setMFC(int iMfcMode) throws android.os.RemoteException;
public int getMFC() throws android.os.RemoteException;
/**
      *  set  pc auto adjust
      *
     */
public boolean ExecAutoPc() throws android.os.RemoteException;
public int getDlcAverageLuma() throws android.os.RemoteException;
public boolean freezeImage() throws android.os.RemoteException;
public boolean unFreezeImage() throws android.os.RemoteException;
public boolean setSwingLevel(int swingLevel) throws android.os.RemoteException;
public int getDlcHistogramMax() throws android.os.RemoteException;
public int getDlcHistogramMin() throws android.os.RemoteException;
}
