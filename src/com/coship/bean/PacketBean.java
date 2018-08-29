package com.coship.bean;

import java.io.Serializable;

/**
 * 包
 * 
 * @author 910131
 *
 */
public class PacketBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int syncByte;

	private int transportErrorIndicator;

	private int payloadUnitStartIndicator;

	private int transportPriority;

	private int pid;

	private int transportScrambingControl;

	private int adaptationFieldControl;

	private int continuityCounter;

	private byte[] packet;

	public PacketBean(byte[] b) {
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

	@Override
	public String toString() {
		String packetString = "";
		for (int i = 0; i < packet.length; i++) {
			String temp = Integer.toHexString(packet[i] & 0xFF);
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			packetString = packetString + " " + temp;
		}
		return packetString;
	}
}
