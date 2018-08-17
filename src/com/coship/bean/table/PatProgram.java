package com.coship.bean.table;

public class PatProgram {
	  /**
     * program_number : 16 bit
     */
    private int programNumber;

    /**
     * program_map_PID : 13 bit
     */
    private int programMapPid;
    
    private int orderId;


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

    public void setOrderId(int orderId) {
    	this.orderId = orderId;
    }
    
	@Override
	public String toString() {
		return "\n节目"+orderId+ "[program_number=" + programNumber + ", PMT_Pid= 0x" + Integer.toHexString(programMapPid) + "]";
	}
    
    
    
}
