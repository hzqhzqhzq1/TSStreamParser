package com.coship.bean.table;

import static java.lang.Integer.toHexString;

import java.util.ArrayList;
import java.util.List;

/**
 * PMT表
 * @author 910131
 *
 */
public class Pmt implements Comparable<Pmt> {

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

	private List<ElementStream> elementStreamList = new ArrayList<>();

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
		int crc32 = (((((sectionData[sectionData.length - 4] & 0xFF) << 8
				| (sectionData[sectionData.length - 3] & 0xFF)) & 0xffff) << 16)
				| (((sectionData[sectionData.length - 2] & 0xFF) << 8 | (sectionData[sectionData.length - 1] & 0xFF))
						& 0xffff))
				& 0xffffffff;

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

	public int getTableId() {
		return tableId;
	}
	public int getSectionSyntaxIndicator() {
		return sectionSyntaxIndicator;
	}

	public int getSectionLength() {
		return sectionLength;
	}

	public int getProgramNumber() {
		return programNumber;
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


	public int getPcrPid() {
		return pcrPid;
	}

	public int getProgramInfoLength() {
		return programInfoLength;
	}

	public List<ElementStream> getElementStreamList() {
		return elementStreamList;
	}

	public void setElementStreamList(List<ElementStream> elementStreamList) {
		this.elementStreamList = elementStreamList;
	}

	public int getCrc32() {
		return crc32;
	}

	@Override
	public int compareTo(Pmt o) {
		return this.programNumber > o.programNumber ? 1 : (this.programNumber == o.programNumber ? 0 : -1);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		int esOrderId = 1;
		stringBuilder.append("--------------PMT---------------\n" + "table_id : 0x" + toHexString(tableId) + "\n"
				+ "section_syntax_indicator : 0x" + toHexString(sectionSyntaxIndicator) + "\n" + "section_length : 0x"
				+ toHexString(sectionLength) + "\n" + "program_number : 0x" + toHexString(programNumber) + "\n"
				+ "version_number : 0x" + toHexString(versionNumber) + "\n" + "current_next_indicator : 0x"
				+ toHexString(currentNextIndicator) + "\n" + "section_number : 0x" + toHexString(sectionNumber) + "\n"
				+ "last_section_number : 0x" + toHexString(lastSectionNumber) + "\n" + "PCR_PID : 0x" + toHexString(pcrPid)
				+ "\n" + "program_info_length : 0x" + toHexString(programInfoLength) + "\n\n");
		for (ElementStream elementStream : elementStreamList) {
			stringBuilder.append("单元流" + (esOrderId++) + ": " + elementStream.toString());
		}
		stringBuilder.append("\n" + "CRC_32: 0x" + toHexString(crc32) + "\n--------------------------------");
		return stringBuilder.toString();
	}

}
