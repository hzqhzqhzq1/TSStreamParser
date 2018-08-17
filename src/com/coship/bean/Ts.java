package com.coship.bean;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.table.Pat;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.Sdt;
import com.coship.tableoperate.impl.PatManager;
import com.coship.tableoperate.impl.TableParserFactory;
import com.coship.tsoperate.PacketManager;
import com.coship.tsoperate.SectionManager;
import com.coship.tsoperate.impl.PacketManagerImpl;
import com.coship.tsoperate.impl.SectionManagerImpl;

public class Ts {
	private static final int PAT_PID=0X0000;
	private static final int PAT_TABLE_ID=0X00;
	
	private String inputFilePath;
	private int packetLength;
	private int startPosition;
	private Sdt sdt;
	private Pmt pmt;
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
		
		/**
		 * SDT
		 */
		
		/**
		 * ProgramList
		 */
		
	}
	
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
	public Pmt getPmt() {
		return pmt;
	}
	public void setPmt(Pmt pmt) {
		this.pmt = pmt;
	}
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
}
