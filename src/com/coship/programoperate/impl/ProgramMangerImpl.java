package com.coship.programoperate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coship.bean.ProgramBean;
import com.coship.bean.tables.DescriptorBean;
import com.coship.bean.tables.PatBean;
import com.coship.bean.tables.PatProgramMapBean;
import com.coship.bean.tables.SdtBean;
import com.coship.bean.tables.SdtServiceBean;
import com.coship.programoperate.ProgramManager;

/**
 * 节目管理器
 * @author 910131
 *
 */
public class ProgramMangerImpl implements ProgramManager {
	List<ProgramBean> programList = new ArrayList<ProgramBean>();

	@Override
	public List<ProgramBean> makeProgramList(PatBean pat, SdtBean sdt) {
		List<ProgramBean> programList = new ArrayList<ProgramBean>();
		List<PatProgramMapBean> pmtPidInfoList = pat.getPmtPidInfoList();
		List<SdtServiceBean> sdtServiceList = sdt.getSdtServiceList();
		Map<Integer, ProgramBean> pm = new HashMap<>();

		for (PatProgramMapBean pp : pmtPidInfoList) {
			ProgramBean program = new ProgramBean(pp.getProgramMapPid(), pp.getProgramNumber());
			pm.put(pp.getProgramNumber(), program);
			programList.add(program);
		}
		for (SdtServiceBean ss : sdtServiceList) {
			ProgramBean program =pm.get(ss.getServiceId());
			
			if(program==null) {
				continue;
			}
			program.setProgramName(
					ss.getServiceName());
			for (DescriptorBean d : ss.getDescriptor()) {
				if (d.getDescriptor_tag() == 0x47) {
					if(d.getData().equals("付费节目")|d.getData().equals("免费节目")) {
						program.setProgramType(d.getData());
					}else {
						program.setProgramGroup(d.getData());
					}
				}
				if(d.getDescriptor_tag() == 0x92) {
					program.setProgramDescription(d.getData());
				}
			}
		}
		return programList;
	}

}
