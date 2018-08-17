package com.coship.tsoperate.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coship.bean.Packet;
import com.coship.tsoperate.PacketManager;

public class PacketManagerImpl implements PacketManager{
	
	private Map<Integer,List<Packet>> packetMap = null;
	
	private String inputFilePath;
	private  int packetStartPosition = -1;
	private  int packetLength = -1;
	
	public PacketManagerImpl(String inputFilePath) {
		super();
		this.inputFilePath = inputFilePath;
		getPacketLengthAndStartPosition();
		getPacket();
	}
	
	
	private int getPacketLengthAndStartPosition() {
		
		long startTime = System.currentTimeMillis();
		
		File file = new File(inputFilePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			int tmp;
			boolean packetLengthConfirm;
			while ((tmp = fis.read()) != -1) {
				
				packetStartPosition++;
				packetLengthConfirm = false;
				System.out.println("====当前位置为：===》" + packetStartPosition + "0x" + Integer.toHexString(tmp&0xff));
				if (tmp == PACKET_HEADER_SYNC_BYTE) {
					/**
					 * 循环10次跳188检测是否为0x47
					 */
					for (int i = 0; i < CYCLE_TEN_TIMES; i++) {
						long lg = fis.skip(PACKET_LENTH_188 - 1);
						// 长度不足情况
						if (lg != PACKET_LENTH_188 - 1) {
							System.out.println("====跳转187字节失败=====");
							return -1;
						}
						// 读取跳过187字节后的字节
						tmp = fis.read();
						System.out.println("====当前已经跳过" + PACKET_LENTH_188 * (i + 1) + "byte");
						// 出现非0x47
						if (tmp != PACKET_HEADER_SYNC_BYTE) {
							// 回到循环起始位置
							lg = fis.skip((-1) * PACKET_LENTH_188 * (i + 1));
							// 需要继续检测是否为204字节
							break;
						}
						if(i==CYCLE_TEN_TIMES-1) {
							packetLengthConfirm = true;
						}
					}
					
					if (packetLengthConfirm) {
						packetLength = PACKET_LENTH_188;
						break;
					}
					/**
					 * 循环10次跳204检测是否为204
					 */
					for (int i = 0; i < CYCLE_TEN_TIMES; i++) {
						long lg = fis.skip(PACKET_LENTH_204 - 1);
						if (lg != PACKET_LENTH_204 - 1) {
							System.out.println("====跳转203字节失败=====");
							return -1;
						}

						tmp = fis.read();
						System.out.println("====已经跳过" + PACKET_LENTH_204 * (i + 1) + "byte");
						if (tmp != PACKET_HEADER_SYNC_BYTE) {
							lg = fis.skip((-1) * PACKET_LENTH_204 * (i + 1));
							break;
						}
						if(i==CYCLE_TEN_TIMES-1) {
							packetLengthConfirm = true;
						}
					}
					if (packetLengthConfirm) {
						packetLength = PACKET_LENTH_204;
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time:"+(endTime - startTime));
		System.out.println("包长:"+packetLength+"  开始位置为："+packetStartPosition);
		return  packetLength;
		
	}

	
	/**
	 * 测试方法
	 * @return
	 */
	public Map<Integer,List<Packet>> getPacketMap(){
		return this.packetMap;
		
	}
	
	private  Map<Integer, List<Packet>> getPacket() {
		long startTime = System.currentTimeMillis();
		packetMap = new HashMap<>();
		
		if (packetLength == -1||packetStartPosition ==-1) {
			getPacketLengthAndStartPosition();
		}
		
		File file = new File(inputFilePath);
		BufferedInputStream bis =null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			
			if (bis.skip(packetStartPosition)!= packetStartPosition) {
				System.out.println("packetStartPosition:"+packetStartPosition);
				throw new IllegalArgumentException("skip to packetStartPosition has problem! ");
			}
			
			int tmp;
			
			do {
				byte[] buff = new byte[packetLength];
				tmp = bis.read(buff);
				if (tmp == packetLength) {
					if (buff[0] == PACKET_HEADER_SYNC_BYTE) {
//						packetNum++;
						Packet packet = new Packet(buff);
						
						/**
						 * 判断包的有效性
						 */
						if (packet.getTransportErrorIndicator() == 0x1) {
							System.out.println("The packet is Error");
							continue;
						}
						
						if(!packetMap.containsKey(packet.getPid())) {
							List<Packet> list = new ArrayList<Packet>();
							list.add(packet);
							packetMap.put(packet.getPid(), list);
						}else{
							packetMap.get(packet.getPid()).add(packet);
						}
					}
				}
			} while (tmp != -1);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(bis!=null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
//		try {
// 			fis = new FileInputStream(inputFilePath);
//			
//			if (fis.skip(packetStartPosition)!= packetStartPosition) {
//				System.out.println("packetStartPosition:"+packetStartPosition);
//				throw new IllegalArgumentException("skip to packetStartPosition has problem! ");
//			}
//			
//			int tmp;
////			int packetNum = 0;
//			
//			do {
//				byte[] buff = new byte[packetLength];
//				tmp = fis.read(buff);
//				if (tmp == packetLength) {
//					if (buff[0] == PACKET_HEADER_SYNC_BYTE) {
////						packetNum++;
//						Packet packet = new Packet(buff);
//						if (packet.getTransportErrorIndicator() == 1) {
//                            continue;
//                        }
//						if(!packetMap.containsKey(packet.getPid())) {
//							List<Packet> list = new ArrayList<Packet>();
//							list.add(packet);
//							packetMap.put(packet.getPid(), list);
//						}else{
//							packetMap.get(packet.getPid()).add(packet);
//						}
//					}
//				}
//			} while (tmp != -1);
////			System.out.println("packetNum:" + packetNum);
////			System.out.println("List长度：" + packetList.size());
//			fis.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//			if(fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		long endTime = System.currentTimeMillis();
		System.out.println("执行时间为："+(endTime - startTime)+"ms");
		return packetMap;
	}

	@Override
	public List<Packet> getPacketOfPid(int pid) {
		return packetMap.get(pid);
	}
}
