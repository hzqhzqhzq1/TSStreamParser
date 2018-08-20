package com.coship.tsoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.Packet;
import com.coship.bean.Section;
import com.coship.tsoperate.SectionManager;

public class SectionManagerImpl implements SectionManager{
	public static final int PACKET_HEADER_LENGTH = 4;
	public  int section_start_position = 5;
//	public  int section_start_position_2 = 4;
	public static final int PACKET_LENTH_188 = 188;
	public static final int PACKET_LENTH_204 = 204;
	public static final int SKIP_ONE = 1;
	
//	初始化VersionNumer
	private int mVersionNumber = -1;
	
	private byte[][] mList;
	private int[] mCursor;
	private int[] mNextContinuityCounter;

	private List<Section> mSectionList = new ArrayList<Section>();
	
//	public int sectionRNum=0;


	@Override
	public List<Section> getSectionList() {
		return mSectionList;
	}


	
	private void initData(int versionNumber,int lastSectionNumber) {
		int size = lastSectionNumber + 1;
		
		mVersionNumber = versionNumber;
		mList = new byte[size][];
		mCursor = new int[size];
		mNextContinuityCounter = new int[size];
		for(int i =0 ; i<size;i++) {
			mCursor[i] = 0;
			mNextContinuityCounter[i] = -1;
		}
		mSectionList.clear();
//		System.out.println("cursor and  mlist size:"+size);
//		System.out.println("initData");
	
	}

	private int getEffectiveLenth(int packetLength,int skip,int adaptationFieldLength) {
		int effectiveLength = 0;
		int adaptation_length = 0 ;
		
		if(adaptationFieldLength != 0 ) {
			adaptation_length = adaptationFieldLength+1;
		}
//		判断当前Packet中有效数据长度
		if (packetLength == PACKET_LENTH_188) {
			effectiveLength = packetLength - PACKET_HEADER_LENGTH - skip  - adaptation_length;
		}else if(packetLength == PACKET_LENTH_204){
			effectiveLength = packetLength -PACKET_HEADER_LENGTH -skip - 16 - adaptation_length;
		}
		return effectiveLength; 
	}


	@Override
	public List<Section> matchSection(List<Packet> packetList,int inputTableId) {
		for(int tmp = 0;tmp<packetList.size();tmp++) {
			section_start_position = 5;
			Packet p = packetList.get(tmp);
			byte[] packet = p.getPacket();
			int packetLength = packet.length;
			int thisPacketEffectiveLength = 0;
//			int syncByte = p.getSyncByte();
//			int transportErrorIndicator = p.getTransportErrorIndicator();
			int payloadUnitStartIndicator = p.getPayloadUnitStartIndicator();
//			int pid = p.getPid();
			int adaptationFieldControl = p.getAdaptationFieldControl();
			int coutinuityCounter = p.getContinuityCounter();

//			System.out.println("读取到第"+tmp+"个包");
			
			int adaptationFieldLength = 0;
			
			if( adaptationFieldControl == 3) {
				adaptationFieldLength = packet[4]&0xff;
//				System.out.println("adaptationFieldLength: "+adaptationFieldLength);
				section_start_position +=adaptationFieldLength+1;
			}
//			判断Packet是否为首包
//			为首包
			if (payloadUnitStartIndicator == 0x1) {
//				解析表头的数据
//				当payloadUnitStartIndicator为0x1时，不考虑adaptationFieldControl,从下标为5的字节开始为有效负载
				int tableId = packet[section_start_position] & 0xFF;
				int sectionLength = (((packet[section_start_position + 1] & 0xF) << 8)
						| (packet[section_start_position + 2] & 0xFF)) & 0xFFF;
				int versionNumber = (packet[section_start_position + 5] >> 1) & 0x1F;
				int sectionNumber = packet[section_start_position + 6] & 0xFF;
				int lastSectionNumber = packet[section_start_position + 7] & 0xFF;
				
				if (tableId != inputTableId) {
//					System.out.println("TableID不一致");
					continue;
	            }
				
				thisPacketEffectiveLength = getEffectiveLenth(packetLength, SKIP_ONE,adaptationFieldLength);
				
//				sectionLength为sectionLength字段后长度
				int sectionSize = sectionLength+3;
				
				/*
				 *判断VersionNumber 
				 */
				if(mVersionNumber == -1) {
					initData(versionNumber, lastSectionNumber);
				}
				if(mVersionNumber!=versionNumber) {
//					System.out.println("versionNumber不一致");
					initData(versionNumber, lastSectionNumber);
				}
				
//				根据sectionNum判断是否已添加到mList中
				int num = mCursor[sectionNumber];
//				System.out.println("当前包SectionNumber:"+sectionNumber);
//				System.out.println("num = "+num);
				
				if(num == 0 ) {
//					System.out.println("sectionSize:"+sectionSize);
//					System.out.println("sectionNumber:=========>"+ sectionNumber);
					mList[sectionNumber]  = new byte[sectionSize];
				}else {
//					sectionRNum++;
//					System.out.println("重复包次数："+sectionRNum);
					continue;
				}
				

				if(sectionSize <= thisPacketEffectiveLength) {
//					Packet中数据转入
//					包有效数据长度大于段
//					按段长度读取
					for(int i =0;i<sectionSize;i++) {
//						System.out.println(i);
						mList[sectionNumber][i] = packet[section_start_position+i];
//						记录当前sectionNumber已经读入了多少个字节
						mCursor[sectionNumber]++;
					}
					
					 // -2 表示当前 sectionNumber 的数据已写完
	                mNextContinuityCounter[sectionNumber] = -2;
					
					Section section = new Section(mList[sectionNumber]);
//					System.out.println("加入SectionList");
					mSectionList.add(section);
				}else {
//					Packet中数据转入未完整的数组内
					for(int i =0;i<thisPacketEffectiveLength;i++) {
//						System.out.println(i);
						mList[sectionNumber][i] = packet[section_start_position+i];
//						记录当前sectionNumber已经读入了多少个字节
						mCursor[sectionNumber]++;
					}
					
//					记录下一个包的ContinuityCouter
					if(coutinuityCounter ==15) {
						coutinuityCounter = -1;
					}
					mNextContinuityCounter[sectionNumber]  = coutinuityCounter +1;
				}
			
//				return 1;
			}
//			非Section首包
			else {
				
				section_start_position --;
				
				if(mVersionNumber == -1) {
//					System.out.println("no versionNumber");
					continue;
				}
				
				thisPacketEffectiveLength = getEffectiveLenth(packetLength, 0,adaptationFieldLength);
				
				int unFinishSectionNumber = -1;
//				寻找未完整的Section
				for(int i=0;i<mNextContinuityCounter.length;i++) {
					if(mNextContinuityCounter[i] == coutinuityCounter) {
						unFinishSectionNumber = i;
					}
				}
				if(unFinishSectionNumber == -1) {
//					System.out.println("没有未完成组装的Section__退出");
					continue;
				}
				
				int sectionSize = mList[unFinishSectionNumber].length;
				int afterSize = sectionSize - mCursor[unFinishSectionNumber];
				

				
				if(afterSize <= thisPacketEffectiveLength) {
					
//					Packet有效长度比剩余Section长度要大
					for(int i =0;i<afterSize;i++) {
//						System.out.println("组装进入原有Section，组装完该Section已完成"+thisPacketEffectiveLength);
						mList[unFinishSectionNumber][mCursor[unFinishSectionNumber]]=packet[section_start_position+i];
						mCursor[unFinishSectionNumber]++;
					}
					
					 // -2 表示当前 sectionNumber 的数据已写完
	                mNextContinuityCounter[unFinishSectionNumber] = -2;
					
					Section section = new Section(mList[unFinishSectionNumber]);
					mSectionList.add(section);
//					System.out.println("非首包加入SectionList");

				}else {
					
//					Packet有效长度比剩余Section长度要小
					for(int i =0;i<thisPacketEffectiveLength;i++) {
//						System.out.println("组装进入原有Section 未完待续："+thisPacketEffectiveLength);
						mList[unFinishSectionNumber][mCursor[unFinishSectionNumber]]=packet[section_start_position+i];
						mCursor[unFinishSectionNumber]++;
					}
					
					if(coutinuityCounter ==15) {
						coutinuityCounter = -1;
					}
					mNextContinuityCounter[unFinishSectionNumber]  = coutinuityCounter +1;
				}
			}			
		}
		return mSectionList;
	}

}
