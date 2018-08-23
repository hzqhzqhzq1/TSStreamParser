package com.coship.programoperate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coship.bean.Program;
import com.coship.bean.table.Descriptor;
import com.coship.bean.table.Pat;
import com.coship.bean.table.PmtPidInfo;
import com.coship.bean.table.Sdt;
import com.coship.bean.table.SdtService;
import com.coship.programoperate.ProgramManager;

public class ProgramMangerImpl implements ProgramManager {
	List<Program> programList = new ArrayList<Program>();

	@Override
	public List<Program> makeProgramList(Pat pat, Sdt sdt) {
		List<Program> programList = new ArrayList<Program>();
		List<PmtPidInfo> pmtPidInfoList = pat.getPmtPidInfoList();
		List<SdtService> sdtServiceList = sdt.getSdtServiceList();
		Map<Integer, Program> pm = new HashMap<>();

		for (PmtPidInfo pp : pmtPidInfoList) {
			Program program = new Program(pp.getProgramMapPid(), pp.getProgramNumber());
			pm.put(pp.getProgramNumber(), program);
			programList.add(program);
		}
		for (SdtService ss : sdtServiceList) {
			Program program =pm.get(ss.getServiceId());
			
			if(program==null) {
				continue;
			}
			program.setProgramName(
					ss.getServiceName());
			for (Descriptor d : ss.getDescriptor()) {
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
