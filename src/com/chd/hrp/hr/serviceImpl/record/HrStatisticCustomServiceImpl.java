package com.chd.hrp.hr.serviceImpl.record;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.Parameter;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.dao.record.HrStatisticConditionMapper;
import com.chd.hrp.hr.dao.record.HrStatisticCustomMapper;
import com.chd.hrp.hr.dao.record.HrStatisticTabStrucMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
import com.chd.hrp.hr.service.record.HrStatisticCustomService;

@Service("hrStatisticCustomService")
public class HrStatisticCustomServiceImpl implements HrStatisticCustomService {
	
	private static Logger logger = Logger.getLogger(HrStatisticCustomServiceImpl.class);
	
	@Resource(name = "hrStatisticCustomMapper")
	private final HrStatisticCustomMapper hrStatisticCustomMapper = null;
	
	@Resource(name = "hrStatisticTabStrucMapper")
	private final HrStatisticTabStrucMapper hrStatisticTabStrucMapper = null;
	
	@Resource(name = "hrStatisticConditionMapper")
	private final HrStatisticConditionMapper hrStatisticConditionMapper = null;

	@Override
	public String queryHrStatisticCustomSetMenu(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrStatisticCustomMapper.queryHrStatisticCustomSet(entityMap);
		for (Map<String, Object> map : list) {
			map.put("id", map.get("statistic_code"));
			map.put("name", map.get("statistic_name"));
		}
		return JSONArray.toJSONString(list);
	}

	@Override
	public Map<String, Object> queryHrStatisticCustomSetByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> data = hrStatisticCustomMapper.queryHrStatisticCustomSetByCode(entityMap);
		return data;
	}

	@Override
	public String deleteHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrStatisticConditionMapper.deleteHrStatisticSetCondition(entityMap);
			hrStatisticTabStrucMapper.deleteHrStatisticSetTab(entityMap);
			hrStatisticCustomMapper.deleteHrStatisticCustomSet(entityMap);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> data = hrStatisticCustomMapper.queryHrStatisticCustomSetByCode(entityMap);

		try {
			if (data != null) {
				hrStatisticCustomMapper.updateHrStatisticCustomSet(entityMap);
			} else {
				List<Map<String, Object>> list = hrStatisticCustomMapper.queryHrStatisticCustomSetByName(entityMap);
				if (list != null) {
					for (Map<String, Object> map : list) {
						if (map.get("statistic_name").equals(entityMap.get("statistic_name"))) {
							return "{\"error\":\"统计表名：" + map.get("statistic_name").toString() + "已存在.\"}";
						}
					}
				}
				
				hrStatisticCustomMapper.insertHrStatisticCustomSet(entityMap);
			}

			// 表列
			if (entityMap.get("data") != null && StringUtils.isNotBlank(entityMap.get("data").toString())) {
				List<Parameter> dataList = null;
				try {
					dataList = JSONArray.parseArray(entityMap.get("data").toString(), Parameter.class);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new SysException("表格数据转换错误");
				}
				if (dataList != null && dataList.size() > 0) {
					int sort = 1;
					for (Parameter parameter : dataList) {
						parameter.put("group_id", SessionManager.getGroupId());
						parameter.put("hos_id", SessionManager.getHosId());
						parameter.put("copy_code", SessionManager.getCopyCode());
						parameter.put("statistic_code", entityMap.get("statistic_code"));
						parameter.put("sort", sort);
						sort++;
					}
					// 数据表对应数据 先删后加
					hrStatisticTabStrucMapper.deleteHrStatisticSetTab(entityMap);
					hrStatisticTabStrucMapper.insertHrStatisticSetTab(dataList);
				}

			}
			
			// 数据表对应数据 先删后加
			hrStatisticConditionMapper.deleteHrStatisticSetCondition(entityMap);
			
			// 条件
			if (entityMap.get("conditionData") != null) {
				List<Parameter> dataList = null;
				try {
					dataList = JSONArray.parseArray(entityMap.get("conditionData").toString(), Parameter.class);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new SysException("表格数据转换错误");
				}
				if (dataList != null && dataList.size() > 0) {
					int line_no = 1;
					for (Parameter parameter : dataList) {
						parameter.put("group_id", SessionManager.getGroupId());
						parameter.put("hos_id", SessionManager.getHosId());
						parameter.put("copy_code", SessionManager.getCopyCode());
						parameter.put("statistic_code", entityMap.get("statistic_code"));
					if (parameter.get("l_bracket") == null
								|| StringUtils.isBlank(parameter.get("l_bracket").toString())) {
							parameter.put("l_bracket", "");
						}
						if (parameter.get("r_bracket") == null
								|| StringUtils.isBlank(parameter.get("r_bracket").toString())) {
							parameter.put("r_bracket", "");
						}
						if (parameter.get("con_sign_code") == null
								|| StringUtils.isBlank(parameter.get("con_sign_code").toString())) {
							parameter.put("con_sign_code", "=");
						}
						if (parameter.get("join_sign_code") == null|| StringUtils.isBlank(parameter.get("join_sign_code").toString())) {
							parameter.put("join_sign_code", "");
						}
						parameter.put("col_value",
								parameter.get("field_col_code") == null
										|| StringUtils.isBlank(parameter.get("field_col_code").toString())
												? parameter.get("field_col_name") : parameter.get("field_col_code"));
						parameter.put("line_no", line_no);
						line_no++;
					}

					hrStatisticConditionMapper.insertHrStatisticSetCondition(dataList);
				}

			}

		} catch (Exception e1) {
			logger.error(e1.getMessage());
			throw new SysException("数据保存失败");
		}

		return "{\"msg\":\"保存成功.\",\"statistic_code\":\"" + entityMap.get("statistic_code").toString()
				+ "\",\"state\":\"true\"}";
	}

}
