package com.coship.tableoperate.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coship.bean.Section;
import com.coship.bean.table.Descriptor;
import com.coship.bean.table.Sdt;
import com.coship.bean.table.SdtService;
import com.coship.tableoperate.TableManager;

public class SdtManager implements TableManager {
	private Sdt sdt;

	private List<SdtService> sdtServiceList = new ArrayList<SdtService>();

	@Override
	public int makeTable(List<Section> sectionList) {
		for (int i = 0; i < sectionList.size(); i++) {

			Section section = sectionList.get(i);
			byte[] sectionData = section.getSectionData();

			if (sdt == null) {
				sdt = new Sdt(sectionData);
			}

			/*
			 * to reserved_future_use : 11 byte CRC_32 : 4 byte
			 *
			 * need to delete = 15
			 *
			 * service_id : 16 bit reserved_future_use : 6 bit eit_schedule_flag : 1 bit
			 * eit_present_following_flag : 1 bit running_status : 3 bit free_CA_mode : 1
			 * bit descriptors_loop_length : 12 bit
			 *
			 * to descriptors_loop_length : 5 byte Service = 5 + ?
			 *
			 * -> descriptors_loop_length tag : 1 byte len : 1 byte data -> len service_type
			 * : 1 byte service_provider_name_length : 1 byte service_provider_name ->
			 * name_length service_name_length : 1 byte service_name -> name_length
			 */
			int sectionSize = sectionData.length;
			int theEffectiveLength = sectionSize - 15;

			for (int j = 0; j < theEffectiveLength;) {

				List<Descriptor> bNDescriptorList = new ArrayList<Descriptor>();

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

					Descriptor bouquetNameDescriptor = new Descriptor(descriptorTag, descriptorLength, data);
					bNDescriptorList.add(bouquetNameDescriptor);
					k = k + 2 + descriptorLength;
//					} else {
//						System.out.println("no bouquet Name");
//						break;
//					}
				}

				SdtService sdtService = new SdtService(serviceId, eitScheduleFlag, eitPresentFollowingFlag,
						runningStatus, freeCaMode, descriptorsLoopLength, serviceType, serviceProviderNameLength,
						serviceProviderName, serviceNameLength, serviceName, reservedFutureUse, descriptor_tag,
						descriptor_length);

				sdtService.setDescriptor(bNDescriptorList);
				sdtServiceList.add(sdtService);

				j += (5 + descriptorsLoopLength);

			}
		}
		Collections.sort(sdtServiceList);
		sdt.setSdtServiceList(sdtServiceList);
		return 0;
	}

	public Sdt getSdt() {
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

//	private String print() {
//		System.out.println(sdt.toString());
//		return sdt.getSdtServiceList().get(0).getServiceName();
//	}

}
