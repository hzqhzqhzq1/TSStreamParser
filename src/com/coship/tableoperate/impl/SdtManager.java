package com.coship.tableoperate.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coship.bean.SectionBean;
import com.coship.bean.tables.DescriptorBean;
import com.coship.bean.tables.SdtBean;
import com.coship.bean.tables.SdtServiceBean;
import com.coship.tableoperate.TableManager;

/**
 * SDT表管理器
 * @author 910131
 *
 */
public class SdtManager implements TableManager {
	private SdtBean sdt;

	@Override
	public int makeTable(List<SectionBean> sectionList) {
		if(sectionList==null) {
			return 0;
		}
		List<SdtServiceBean> sdtServiceList = new ArrayList<SdtServiceBean>();
		for (int i = 0; i < sectionList.size(); i++) {
			SectionBean section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();

			if (sdt == null) {
				sdt = new SdtBean(sectionData);
			}
			/*
			 * 表头 : 11 byte 
			 *CRC_32 : 4 byte
			 * 循环部分 = sectionSize-15
			 */
			int sectionSize = sectionData.length;
			int theEffectiveLength = sectionSize - 15;

			for (int j = 0; j < theEffectiveLength;) {

				List<DescriptorBean> descriptorList = new ArrayList<DescriptorBean>();

				int serviceId = (((sectionData[11 + j] & 0xFF) << 8) | (sectionData[12 + j] & 0xFF)) & 0xFFFF;
				int reservedFutureUse = (sectionData[13 + j] >> 6) & 0x3f;
				int eitScheduleFlag = (sectionData[13 + j] >> 1) & 0x1;
				int eitPresentFollowingFlag = sectionData[13 + j] & 0x1;
				int runningStatus = (sectionData[14 + j] >> 5) & 0x7;
				int freeCaMode = (sectionData[14 + j] >> 4) & 0x1;
				int descriptorsLoopLength = (((sectionData[14 + j] & 0xF) << 8) | (sectionData[15 + j] & 0xFF)) & 0xFFF;
				int descriptor_tag = sectionData[16 + j] & 0xff;
				int descriptor_length = sectionData[17 + j] & 0xFF;
				int serviceType = sectionData[18 + j] & 0xFF;
				int serviceProviderNameLength = sectionData[19 + j] & 0xFF;

				byte[] strBytes = new byte[serviceProviderNameLength];
				String serviceProviderName = "未知";
				StringBuilder stringBuilder = new StringBuilder();
				if (serviceProviderNameLength != 0) {
					for (int n = 0; n < serviceProviderNameLength; n++) {
						strBytes[n] = sectionData[20 + j + n];
					}
					try {
						stringBuilder.append(new String(strBytes, "GBK") + " [ 原始数据 ：0x" + byte2hex(strBytes) + " ]");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					serviceProviderName = stringBuilder.toString();
				}

				stringBuilder.delete(0, stringBuilder.length());

				int serviceNameLength = sectionData[20 + serviceProviderNameLength + j] & 0xFF;

				strBytes = new byte[serviceNameLength];

				for (int n = 0; n < serviceNameLength; n++) {
					strBytes[n] = sectionData[21 + serviceProviderNameLength + j + n];
				}
				try {
					stringBuilder.append(new String(strBytes, "GBK") + " [ 原始数据 ：0x" + byte2hex(strBytes) + " ]");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String serviceName = stringBuilder.toString();
				stringBuilder.delete(0, stringBuilder.length());

				for (int k = 0; k < (descriptorsLoopLength - (serviceProviderNameLength + serviceNameLength + 5));) {
//					只获取bouquet_descriptor的信息
//					if (sectionData[21 + serviceProviderNameLength + serviceNameLength + j+k] == 0x47) {

					int descriptorTag = sectionData[21 + serviceProviderNameLength + serviceNameLength + j + k] & 0xff;
					int descriptorLength = sectionData[22 + serviceProviderNameLength + serviceNameLength + j + k]
							& 0xff;
					strBytes = new byte[descriptorLength];
					for (int m = 0; m < descriptorLength; m++) {
						strBytes[m] = sectionData[23 + serviceProviderNameLength + serviceNameLength + j + m + k];
					}
					String data = null;
					if (descriptorTag == 0x92) {
						try {
							data = new String(strBytes, "GBK");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						try {
							stringBuilder
									.append(new String(strBytes, "GBK") + " [ 原始数据 ：0x" + byte2hex(strBytes) + " ]");

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						data = stringBuilder.toString();
					}

					stringBuilder.delete(0, stringBuilder.length());

					DescriptorBean bouquetNameDescriptor = new DescriptorBean(descriptorTag, descriptorLength, data);
					descriptorList.add(bouquetNameDescriptor);
					k = k + 2 + descriptorLength;
//					} else {
//						System.out.println("no bouquet Name");
//						break;
//					}
				}

				SdtServiceBean sdtService = new SdtServiceBean(serviceId, eitScheduleFlag, eitPresentFollowingFlag,
						runningStatus, freeCaMode, descriptorsLoopLength, serviceType, serviceProviderNameLength,
						serviceProviderName, serviceNameLength, serviceName, reservedFutureUse, descriptor_tag,
						descriptor_length);

				sdtService.setDescriptor(descriptorList);
				sdtServiceList.add(sdtService);

				j += (5 + descriptorsLoopLength);

			}
		}
		Collections.sort(sdtServiceList);
		sdt.setSdtServiceList(sdtServiceList);
		System.out.println("**************************SDT解析完成****************************************");
		return 1;
	}

	public SdtBean getSdt() {
		return sdt;
	}

	private static String byte2hex(byte[] buffer) {
		String h = "";

		for (int i = 0; i < buffer.length; i++) {
			String temp = Integer.toHexString(buffer[i] & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + " " + temp;
		}
		return h;
	}
}
