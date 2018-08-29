package com.coship.tableoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.SectionBean;
import com.coship.bean.tables.PatBean;
import com.coship.bean.tables.PatProgramMapBean;
import com.coship.tableoperate.TableManager;

/**
 * PAT表管理器
 * @author 910131
 *
 */
public class PatManager implements TableManager {

	private PatBean pat ;

	@Override
	public int makeTable(List<SectionBean> sectionList) {
		if(sectionList==null) {
			return 0;
		}
		List<PatProgramMapBean> pmtPidInfoList = new ArrayList<PatProgramMapBean>();

		System.out.println(sectionList.size());
		for (int i = 0; i < sectionList.size(); i++) {
			SectionBean section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();

			if (pat == null) {
				pat = new PatBean(sectionData);
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
					PatProgramMapBean pmtPidInfo = new PatProgramMapBean(programNumber, programMapPid);
					pmtPidInfoList.add(pmtPidInfo);
				}
			}
		}
		System.out.println("**************************PAT解析完成****************************************");
		pat.setPmtPidInfoList(pmtPidInfoList);
		return 1;
	}

	public PatBean getPat() {
		return pat;
	};

}
