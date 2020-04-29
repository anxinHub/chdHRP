package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrSchedulingMapper;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrSequestrationMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrSequestration;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSequestrationService;
import com.chd.hrp.hr.service.base.HrBaseService;


@Service("hrSequestrationService")
public class HrSequestrationServiceImpl  implements HrSequestrationService{
	private static Logger logger = Logger.getLogger(HrSequestrationServiceImpl.class);
	
@Resource(name="hrSequestrationMapper")
private final	HrSequestrationMapper hrSequestrationMapper =null;

@Resource(name="hrSchedulingMapper")
private final	HrSchedulingMapper hrSchedulingMapper =null;

@Resource(name = "hrBaseService")
private final HrBaseService hrBaseService = null;
	
	@Override
	public String addSequestration(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapM = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			mapM.put("group_id", SessionManager.getGroupId());
			mapM.put("hos_id", SessionManager.getHosId());
			mapM.put("attend_pbrule", "1");
			mapM.put("attend_pbisfile", entityMap.get("attend_pbisfileM"));
			mapM.put("attend_pbfile_set", entityMap.get("tran_freqM"));
			mapM.put("attend_pbfle_val", entityMap.get("attend_pbfle_valM"));
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("attend_pbrule", "0");
			map.put("attend_pbisfile", entityMap.get("attend_pbisfile"));
			map.put("attend_pbfile_set", entityMap.get("tran_freq"));
			map.put("attend_pbfle_val", entityMap.get("attend_pbfle_val"));
			list.add(map);
			list.add(mapM);
			hrSequestrationMapper.delete(entityMap);
			hrSequestrationMapper.addBatch(list);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"	}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String UpdateSequestration(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			hrSequestrationMapper.delete(entityMap);
		
			return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}

	@Override
	public String querySequestration(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			List<Map<String,Object>> hrSequestration = (List<Map<String, Object>>) hrSequestrationMapper.querySequestration(entityMap);
			
			//当未做保存操作前,数据为空。返回此JSON串避免出现异常
			if(hrSequestration == null){
				return "{\"attdent_cycle_style\":\"2\"}";
			}
			
			return ChdJson.toJson(hrSequestration);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
	}




}
