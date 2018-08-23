package com.coship.bean.table;

import static java.lang.Integer.toHexString;


public class Descriptor {


	private int  descriptor_tag;
	private int  descriptor_length;
	private String data;
	
	
	public Descriptor(int descriptor_tag,int descriptor_len,String data) {
		super();
		this.descriptor_tag = descriptor_tag;
		this.descriptor_length = descriptor_len;
		this.data = data;
	}
	
	public int getDescriptor_tag() {
		return descriptor_tag;
	}
//	public void setDescriptor_tag(int descriptor_tag) {
//		this.descriptor_tag = descriptor_tag;
//	}
	public int getDescriptor_length() {
		return descriptor_length;
	}
	
//	public void setDescriptor_length(int descriptor_length) {
//		this.descriptor_length = descriptor_length;
//	}
	
	public String getData() {
		return data;
	}

//	public void setData(String data) {
//		this.data = data;
//	}

	@Override
	public String toString() {
		return "		[descriptor_tag= 0x" + toHexString(descriptor_tag) + "\n		descriptor_length= 0x" + toHexString(descriptor_length)
				+ "\n		data=" + data + "]\n";
	}
	
}
