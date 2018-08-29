package com.coship.packetoperate;

import java.util.List;

import com.coship.bean.PacketBean;

public interface PacketManager {
	static final int PACKET_HEADER_SYNC_BYTE = 0x47;
	static final int PACKET_LENTH_188 = 188;
	static final int PACKET_LENTH_204 = 204;
	static final int CYCLE_TEN_TIMES = 10;
	
	List<PacketBean> getPacketListByPid(int pid);
}
