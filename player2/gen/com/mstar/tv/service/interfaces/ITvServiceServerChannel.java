/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/mstar/tv/service/interfaces/ITvServiceServerChannel.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerChannel extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerChannel
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerChannel";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerChannel interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerChannel asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerChannel))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerChannel)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerChannel.Stub.Proxy(obj);
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
case TRANSACTION_makeSourceAtv:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.makeSourceAtv();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_makeSourceDtv:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.makeSourceDtv();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchMSrvDtvRouteCmd:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.switchMSrvDtvRouteCmd(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetManualTuningStart:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
com.mstar.tv.service.aidl.EN_ATV_MANUAL_TUNE_MODE _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mstar.tv.service.aidl.EN_ATV_MANUAL_TUNE_MODE.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
boolean _result = this.atvSetManualTuningStart(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_changeToFirstService:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_FIRST_SERVICE_INPUT_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_FIRST_SERVICE_INPUT_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_FIRST_SERVICE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_FIRST_SERVICE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.changeToFirstService(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetManualTuningEnd:
{
data.enforceInterface(DESCRIPTOR);
this.atvSetManualTuningEnd();
reply.writeNoException();
return true;
}
case TRANSACTION_atvSetAutoTuningStart:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.atvSetAutoTuningStart(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_addProgramToFavorite:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_FAVORITE_ID _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_FAVORITE_ID.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.addProgramToFavorite(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_atvGetCurrentFrequency:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.atvGetCurrentFrequency();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_atvGetProgramInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_GET_PROGRAM_INFO _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_GET_PROGRAM_INFO.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.lang.String _arg3;
_arg3 = data.readString();
int _result = this.atvGetProgramInfo(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_atvGetSoundSystem:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD _result = this.atvGetSoundSystem();
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
case TRANSACTION_atvGetVideoSystem:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE _result = this.atvGetVideoSystem();
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
case TRANSACTION_getCurrentChannelNumber:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrentChannelNumber();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_atvSetAutoTuningEnd:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.atvSetAutoTuningEnd();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetAutoTuningPause:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.atvSetAutoTuningPause();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetAutoTuningResume:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.atvSetAutoTuningResume();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetChannel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
int _result = this.atvSetChannel(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_atvSetForceSoundSystem:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.atvSetForceSoundSystem(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetForceVedioSystem:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.atvSetForceVedioSystem(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetFrequency:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.atvSetFrequency(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_atvSetProgramInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SET_PROGRAM_INFO _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SET_PROGRAM_INFO.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.lang.String _arg3;
_arg3 = data.readString();
int _result = this.atvSetProgramInfo(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_closeSubtitle:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.closeSubtitle();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_deleteProgramFromFavorite:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_FAVORITE_ID _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_FAVORITE_ID.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.deleteProgramFromFavorite(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_dtvAutoScan:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dtvAutoScan();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvChangeManualScanRF:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.dtvChangeManualScanRF(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvFullScan:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dtvFullScan();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getAtvStationName:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getAtvStationName(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getProgramCount:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_PROGRAM_COUNT_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_PROGRAM_COUNT_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.getProgramCount(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_programSel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.programSel(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchAudioTrack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.switchAudioTrack(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setUserScanType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setUserScanType(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSystemCountry:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setSystemCountry(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setProgramName:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.setProgramName(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setProgramCtrl:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SET_PROGRAM_CTRL _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SET_PROGRAM_CTRL.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.lang.String _arg3;
_arg3 = data.readString();
int _result = this.setProgramCtrl(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setProgramAttribute:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
boolean _arg4;
_arg4 = (0!=data.readInt());
this.setProgramAttribute(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_sendDtvScaninfo:
{
data.enforceInterface(DESCRIPTOR);
this.sendDtvScaninfo();
reply.writeNoException();
return true;
}
case TRANSACTION_sendAtvScaninfo:
{
data.enforceInterface(DESCRIPTOR);
this.sendAtvScaninfo();
reply.writeNoException();
return true;
}
case TRANSACTION_programUp:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.programUp();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_programReturn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.programReturn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_programDown:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.programDown();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_openSubtitle:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.openSubtitle(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_moveProgram:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.moveProgram(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isSignalStabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSignalStabled();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getVideoStandard:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE _result = this.getVideoStandard();
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
case TRANSACTION_getUserScanType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE _result = this.getUserScanType();
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
case TRANSACTION_GetTsStatus:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_TV_TS_STATUS _result = this.GetTsStatus();
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
case TRANSACTION_getProgramAttribute:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
boolean _arg4;
_arg4 = (0!=data.readInt());
boolean _result = this.getProgramAttribute(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getProgramCtrl:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_GET_PROGRAM_CTRL _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_GET_PROGRAM_CTRL.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.lang.String _arg3;
_arg3 = data.readString();
int _result = this.getProgramCtrl(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getProgramName:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _arg2;
_arg2 = data.readInt();
java.lang.String _result = this.getProgramName(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getSIFMtsMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ATV_AUDIO_MODE_TYPE _result = this.getSIFMtsMode();
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
case TRANSACTION_getSystemCountry:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY _result = this.getSystemCountry();
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
case TRANSACTION_getSubtitleInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.DtvSubtitleInfo _result = this.getSubtitleInfo();
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
case TRANSACTION_getProgramInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ProgramInfoQueryCriteria _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.ProgramInfoQueryCriteria.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mstar.tv.service.aidl.EN_PROGRAM_INFO_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_PROGRAM_INFO_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mstar.tv.service.aidl.ProgramInfo _result = this.getProgramInfo(_arg0, _arg1);
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
case TRANSACTION_getMSrvDtvRoute:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMSrvDtvRoute();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCurrProgramInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ProgramInfo _result = this.getCurrProgramInfo();
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
case TRANSACTION_dtvGetAntennaType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ANTENNA_TYPE _result = this.dtvGetAntennaType();
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
case TRANSACTION_dtvManualScanFreq:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.dtvManualScanFreq(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvManualScanRF:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.dtvManualScanRF(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvPauseScan:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dtvPauseScan();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvplayCurrentProgram:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dtvplayCurrentProgram();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvResumeScan:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dtvResumeScan();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvSetAntennaType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ANTENNA_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ANTENNA_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.dtvSetAntennaType(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_dtvStartManualScan:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.dtvStartManualScan();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_DtvStopScan:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.DtvStopScan();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getCurrentMuxInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.DvbMuxInfo _result = this.getCurrentMuxInfo();
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
case TRANSACTION_getCurrentLanguageIndex:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.mstar.tv.service.aidl.EN_MS_LANGUAGE _result = this.getCurrentLanguageIndex(_arg0);
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
case TRANSACTION_dvbcgetScanParam:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.DvbcScanParam _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.DvbcScanParam.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.dvbcgetScanParam(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dvbcsetScanParam:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mstar.tv.service.aidl.EN_CAB_CONSTEL_TYPE _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mstar.tv.service.aidl.EN_CAB_CONSTEL_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
boolean _result = this.dvbcsetScanParam(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getCurrentProgramSpecificInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.ST_DTV_SPECIFIC_PROGINFO _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.ST_DTV_SPECIFIC_PROGINFO.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.getCurrentProgramSpecificInfo(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dtvGetRFInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EnumInfoType _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EnumInfoType.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
com.mstar.tv.service.aidl.RfInfo _result = this.dtvGetRFInfo(_arg0, _arg1);
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
case TRANSACTION_getAudioInfo:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.DtvAudioInfo _result = this.getAudioInfo();
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
case TRANSACTION_setChannelChangeFreezeMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setChannelChangeFreezeMode(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerChannel
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
@Override public boolean makeSourceAtv() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_makeSourceAtv, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean makeSourceDtv() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_makeSourceDtv, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean switchMSrvDtvRouteCmd(int u8DtvRoute) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(u8DtvRoute);
mRemote.transact(Stub.TRANSACTION_switchMSrvDtvRouteCmd, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetManualTuningStart(int EventIntervalMs, int Frequency, com.mstar.tv.service.aidl.EN_ATV_MANUAL_TUNE_MODE eMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(EventIntervalMs);
_data.writeInt(Frequency);
if ((eMode!=null)) {
_data.writeInt(1);
eMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_atvSetManualTuningStart, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean changeToFirstService(com.mstar.tv.service.aidl.EN_FIRST_SERVICE_INPUT_TYPE enInputType, com.mstar.tv.service.aidl.EN_FIRST_SERVICE_TYPE enServiceType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((enInputType!=null)) {
_data.writeInt(1);
enInputType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((enServiceType!=null)) {
_data.writeInt(1);
enServiceType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_changeToFirstService, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void atvSetManualTuningEnd() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvSetManualTuningEnd, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean atvSetAutoTuningStart(int EventIntervalMs, int FrequencyStart, int FrequencyEnd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(EventIntervalMs);
_data.writeInt(FrequencyStart);
_data.writeInt(FrequencyEnd);
mRemote.transact(Stub.TRANSACTION_atvSetAutoTuningStart, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void addProgramToFavorite(com.mstar.tv.service.aidl.EN_FAVORITE_ID favoriteId, int programNo, int programType, int programId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((favoriteId!=null)) {
_data.writeInt(1);
favoriteId.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(programNo);
_data.writeInt(programType);
_data.writeInt(programId);
mRemote.transact(Stub.TRANSACTION_addProgramToFavorite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int atvGetCurrentFrequency() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvGetCurrentFrequency, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int atvGetProgramInfo(com.mstar.tv.service.aidl.EN_GET_PROGRAM_INFO Cmd, int u16Program, int u16Param2, java.lang.String str) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((Cmd!=null)) {
_data.writeInt(1);
Cmd.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(u16Program);
_data.writeInt(u16Param2);
_data.writeString(str);
mRemote.transact(Stub.TRANSACTION_atvGetProgramInfo, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD atvGetSoundSystem() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvGetSoundSystem, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE atvGetVideoSystem() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvGetVideoSystem, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public int getCurrentChannelNumber() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentChannelNumber, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetAutoTuningEnd() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvSetAutoTuningEnd, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetAutoTuningPause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvSetAutoTuningPause, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetAutoTuningResume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_atvSetAutoTuningResume, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int atvSetChannel(int ChannelNum, boolean bCheckBlock) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ChannelNum);
_data.writeInt(((bCheckBlock)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_atvSetChannel, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetForceSoundSystem(com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD eSoundSystem) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eSoundSystem!=null)) {
_data.writeInt(1);
eSoundSystem.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_atvSetForceSoundSystem, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetForceVedioSystem(com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE eVideoSystem) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((eVideoSystem!=null)) {
_data.writeInt(1);
eVideoSystem.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_atvSetForceVedioSystem, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean atvSetFrequency(int Frequency) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(Frequency);
mRemote.transact(Stub.TRANSACTION_atvSetFrequency, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int atvSetProgramInfo(com.mstar.tv.service.aidl.EN_SET_PROGRAM_INFO Cmd, int Program, int Param2, java.lang.String str) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((Cmd!=null)) {
_data.writeInt(1);
Cmd.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(Program);
_data.writeInt(Param2);
_data.writeString(str);
mRemote.transact(Stub.TRANSACTION_atvSetProgramInfo, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean closeSubtitle() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_closeSubtitle, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void deleteProgramFromFavorite(com.mstar.tv.service.aidl.EN_FAVORITE_ID favoriteId, int programNo, int programType, int programId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((favoriteId!=null)) {
_data.writeInt(1);
favoriteId.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(programNo);
_data.writeInt(programType);
_data.writeInt(programId);
mRemote.transact(Stub.TRANSACTION_deleteProgramFromFavorite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean dtvAutoScan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvAutoScan, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dtvChangeManualScanRF(int RFNum) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(RFNum);
mRemote.transact(Stub.TRANSACTION_dtvChangeManualScanRF, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dtvFullScan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvFullScan, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getAtvStationName(int programNo) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(programNo);
mRemote.transact(Stub.TRANSACTION_getAtvStationName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getProgramCount(com.mstar.tv.service.aidl.EN_PROGRAM_COUNT_TYPE programCountType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((programCountType!=null)) {
_data.writeInt(1);
programCountType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getProgramCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean programSel(int u32Number, com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE u8ServiceType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(u32Number);
if ((u8ServiceType!=null)) {
_data.writeInt(1);
u8ServiceType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_programSel, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void switchAudioTrack(int track) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(track);
mRemote.transact(Stub.TRANSACTION_switchAudioTrack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setUserScanType(com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE scantype) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((scantype!=null)) {
_data.writeInt(1);
scantype.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setUserScanType, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSystemCountry(com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY mem_country) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((mem_country!=null)) {
_data.writeInt(1);
mem_country.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSystemCountry, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setProgramName(int programNum, int programType, java.lang.String porgramName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(programNum);
_data.writeInt(programType);
_data.writeString(porgramName);
mRemote.transact(Stub.TRANSACTION_setProgramName, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int setProgramCtrl(com.mstar.tv.service.aidl.EN_SET_PROGRAM_CTRL Cmd, int u16Param2, int u16Param3, java.lang.String pVoid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((Cmd!=null)) {
_data.writeInt(1);
Cmd.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(u16Param2);
_data.writeInt(u16Param3);
_data.writeString(pVoid);
mRemote.transact(Stub.TRANSACTION_setProgramCtrl, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setProgramAttribute(com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE enpa, int programNo, int pt, int pd, boolean bv) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((enpa!=null)) {
_data.writeInt(1);
enpa.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(programNo);
_data.writeInt(pt);
_data.writeInt(pd);
_data.writeInt(((bv)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setProgramAttribute, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void sendDtvScaninfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_sendDtvScaninfo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void sendAtvScaninfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_sendAtvScaninfo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean programUp() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_programUp, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean programReturn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_programReturn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean programDown() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_programDown, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean openSubtitle(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_openSubtitle, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void moveProgram(int progSourcePosition, int progTargetPosition) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progSourcePosition);
_data.writeInt(progTargetPosition);
mRemote.transact(Stub.TRANSACTION_moveProgram, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isSignalStabled() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSignalStabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE getVideoStandard() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVideoStandard, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE getUserScanType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUserScanType, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.EN_TV_TS_STATUS GetTsStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_TV_TS_STATUS _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetTsStatus, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_TV_TS_STATUS.CREATOR.createFromParcel(_reply);
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
@Override public boolean getProgramAttribute(com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE enpa, int programNo, int pt, int pd, boolean bv) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((enpa!=null)) {
_data.writeInt(1);
enpa.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(programNo);
_data.writeInt(pt);
_data.writeInt(pd);
_data.writeInt(((bv)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_getProgramAttribute, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getProgramCtrl(com.mstar.tv.service.aidl.EN_GET_PROGRAM_CTRL Cmd, int u16Param2, int u16Param3, java.lang.String pVoid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((Cmd!=null)) {
_data.writeInt(1);
Cmd.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(u16Param2);
_data.writeInt(u16Param3);
_data.writeString(pVoid);
mRemote.transact(Stub.TRANSACTION_getProgramCtrl, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getProgramName(int progNo, com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE progType, int progrID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progNo);
if ((progType!=null)) {
_data.writeInt(1);
progType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(progrID);
mRemote.transact(Stub.TRANSACTION_getProgramName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_ATV_AUDIO_MODE_TYPE getSIFMtsMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ATV_AUDIO_MODE_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSIFMtsMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ATV_AUDIO_MODE_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY getSystemCountry() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSystemCountry, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.DtvSubtitleInfo getSubtitleInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.DtvSubtitleInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSubtitleInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.DtvSubtitleInfo.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.ProgramInfo getProgramInfo(com.mstar.tv.service.aidl.ProgramInfoQueryCriteria criteria, com.mstar.tv.service.aidl.EN_PROGRAM_INFO_TYPE programInfoType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ProgramInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((criteria!=null)) {
_data.writeInt(1);
criteria.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((programInfoType!=null)) {
_data.writeInt(1);
programInfoType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getProgramInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ProgramInfo.CREATOR.createFromParcel(_reply);
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
@Override public int getMSrvDtvRoute() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMSrvDtvRoute, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.ProgramInfo getCurrProgramInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.ProgramInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrProgramInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.ProgramInfo.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.EN_ANTENNA_TYPE dtvGetAntennaType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ANTENNA_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvGetAntennaType, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ANTENNA_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public boolean dtvManualScanFreq(int FrequencyKHz) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(FrequencyKHz);
mRemote.transact(Stub.TRANSACTION_dtvManualScanFreq, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dtvManualScanRF(int RFNum) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(RFNum);
mRemote.transact(Stub.TRANSACTION_dtvManualScanRF, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dtvPauseScan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvPauseScan, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dtvplayCurrentProgram() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvplayCurrentProgram, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dtvResumeScan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvResumeScan, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void dtvSetAntennaType(com.mstar.tv.service.aidl.EN_ANTENNA_TYPE type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((type!=null)) {
_data.writeInt(1);
type.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_dtvSetAntennaType, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean dtvStartManualScan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dtvStartManualScan, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean DtvStopScan() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_DtvStopScan, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.DvbMuxInfo getCurrentMuxInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.DvbMuxInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentMuxInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.DvbMuxInfo.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.EN_MS_LANGUAGE getCurrentLanguageIndex(java.lang.String languageCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MS_LANGUAGE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(languageCode);
mRemote.transact(Stub.TRANSACTION_getCurrentLanguageIndex, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MS_LANGUAGE.CREATOR.createFromParcel(_reply);
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
@Override public boolean dvbcgetScanParam(com.mstar.tv.service.aidl.DvbcScanParam sp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((sp!=null)) {
_data.writeInt(1);
sp.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_dvbcgetScanParam, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean dvbcsetScanParam(int u16SymbolRate, com.mstar.tv.service.aidl.EN_CAB_CONSTEL_TYPE enConstellation, int u32nitFrequency, int u32EndFrequncy, int u16NetworkID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(u16SymbolRate);
if ((enConstellation!=null)) {
_data.writeInt(1);
enConstellation.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(u32nitFrequency);
_data.writeInt(u32EndFrequncy);
_data.writeInt(u16NetworkID);
mRemote.transact(Stub.TRANSACTION_dvbcsetScanParam, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean getCurrentProgramSpecificInfo(com.mstar.tv.service.aidl.ST_DTV_SPECIFIC_PROGINFO cResult) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((cResult!=null)) {
_data.writeInt(1);
cResult.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getCurrentProgramSpecificInfo, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.RfInfo dtvGetRFInfo(com.mstar.tv.service.aidl.EnumInfoType enInfoType, int RFChNum) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.RfInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((enInfoType!=null)) {
_data.writeInt(1);
enInfoType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(RFChNum);
mRemote.transact(Stub.TRANSACTION_dtvGetRFInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.RfInfo.CREATOR.createFromParcel(_reply);
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
@Override public com.mstar.tv.service.aidl.DtvAudioInfo getAudioInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.DtvAudioInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAudioInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.DtvAudioInfo.CREATOR.createFromParcel(_reply);
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
@Override public void setChannelChangeFreezeMode(boolean freezeMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((freezeMode)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setChannelChangeFreezeMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_makeSourceAtv = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_makeSourceDtv = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_switchMSrvDtvRouteCmd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_atvSetManualTuningStart = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_changeToFirstService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_atvSetManualTuningEnd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_atvSetAutoTuningStart = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_addProgramToFavorite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_atvGetCurrentFrequency = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_atvGetProgramInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_atvGetSoundSystem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_atvGetVideoSystem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getCurrentChannelNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_atvSetAutoTuningEnd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_atvSetAutoTuningPause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_atvSetAutoTuningResume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_atvSetChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_atvSetForceSoundSystem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_atvSetForceVedioSystem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_atvSetFrequency = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_atvSetProgramInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_closeSubtitle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_deleteProgramFromFavorite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_dtvAutoScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_dtvChangeManualScanRF = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_dtvFullScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getAtvStationName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getProgramCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_programSel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_switchAudioTrack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_setUserScanType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_setSystemCountry = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_setProgramName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_setProgramCtrl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_setProgramAttribute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_sendDtvScaninfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_sendAtvScaninfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_programUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_programReturn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_programDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_openSubtitle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_moveProgram = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_isSignalStabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_getVideoStandard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_getUserScanType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_GetTsStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_getProgramAttribute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
static final int TRANSACTION_getProgramCtrl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
static final int TRANSACTION_getProgramName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
static final int TRANSACTION_getSIFMtsMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
static final int TRANSACTION_getSystemCountry = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
static final int TRANSACTION_getSubtitleInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
static final int TRANSACTION_getProgramInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
static final int TRANSACTION_getMSrvDtvRoute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
static final int TRANSACTION_getCurrProgramInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
static final int TRANSACTION_dtvGetAntennaType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
static final int TRANSACTION_dtvManualScanFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 56);
static final int TRANSACTION_dtvManualScanRF = (android.os.IBinder.FIRST_CALL_TRANSACTION + 57);
static final int TRANSACTION_dtvPauseScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 58);
static final int TRANSACTION_dtvplayCurrentProgram = (android.os.IBinder.FIRST_CALL_TRANSACTION + 59);
static final int TRANSACTION_dtvResumeScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 60);
static final int TRANSACTION_dtvSetAntennaType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 61);
static final int TRANSACTION_dtvStartManualScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 62);
static final int TRANSACTION_DtvStopScan = (android.os.IBinder.FIRST_CALL_TRANSACTION + 63);
static final int TRANSACTION_getCurrentMuxInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 64);
static final int TRANSACTION_getCurrentLanguageIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 65);
static final int TRANSACTION_dvbcgetScanParam = (android.os.IBinder.FIRST_CALL_TRANSACTION + 66);
static final int TRANSACTION_dvbcsetScanParam = (android.os.IBinder.FIRST_CALL_TRANSACTION + 67);
static final int TRANSACTION_getCurrentProgramSpecificInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 68);
static final int TRANSACTION_dtvGetRFInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 69);
static final int TRANSACTION_getAudioInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 70);
static final int TRANSACTION_setChannelChangeFreezeMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 71);
}
public boolean makeSourceAtv() throws android.os.RemoteException;
public boolean makeSourceDtv() throws android.os.RemoteException;
public boolean switchMSrvDtvRouteCmd(int u8DtvRoute) throws android.os.RemoteException;
public boolean atvSetManualTuningStart(int EventIntervalMs, int Frequency, com.mstar.tv.service.aidl.EN_ATV_MANUAL_TUNE_MODE eMode) throws android.os.RemoteException;
public boolean changeToFirstService(com.mstar.tv.service.aidl.EN_FIRST_SERVICE_INPUT_TYPE enInputType, com.mstar.tv.service.aidl.EN_FIRST_SERVICE_TYPE enServiceType) throws android.os.RemoteException;
public void atvSetManualTuningEnd() throws android.os.RemoteException;
public boolean atvSetAutoTuningStart(int EventIntervalMs, int FrequencyStart, int FrequencyEnd) throws android.os.RemoteException;
public void addProgramToFavorite(com.mstar.tv.service.aidl.EN_FAVORITE_ID favoriteId, int programNo, int programType, int programId) throws android.os.RemoteException;
public int atvGetCurrentFrequency() throws android.os.RemoteException;
public int atvGetProgramInfo(com.mstar.tv.service.aidl.EN_GET_PROGRAM_INFO Cmd, int u16Program, int u16Param2, java.lang.String str) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD atvGetSoundSystem() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE atvGetVideoSystem() throws android.os.RemoteException;
public int getCurrentChannelNumber() throws android.os.RemoteException;
public boolean atvSetAutoTuningEnd() throws android.os.RemoteException;
public boolean atvSetAutoTuningPause() throws android.os.RemoteException;
public boolean atvSetAutoTuningResume() throws android.os.RemoteException;
public int atvSetChannel(int ChannelNum, boolean bCheckBlock) throws android.os.RemoteException;
public boolean atvSetForceSoundSystem(com.mstar.tv.service.aidl.EN_ATV_SYSTEM_STANDARD eSoundSystem) throws android.os.RemoteException;
public boolean atvSetForceVedioSystem(com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE eVideoSystem) throws android.os.RemoteException;
public boolean atvSetFrequency(int Frequency) throws android.os.RemoteException;
public int atvSetProgramInfo(com.mstar.tv.service.aidl.EN_SET_PROGRAM_INFO Cmd, int Program, int Param2, java.lang.String str) throws android.os.RemoteException;
public boolean closeSubtitle() throws android.os.RemoteException;
public void deleteProgramFromFavorite(com.mstar.tv.service.aidl.EN_FAVORITE_ID favoriteId, int programNo, int programType, int programId) throws android.os.RemoteException;
public boolean dtvAutoScan() throws android.os.RemoteException;
public boolean dtvChangeManualScanRF(int RFNum) throws android.os.RemoteException;
public boolean dtvFullScan() throws android.os.RemoteException;
public java.lang.String getAtvStationName(int programNo) throws android.os.RemoteException;
public int getProgramCount(com.mstar.tv.service.aidl.EN_PROGRAM_COUNT_TYPE programCountType) throws android.os.RemoteException;
public boolean programSel(int u32Number, com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE u8ServiceType) throws android.os.RemoteException;
public void switchAudioTrack(int track) throws android.os.RemoteException;
public void setUserScanType(com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE scantype) throws android.os.RemoteException;
public void setSystemCountry(com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY mem_country) throws android.os.RemoteException;
public void setProgramName(int programNum, int programType, java.lang.String porgramName) throws android.os.RemoteException;
public int setProgramCtrl(com.mstar.tv.service.aidl.EN_SET_PROGRAM_CTRL Cmd, int u16Param2, int u16Param3, java.lang.String pVoid) throws android.os.RemoteException;
public void setProgramAttribute(com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE enpa, int programNo, int pt, int pd, boolean bv) throws android.os.RemoteException;
public void sendDtvScaninfo() throws android.os.RemoteException;
public void sendAtvScaninfo() throws android.os.RemoteException;
public boolean programUp() throws android.os.RemoteException;
public boolean programReturn() throws android.os.RemoteException;
public boolean programDown() throws android.os.RemoteException;
public boolean openSubtitle(int index) throws android.os.RemoteException;
public void moveProgram(int progSourcePosition, int progTargetPosition) throws android.os.RemoteException;
public boolean isSignalStabled() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_AVD_VIDEO_STANDARD_TYPE getVideoStandard() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_TUNING_SCAN_TYPE getUserScanType() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_TV_TS_STATUS GetTsStatus() throws android.os.RemoteException;
public boolean getProgramAttribute(com.mstar.tv.service.aidl.EN_PROGRAM_ATTRIBUTE enpa, int programNo, int pt, int pd, boolean bv) throws android.os.RemoteException;
public int getProgramCtrl(com.mstar.tv.service.aidl.EN_GET_PROGRAM_CTRL Cmd, int u16Param2, int u16Param3, java.lang.String pVoid) throws android.os.RemoteException;
public java.lang.String getProgramName(int progNo, com.mstar.tv.service.aidl.EN_MEMBER_SERVICE_TYPE progType, int progrID) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_ATV_AUDIO_MODE_TYPE getSIFMtsMode() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MEMBER_COUNTRY getSystemCountry() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.DtvSubtitleInfo getSubtitleInfo() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.ProgramInfo getProgramInfo(com.mstar.tv.service.aidl.ProgramInfoQueryCriteria criteria, com.mstar.tv.service.aidl.EN_PROGRAM_INFO_TYPE programInfoType) throws android.os.RemoteException;
public int getMSrvDtvRoute() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.ProgramInfo getCurrProgramInfo() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_ANTENNA_TYPE dtvGetAntennaType() throws android.os.RemoteException;
public boolean dtvManualScanFreq(int FrequencyKHz) throws android.os.RemoteException;
public boolean dtvManualScanRF(int RFNum) throws android.os.RemoteException;
public boolean dtvPauseScan() throws android.os.RemoteException;
public boolean dtvplayCurrentProgram() throws android.os.RemoteException;
public boolean dtvResumeScan() throws android.os.RemoteException;
public void dtvSetAntennaType(com.mstar.tv.service.aidl.EN_ANTENNA_TYPE type) throws android.os.RemoteException;
public boolean dtvStartManualScan() throws android.os.RemoteException;
public boolean DtvStopScan() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.DvbMuxInfo getCurrentMuxInfo() throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MS_LANGUAGE getCurrentLanguageIndex(java.lang.String languageCode) throws android.os.RemoteException;
public boolean dvbcgetScanParam(com.mstar.tv.service.aidl.DvbcScanParam sp) throws android.os.RemoteException;
public boolean dvbcsetScanParam(int u16SymbolRate, com.mstar.tv.service.aidl.EN_CAB_CONSTEL_TYPE enConstellation, int u32nitFrequency, int u32EndFrequncy, int u16NetworkID) throws android.os.RemoteException;
public boolean getCurrentProgramSpecificInfo(com.mstar.tv.service.aidl.ST_DTV_SPECIFIC_PROGINFO cResult) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.RfInfo dtvGetRFInfo(com.mstar.tv.service.aidl.EnumInfoType enInfoType, int RFChNum) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.DtvAudioInfo getAudioInfo() throws android.os.RemoteException;
public void setChannelChangeFreezeMode(boolean freezeMode) throws android.os.RemoteException;
}
