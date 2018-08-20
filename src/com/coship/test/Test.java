package com.coship.test;

import com.coship.bean.Ts;
import com.coship.bean.table.Pat;

public class Test {
	private static String fileFordPath = "C:/Users/910131/Desktop/";
	private static String fileName = "江苏现网码流-1.ts";
//	private static String fileName = "1080p.ts";
	

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		
		Ts ts =new Ts(fileFordPath+fileName);
		ts.getPmtList();
		
		
//		Pat pat = ts.getPat();
//		PacketManagerImpl packetManager =  new PacketManagerImpl(fileFordPath+fileName);
//		for(Map.Entry<Integer, List<Packet>> entry : packetManager.getPacketMap().entrySet()) {
//			System.out.println("PID:"+entry.getKey()+" "+entry.getValue().size());
//		}
//		SectionManagerImpl sm  = new SectionManagerImpl();
//		sm.matchSection(packetManager.getPacketOfPid(0x118a),0x02);
//		sm.matchSection(packetManager.getPacketOfPid(0x116c),0x02);
//		sm.matchSection(packetManager.getPacketOfPid(0x00),0x0);
//		sm.matchSection(packetManager.getPacketOfPid(0x0011), 0X42);
////		TableManager tm = TableParserFactory.createTableManager(0x116c, 0x02);
//		TableManager tm = TableParserFactory.createTableManager(0x0011, 0x42);
//		tm.makeTable(sm.getSectionList());
		
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间： "+(endTime-startTime)+"ms");
		
//		System.out.println(sm.getSectionList().size());
//		List<String> sl = new ArrayList<>();
//		for(Section s:sm.getSectionList()) {
//			System.out.println(byte2hex(s.getSectionData()));
//			System.out.println(s.getSectionData().length);
//			sl.add(byte2hex(s.getSectionData()));
//		}
	}
	
//	/**
//	 *
//	 * @param buffer
//	 * @return
//	 */
//	private static String byte2hex(byte [] buffer){  
//        String h = "";  
//        for(int i = 0; i < buffer.length; i++){  
//            String temp = Integer.toHexString(buffer[i] & 0xFF);  
//            if(temp.length() == 1){  
//                temp = "0" + temp;  
//            }  
//            h = h + " "+ temp;  
//        }  
//        return h;  
//    } 
	
}
