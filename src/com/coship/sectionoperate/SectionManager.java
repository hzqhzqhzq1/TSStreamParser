package com.coship.sectionoperate;

import java.util.List;

import com.coship.bean.PacketBean;
import com.coship.bean.SectionBean;

public interface SectionManager {
	public static final int PACKET_HEADER_LENGTH = 4;
	public static final int PACKET_LENTH_188 = 188;
	public static final int PACKET_LENTH_204 = 204;
	public static final int SKIP_ONE = 1;
	

	
	
//	List<Section> getSectionList();
	
	
	List<SectionBean> matchSection(List<PacketBean> packetList,int inputTableId);
	
	
}
