package com.coship.bean;

public class Program {
	private int pid;
	private int program_number;
	private String programName;
	private String programType;
	private String programGroup;
	private String programDescription;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getProgram_number() {
		return program_number;
	}
	public void setProgram_number(int program_number) {
		this.program_number = program_number;
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
		return "Program [节目Pid=" + pid + ", 节目号=" + program_number + ", 节目名称=" + programName
				+ ", 节目类型=" + programType + ", 节目分组=" + programGroup + ", 节目描述="
				+ programDescription + "]";
	}
	
	
	
	
}
