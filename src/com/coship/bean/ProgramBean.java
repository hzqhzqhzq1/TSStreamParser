package com.coship.bean;

import java.io.Serializable;

/**
 * 节目信息
 * @author 910131
 *
 */
public class ProgramBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int pid;
	private int programNumber;
	private String programName;
	private String programType;
	private String programGroup;
	private String programDescription;
	
	public ProgramBean(int pid,int programNumber) {
		this.pid = pid;
		this.programNumber = programNumber;
		String unKnown = "unknown";
		this.programName = unKnown;
		this.programType = unKnown;
		this.programGroup = unKnown;
		this.programDescription = unKnown;
	}
	
	
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getProgramNumber() {
		return programNumber;
	}
	public void setProgramNumber(int programNumber) {
		this.programNumber = programNumber;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public String getProgramGroup() {
		return programGroup;
	}
	public void setProgramGroup(String programGroup) {
		this.programGroup = programGroup;
	}
	public String getProgramDescription() {
		return programDescription;
	}
	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}
	
	@Override
	public String toString() {
		return "[ 节目Pid=" + pid + ", 节目号=" + programNumber + ", 节目名称=" + programName
				+ ", 节目类型=" + programType + ", 节目分组=" + programGroup + ", 节目描述="
				+ programDescription + "]";
	}
	
	
	
	
}
