package com.coship.bean.table;

/**
 * PAT表中的PMTPID信息
 * @author 910131
 *
 */
public class PmtPidInfo implements Comparable<PmtPidInfo> {
	/**
	 * program_number : 16 bit
	 */
	private int programNumber;

	/**
	 * program_map_PID : 13 bit
	 */
	private int programMapPid;

	public PmtPidInfo(int programNumber, int programMapPid) {
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
	public int compareTo(PmtPidInfo o) {
		return this.programNumber > o.programNumber ? 1 : (this.programNumber == o.programNumber ? 0 : -1);
	}

	@Override
	public String toString() {
		return "[program_number=" + programNumber + ", PMT_Pid= 0x" + Integer.toHexString(programMapPid) + "]\n";
	}
}
