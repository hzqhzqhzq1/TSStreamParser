package com.coship.bean;


public class Section {
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

	public Section() {
	}

	public Section(byte[] sectionBuff) {

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

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public int getSectionSyntaxIndicator() {
		return sectionSyntaxIndicator;
	}

	public void setSectionSyntaxIndicator(int sectionSyntaxIndicator) {
		this.sectionSyntaxIndicator = sectionSyntaxIndicator;
	}

	public int getZero() {
		return zero;
	}

	public void setZero(int zero) {
		this.zero = zero;
	}

	public int getReserved1() {
		return reserved1;
	}

	public void setReserved1(int reserved1) {
		this.reserved1 = reserved1;
	}

	public int getSectionLenth() {
		return sectionLenth;
	}

	public void setSectionLenth(int sectionLenth) {
		this.sectionLenth = sectionLenth;
	}

	public int getTransportStreamId() {
		return transportStreamId;
	}

	public void setTransportStreamId(int transportStreamId) {
		this.transportStreamId = transportStreamId;
	}

	public int getReserver2() {
		return reserver2;
	}

	public void setReserver2(int reserver2) {
		this.reserver2 = reserver2;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public int getCurrentNextIndicator() {
		return currentNextIndicator;
	}

	public void setCurrentNextIndicator(int currentNextIndicator) {
		this.currentNextIndicator = currentNextIndicator;
	}

	public int getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public int getLastSectionNumber() {
		return lastSectionNumber;
	}

	public void setLastSectionNumber(int lastSectionNumber) {
		this.lastSectionNumber = lastSectionNumber;
	}

	public byte[] getSectionData() {
		return sectionData;
	}

	public void setSectionData(byte[] sectionData) {
		this.sectionData = sectionData;
	}
	
	private static String byte2hex(byte [] buffer){  
        String h = "";  
          
        for(int i = 0; i < buffer.length; i++){  
            String temp = Integer.toHexString(buffer[i] & 0xFF);  
            if(temp.length() == 1){  
                temp = "0" + temp;  
            }  
            h = h + " "+ temp;  
        }  
        return h;  
    }  

	@Override
	public String toString() {
		return byte2hex(sectionData);
	}

}
