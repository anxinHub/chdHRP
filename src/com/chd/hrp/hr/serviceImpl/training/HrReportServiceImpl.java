/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.training;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;









import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.training.plant.HrPlantMapper;
import com.chd.hrp.hr.dao.training.report.HrTrainReportMapper;
import com.chd.hrp.hr.entity.training.setting.HrExamineMode;
import com.chd.hrp.hr.service.training.HrReportService;
import com.chd.hrp.hr.serviceImpl.training.plant.HrPlantServiceImpl;


/**
 * 
 * @Description: 培训计划
 * @Table: HR_way
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrReportService")
public class HrReportServiceImpl implements HrReportService {

	private static Logger logger = Logger.getLogger(HrReportServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrTrainReportMapper")
	private final HrTrainReportMapper hrTrainReportMapper = null;
	@Override
	public String queryReportSign(Map<String, Object> map)
			throws DataAccessException {
		List<Map<String,Object>> list=hrTrainReportMapper.queryTrainSignReport(map);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public String queryReportExam(Map<String, Object> map)
			throws DataAccessException {
		List<Map<String,Object>> list=hrTrainReportMapper.queryExaminReport(map);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public String queryReportBuKao(Map<String, Object> map)
			throws DataAccessException {
		List<Map<String,Object>> list=hrTrainReportMapper.queryBukaoreport(map);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public String queryReportEvaluate(Map<String, Object> map)
			throws DataAccessException {
		List<Map<String,Object>> list=hrTrainReportMapper.queryTrainAllReport(map);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public String addReportEvaluate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
		List<HrExamineMode> list = hrTrainReportMapper.queryReportEvaluateById(entityMap);

		if (list.size() > 0) {

			
			hrTrainReportMapper.update(entityMap);
				
			
		}else{

			hrTrainReportMapper.add(entityMap);
			
		}
		

		


				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}

	}

	@Override
	public String queryReportEmpYear(Map<String, Object> map)
			throws DataAccessException {
		List<Map<String,Object>> list=hrTrainReportMapper.queryTrainByPerson(map);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public Map<String, Object> queryUser(Map<String, Object> mapVo)
			throws DataAccessException {
		Map<String,Object> mapEmp=hrTrainReportMapper.queryUser(mapVo);
		return 	mapEmp;
	}

	@Override
	public String queryReportEvaluateTable(Map<String, Object> map)
			throws DataAccessException {
		List<Map<String,Object>> list=hrTrainReportMapper.queryReportEvaluateTable(map);
		return  ChdJson.toJsonLower(list);
	}}
