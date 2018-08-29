package com.coship.bean.tables;

import java.io.Serializable;

/**
 * PAT表中的PMTPID信息
 * @author 910131
 *
 */
public class PatProgramMapBean implements Comparable<PatProgramMapBean>,Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * program_number : 16 bit
	 */
	private int programNumber;

	/**
	 * program_map_PID : 13 bit
	 */
	private int programMapPid;

	public PatProgramMapBean(int programNumber, int programMapPid) {
		super();
		this.programNumber = programNumber;
		this.programMapPid = programMapPid;
	}

	public int getProgramNumber() {
		return programNumber;
	}

	public int getProgramMapPid() {
		return programMapPid;
	}
	@Override
	public int compareTo(PatProgramMapBean o) {
		return this.programNumber > o.programNumber ? 1 : (this.programNumber == o.programNumber ? 0 : -1);
	}

	@Override
	public String toString() {
		return "[program_number=" + programNumber + ", PMT_Pid= 0x" + Integer.toHexString(programMapPid) + "]\n";
	}
}
