package com.coship.bean.table;

public class PatProgram implements Comparable<PatProgram> {
	/**
	 * program_number : 16 bit
	 */
	private int programNumber;

	/**
	 * program_map_PID : 13 bit
	 */
	private int programMapPid;

	public PatProgram(int programNumber, int programMapPid) {
		super();
		this.programNumber = programNumber;
		this.programMapPid = programMapPid;
	}

	public int getProgramNumber() {
		return programNumber;
	}

	public void setProgramNumber(int programNumber) {
		this.programNumber = programNumber;
	}

	public int getProgramMapPid() {
		return programMapPid;
	}

	public void setProgramMapPid(int programMapPid) {
		this.programMapPid = programMapPid;
	}

	@Override
	public int compareTo(PatProgram o) {
		return this.programNumber > o.programNumber ? 1 : (this.programNumber == o.programNumber ? 0 : -1);
	}

	@Override
	public String toString() {
		return "[program_number=" + programNumber + ", PMT_Pid= 0x" + Integer.toHexString(programMapPid) + "]\n";
	}
}
