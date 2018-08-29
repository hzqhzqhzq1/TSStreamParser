package com.coship.bean.tables;

import static java.lang.Integer.toHexString;

import java.io.Serializable;

/**
 * PMT表单元流
 * 
 * @author 910131
 *
 */
public class ElementStreamBean implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * stream_type : 8 bit
	 */
	private int streamType;

	/**
	 * ES_pid : 13 bit
	 */
	private int esPid;

	/**
	 * 
	 * es_info_length : 12 bit
	 */
	private int esInfoLength;

	public ElementStreamBean(int streamType, int elementaryPid, int esInfoLength) {
		super();
		this.streamType = streamType;
		this.esPid = elementaryPid;
		this.esInfoLength = esInfoLength;
	}

	public int getStreamType() {
		return streamType;
	}

	public int getesPid() {
		return esPid;
	}

	public int getEsInfoLength() {
		return esInfoLength;
	}

	@Override
	public String toString() {
		return "[ES PID= 0x" + toHexString(esPid) + ", stream_type= 0x" + toHexString(streamType) + ", Es_Info_Len= 0x"
				+ toHexString(esInfoLength) + "]\n";
	}
}
