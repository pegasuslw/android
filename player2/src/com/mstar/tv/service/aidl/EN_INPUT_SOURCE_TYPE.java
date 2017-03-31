/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (“MStar Software”) are 
 * intellectual property of MStar Semiconductor, Inc. (“MStar”) and protected by 
 * law, including, but not limited to, copyright law and international treaties. 
 * Any use, modification, reproduction, retransmission, or republication of all 
 * or part of MStar Software is expressly prohibited, unless prior written 
 * permission has been granted by MStar. 
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you 
 * have read, understood, and agree, to be bound by below terms (“Terms”) and to 
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar 
 * Software and any modification/derivatives thereof.  No right, ownership, 
 * or interest to MStar Software and any modification/derivatives thereof is 
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied 
 * together with third party’s software and the use of MStar Software may require 
 * additional licenses from third parties.  Therefore, you hereby agree it is your 
 * sole responsibility to separately obtain any and all third party right and 
 * license necessary for your use of such third party’s software. 
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as 
 * MStar’s confidential information and you agree to keep MStar’s confidential 
 * information in strictest confidence and not disclose to any third party.  
 *	
 * 4. MStar Software is provided on an “AS IS” basis without warranties of any kind. 
 * Any warranties are hereby expressly disclaimed by MStar, including without 
 * limitation, any warranties of merchantability, non-infringement of intellectual 
 * property rights, fitness for a particular purpose, error free and in conformity 
 * with any international standard.  You agree to waive any claim against MStar for 
 * any loss, damage, cost or expense that you may incur related to your use of MStar 
 * Software.  In no event shall MStar be liable for any direct, indirect, incidental 
 * or consequential damages, including without limitation, lost of profit or revenues, 
 * lost or damage of data, and unauthorized system use.  You agree that this Section 4 
 * shall still apply without being affected even if MStar Software has been modified 
 * by MStar in accordance with your request or instruction for your use, except 
 * otherwise agreed by both parties in writing.
 *
 * 5. If requested, MStar may from time to time provide technical supports or 
 * services in relation with MStar Software to you for your use of MStar Software 
 * in conjunction with your or your customer’s product (“Services”).  You understand 
 * and agree that, except otherwise agreed by both parties in writing, Services are 
 * provided on an “AS IS” basis and the warranty disclaimer set forth in Section 4 
 * above shall apply.  
 *
 * 6. Nothing contained herein shall be construed as by implication, estoppels or 
 * otherwise: (a) conferring any license or right to use MStar name, trademark, 
 * service mark, symbol or any other identification; (b) obligating MStar or any 
 * of its affiliates to furnish any person, including without limitation, you and 
 * your customers, any assistance of any kind whatsoever, or any information; or 
 * (c) conferring any license or right under any intellectual property right.
 *
 * 7. These terms shall be governed by and construed in accordance with the laws 
 * of Taiwan, R.O.C., excluding its conflict of law rules.  Any and all dispute 
 * arising out hereof or related hereto shall be finally settled by arbitration 
 * referred to the Chinese Arbitration Association, Taipei in accordance with 
 * the ROC Arbitration Law and the Arbitration Rules of the Association by three (3) 
 * arbitrators appointed in accordance with the said Rules.  The place of 
 * arbitration shall be in Taipei, Taiwan and the language shall be English.  
 * The arbitration award shall be final and binding to both parties.
 */
package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_INPUT_SOURCE_TYPE implements Parcelable
{
    E_INPUT_SOURCE_VGA,          ///<VGA input
    E_INPUT_SOURCE_ATV,          ///<TV input

    E_INPUT_SOURCE_CVBS,         ///<AV 1
    E_INPUT_SOURCE_CVBS2,        ///<AV 2
    E_INPUT_SOURCE_CVBS3,        ///<AV 3
    E_INPUT_SOURCE_CVBS4,        ///<AV 4
    E_INPUT_SOURCE_CVBS5,        ///<AV 5
    E_INPUT_SOURCE_CVBS6,        ///<AV 6
    E_INPUT_SOURCE_CVBS7,        ///<AV 7
    E_INPUT_SOURCE_CVBS8,        ///<AV 8
    E_INPUT_SOURCE_CVBS_MAX,     ///<AV max

    E_INPUT_SOURCE_SVIDEO,       ///<S-video 1
    E_INPUT_SOURCE_SVIDEO2,      ///<S-video 2
    E_INPUT_SOURCE_SVIDEO3,      ///<S-video 3
    E_INPUT_SOURCE_SVIDEO4,      ///<S-video 4
    E_INPUT_SOURCE_SVIDEO_MAX,   ///<S-video max

    E_INPUT_SOURCE_YPBPR,        ///<Component 1
    E_INPUT_SOURCE_YPBPR2,       ///<Component 2
    E_INPUT_SOURCE_YPBPR3,       ///<Component 3
    E_INPUT_SOURCE_YPBPR_MAX,    ///<Component max

    E_INPUT_SOURCE_SCART,        ///<Scart 1
    E_INPUT_SOURCE_SCART2,       ///<Scart 2
    E_INPUT_SOURCE_SCART_MAX,    ///<Scart max

    E_INPUT_SOURCE_HDMI,         ///<HDMI 1
    E_INPUT_SOURCE_HDMI2,        ///<HDMI 2
    E_INPUT_SOURCE_HDMI3,        ///<HDMI 3
    E_INPUT_SOURCE_HDMI4,        ///<HDMI 4
    E_INPUT_SOURCE_HDMI_MAX,     ///<HDMI max

    E_INPUT_SOURCE_DTV,          ///<DTV

    E_INPUT_SOURCE_DVI,          ///<DVI 1
    E_INPUT_SOURCE_DVI2,         ///<DVI 2
    E_INPUT_SOURCE_DVI3,         ///<DVI 2
    E_INPUT_SOURCE_DVI4,         ///<DVI 4
    E_INPUT_SOURCE_DVI_MAX,      ///<DVI max

    // Application source
    E_INPUT_SOURCE_STORAGE,  ///<Storage
    E_INPUT_SOURCE_KTV,      ///<KTV
    E_INPUT_SOURCE_JPEG,     ///<JPEG
    E_INPUT_SOURCE_DTV2,      ///<DTV2
    E_INPUT_SOURCE_STORAGE2,     ///<Storage2

    E_INPUT_SOURCE_NUM,      ///<number of the source
    E_INPUT_SOURCE_NONE;    ///<NULL input

	public static final Parcelable.Creator<EN_INPUT_SOURCE_TYPE> CREATOR = new Parcelable.Creator<EN_INPUT_SOURCE_TYPE>()
	{
		public EN_INPUT_SOURCE_TYPE createFromParcel(Parcel in)
		{
			return EN_INPUT_SOURCE_TYPE.values()[in.readInt()];
		}

		public EN_INPUT_SOURCE_TYPE[] newArray(int size)
		{
			return new EN_INPUT_SOURCE_TYPE[size];
		}
	};

	@Override
    public int describeContents()
    {
	    // TODO Auto-generated method stub
	    return 0;
    }

	@Override
    public void writeToParcel(Parcel arg0, int arg1)
    {
		arg0.writeInt(this.ordinal());
    }
	
}