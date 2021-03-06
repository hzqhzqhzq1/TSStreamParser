package com.coship.packetoperate.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coship.bean.PacketBean;
import com.coship.packetoperate.PacketManager;

/**
 * 包管理器
 * @author 910131
 *
 */
public class PacketManagerImpl implements PacketManager {

	private Map<Integer, List<PacketBean>> packetMap = null;

	private String inputFilePath;
	private int packetStartPosition = -1;
	private int packetLength = -1;

	public PacketManagerImpl(String inputFilePath) {
		super();
		this.inputFilePath = inputFilePath;
		packetAttributesObtain();
		sortPacket2MapByPid();
	}

	private void packetAttributesObtain() {
		File file = new File(inputFilePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			int tmp;
			boolean packetLengthConfirm;
			while ((tmp = fis.read()) != -1) {
				packetStartPosition++;
				packetLengthConfirm = false;
//				System.out.println("====当前位置为：===》" + packetStartPosition + "0x" + Integer.toHexString(tmp&0xff));
				if (tmp == PACKET_HEADER_SYNC_BYTE) {
					/**
					 * 循环10次跳188检测是否为0x47
					 */
					for (int i = 0; i < CYCLE_TEN_TIMES; i++) {
						long lg = fis.skip(PACKET_LENTH_188 - 1);
						// 长度不足情况
						if (lg != PACKET_LENTH_188 - 1) {
							System.out.println("====跳转187字节失败=====");
							return;
						}
						// 读取跳过187字节后的字节
						tmp = fis.read();
//						System.out.println("====当前已经跳过" + PACKET_LENTH_188 * (i + 1) + "byte");
						// 出现非0x47
						if (tmp != PACKET_HEADER_SYNC_BYTE) {
							// 回到循环起始位置
							lg = fis.skip((-1) * PACKET_LENTH_188 * (i + 1));
							// 需要继续检测是否为204字节
							break;
						}
						if (i == CYCLE_TEN_TIMES - 1) {
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
//							System.out.println("====跳转203字节失败=====");
							return;
						}
						tmp = fis.read();
//						System.out.println("====已经跳过" + PACKET_LENTH_204 * (i + 1) + "byte");
						if (tmp != PACKET_HEADER_SYNC_BYTE) {
							lg = fis.skip((-1) * PACKET_LENTH_204 * (i + 1));
							break;
						}
						if (i == CYCLE_TEN_TIMES - 1) {
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
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		System.out.println("包长:" + packetLength + "  开始位置为：" + packetStartPosition);
	}

	private Map<Integer, List<PacketBean>> sortPacket2MapByPid() {
		long startTime = System.currentTimeMillis();
		packetMap = new HashMap<>();

		if (packetLength == -1 || packetStartPosition == -1) {
			packetAttributesObtain();
		}

		File file = new File(inputFilePath);
		BufferedInputStream bis = null;
		try {
//			缓冲区大小调优
			/*
			 * 默认大小8K-->缓冲区大小64K：
			 *	800m-->3.114-3.5s 
			 *	33m -->0.97-0.99s
			 *	159m -->0.53-0.56s
			 *	181m -->0.73-0.77s
			 *	解析速度大约为 250-300 mb/s
			 */
			bis = new BufferedInputStream(new FileInputStream(file), 8 * 8 * 1024);//128K

			if (bis.skip(packetStartPosition) != packetStartPosition) {
				System.out.println("packetStartPosition:" + packetStartPosition);
				throw new IllegalArgumentException("skip to packetStartPosition has problem! ");
			}

			int tmp;

			do {

				byte[] buff = new byte[packetLength];
				tmp = bis.read(buff);
				if (tmp != packetLength) {
					break;
				}
				if (buff[0] == PACKET_HEADER_SYNC_BYTE) {
					PacketBean packet = new PacketBean(buff);
					if (!packetMap.containsKey(packet.getPid())) {
						List<PacketBean> list = new ArrayList<PacketBean>();
						list.add(packet);
						packetMap.put(packet.getPid(), list);
					} else {
						packetMap.get(packet.getPid()).add(packet);
					}
				}

			} while (tmp != -1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("**************************全部Packet获取完成****************************************");
		System.out.println("执行时间为：" + (endTime - startTime) + "ms");
		return packetMap;
	}

	@Override
	public List<PacketBean> getPacketListByPid(int pid) {
		return packetMap.get(pid);
	}
}
