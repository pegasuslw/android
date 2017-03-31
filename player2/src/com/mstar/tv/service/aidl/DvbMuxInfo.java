package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class DvbMuxInfo implements Parcelable{
	// / Satellite table ID
    public int satTableId; // please don't modify this variable...
    // / Network table ID
    public int networkTableID; // please don't modify this variable...
    // / MUX reference count
    public int refCnt; // please don't modify this variable...
    // / MUX information transportstream ID
    public int transportStreamId;
    // / MUX information original network ID
    public int originalNetworkId;
    // MUX information network ID
    public int networkId;
    // / MUX information cell ID
    public int cellID;
    // / MUX information RF channel number
    public short rfNumber;
    // / MUX RF channel frequency
    public int frequency;
    // / MUX information lossSignal frequency
    public int lossSignalFrequency;
    // / MUX information lossSignal start time
    public int lossSignalStartTime;
    // / MUX information sambol rate
    public int symbRate; // DVBT no use,DVBC use
    // / MUX information QAM mode
    public short modulationMode;
    // / MUX information physical layer pipes ID
    public int plpID; // DVBT-2 use only
    // / MUX information High priority/Low Priority
    public boolean lpCoding; // DVBT use only
    // / Satellite ID
    public short satID; // DVBS use only
    // / Bandwidth
    public short bandwidth;
    // / Satellite polarization 0: vertical, 1: horizontal

    // polarity DVBS use only // 0:V, H:1, bitfield:1
    // pilots DVBS use only // 0:Off, 1:On, bitfield:1
    // reserved DVBS use only, bitfield:4
    public int polarityPilotsReserved;
    public static final Parcelable.Creator<DvbMuxInfo> CREATOR = new Parcelable.Creator<DvbMuxInfo>()
    		{
    			public DvbMuxInfo createFromParcel(Parcel in)
    			{
    				return new DvbMuxInfo(in);
    			}

    			public DvbMuxInfo[] newArray(int size)
    			{
    				return new DvbMuxInfo[size];
    			}
    		};
    private DvbMuxInfo(Parcel in) 
    	{  
    		satTableId = in.readInt();
    		networkTableID = in.readInt();
    		refCnt = in.readInt();
    		transportStreamId = in.readInt();
    		originalNetworkId = in.readInt();
    		networkId = in.readInt();
    		cellID = in.readInt();
    		rfNumber = (short)in.readInt();
    		frequency = in.readInt();
    		lossSignalFrequency = in.readInt();
    		lossSignalStartTime = in.readInt();
    		symbRate = in.readInt();
    		modulationMode = (short) in.readInt();
    		plpID = in.readInt();
    		lpCoding = (in.readInt() == 1?true:false);
    		satID = (short) in.readInt();
    		bandwidth = (short) in.readInt();
    		polarityPilotsReserved = in.readInt();
    	 }
    	
    public DvbMuxInfo(int satTableId, int networkTableID, int refCnt,
				int transportStreamId, int originalNetworkId, int networkId,
				int cellID, short rfNumber, int frequency,
				int lossSignalFrequency, int lossSignalStartTime, int symbRate,
				short modulationMode, int plpID, boolean lpCoding, short satID,
				short bandwidth, int polarityPilotsReserved) {
			super();
			this.satTableId = satTableId;
			this.networkTableID = networkTableID;
			this.refCnt = refCnt;
			this.transportStreamId = transportStreamId;
			this.originalNetworkId = originalNetworkId;
			this.networkId = networkId;
			this.cellID = cellID;
			this.rfNumber = rfNumber;
			this.frequency = frequency;
			this.lossSignalFrequency = lossSignalFrequency;
			this.lossSignalStartTime = lossSignalStartTime;
			this.symbRate = symbRate;
			this.modulationMode = modulationMode;
			this.plpID = plpID;
			this.lpCoding = lpCoding;
			this.satID = satID;
			this.bandwidth = bandwidth;
			this.polarityPilotsReserved = polarityPilotsReserved;
		}

	public DvbMuxInfo()
    {
        satTableId = 0;
        networkTableID = 0;
        refCnt = 0;
        transportStreamId = 0;
        originalNetworkId = 0;
        networkId = 0;
        cellID = 0;
        rfNumber = 0;
        frequency = 0;
        lossSignalFrequency = 0;
        lossSignalStartTime = 0;
        symbRate = 0;
        modulationMode = 0;
        plpID = 0;
        lpCoding = false;
        satID = 0;
        bandwidth = 0;
        polarityPilotsReserved = 0;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stube
		dest.writeInt(satTableId);
		dest.writeInt(networkTableID);
		dest.writeInt(refCnt);
		dest.writeInt(transportStreamId);
		dest.writeInt(originalNetworkId);
		dest.writeInt(networkId);
		dest.writeInt(cellID);
		dest.writeInt(rfNumber);
		dest.writeInt(frequency);
		dest.writeInt(lossSignalFrequency);
		dest.writeInt(lossSignalStartTime);
		dest.writeInt(symbRate);
		dest.writeInt(modulationMode);
		dest.writeInt(plpID);
		dest.writeInt(lpCoding?1:0);
		dest.writeInt(satID);
		dest.writeInt(bandwidth);
		dest.writeInt(polarityPilotsReserved);
	}
}
