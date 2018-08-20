package com.coship.programoperate.impl;

import java.util.ArrayList;
import java.util.List;

import com.coship.bean.Program;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.Sdt;
import com.coship.programoperate.ProgramManager;

public class ProgramMangerImpl implements ProgramManager{
	List<Program> programList = new ArrayList<Program>();

	@Override
	public List<Program> makeProgramList(Sdt sdt,List<Pmt> pmtList) {
		for(Pmt p:pmtList) {
		}
		return programList;
	}

}
