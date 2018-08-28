package com.coship.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coship.bean.table.Pat;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.PmtPidInfo;
import com.coship.bean.table.Sdt;
import com.coship.packetoperate.PacketManager;
import com.coship.packetoperate.impl.PacketManagerImpl;
import com.coship.programoperate.ProgramManager;
import com.coship.programoperate.impl.ProgramMangerImpl;
import com.coship.sectionoperate.SectionManager;
import com.coship.sectionoperate.impl.SectionManagerImpl;
import com.coship.tableoperate.TableManagerFactory;
import com.coship.tableoperate.impl.PatManager;
import com.coship.tableoperate.impl.PmtManager;
import com.coship.tableoperate.impl.SdtManager;

/**
 * TS流对象
 * @author 910131
 *
 */
public class Ts implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final int PAT_PID = 0X0000;
	private static final int PAT_TABLE_ID = 0X00;
	private static final int SDT_PID = 0X0011;
	private static final int SDT_TABLE_ID = 0X42;
	private static final int PMT_TABLE_ID = 0X02;

	private String inputFilePath;
	private Sdt sdt;
	private List<Pmt> pmtList;
	private Pat pat;
	private List<Program> programList;

	public Ts(String inputFilePath) {

		this.inputFilePath = inputFilePath;

		PacketManager packetManager = new PacketManagerImpl(inputFilePath);

		/**
		 * PAT
		 */
		SectionManager patSectionManager = new SectionManagerImpl();
		PatManager patManager = (PatManager) TableManagerFactory.createTableManager(PAT_PID, PAT_TABLE_ID);
		
//		if (packetManager.getPacketOfPid(PAT_PID) != null) {
			
			if (patManager.makeTable(
					patSectionManager.matchSection(packetManager.getPacketOfPid(PAT_PID), PAT_TABLE_ID)) == 1) {
				this.pat = patManager.getPat();
				System.out.println(pat.toString());
			}else {
				return;
			}

//		} else {
//			return;
//		}

		/**
		 * PMT list
		 */
		PmtManager pmtManager = null;
		SectionManager pmtSectionManager = null;
		pmtList = new ArrayList<Pmt>();
		for (PmtPidInfo p : pat.getPmtPidInfoList()) {
			pmtSectionManager = new SectionManagerImpl();
			pmtManager = (PmtManager) TableManagerFactory.createTableManager(p.getProgramMapPid(), PMT_TABLE_ID);
			if (pmtManager.makeTable(pmtSectionManager.matchSection(packetManager.getPacketOfPid(p.getProgramMapPid()),
					PMT_TABLE_ID)) == 1) {
				pmtList.add(pmtManager.getPmt());
			}
		}
		Collections.sort(pmtList);
		for (Pmt p : pmtList) {
			System.out.println(p.toString());
		}

		/**
		 * SDT
		 */
		SectionManager sdtSectionManager = new SectionManagerImpl();
		SdtManager sdtManager = (SdtManager) TableManagerFactory.createTableManager(SDT_PID, SDT_TABLE_ID);
		if (sdtManager
				.makeTable(sdtSectionManager.matchSection(packetManager.getPacketOfPid(SDT_PID), SDT_TABLE_ID)) == 1) {
			this.sdt = sdtManager.getSdt();
			System.out.println(sdt.toString());
		}

		/**
		 * Program list
		 */
		ProgramManager pm = new ProgramMangerImpl();
		if (sdt != null) {
			this.programList = pm.makeProgramList(pat, sdt);
			for (Program p : programList) {
				System.out.println(p.toString());
			}
		}

	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public Sdt getSdt() {
		return sdt;
	}

	public Pat getPat() {
		return pat;
	}

	public List<Program> getProgramList() {
		return programList;
	}

	public List<Pmt> getPmtList() {
		return pmtList;
	}

}
