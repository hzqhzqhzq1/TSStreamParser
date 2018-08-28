package com.coship.tableoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.Section;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.ElementStream;
import com.coship.tableoperate.TableManager;

/**
 * PMT表管理器
 * @author 910131
 *
 */
public class PmtManager implements TableManager {
	private Pmt pmt;

	@Override
	public int makeTable(List<Section> sectionList) {
		if (sectionList == null) {
			return 0;
		}
		List<ElementStream> elementStreamList = new ArrayList<ElementStream>();
		for (int i = 0; i < sectionList.size(); i++) {
			Section section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();

			if (pmt == null) {
				pmt = new Pmt(sectionData);
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

				ElementStream elementStream = new ElementStream(streamType, elementaryPid, esInfoLength);
				elementStreamList.add(elementStream);

				n += (5 + esInfoLength);
			}
		}

		pmt.setElementStreamList(elementStreamList);
		System.out.println("**************************PMT解析完成****************************************");
		return 1;
	}

	public Pmt getPmt() {
		return pmt;
	}
}
