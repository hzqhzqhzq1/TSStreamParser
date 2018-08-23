package com.coship.tableoperate;

import com.coship.tableoperate.impl.PatManager;
import com.coship.tableoperate.impl.PmtManager;
import com.coship.tableoperate.impl.SdtManager;

public class TableManagerFactory {
	private static final int PAT_PID=0X0000;
//	private static final int PAT_TABLE_ID=0X00;

	private static final int PMT_TABLE_ID=0X02;

//	private static final int CAT_PID=0X001;
//	private static final int CAT_TABLE_ID=0X01;
//
//	private static final int NIT_PID=0X0010;
//	private static final int NIT_TABLE_ID=0X40;

	private static final int SDT_PID=0X0011;
	private static final int SDT_TABLE_ID=0X42;
	
//	private static final int BAT_PID =0X0011;
//	private static final int BAT_TABLE_ID=0X4A;
//
//	private static final int EIT_PID=0X0012;
//
//	private static final int TDT_PID=0X0014;
//	private static final int TDT_TABLE_ID=0X70;

	
	public static TableManager createTableManager(int pid,int tableId) {
		switch(pid) {
		case PAT_PID:
			return new PatManager();
		case SDT_PID:
			if(tableId==SDT_TABLE_ID) {
				return new SdtManager();
			}
		default:
			if(tableId == PMT_TABLE_ID) {
				return new PmtManager();
			}
			return null;
		}
	}
}
