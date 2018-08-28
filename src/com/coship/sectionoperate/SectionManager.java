package com.coship.sectionoperate;

import java.util.List;

import com.coship.bean.Packet;
import com.coship.bean.Section;

public interface SectionManager {
	public static final int PACKET_HEADER_LENGTH = 4;
	public static final int PACKET_LENTH_188 = 188;
	public static final int PACKET_LENTH_204 = 204;
	public static final int SKIP_ONE = 1;
	

	
	
//	List<Section> getSectionList();
	
	
	List<Section> matchSection(List<Packet> packetList,int inputTableId);
	
	
}
