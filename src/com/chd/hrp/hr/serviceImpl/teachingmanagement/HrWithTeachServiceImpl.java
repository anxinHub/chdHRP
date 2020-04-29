package com.chd.hrp.hr.serviceImpl.teachingmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.teachingmanagement.HrWithTeachMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrWithTeach;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainIcu;
import com.chd.hrp.hr.service.teachingmanagement.HrWithTeachService;
import com.github.pagehelper.PageInfo;

@Service("hrWithTeachService")
public class HrWithTeachServiceImpl implements HrWithTeachService {
	private static Logger logger = Logger.getLogger(HrWithTeachServiceImpl.class);
	
	
	@Resource(name = "hrWithTeachMapper")
	private final HrWithTeachMapper hrWithTeachMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	/**
	 * 保存数据
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
try {
			
			List<HrWithTeach> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")),
				HrWithTeach.class);

		if (alllistVo != null && alllistVo.size() > 0) {
			for (HrWithTeach hrWithTeach : alllistVo) {
				hrWithTeach.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrWithTeach.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				/*hrZyyNtrainInarea.setYear(mapVo.get("year")
						.toString());*/
				/*hrZyyNtrainInarea.setNote("");*/
			}

		
		
			hrWithTeachMapper.deleteHrWithTeach(alllistVo);
			hrWithTeachMapper.addBatch(alllistVo);
		}
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}
	}
	/**
	 * 查询
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrWithTeach> list = (List<HrWithTeach>) hrWithTeachMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrWithTeach> list = (List<HrWithTeach>) hrWithTeachMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


	/**
	 * 查询轮转科室信息  下拉框
	 */
	@Override
	public String queryDeptComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSONArray.toJSONString(hrWithTeachMapper.queryDeptComboBox(entityMap));
	}
	/**
	 * 查询  双击工号出现下拉框
	 */
	@Override
	public String queryComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		return ChdJson.toJson(hrWithTeachMapper.queryComboBox(entityMap));
	}
	/**
	 * 删除
	 */
	@Override
	public String deleteHrWithTeach(List<HrWithTeach> entityList)
			throws DataAccessException {
		try {
			hrWithTeachMapper.deleteHrWithTeach(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String importWithTeach(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					// 过滤空行
					if(StringUtils.isEmpty(map.get("emp_id").get(1)) || StringUtils.isEmpty(map.get("month").get(1))
							|| StringUtils.isEmpty(map.get("year").get(1))){
						continue;
					}

					if ("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("month").get(1))
							|| "null".equals(map.get("year").get(1))) {
						continue;
					}
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("emp_code", map.get("emp_id").get(1));
					Map<String, Object> empMap = hrSelectMapper.queryEmpByCode(saveMap);
					if (empMap == null) {
						failureMsg.append("<br/>员工 " + map.get("emp_id").get(1) + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", empMap.get("emp_id"));
					saveMap.put("dept_code", map.get("dept_id").get(1));
					if(StringUtils.isNotEmpty(map.get("dept_id").get(1))){
						Map<String, Object> dept = hrSelectMapper.queryDeptByCode(saveMap);
						if (dept == null) {
							failureMsg.append("<br/>科室 " + map.get("dept_id").get(1) + " 不存在; ");
							failureNum++;
							continue;
						}
						saveMap.put("dept_id", dept.get("dept_id"));
					}else{
						saveMap.put("dept_id", map.get("dept_id").get(1));
					}
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("month", map.get("month").get(1));
					HrWithTeach hrZyyNtrainIcu = hrWithTeachMapper.queryByCode(saveMap);
					if (hrZyyNtrainIcu != null) {
						failureMsg.append("<br/> " + "年份" + map.get("year").get(1) + "月份" + map.get("month").get(1)
								+ "员工" + map.get("emp_id").get(1) + " 已存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("student", map.get("student").get(1));
					saveMap.put("rotate", map.get("rotate").get(1));
					saveMap.put("stud_eval", map.get("stud_eval").get(1));
					saveMap.put("net_aduit", map.get("net_aduit").get(1));
					saveMap.put("attend_meet", map.get("attend_meet").get(1));

					saveMap.put("with_teach_grade", map.get("with_teach_grade").get(1));
					saveMap.put("with_teach_money", map.get("with_teach_money").get(1));
					saveMap.put("ultrasound", map.get("ultrasound").get(1));
					saveMap.put("case_qc", map.get("case_qc").get(1));
					saveMap.put("teach", map.get("teach").get(1));
					saveMap.put("note", map.get("note").get(1));

					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					hrWithTeachMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return "{\"error\":\"导入失败！\"}";
		}}

	@Override
	public List<Map<String, Object>> queryWithTeachByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrWithTeachMapper.queryWithTeachByPrint(entityMap));
		 return list;
	}

}
