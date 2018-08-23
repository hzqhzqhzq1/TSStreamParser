package com.coship.test;

import com.coship.bean.Ts;

public class Test {
	private static String fileFordPath = "C:/Users/910131/Desktop/";
	private static String fileName = "江苏现网码流-1.ts";
//	private static String fileName = "dmx_allts_1-05_45_04.ts";
//	private static String fileName = "dmx_allts_1-06_36_47.ts";
//	private static String fileName = "dmx_allts_1-05_32_40.ts";
//	private static String fileName = "7.2M_2012Prevue_184s_1.ts";
//	private static String fileName = "TS-1080P-23M.ts";
//	private static String fileName = "1080p.ts";
	

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		Ts ts =new Ts(fileFordPath+fileName);
		System.out.println(ts.getPat()==null);
		System.out.println(ts.getPmtList()==null);
		System.out.println(ts.getSdt()==null);
		System.out.println(ts.getProgramList()==null);
		
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间： "+(endTime-startTime)+"ms");
	}

}
