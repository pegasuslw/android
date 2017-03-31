package com.mstar.tv.service.interfaces;
import com.mstar.tv.service.aidl.EN_SOUND_MODE;
import com.mstar.tv.service.aidl.EN_SURROUND_MODE;
import com.mstar.tv.service.aidl.EN_SPDIF_OUT_MODE;
import com.mstar.tv.service.aidl.EN_ON_OFF_TYPE;
import com.mstar.tv.service.aidl.EN_MAIN_AMP_TYPE;
import com.mstar.tv.service.aidl.EN_SUB_WOOF_TYPE;
import com.mstar.tv.service.aidl.EN_DTV_SOUND_MODE;

/** @hide */
interface ITvServiceServerAudio
{
	boolean setSoundMode(in EN_SOUND_MODE SoundMode);
	
	EN_SOUND_MODE getSoundMode();
	
    boolean setVolume(int volume);
    
    boolean setVolumeFast(int volume);
    
    int getVolume();
    
    boolean setEarPhoneVolume(int volume);
    
    int getEarPhoneVolume();
    
    boolean setMuteFlag(boolean muteFlag);

    boolean GetMuteFlag();
    	
	 boolean setBass(int bassValue);

	/**
	 * 
	 * Get Bass Value
	 * 
	 * 
	 * 
	 * @return Bass Value 0~100
	 */
	 int getBass();

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
	 boolean setTreble(int bassValue);

	/**
	 * 
	 * Get treble value
	 * 
	 * 
	 * 
	 * @return treble value 0~100
	 */
	 int getTreble();

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
	 boolean setBalance(int balanceValue);

	/**
	 * 
	 * Get balance
	 * 
	 * 
	 * 
	 * @return balance value 0~100
	 */
	 int getBalance();

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
	 boolean setAVCMode(boolean isAvcEnable);

	/**
	 * 
	 * Get Avc mode
	 * 
	 * 
	 * 
	 * @return isAvcEnable, boolean value, true if enable, false if disable
	 */
	 boolean getAVCMode();

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
	 boolean setSurroundMode(in EN_SURROUND_MODE surroundMode);

	/**
	 * 
	 * Get surround sound mode
	 * 
	 * 
	 * 
	 * @return EN_SURROUND_MODE
	 */
	 EN_SURROUND_MODE getSurroundMode();
	 
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
	 boolean setSpdifOutMode(in EN_SPDIF_OUT_MODE SpdifMode);
	 
	 EN_SPDIF_OUT_MODE getSpdifOutMode();
	 
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
	 boolean setEqBand120(int eqValue);

	/**
	 * 
	 * Get EQ of band 120
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
	 int getEqBand120();

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
	 boolean setEqBand500(int eqValue);

	/**
	 * 
	 * Get EQ of band 500
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
	 int getEqBand500();

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
	 boolean setEqBand1500(int eqValue);

	/**
	 * 
	 * Get EQ of band 1500
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
	 int getEqBand1500();

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
	boolean setEqBand5k(int eqValue);

	/**
	 * 
	 * Get EQ of band 5k
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
	int getEqBand5k();

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
	boolean setEqBand10k(int eqValue);

	/**
	 * 
	 * Get EQ of band 10k
	 * 
	 * 
	 * 
	 * @return eq value 0~100
	 */
	int getEqBand10k();
	 
 /**
	 * 
	 * Set bassswitch  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
	boolean setBassSwitch(in EN_ON_OFF_TYPE en);

	/**
	 * 
	 * Get  bassswitch  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
	 EN_ON_OFF_TYPE getBassSwitch();
	 
	 /**
	 * Set bassvolume
	 * 
	 * @param bassvolume value 0~100
	 * 
	 * @return true if operation success or false if fail.
	 */
	boolean setBassVolume(int volume);

	/**
	 * 
	 * Get  bassvolume
	 * 
	 * @return bassvolume value 0~100
	 */
	 int getBassVolume();
	 
	 /**
	 * 
	 * Set power_on_off_music  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
	boolean setPowerOnOffMusic(in EN_ON_OFF_TYPE en);

	/**
	 * 
	 * Get  power_on_off_music  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
	 EN_ON_OFF_TYPE getPowerOnOffMusic();
	 
	 /**
	 * 
	 * Set Wallmusic  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
	boolean setWallmusic(in EN_ON_OFF_TYPE en);

	/**
	 * 
	 * Get  Wallmusic  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
	 EN_ON_OFF_TYPE getWallmusic();
	 
	 
	 /**
	 * 
	 * Set SeparateHear  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
	boolean setSeparateHear(in EN_ON_OFF_TYPE en);

	/**
	 * 
	 * Get  SeparateHear  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
	 EN_ON_OFF_TYPE getSeparateHear();
	 
	 
	 /**
	 * 
	 * Set DGClarity  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
	boolean setDGClarity(in EN_ON_OFF_TYPE en);

	/**
	 * 
	 * Get  DGClarity  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
	 EN_ON_OFF_TYPE getDGClarity();
	 
	 /**
	 * 
	 * Set TrueBass  mode
	 * 
	 * @param EN_ON_OFF_TYPE
	 * 
	 * @return boolean value, true if enable, false if disable
	 */
	boolean setTrueBass(in EN_ON_OFF_TYPE en);

	/**
	 * 
	 * Get  TrueBass  mode
	 * 
	 * @return EN_ON_OFF_TYPE
	 */
	 EN_ON_OFF_TYPE getTrueBass();
	 	 
	 	 
	 void setAMPSubWoofVol(int vol);
	 
	 int getAMPSubWoofVol();
	 
	 void setAMPSubWoofType(in EN_SUB_WOOF_TYPE subWoofType);
	 
	 EN_SUB_WOOF_TYPE getAMPSubWoofType();
	 
	 void setAMPMainType(in EN_MAIN_AMP_TYPE mainAmpType);
	 
	 EN_MAIN_AMP_TYPE getAMPMainType();
	 
	 
	 /**
	 *
	 * Set DTV output mode
	 *
	 * @param EN_DTV_SOUND_MODE
	 *
	 * @return null
	 */
	 void setDtvOutputMode(in EN_DTV_SOUND_MODE enDtvSoundMode);

	 /**
	 *
	 * Get DTV output mode
	 *
	 * @return EN_DTV_SOUND_MODE
	 */
	 EN_DTV_SOUND_MODE getDtvOutputMode();
	 
	 void volumeUp();
	 
	 void volumeDown();
	 
	 boolean setMomentMuteFlag(boolean muteFlag);
	 
}