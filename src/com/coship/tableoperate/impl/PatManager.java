package com.coship.tableoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.Section;
import com.coship.bean.table.Pat;
import com.coship.bean.table.PatProgram;
import com.coship.tableoperate.TableManager;

public class PatManager implements TableManager{
	
	private Pat pat = null;
	private List<PatProgram> patProgramList = new ArrayList<PatProgram>();
	
	@Override
	public int makeTable(List<Section> sectionList) {
		for(int i =0;i<sectionList.size();i++) {
			Section section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();
			
			if(pat == null) {
				pat = new Pat(sectionData);
			}else {
				int sectionNumber = sectionData[6] & 0xFF;
                pat.setSectionNumber(sectionNumber);
			}
			
			int sectionSize = sectionData.length;
			int theEffectiveLength = sectionSize - 12;
			
			 for (int j = 0; j < theEffectiveLength; j += 4) {
	                int programNumber = (((sectionData[8 + j] & 0xFF) << 8) | (sectionData[9 + j] & 0xFF)) & 0xFFFF;
	                int reserved3 = (sectionData[10 + j] >> 5) & 0x7;
	                pat.setReserved3(reserved3);
	                if (programNumber == 0x00) {
	                    int networkPid = (((sectionData[10 + j] & 0x1F) << 8) | (sectionData[11 + j] & 0xFF)) & 0x1FFF;
	                    pat.setNetworkPid(networkPid);
	                } else {
	                	
	                    int programMapPid = (((sectionData[10 + j] & 0x1F) << 8) | (sectionData[11 + j] & 0xFF)) & 0x1FFF;
	                    PatProgram patProgram = new PatProgram(programNumber, programMapPid);
	                    patProgram.setOrderId(j/4+1);
	                    patProgramList.add(patProgram);
	                }
	            }
		}
		
		pat.setPatProgramList(patProgramList);
		print();
		return 1;
	}
	
	public Pat getPat() {
		return pat;
	};
	
	public String print() {
		System.out.println("table_id = 0x"+Integer.toHexString(pat.getTableId()));
		System.out.println("section_syntax_indicator = 0x"+Integer.toHexString(pat.getSectionSyntaxIndicator()));
		System.out.println("section_length = 0x"+Integer.toHexString(pat.getSectionLength()));
		System.out.println("transport_stream_id = 0x"+Integer.toHexString(pat.getTransportStreamId()));
		System.out.println("version_number = 0x"+Integer.toHexString(pat.getVersionNumber()));
		System.out.println("current_next_indicator = 0x"+Integer.toHexString(pat.getCurrentNextIndicator()));
		System.out.println("section_number = 0x"+Integer.toHexString(pat.getSectionNumber()));
		System.out.println("last_section_number = 0x"+Integer.toHexString(pat.getLastSectionNumber()));
		if(pat.getNetworkPid() != 0) {
			System.out.println("network_pid = 0x"+ Integer.toHexString(pat.getNetworkPid()));
		}
		System.out.println("PMT表PID信息： "+pat.getPatProgramList().toString());
		System.out.println("CRC_32 = 0x"+Integer.toHexString(pat.getCrc32()));

		return null;
	}
}
