package com.coship.programoperate;

import java.util.List;

import com.coship.bean.Program;
import com.coship.bean.table.Pmt;
import com.coship.bean.table.Sdt;

public interface ProgramManager {

	List<Program> makeProgramList(Sdt sdt,List<Pmt> pmtList);
}
