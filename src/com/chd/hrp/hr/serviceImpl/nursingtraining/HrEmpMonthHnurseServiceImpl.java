/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.nursingtraining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.hr.dao.nursing.HrAcademicAbilityMapper;
import com.chd.hrp.hr.dao.nursingtraining.HrEmpMonthHnurseMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.nursingtraining.HrEmpMonthHnurse;
import com.chd.hrp.hr.service.nursingtraining.HrEmpMonthHnurseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table: HR_EMP_MONTH_HNURSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrEmpMonthHnurseService")
public class HrEmpMonthHnurseServiceImpl implements HrEmpMonthHnurseService {

	private static Logger logger = Logger.getLogger(HrEmpMonthHnurseServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrEmpMonthHnurseMapper")
	private final HrEmpMonthHnurseMapper hrEmpMonthHnurseMapper = null;

	@Resource(name = "hrAcademicAbilityMapper")
	private final HrAcademicAbilityMapper hrAcademicAbilityMapper = null;
	@Override
	public String saveHrEmpMonthHnurse(Map<String, Object> entityMap) throws DataAccessException {
		String jsonStr = entityMap.get("paramVo").toString();
		List<HrEmpMonthHnurse> list = JSONArray.parseArray(jsonStr, HrEmpMonthHnurse.class);
		for (HrEmpMonthHnurse mh : list) {
			mh.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			mh.setHos_id(Long.parseLong(SessionManager.getHosId()));
		}
		try {
			hrEmpMonthHnurseMapper.deleteHrEmpMonth(list);
			hrEmpMonthHnurseMapper.addBatch(list);
			return "{\"msg\":\"数据保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteHrEmpMonthHnurse(Map<String, Object> entityMap) throws DataAccessException {
		String jsonStr = entityMap.get("paramVo").toString();
		List<HrEmpMonthHnurse> list = JSONArray.parseArray(jsonStr, HrEmpMonthHnurse.class);
		/*for (HrEmpMonthHnurse mh : list) {
			mh.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			mh.setHos_id(Long.parseLong(SessionManager.getHosId()));
		}*/
		try {
			hrEmpMonthHnurseMapper.deleteHrEmpMonthHnurse(list,entityMap);
			return "{\"msg\":\"数据保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryHrEmpMonthHnurse(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<HrEmpMonthHnurse> datalist = hrEmpMonthHnurseMapper.queryHrEmpMonthHnurse(entityMap);
			return ChdJson.toJson(datalist);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrEmpMonthHnurse> datalist = hrEmpMonthHnurseMapper.queryHrEmpMonthHnurse(entityMap, rowBounds);
			PageInfo page = new PageInfo(datalist);
			return ChdJson.toJson(datalist, page.getTotal());
		}
	}

	@Override
	public String importMonthHNurse(Map<String, Object> entityMap)
			throws DataAccessException {

		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("提交", "1");
		whetherMap.put("新建", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if("".equals(map.get("emp_id").get(1)) || "".equals(map.get("year").get(1)) || "".equals(map.get("month").get(1))){
						continue;
					}
					
					if("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("year").get(1))|| "null".equals(map.get("month").get(1))){
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("month", map.get("month").get(1));
					saveMap.put("emp_id", map.get("emp_id").get(1));
					
					Map<String, Object> emp=hrAcademicAbilityMapper.queryEmp(saveMap);
					if(emp == null){
						failureMsg.append("<br/>职工ID"+map.get("emp_id")+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", emp.get("emp_id"));
					saveMap.put("morality", map.get("morality").get(1));
					saveMap.put("quality", map.get("quality").get(1));
					saveMap.put("safety", map.get("safety").get(1));
					saveMap.put("accessory", map.get("accessory").get(1));
					saveMap.put("note", map.get("note").get(1));
					HrAcademicAbility type = hrEmpMonthHnurseMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append(map.get("year")+"年"+"<br/>人员 "+map.get("emp_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrEmpMonthHnurseMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public List<Map<String, Object>> queryHNurseByPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrEmpMonthHnurseMapper.queryHNurseByPrint(entityMap));
		 return list;
	}

}
