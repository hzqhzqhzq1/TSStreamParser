package com.coship.bean;

/**
 * 包
 * @author 910131
 *
 */
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

	public int getTransportErrorIndicator() {
		return transportErrorIndicator;
	}

	public int getPayloadUnitStartIndicator() {
		return payloadUnitStartIndicator;
	}

	public int getTransportPriority() {
		return transportPriority;
	}

	public int getPid() {
		return pid;
	}

	public int getTransportScrambingControl() {
		return transportScrambingControl;
	}


	public int getAdaptationFieldControl() {
		return adaptationFieldControl;
	}

	public int getContinuityCounter() {
		return continuityCounter;
	}

	public byte[] getPacket() {
		return packet;
	}
}
