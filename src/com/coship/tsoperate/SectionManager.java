package com.coship.tsoperate;

import java.util.List;

import com.coship.bean.Packet;
import com.coship.bean.Section;

public interface SectionManager {
	public static final int PACKET_HEADER_LENGTH = 4;
//	public static final int SECTION_START_POSITION_1 = 5;
//	public static final int SECTION_START_POSITION_2 = 4;
	public static final int PACKET_LENTH_188 = 188;
	public static final int PACKET_LENTH_204 = 204;
	public static final int SKIP_ONE = 1;
	

//	List<Section> mSectionList = new ArrayList<Section>();
	
//	int getEffectiveLength(int packetLength,int skip);
	
	List<Section> getSectionList();
	
//	void initData(int versionNumber,int lastSectionNumber);
	
	List<Section> matchSection(List<Packet> packetList,int inputTableId);
	
	
}
