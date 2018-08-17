package com.coship.tsoperate;

import java.util.List;

import com.coship.bean.Packet;

public interface PacketManager {
	static final int PACKET_HEADER_SYNC_BYTE = 0x47;
	static final int PACKET_LENTH_188 = 188;
	static final int PACKET_LENTH_204 = 204;
	static final int CYCLE_TEN_TIMES = 10;

//	private int getPacketLength() {
//		return 0;
//	}

//	 int getPacketStartPositon();

//	HashMap<Integer, List<Packet>> getPacket();

	List<Packet> getPacketOfPid(int pid);

}
