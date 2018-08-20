package com.coship.bean.table;

import static java.lang.Integer.toHexString;

import java.util.ArrayList;
import java.util.List;


public class Pmt implements Comparable<Pmt>{

	/**
     * table_id : 8 bit
     */
    private int tableId = 0x02;

    /**
     * section_syntax_indicator : 1 bit
     */
    private int sectionSyntaxIndicator = 0x1;

    /**
     * section_length : 12 bit
     */
    private int sectionLength = 0;

    /**
     * program_number : 16 bit
     */
    private int programNumber;

    /**
     * version_number : 5 bit
     */
    private int versionNumber;

    /**
     * current_next_indicator : 1 bit
     */
    private int currentNextIndicator;

    /**
     * section_number : 8 bit
     */
    private int sectionNumber;

    /**
     * last_section_number 8 bit
     */
    private int lastSectionNumber;

    /**
     * pcr_pid : 13 bit
     */
    private int pcrPid;

    /**
     * pcr_pid : 12 bit
     */
    private int programInfoLength;


    List<PmtStream> pmtStreamList = new ArrayList<>();


    /**
     * CRC_32 : 32 bit
     */
    private int crc32;


    public Pmt(byte[] sectionData) {
        super();
        int tableId = sectionData[0] & 0xFF;
        int sectionSyntaxIndicator = (sectionData[1] >> 7) & 0x1;
        int sectionLength = (((sectionData[1] & 0xF) << 8) | (sectionData[2] & 0xFF)) & 0xFFF;
        int programNumber = (((sectionData[3] & 0xFF) << 8) | (sectionData[4] & 0xFF)) & 0xFFFF;
        int versionNumber = (sectionData[5] >> 1) & 0x1F;
        int currentNextIndicator = sectionData[5] & 0x1;
        int sectionNumber = sectionData[6] & 0xFF;
        int lastSectionNumber = sectionData[7] & 0xFF;
        int pcrPid = (((sectionData[8] & 0x1F) << 8) | (sectionData[9] & 0xFF)) & 0x1FFF;
        int programInfoLength = (((sectionData[10] & 0xF) << 8) | (sectionData[11] & 0xFF)) & 0xFFF;
        int crc32 = (((((sectionData[sectionData.length - 4] & 0xFF)<<8
        		|(sectionData[sectionData.length - 3] & 0xFF))&0xffff)<<16)
        		|(((sectionData[sectionData.length - 2] & 0xFF)<<8
        		|(sectionData[sectionData.length - 1] & 0xFF))&0xffff))&0xffffffff;

        this.tableId = tableId;
        this.sectionSyntaxIndicator = sectionSyntaxIndicator;
        this.sectionLength = sectionLength;
        this.programNumber = programNumber;
        this.versionNumber = versionNumber;
        this.currentNextIndicator = currentNextIndicator;
        this.sectionNumber = sectionNumber;
        this.lastSectionNumber = lastSectionNumber;
        this.pcrPid = pcrPid;
        this.programInfoLength = programInfoLength;
        this.crc32 = crc32;
    }

    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--------------PMT---------------\n"+"tableId : 0x" + toHexString(tableId) + "\n" +
                "sectionSyntaxIndicator : 0x" + toHexString(sectionSyntaxIndicator) + "\n" +
                "sectionLength : 0x" + toHexString(sectionLength) + "\n" +
                "programNumber : 0x" + toHexString(programNumber) + "\n" +
                "versionNumber : 0x" + toHexString(versionNumber) + "\n" +
                "currentNextIndicator : 0x" + toHexString(currentNextIndicator) + "\n" +
                "sectionNumber : 0x" + toHexString(sectionNumber) + "\n" +
                "lastSectionNumber : 0x" + toHexString(lastSectionNumber) + "\n" +
                "pcrPid : 0x" + toHexString(pcrPid) + "\n" +
                "programInfoLength : 0x" + toHexString(programInfoLength) + "\n");
        for (PmtStream pmtStream : pmtStreamList) {
            stringBuilder.append(" -- \n" +
            		"esPid : 0x" + toHexString(pmtStream.getesPid()) + "\n" +
                    "streamType : 0x" + toHexString(pmtStream.getStreamType()) + "\n" +
                    "esInfoLength : 0x" + toHexString(pmtStream.getEsInfoLength()) + "\n");
        }
        stringBuilder.append("-- \n"+"crc32: 0x"+toHexString(crc32)+"\n---------------------------");
        return stringBuilder.toString();
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

    public int getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(int sectionLength) {
        this.sectionLength = sectionLength;
    }

    public int getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(int programNumber) {
        this.programNumber = programNumber;
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

    public int getPcrPid() {
        return pcrPid;
    }

    public void setPcrPid(int pcrPid) {
        this.pcrPid = pcrPid;
    }

    public int getProgramInfoLength() {
        return programInfoLength;
    }

    public void setProgramInfoLength(int programInfoLength) {
        this.programInfoLength = programInfoLength;
    }

    public List<PmtStream> getPmtStreamList() {
        return pmtStreamList;
    }

    public void setPmtStreamList(List<PmtStream> pmtStreamList) {
        this.pmtStreamList = pmtStreamList;
    }

    public int getCrc32() {
        return crc32;
    }

    public void setCrc32(int crc32) {
        this.crc32 = crc32;
    }

	@Override
	public int compareTo(Pmt o) {
		int result  = this.programNumber>o.programNumber?1:(this.programNumber  == o.programNumber?0:-1);
		return result;
	}

}
