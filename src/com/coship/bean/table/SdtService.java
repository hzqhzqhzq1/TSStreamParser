package com.coship.bean.table;

import java.util.List;
import static java.lang.Integer.toHexString;

/**
 * SDT业务描述
 * @author 910131
 *
 */
public class SdtService implements Comparable<SdtService>{
	/**
	 * service_id : 16 bit
	 */
	private int serviceId;

	/**
	 * reserved_future_use: 8bit
	 */
	private int reservedFutureUse;

	/**
	 * eit_schedule_flag : 1 bit
	 */
	private int eitScheduleFlag;

	/**
	 * eit_present_following_flag : 1 bit
	 */
	private int eitPresentFollowingFlag;

	/**
	 * running_status : 3 bit
	 */
	private int runningStatus;

	/**
	 * free_CA_mode : 1 bit
	 */
	private int freeCaMode;

	/**
	 * descriptors_loop_length : 12 bit
	 */
	private int descriptorsLoopLength;

	// descriptor()
//    private Descriptor descriptorList = new Descriptor();

	/**
	 * descriptor_tag:8 bit (1 byte)
	 */
	private int descriptorTag;

	/**
	 * descriptor_length:8bit (1 byte)
	 */
	private int descriptorLength;

	/**
	 * service_type : 8 bit (1byte)
	 */
	private int serviceType;

	/**
	 * service_provider_name_length : 8 bit (1 byte)
	 */
	private int serviceProviderNameLength;

	/**
	 * service_provider_name : serviceproviderNamelength
	 */
	private String serviceProviderName;

	/**
	 * service_name_length : 8 bit (1 byte)
	 */
	private int serviceNameLength;

	/**
	 * service_name : serviceNameLength
	 */
	private String serviceName;

	private List<Descriptor> descriptorList;

	public SdtService(int serviceId, int eitScheduleFlag, int eitPresentFollowingFlag, int runningStatus,
			int freeCaMode, int descriptorsLoopLength, int serviceType, int serviceProviderNameLength,
			String serviceProviderName, int serviceNameLength, String serviceName, int reservedFutureUse,
			int descriptor_tag, int descriptor_length) {
		super();
		this.reservedFutureUse = reservedFutureUse;
		this.descriptorTag = descriptor_tag;
		this.descriptorLength = descriptor_length;
		this.serviceId = serviceId;
		this.eitScheduleFlag = eitScheduleFlag;
		this.eitPresentFollowingFlag = eitPresentFollowingFlag;
		this.runningStatus = runningStatus;
		this.freeCaMode = freeCaMode;
		this.descriptorsLoopLength = descriptorsLoopLength;
		this.serviceType = serviceType;
		this.serviceProviderNameLength = serviceProviderNameLength;
		this.serviceProviderName = serviceProviderName;
		this.serviceNameLength = serviceNameLength;
		this.serviceName = serviceName;

	}

	public int getServiceId() {
		return serviceId;
	}

	public int getEitScheduleFlag() {
		return eitScheduleFlag;
	}

	public int getEitPresentFollowingFlag() {
		return eitPresentFollowingFlag;
	}


	public int getRunningStatus() {
		return runningStatus;
	}

	public int getFreeCaMode() {
		return freeCaMode;
	}

	public int getDescriptorsLoopLength() {
		return descriptorsLoopLength;
	}

	public int getServiceType() {
		return serviceType;
	}

	public int getServiceProviderNameLength() {
		return serviceProviderNameLength;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public int getServiceNameLength() {
		return serviceNameLength;
	}

	public int getReservedFutureUse() {
		return reservedFutureUse;
	}

	public String getServiceName() {
		return serviceName;
	}

	public int getDescriptorTag() {
		return descriptorTag;
	}

	public int getDescriptorLength() {
		return descriptorLength;
	}

	public List<Descriptor> getDescriptor() {
		return descriptorList;
	}

	public void setDescriptor(List<Descriptor> descriptorList) {
		this.descriptorList = descriptorList;
	}

	@Override
	public String toString() {
		if (!descriptorList.isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("[service_id = " + serviceId + ", " + "reserved_future_use = 0x"
					+ toHexString(reservedFutureUse) + ", " + "EIT_schedule_flag = 0x" + toHexString(eitScheduleFlag)
					+ ", " + "EIT_present_following_flag = 0x" + toHexString(eitPresentFollowingFlag) + ", "
					+ "running_status = 0x" + toHexString(runningStatus) + ", " + "free_CA_mode = 0x"
					+ toHexString(freeCaMode) + ", " + "descriptor_loop_length = 0x"
					+ toHexString(descriptorsLoopLength)+"\n");
			stringBuilder.append("	service_descriptor :\n"+"		[descriptor_tag = 0x"+toHexString(descriptorTag)+"\n"+
					"		descriptor_length = "+toHexString(descriptorLength)+"\n"+"		service_type = 0x"+toHexString(serviceType)+
					"\n"+"		service_provider_name_length = 0x"+toHexString(serviceProviderNameLength)+"\n"+"		service_provider_name = "+
					serviceProviderName+"\n"+"		service_name_length = 0x"+toHexString(serviceNameLength)+"\n"+"		service_name = "+serviceName+"  ]\n");
			for(Descriptor d:descriptorList) {
				if(d.getDescriptor_tag()==0x47) {
					stringBuilder.append("	bouquet_name_descriptor :\n");
				}else {
					stringBuilder.append("	unknown_descriptor :\n");
				}
				stringBuilder.append(d.toString());
			}
			stringBuilder.append("\n");
			return stringBuilder.toString();

		} else {
			return "\nSdtService [serviceId=" + serviceId + ", reservedFutureUse=" + reservedFutureUse
					+ ", eitScheduleFlag=" + eitScheduleFlag + ", eitPresentFollowingFlag=" + eitPresentFollowingFlag
					+ ", runningStatus=" + runningStatus + ", freeCaMode=" + freeCaMode + ", descriptorsLoopLength="
					+ descriptorsLoopLength + ", descriptorTag=" + descriptorTag + ", descriptorLength="
					+ descriptorLength + ", serviceType=" + serviceType + ", serviceProviderNameLength="
					+ serviceProviderNameLength + ", serviceProviderName=" + serviceProviderName
					+ ", serviceNameLength=" + serviceNameLength + ", serviceName=" + serviceName + "]\n";
		}

	}

	@Override
	public int compareTo(SdtService o) {
		return this.serviceId > o.serviceId ? 1 : (this.serviceId == o.serviceId ? 0 : -1);
	}

}
