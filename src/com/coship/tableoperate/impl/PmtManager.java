package com.coship.tableoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.SectionBean;
import com.coship.bean.tables.ElementStreamBean;
import com.coship.bean.tables.PmtBean;
import com.coship.tableoperate.TableManager;

/**
 * PMT表管理器
 * @author 910131
 *
 */
public class PmtManager implements TableManager {
	private PmtBean pmt;

	@Override
	public int makeTable(List<SectionBean> sectionList) {
		if (sectionList == null) {
			return 0;
		}
		List<ElementStreamBean> elementStreamList = new ArrayList<ElementStreamBean>();
		for (int i = 0; i < sectionList.size(); i++) {
			SectionBean section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();

			if (pmt == null) {
				pmt = new PmtBean(sectionData);
			} 
			/*
			 * table头部 : 12 byte 
			 * programInfoLength
			 * CRC_32 : 4 byte
			 * total = 16 + programInfoLength
			 * esInfoLength Stream长度 = 5+esInfolength
			 */
			int sectionSize = sectionData.length;
			int theEffectiveLength = sectionSize - (16 + pmt.getProgramInfoLength());
			int pos = 12 + pmt.getProgramInfoLength();
			for (int n = 0; n < theEffectiveLength;) {
				int streamType = sectionData[pos + n] & 0xFF;
				int elementaryPid = (((sectionData[pos + 1 + n] & 0x1F) << 8) | (sectionData[pos + 2 + n] & 0xFF))
						& 0x1FFF;
				int esInfoLength = (((sectionData[pos + 3 + n] & 0xF) << 8) | (sectionData[pos + 4 + n] & 0xFF))
						& 0xFFF;

				ElementStreamBean elementStream = new ElementStreamBean(streamType, elementaryPid, esInfoLength);
				elementStreamList.add(elementStream);

				n += (5 + esInfoLength);
			}
		}

		pmt.setElementStreamList(elementStreamList);
		System.out.println("**************************PMT解析完成****************************************");
		return 1;
	}

	public PmtBean getPmt() {
		return pmt;
	}
}
