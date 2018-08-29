package com.coship.programoperate;

import java.util.List;

import com.coship.bean.ProgramBean;
import com.coship.bean.tables.PatBean;
import com.coship.bean.tables.SdtBean;

public interface ProgramManager {

	List<ProgramBean> makeProgramList(PatBean pat,SdtBean sdt);
}
