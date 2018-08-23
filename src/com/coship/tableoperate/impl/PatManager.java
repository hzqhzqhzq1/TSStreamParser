package com.coship.tableoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.Section;
import com.coship.bean.table.Pat;
import com.coship.bean.table.PmtPidInfo;
import com.coship.tableoperate.TableManager;

public class PatManager implements TableManager {

	private Pat pat = null;
	private List<PmtPidInfo> pmtPidInfoList = new ArrayList<PmtPidInfo>();

	@Override
	public int makeTable(List<Section> sectionList) {
		for (int i = 0; i < sectionList.size(); i++) {
			Section section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();

			if (pat == null) {
				pat = new Pat(sectionData);
			} else {
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
					PmtPidInfo pmtPidInfo = new PmtPidInfo(programNumber, programMapPid);
					pmtPidInfoList.add(pmtPidInfo);
				}
			}
		}

		pat.setPmtPidInfoList(pmtPidInfoList);
		return 1;
	}

	public Pat getPat() {
		return pat;
	};

}
