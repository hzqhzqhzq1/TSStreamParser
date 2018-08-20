package com.coship.bean.table;

public class SdtService {
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
	
	
	


    public SdtService(int serviceId, int eitScheduleFlag, int eitPresentFollowingFlag,
                      int runningStatus, int freeCaMode, int descriptorsLoopLength,
                      int serviceType, int serviceProviderNameLength, String serviceProviderName,
                      int serviceNameLength, String serviceName,int reservedFutureUse,int descriptor_tag,int descriptor_length) {
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

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getEitScheduleFlag() {
        return eitScheduleFlag;
    }

    public void setEitScheduleFlag(int eitScheduleFlag) {
        this.eitScheduleFlag = eitScheduleFlag;
    }

    public int getEitPresentFollowingFlag() {
        return eitPresentFollowingFlag;
    }

    public void setEitPresentFollowingFlag(int eitPresentFollowingFlag) {
        this.eitPresentFollowingFlag = eitPresentFollowingFlag;
    }

    public int getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(int runningStatus) {
        this.runningStatus = runningStatus;
    }

    public int getFreeCaMode() {
        return freeCaMode;
    }

    public void setFreeCaMode(int freeCaMode) {
        this.freeCaMode = freeCaMode;
    }

    public int getDescriptorsLoopLength() {
        return descriptorsLoopLength;
    }

    public void setDescriptorsLoopLength(int descriptorsLoopLength) {
        this.descriptorsLoopLength = descriptorsLoopLength;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceProviderNameLength() {
        return serviceProviderNameLength;
    }

    public void setServiceProviderNameLength(int serviceProviderNameLength) {
        this.serviceProviderNameLength = serviceProviderNameLength;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public int getServiceNameLength() {
        return serviceNameLength;
    }

    public void setServiceNameLength(int serviceNameLength) {
        this.serviceNameLength = serviceNameLength;
    }

    public int getReservedFutureUse() {
		return reservedFutureUse;
	}


	public void setReservedFutureUse(int reservedFutureUse) {
		this.reservedFutureUse = reservedFutureUse;
	}

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public int getDescriptorTag() {
		return descriptorTag;
	}


	public void setDescriptorTag(int descriptorTag) {
		this.descriptorTag = descriptorTag;
	}


	public int getDescriptorLength() {
		return descriptorLength;
	}


	public void setDescriptorLength(int descriptorLength) {
		this.descriptorLength = descriptorLength;
	}


	@Override
	public String toString() {
		return "SdtService [serviceId=" + serviceId + ", reservedFutureUse=" + reservedFutureUse + ", eitScheduleFlag="
				+ eitScheduleFlag + ", eitPresentFollowingFlag=" + eitPresentFollowingFlag + ", runningStatus="
				+ runningStatus + ", freeCaMode=" + freeCaMode + ", descriptorsLoopLength=" + descriptorsLoopLength
				+ ", descriptorTag=" + descriptorTag + ", descriptorLength=" + descriptorLength + ", serviceType="
				+ serviceType + ", serviceProviderNameLength=" + serviceProviderNameLength + ", serviceProviderName="
				+ serviceProviderName + ", serviceNameLength=" + serviceNameLength + ", serviceName=" + serviceName
				+ "]";
	}
	
	
}
	