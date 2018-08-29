package com.coship.bean;

import java.io.Serializable;

/**
 * 段
 * @author 910131
 *
 */
public class SectionBean implements Serializable{
	private static final long serialVersionUID = 1L;
//	8bit
	private int tableId;
//	1bit
	private int sectionSyntaxIndicator;
//	1bit
	private int zero;
//	2bit
	private int reserved1;
//	12bit
	private int sectionLenth;
//	16bit
	private int transportStreamId;
//	2bit
	private int reserver2;
//	5bit
	private int versionNumber;
//	1bit
	private int currentNextIndicator;
//	8bit
	private int sectionNumber;
//	8bit
	private int lastSectionNumber;

	private byte[] sectionData;

	public SectionBean(byte[] sectionBuff) {

		byte[] s = sectionBuff;

		this.tableId = s[0] & 0xFF;
		this.sectionSyntaxIndicator = (s[1] >> 7) & 0x1;
		this.zero = (s[1] >> 6) & 0x1;
		this.reserved1 = (s[1] >> 4) & 0x3;
		this.sectionLenth = (((s[1] & 0xf) << 8) | (s[2] & 0xff)) & 0xfff;
		this.transportStreamId = (((s[3] & 0xff) << 8) | (s[4] & 0xff)) & 0xffff;
		this.reserver2 = (s[5] >> 6) & 0x3;
		this.versionNumber = (s[5] >> 1) & 0x1f;
		this.currentNextIndicator = s[5] & 0x1;
		this.sectionNumber = s[6] & 0xff;
		this.lastSectionNumber = s[7] & 0xff;

		this.sectionData = s;
	}

	public int getTableId() {
		return tableId;
	}

	public int getSectionSyntaxIndicator() {
		return sectionSyntaxIndicator;
	}

	public int getZero() {
		return zero;
	}

	public int getReserved1() {
		return reserved1;
	}

	public int getSectionLenth() {
		return sectionLenth;
	}

	public int getTransportStreamId() {
		return transportStreamId;
	}

	public int getReserver2() {
		return reserver2;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public int getCurrentNextIndicator() {
		return currentNextIndicator;
	}

	public int getSectionNumber() {
		return sectionNumber;
	}

	public int getLastSectionNumber() {
		return lastSectionNumber;
	}

	public byte[] getSectionData() {
		return sectionData;
	}
	
	@Override
	public String toString() {
		String sectionString = "";  
        for(int i = 0; i < sectionData.length; i++){  
            String temp = Integer.toHexString(sectionData[i] & 0xFF);  
            if(temp.length() == 1){  
                temp = "0" + temp;  
            }  
            sectionString = sectionString + " "+ temp;  
        }  
        return sectionString;  
	}

}
