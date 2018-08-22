package com.coship.bean;

//import java.util.Arrays;

public class Packet {
	private int syncByte;

	private int transportErrorIndicator;

	private int payloadUnitStartIndicator;

	private int transportPriority;

	private int pid;

	private int transportScrambingControl;

	private int adaptationFieldControl;

	private int continuityCounter;

	private byte[] packet;

	public Packet(byte[] b) {
		this.syncByte = b[0] & 0xFF;
		this.transportErrorIndicator = (b[1] >> 7) & 0x1;
		this.payloadUnitStartIndicator = (b[1] >> 6) & 0x1;
		this.transportPriority = (b[1] >> 5) & 0x1;
		this.pid = (((b[1] & 0x1F) << 8) | (b[2] & 0xFF)) & 0x1FFF;
		this.transportScrambingControl = b[3] >> 6 & 0x3;
		this.adaptationFieldControl = b[3] >> 4 & 0x3;
		this.continuityCounter = b[3] & 0xF;

		this.packet = b;

	}
	
	private static String byte2hex(byte [] buffer){  
        String h = "";  
          
        for(int i = 0; i < buffer.length; i++){  
            String temp = Integer.toHexString(buffer[i] & 0xFF);  
            if(temp.length() == 1){  
                temp = "0" + temp;  
            }  
            h = h + " "+ temp;  
        }  
          
        return h;  
          
    }  
 

	@Override
	public String toString() {
		return byte2hex(packet);
	}

	public int getSyncByte() {
		return syncByte;
	}

	public void setSyncByte(int syncByte) {
		this.syncByte = syncByte;
	}

	public int getTransportErrorIndicator() {
		return transportErrorIndicator;
	}

	public void setTransportErrorIndicator(int transportErrorIndicator) {
		this.transportErrorIndicator = transportErrorIndicator;
	}

	public int getPayloadUnitStartIndicator() {
		return payloadUnitStartIndicator;
	}

	public void setPayloadUnitStartIndicator(int payloadUnitStartIndicator) {
		this.payloadUnitStartIndicator = payloadUnitStartIndicator;
	}

	public int getTransportPriority() {
		return transportPriority;
	}

	public void setTransportPriority(int transportPriority) {
		this.transportPriority = transportPriority;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getTransportScrambingControl() {
		return transportScrambingControl;
	}

	public void setTransportScrambingControl(int transportScrambingControl) {
		this.transportScrambingControl = transportScrambingControl;
	}

	public int getAdaptationFieldControl() {
		return adaptationFieldControl;
	}

	public void setAdaptationFieldControl(int adaptationFieldControl) {
		this.adaptationFieldControl = adaptationFieldControl;
	}

	public int getContinuityCounter() {
		return continuityCounter;
	}

	public void setContinuityCounter(int continuityCounter) {
		this.continuityCounter = continuityCounter;
	}

	public byte[] getPacket() {
		return packet;
	}

	public void setPacket(byte[] packet) {
		this.packet = packet;
	}
}
