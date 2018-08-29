package com.coship.bean.tables;

import static java.lang.Integer.toHexString;

import java.io.Serializable;

/**
 * 描述子
 * @author 910131
 *
 */
public class DescriptorBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private int  descriptor_tag;
	private int  descriptor_length;
	private String data;
	
	
	public DescriptorBean(int descriptor_tag,int descriptor_len,String data) {
		super();
		this.descriptor_tag = descriptor_tag;
		this.descriptor_length = descriptor_len;
		this.data = data;
	}
	
	public int getDescriptor_tag() {
		return descriptor_tag;
	}
	public int getDescriptor_length() {
		return descriptor_length;
	}
	
	
	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "		[descriptor_tag= 0x" + toHexString(descriptor_tag) + "\n		descriptor_length= 0x" + toHexString(descriptor_length)
				+ "\n		data=" + data + "]\n";
	}
	
}
