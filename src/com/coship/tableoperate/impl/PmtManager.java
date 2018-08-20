package com.coship.tableoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.Section;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.PmtStream;
import com.coship.tableoperate.TableManager;

public class PmtManager implements TableManager{
	private Pmt pmt ;
	
	private List<PmtStream> pmtStreamList = new ArrayList<PmtStream>();
	@Override
	public int makeTable(List<Section> sectionList) {
		 for (int i = 0; i < sectionList.size(); i++) {

	            Section section = sectionList.get(i);
	            byte[] sectionData = section.getSectionData();

	            if (pmt == null) {
	                pmt = new Pmt(sectionData);
	            } else {
	                int sectionNumber = sectionData[6] & 0xFF;
	                pmt.setSectionNumber(sectionNumber);
	            }

	            /*
	            * to programInfoLength : 12 byte
	            * programInfoLength : 不定
	            * CRC_32 : 4 byte
	            * total = 16 + ？
	            *
	            * esInfoLength : 不定
	            * Stream : 5 + ？
	            * */
	            int sectionSize = sectionData.length;
	            int theEffectiveLength = sectionSize - (16 + pmt.getProgramInfoLength());
	            int pos = 12 + pmt.getProgramInfoLength();
	            for (int n = 0; n < theEffectiveLength; ) {
	                int streamType = sectionData[pos + n] & 0xFF;
	                int elementaryPid = (((sectionData[pos + 1 + n] & 0x1F) << 8) |
	                        (sectionData[pos + 2 + n] & 0xFF)) & 0x1FFF;
	                int esInfoLength = (((sectionData[pos + 3 + n] & 0xF) << 8) |
	                        (sectionData[pos + 4 + n] & 0xFF)) & 0xFFF;

	                PmtStream pmtStream = new PmtStream(streamType, elementaryPid, esInfoLength);
	                pmtStreamList.add(pmtStream);

	                n += (5 + esInfoLength);
	            }
	        }

	        pmt.setPmtStreamList(pmtStreamList);
		return 0;
	}
	
	public Pmt getPmt() {
		return pmt;
	}
}
