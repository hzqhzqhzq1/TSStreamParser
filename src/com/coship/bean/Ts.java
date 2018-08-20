package com.coship.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coship.bean.table.Pat;
import com.coship.bean.table.PatProgram;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.Sdt;
import com.coship.tableoperate.impl.PatManager;
import com.coship.tableoperate.impl.PmtManager;
import com.coship.tableoperate.impl.SdtManager;
import com.coship.tableoperate.impl.TableParserFactory;
import com.coship.tsoperate.PacketManager;
import com.coship.tsoperate.SectionManager;
import com.coship.tsoperate.impl.PacketManagerImpl;
import com.coship.tsoperate.impl.SectionManagerImpl;

public class Ts implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final int PAT_PID = 0X0000;
	private static final int PAT_TABLE_ID = 0X00;
	private static final int SDT_PID = 0X0011;
	private static final int SDT_TABLE_ID = 0X42;
	private static final int PMT_TABLE_ID=0X02;

	private String inputFilePath;
	private int packetLength;
	private int startPosition;
	private Sdt sdt;
	private List<Pmt> pmtList;
//	private Pmt pmt;
	private Pat pat;
	private List<Program> programList = new ArrayList<Program>();

	public Ts(String inputFilePath) {

		this.inputFilePath = inputFilePath;
		PacketManager packetManager = new PacketManagerImpl(inputFilePath);
		SectionManager sectionManager = new SectionManagerImpl();

		/**
		 * PAT
		 */
		
		PatManager patManager = (PatManager) TableParserFactory.createTableManager(PAT_PID, PAT_TABLE_ID);
		patManager.makeTable(sectionManager.matchSection(packetManager.getPacketOfPid(PAT_PID), PAT_TABLE_ID));
		this.pat = patManager.getPat();

		/**
		 * PMT
		 */
		PmtManager pmtManager = null;
		List<Pmt> pmtList = new ArrayList<Pmt>();
		for(PatProgram p:pat.getPatProgramList()) {
			pmtManager = (PmtManager) TableParserFactory.createTableManager(p.getProgramMapPid(), PMT_TABLE_ID);
			pmtManager.makeTable(sectionManager.matchSection(packetManager.getPacketOfPid(p.getProgramMapPid()), PMT_TABLE_ID));
			pmtList.add(pmtManager.getPmt());
		}
		this.pmtList = pmtList;
		Collections.sort(pmtList);
		for(Pmt p :pmtList) {
			System.out.println(p.print());
		}
		
		

		/**
		 * SDT
		 */
		SectionManager msectionManager = new SectionManagerImpl();
		SdtManager sdtManager = (SdtManager) TableParserFactory.createTableManager(SDT_PID, SDT_TABLE_ID);
		List<Section> sectionList =  msectionManager.matchSection(packetManager.getPacketOfPid(SDT_PID), SDT_TABLE_ID);
//		System.out.println("==========Section length:====="+sectionList.get(0).getSectionData().length);
		sdtManager.makeTable(sectionList);
		this.sdt = sdtManager.getSdt();

		/**
		 * ProgramList
		 */

	}

//	private static String byte2hex(byte[] buffer) {
//		String h = "";
//		for (int i = 0; i < buffer.length; i++) {
//			String temp = Integer.toHexString(buffer[i] & 0xFF);
//			if (temp.length() == 1) {
//				temp = "0" + temp;
//			}
//			h = h + " " + temp;
//		}
//		return h;
//	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public int getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public Sdt getSdt() {
		return sdt;
	}

	public void setSdt(Sdt sdt) {
		this.sdt = sdt;
	}

//	public Pmt getPmt() {
//		return pmt;
//	}
//
//	public void setPmt(Pmt pmt) {
//		this.pmt = pmt;
//	}

	public Pat getPat() {
		return pat;
	}

	public void setPat(Pat pat) {
		this.pat = pat;
	}

	public List<Program> getProgramList() {
		return programList;
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}
	
	public List<Pmt> getPmtList() {
		return pmtList;
	}

	public void setPmtList(List<Pmt> pmtList) {
		this.pmtList = pmtList;
	}
}
