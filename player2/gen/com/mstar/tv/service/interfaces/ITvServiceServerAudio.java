/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pegasus/tmp/player2/src/com/mstar/tv/service/interfaces/ITvServiceServerAudio.aidl
 */
package com.mstar.tv.service.interfaces;
/** @hide */
public interface ITvServiceServerAudio extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mstar.tv.service.interfaces.ITvServiceServerAudio
{
private static final java.lang.String DESCRIPTOR = "com.mstar.tv.service.interfaces.ITvServiceServerAudio";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mstar.tv.service.interfaces.ITvServiceServerAudio interface,
 * generating a proxy if needed.
 */
public static com.mstar.tv.service.interfaces.ITvServiceServerAudio asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mstar.tv.service.interfaces.ITvServiceServerAudio))) {
return ((com.mstar.tv.service.interfaces.ITvServiceServerAudio)iin);
}
return new com.mstar.tv.service.interfaces.ITvServiceServerAudio.Stub.Proxy(obj);
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
case TRANSACTION_setSoundMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SOUND_MODE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SOUND_MODE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSoundMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSoundMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SOUND_MODE _result = this.getSoundMode();
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
case TRANSACTION_setVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setVolume(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setVolumeFast:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setVolumeFast(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
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
case TRANSACTION_setEarPhoneVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setEarPhoneVolume(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getEarPhoneVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getEarPhoneVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setMuteFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setMuteFlag(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetMuteFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetMuteFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBass:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setBass(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getBass:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBass();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setTreble:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setTreble(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getTreble:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getTreble();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setBalance:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setBalance(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getBalance:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBalance();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setAVCMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setAVCMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getAVCMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getAVCMode();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setSurroundMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SURROUND_MODE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SURROUND_MODE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSurroundMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSurroundMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SURROUND_MODE _result = this.getSurroundMode();
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
case TRANSACTION_setSpdifOutMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSpdifOutMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSpdifOutMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE _result = this.getSpdifOutMode();
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
case TRANSACTION_setEqBand120:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setEqBand120(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getEqBand120:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getEqBand120();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setEqBand500:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setEqBand500(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getEqBand500:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getEqBand500();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setEqBand1500:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setEqBand1500(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getEqBand1500:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getEqBand1500();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setEqBand5k:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setEqBand5k(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getEqBand5k:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getEqBand5k();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setEqBand10k:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setEqBand10k(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getEqBand10k:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getEqBand10k();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setBassSwitch:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setBassSwitch(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getBassSwitch:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result = this.getBassSwitch();
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
case TRANSACTION_setBassVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setBassVolume(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getBassVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBassVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPowerOnOffMusic:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setPowerOnOffMusic(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPowerOnOffMusic:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result = this.getPowerOnOffMusic();
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
case TRANSACTION_setWallmusic:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setWallmusic(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getWallmusic:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result = this.getWallmusic();
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
case TRANSACTION_setSeparateHear:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setSeparateHear(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSeparateHear:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result = this.getSeparateHear();
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
case TRANSACTION_setDGClarity:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setDGClarity(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDGClarity:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result = this.getDGClarity();
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
case TRANSACTION_setTrueBass:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setTrueBass(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getTrueBass:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result = this.getTrueBass();
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
case TRANSACTION_setAMPSubWoofVol:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setAMPSubWoofVol(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAMPSubWoofVol:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getAMPSubWoofVol();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setAMPSubWoofType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setAMPSubWoofType(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAMPSubWoofType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE _result = this.getAMPSubWoofType();
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
case TRANSACTION_setAMPMainType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setAMPMainType(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAMPMainType:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE _result = this.getAMPMainType();
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
case TRANSACTION_setDtvOutputMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setDtvOutputMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getDtvOutputMode:
{
data.enforceInterface(DESCRIPTOR);
com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE _result = this.getDtvOutputMode();
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
case TRANSACTION_volumeUp:
{
data.enforceInterface(DESCRIPTOR);
this.volumeUp();
reply.writeNoException();
return true;
}
case TRANSACTION_volumeDown:
{
data.enforceInterface(DESCRIPTOR);
this.volumeDown();
reply.writeNoException();
return true;
}
case TRANSACTION_setMomentMuteFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setMomentMuteFlag(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mstar.tv.service.interfaces.ITvServiceServerAudio
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
@Override public boolean setSoundMode(com.mstar.tv.service.aidl.EN_SOUND_MODE SoundMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((SoundMode!=null)) {
_data.writeInt(1);
SoundMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSoundMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_SOUND_MODE getSoundMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_SOUND_MODE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSoundMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_SOUND_MODE.CREATOR.createFromParcel(_reply);
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
@Override public boolean setVolume(int volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(volume);
mRemote.transact(Stub.TRANSACTION_setVolume, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setVolumeFast(int volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(volume);
mRemote.transact(Stub.TRANSACTION_setVolumeFast, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
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
@Override public boolean setEarPhoneVolume(int volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(volume);
mRemote.transact(Stub.TRANSACTION_setEarPhoneVolume, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getEarPhoneVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEarPhoneVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setMuteFlag(boolean muteFlag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((muteFlag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setMuteFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean GetMuteFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetMuteFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setBass(int bassValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(bassValue);
mRemote.transact(Stub.TRANSACTION_setBass, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get Bass Value
	 * 
	 * 
	 * 
	 * @return Bass Value 0~100
	 */
@Override public int getBass() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBass, _data, _reply, 0);
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
	 * 
	 * Set treble value
	 * 
	 * 
	 * 
	 * @param treble
	 * 
	 *            value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setTreble(int bassValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(bassValue);
mRemote.transact(Stub.TRANSACTION_setTreble, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get treble value
	 * 
	 * 
	 * 
	 * @return treble value 0~100
	 */
@Override public int getTreble() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTreble, _data, _reply, 0);
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
	 * 
	 * Set balance
	 * 
	 * 
	 * 
	 * @param balance
	 * 
	 *            value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setBalance(int balanceValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(balanceValue);
mRemote.transact(Stub.TRANSACTION_setBalance, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get balance
	 * 
	 * 
	 * 
	 * @return balance value 0~100
	 */
@Override public int getBalance() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBalance, _data, _reply, 0);
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
	 * 
	 * Set Avc mode
	 * 
	 * 
	 * 
	 * @param AvcEnable
	 * 
	 *            , boolean value, true if enable, false if disable
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setAVCMode(boolean isAvcEnable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isAvcEnable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setAVCMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get Avc mode
	 * 
	 * 
	 * 
	 * @return isAvcEnable, boolean value, true if enable, false if disable
	 */
@Override public boolean getAVCMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAVCMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Set surround sound mode
	 * 
	 * 
	 * 
	 * @param EN_SURROUND_MODE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setSurroundMode(com.mstar.tv.service.aidl.EN_SURROUND_MODE surroundMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((surroundMode!=null)) {
_data.writeInt(1);
surroundMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSurroundMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get surround sound mode
	 * 
	 * 
	 * 
	 * @return EN_SURROUND_MODE
	 */
@Override public com.mstar.tv.service.aidl.EN_SURROUND_MODE getSurroundMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_SURROUND_MODE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSurroundMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_SURROUND_MODE.CREATOR.createFromParcel(_reply);
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
/**
	 * 
	 * Set SpdifOut mode
	 * 
	 * 
	 * 
	 * @param EN_SPDIF_MODE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setSpdifOutMode(com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE SpdifMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((SpdifMode!=null)) {
_data.writeInt(1);
SpdifMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSpdifOutMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE getSpdifOutMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSpdifOutMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE.CREATOR.createFromParcel(_reply);
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
/**
	 * 
	 * Set EQ of band 120
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setEqBand120(int eqValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eqValue);
mRemote.transact(Stub.TRANSACTION_setEqBand120, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get EQ of band 120
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
@Override public int getEqBand120() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEqBand120, _data, _reply, 0);
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
	 * 
	 * Set EQ of band 500
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setEqBand500(int eqValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eqValue);
mRemote.transact(Stub.TRANSACTION_setEqBand500, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get EQ of band 500
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
@Override public int getEqBand500() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEqBand500, _data, _reply, 0);
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
	 * 
	 * Set EQ of band 1500
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setEqBand1500(int eqValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eqValue);
mRemote.transact(Stub.TRANSACTION_setEqBand1500, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get EQ of band 1500
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
@Override public int getEqBand1500() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEqBand1500, _data, _reply, 0);
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
	 * 
	 * Set EQ of band 5k
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setEqBand5k(int eqValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eqValue);
mRemote.transact(Stub.TRANSACTION_setEqBand5k, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get EQ of band 5k
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
@Override public int getEqBand5k() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEqBand5k, _data, _reply, 0);
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
	 * 
	 * Set EQ of band 10k
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setEqBand10k(int eqValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eqValue);
mRemote.transact(Stub.TRANSACTION_setEqBand10k, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get EQ of band 10k
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
@Override public int getEqBand10k() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEqBand10k, _data, _reply, 0);
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
	 * 
	 * Set bassswitch  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setBassSwitch(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((en!=null)) {
_data.writeInt(1);
en.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setBassSwitch, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  bassswitch  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
@Override public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getBassSwitch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBassSwitch, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(_reply);
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
/**
	 * Set bassvolume
	 * 
	 * @param bassvolume value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
@Override public boolean setBassVolume(int volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(volume);
mRemote.transact(Stub.TRANSACTION_setBassVolume, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  bassvolume
	 * 
	 * @return bassvolume value 0~100
	 */
@Override public int getBassVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBassVolume, _data, _reply, 0);
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
	 * 
	 * Set power_on_off_music  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setPowerOnOffMusic(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((en!=null)) {
_data.writeInt(1);
en.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setPowerOnOffMusic, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  power_on_off_music  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
@Override public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getPowerOnOffMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPowerOnOffMusic, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(_reply);
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
/**
	 * 
	 * Set Wallmusic  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setWallmusic(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((en!=null)) {
_data.writeInt(1);
en.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setWallmusic, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  Wallmusic  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
@Override public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getWallmusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWallmusic, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(_reply);
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
/**
	 * 
	 * Set SeparateHear  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setSeparateHear(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((en!=null)) {
_data.writeInt(1);
en.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSeparateHear, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  SeparateHear  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
@Override public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getSeparateHear() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSeparateHear, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(_reply);
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
/**
	 * 
	 * Set DGClarity  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setDGClarity(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((en!=null)) {
_data.writeInt(1);
en.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setDGClarity, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  DGClarity  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
@Override public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getDGClarity() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDGClarity, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(_reply);
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
/**
	 * 
	 * Set TrueBass  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
@Override public boolean setTrueBass(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((en!=null)) {
_data.writeInt(1);
en.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setTrueBass, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 
	 * Get  TrueBass  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
@Override public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getTrueBass() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_ON_OFF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTrueBass, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_ON_OFF_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public void setAMPSubWoofVol(int vol) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vol);
mRemote.transact(Stub.TRANSACTION_setAMPSubWoofVol, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getAMPSubWoofVol() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAMPSubWoofVol, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setAMPSubWoofType(com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE subWoofType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((subWoofType!=null)) {
_data.writeInt(1);
subWoofType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setAMPSubWoofType, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE getAMPSubWoofType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAMPSubWoofType, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE.CREATOR.createFromParcel(_reply);
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
@Override public void setAMPMainType(com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE mainAmpType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((mainAmpType!=null)) {
_data.writeInt(1);
mainAmpType.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setAMPMainType, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE getAMPMainType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAMPMainType, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE.CREATOR.createFromParcel(_reply);
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
/**
	 *
	 * Set DTV output mode
	 *
	 * @param EN_DTV_SOUND_MODE
	 *
	 * @return null
	 */
@Override public void setDtvOutputMode(com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE enDtvSoundMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((enDtvSoundMode!=null)) {
_data.writeInt(1);
enDtvSoundMode.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setDtvOutputMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 *
	 * Get DTV output mode
	 *
	 * @return EN_DTV_SOUND_MODE
	 */
@Override public com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE getDtvOutputMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDtvOutputMode, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE.CREATOR.createFromParcel(_reply);
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
@Override public void volumeUp() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_volumeUp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void volumeDown() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_volumeDown, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean setMomentMuteFlag(boolean muteFlag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((muteFlag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setMomentMuteFlag, _data, _reply, 0);
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
static final int TRANSACTION_setSoundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getSoundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setVolumeFast = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setEarPhoneVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getEarPhoneVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setMuteFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_GetMuteFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setBass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getBass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_setTreble = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getTreble = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setBalance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getBalance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_setAVCMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getAVCMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_setSurroundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getSurroundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setSpdifOutMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getSpdifOutMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setEqBand120 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getEqBand120 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setEqBand500 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_getEqBand500 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_setEqBand1500 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getEqBand1500 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_setEqBand5k = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_getEqBand5k = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_setEqBand10k = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_getEqBand10k = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_setBassSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_getBassSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_setBassVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_getBassVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_setPowerOnOffMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_getPowerOnOffMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_setWallmusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_getWallmusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_setSeparateHear = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_getSeparateHear = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_setDGClarity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_getDGClarity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_setTrueBass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_getTrueBass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_setAMPSubWoofVol = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_getAMPSubWoofVol = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
static final int TRANSACTION_setAMPSubWoofType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
static final int TRANSACTION_getAMPSubWoofType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
static final int TRANSACTION_setAMPMainType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
static final int TRANSACTION_getAMPMainType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
static final int TRANSACTION_setDtvOutputMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
static final int TRANSACTION_getDtvOutputMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
static final int TRANSACTION_volumeUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
static final int TRANSACTION_volumeDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
static final int TRANSACTION_setMomentMuteFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
}
public boolean setSoundMode(com.mstar.tv.service.aidl.EN_SOUND_MODE SoundMode) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_SOUND_MODE getSoundMode() throws android.os.RemoteException;
public boolean setVolume(int volume) throws android.os.RemoteException;
public boolean setVolumeFast(int volume) throws android.os.RemoteException;
public int getVolume() throws android.os.RemoteException;
public boolean setEarPhoneVolume(int volume) throws android.os.RemoteException;
public int getEarPhoneVolume() throws android.os.RemoteException;
public boolean setMuteFlag(boolean muteFlag) throws android.os.RemoteException;
public boolean GetMuteFlag() throws android.os.RemoteException;
public boolean setBass(int bassValue) throws android.os.RemoteException;
/**
	 * 
	 * Get Bass Value
	 * 
	 * 
	 * 
	 * @return Bass Value 0~100
	 */
public int getBass() throws android.os.RemoteException;
/**
	 * 
	 * Set treble value
	 * 
	 * 
	 * 
	 * @param treble
	 * 
	 *            value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setTreble(int bassValue) throws android.os.RemoteException;
/**
	 * 
	 * Get treble value
	 * 
	 * 
	 * 
	 * @return treble value 0~100
	 */
public int getTreble() throws android.os.RemoteException;
/**
	 * 
	 * Set balance
	 * 
	 * 
	 * 
	 * @param balance
	 * 
	 *            value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setBalance(int balanceValue) throws android.os.RemoteException;
/**
	 * 
	 * Get balance
	 * 
	 * 
	 * 
	 * @return balance value 0~100
	 */
public int getBalance() throws android.os.RemoteException;
/**
	 * 
	 * Set Avc mode
	 * 
	 * 
	 * 
	 * @param AvcEnable
	 * 
	 *            , boolean value, true if enable, false if disable
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setAVCMode(boolean isAvcEnable) throws android.os.RemoteException;
/**
	 * 
	 * Get Avc mode
	 * 
	 * 
	 * 
	 * @return isAvcEnable, boolean value, true if enable, false if disable
	 */
public boolean getAVCMode() throws android.os.RemoteException;
/**
	 * 
	 * Set surround sound mode
	 * 
	 * 
	 * 
	 * @param EN_SURROUND_MODE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setSurroundMode(com.mstar.tv.service.aidl.EN_SURROUND_MODE surroundMode) throws android.os.RemoteException;
/**
	 * 
	 * Get surround sound mode
	 * 
	 * 
	 * 
	 * @return EN_SURROUND_MODE
	 */
public com.mstar.tv.service.aidl.EN_SURROUND_MODE getSurroundMode() throws android.os.RemoteException;
/**
	 * 
	 * Set SpdifOut mode
	 * 
	 * 
	 * 
	 * @param EN_SPDIF_MODE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setSpdifOutMode(com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE SpdifMode) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE getSpdifOutMode() throws android.os.RemoteException;
/**
	 * 
	 * Set EQ of band 120
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setEqBand120(int eqValue) throws android.os.RemoteException;
/**
	 * 
	 * Get EQ of band 120
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
public int getEqBand120() throws android.os.RemoteException;
/**
	 * 
	 * Set EQ of band 500
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setEqBand500(int eqValue) throws android.os.RemoteException;
/**
	 * 
	 * Get EQ of band 500
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
public int getEqBand500() throws android.os.RemoteException;
/**
	 * 
	 * Set EQ of band 1500
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setEqBand1500(int eqValue) throws android.os.RemoteException;
/**
	 * 
	 * Get EQ of band 1500
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
public int getEqBand1500() throws android.os.RemoteException;
/**
	 * 
	 * Set EQ of band 5k
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setEqBand5k(int eqValue) throws android.os.RemoteException;
/**
	 * 
	 * Get EQ of band 5k
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
public int getEqBand5k() throws android.os.RemoteException;
/**
	 * 
	 * Set EQ of band 10k
	 * 
	 * 
	 * 
	 * @param eq
	 * 
	 *            value 0~100
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setEqBand10k(int eqValue) throws android.os.RemoteException;
/**
	 * 
	 * Get EQ of band 10k
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
public int getEqBand10k() throws android.os.RemoteException;
/**
	 * 
	 * Set bassswitch  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setBassSwitch(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException;
/**
	 * 
	 * Get  bassswitch  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getBassSwitch() throws android.os.RemoteException;
/**
	 * Set bassvolume
	 * 
	 * @param bassvolume value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
public boolean setBassVolume(int volume) throws android.os.RemoteException;
/**
	 * 
	 * Get  bassvolume
	 * 
	 * @return bassvolume value 0~100
	 */
public int getBassVolume() throws android.os.RemoteException;
/**
	 * 
	 * Set power_on_off_music  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setPowerOnOffMusic(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException;
/**
	 * 
	 * Get  power_on_off_music  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getPowerOnOffMusic() throws android.os.RemoteException;
/**
	 * 
	 * Set Wallmusic  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setWallmusic(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException;
/**
	 * 
	 * Get  Wallmusic  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getWallmusic() throws android.os.RemoteException;
/**
	 * 
	 * Set SeparateHear  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setSeparateHear(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException;
/**
	 * 
	 * Get  SeparateHear  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getSeparateHear() throws android.os.RemoteException;
/**
	 * 
	 * Set DGClarity  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setDGClarity(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException;
/**
	 * 
	 * Get  DGClarity  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getDGClarity() throws android.os.RemoteException;
/**
	 * 
	 * Set TrueBass  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
public boolean setTrueBass(com.mstar.tv.service.aidl.EN_ON_OFF_TYPE en) throws android.os.RemoteException;
/**
	 * 
	 * Get  TrueBass  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
public com.mstar.tv.service.aidl.EN_ON_OFF_TYPE getTrueBass() throws android.os.RemoteException;
public void setAMPSubWoofVol(int vol) throws android.os.RemoteException;
public int getAMPSubWoofVol() throws android.os.RemoteException;
public void setAMPSubWoofType(com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE subWoofType) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE getAMPSubWoofType() throws android.os.RemoteException;
public void setAMPMainType(com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE mainAmpType) throws android.os.RemoteException;
public com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE getAMPMainType() throws android.os.RemoteException;
/**
	 *
	 * Set DTV output mode
	 *
	 * @param EN_DTV_SOUND_MODE
	 *
	 * @return null
	 */
public void setDtvOutputMode(com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE enDtvSoundMode) throws android.os.RemoteException;
/**
	 *
	 * Get DTV output mode
	 *
	 * @return EN_DTV_SOUND_MODE
	 */
public com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE getDtvOutputMode() throws android.os.RemoteException;
public void volumeUp() throws android.os.RemoteException;
public void volumeDown() throws android.os.RemoteException;
public boolean setMomentMuteFlag(boolean muteFlag) throws android.os.RemoteException;
}
