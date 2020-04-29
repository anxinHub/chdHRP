/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.medicalmanagement.HrAdverseEventMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrTechDocWorkMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAdverseEvent;
import com.chd.hrp.hr.service.medicalmanagement.HrAdverseEventService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_CLINIC_DOC_WORK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrAdverseEventService")
public class HrAdverseEventServiceImpl implements HrAdverseEventService {

	private static Logger logger = Logger.getLogger(HrAdverseEventServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrAdverseEventMapper")
	private final HrAdverseEventMapper hrAdverseEventMapper = null;
	
	//引入DAO操作
		@Resource(name = "hrTechDocWorkMapper")
		private final HrTechDocWorkMapper hrTechDocWorkMapper = null;
    
	@Override
	public String addAdverseEvent(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			List<HrAdverseEvent> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrAdverseEvent.class);
			
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				
				for (HrAdverseEvent hrAdverseEvent : alllistVo) {
			
					hrAdverseEvent.setGroup_id(Double.parseDouble((entityMap.get("group_id").toString())));
					hrAdverseEvent.setHos_id(Double.parseDouble(entityMap.get("hos_id").toString()));
				}}
			/**
			 * 先删除
			 */
			
			hrAdverseEventMapper.deleteAdverseEvent(alllistVo);
			int state = hrAdverseEventMapper.addBatchAdverseEvent(alllistVo);

		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}






	@Override
	public String queryAdverseEvent(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrAdverseEvent> list = (List<HrAdverseEvent>)hrAdverseEventMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrAdverseEvent> list = (List<HrAdverseEvent>)hrAdverseEventMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}






	@Override
	public String deleteAdverseEvent(List<HrAdverseEvent> entityList)
			throws DataAccessException {

		try {
			if (entityList !=null) {
				hrAdverseEventMapper.deleteAdverseEvent(entityList);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}






	@Override
	public String importAdverseEvent(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("男", "1");
		whetherMap.put("女", "2");
		whetherMap.put("1", "1");
		whetherMap.put("2", "2");
		boolean dateFlag=true;
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if (map.get("occ_dept_id").get(1) == null || map.get("emp_id").get(1) == null ) {
						continue;
					}
					if ("".equals(map.get("occ_dept_id").get(1))|| "".equals(map.get("emp_id").get(1)) ) {
						continue;
					}
					if ("null".equals(map.get("occ_dept_id").get(1))|| "null".equals(map.get("emp_id").get(1)) ) {
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					
					
					// 科室
					Map<String, Object> useDeptDict = null;
					Map<String, Object> useDeptMap = new HashMap<String, Object>();
					if (map.get("occ_dept_id") != null && map.get("occ_dept_id").get(1) != null) {
						Map<String, Object> deptMap = new HashMap<String, Object>();
						deptMap.put("group_id", SessionManager.getGroupId());
						deptMap.put("hos_id", SessionManager.getHosId());
						deptMap.put("copy_code", SessionManager.getCopyCode());
						deptMap.put("dept_code", map.get("occ_dept_id").get(1));
						deptMap.put("dept_name", map.get("occ_dept_id").get(1));
						useDeptDict = hrTechDocWorkMapper.queryDeptDictByCodeOrName(deptMap);
						if (useDeptDict == null || useDeptDict.get("dept_id") == null) {
							failureMsg.append(map.get("occ_dept_id").get(0) + ":科室不存在！<br/>");
							failureNum++;
							continue;
						}
					}
					saveMap.put("occ_dept_id", useDeptDict.get("dept_id"));
					// 人员
					Map<String, Object> hos_emp = null;
					Map<String, Object> emp = new HashMap<String, Object>();
					if (map.get("emp_id") != null && map.get("emp_id").get(1) != null) {
						Map<String, Object> empMap = new HashMap<String, Object>();
						empMap.put("group_id", SessionManager.getGroupId());
						empMap.put("hos_id", SessionManager.getHosId());
						empMap.put("copy_code", SessionManager.getCopyCode());
						empMap.put("emp_id", map.get("emp_id").get(1));
						empMap.put("emp_name", map.get("emp_id").get(1));
						empMap.put("dept_id", useDeptDict.get("dept_id"));
						empMap.put("dept_no", useDeptDict.get("dept_no"));
						hos_emp = hrTechDocWorkMapper.queryEmpByCode(empMap);
						if (hos_emp == null || hos_emp.get("emp_id") == null) {
							failureMsg.append("在"+useDeptDict.get("dept_name")+"科中"+map.get("emp_id").get(0) + "人员不存在！<br/>");
							failureNum++;
							continue;
						}
					}
					saveMap.put("emp_id", hos_emp.get("emp_id"));
					if(map.get("occ_date").get(1).toString()!=null || map.get("occ_date").get(1).toString() !=""){
						dateFlag=  DateUtil.CheckDate(map.get("occ_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append(map.get("occ_date").get(1)+"<br/> 日期格式不合法;日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("occ_date", map.get("occ_date").get(1));
				        }	
					}
					saveMap.put("occ_date", map.get("occ_date").get(1));
					  
					saveMap.put("in_hos_no", map.get("in_hos_no").get(1));
					saveMap.put("patient", map.get("patient").get(1));
					saveMap.put("sex_code", whetherMap.get(map.get("sex_code").get(1)));
					saveMap.put("age", map.get("age").get(1));
					saveMap.put("diagnose", map.get("diagnose").get(1));
					saveMap.put("content", map.get("content").get(1));
					saveMap.put("finder", map.get("finder").get(1));
					saveMap.put("dept_opinion", map.get("dept_opinion").get(1));
					if(map.get("dept_aff_date").get(1) !=null){
					  dateFlag=  DateUtil.CheckDate(map.get("dept_aff_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append(map.get("dept_aff_date").get(1)+" 日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("dept_aff_date", map.get("dept_aff_date").get(1));
				        }
					}else{
			        	saveMap.put("dept_aff_date", map.get("dept_aff_date").get(1));
			        }
					if(map.get("hos_aff_date").get(1) !=null){
					  dateFlag=  DateUtil.CheckDate(map.get("hos_aff_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append("<br/>"+map.get("hos_aff_date").get(1)+" 日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("hos_aff_date", map.get("hos_aff_date").get(1));
				        }
					}else{
			        	saveMap.put("hos_aff_date", map.get("hos_aff_date").get(1));
			        }
					if(map.get("mmd_aff_date").get(1) !=null){
					  dateFlag=  DateUtil.CheckDate(map.get("mmd_aff_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append("<br/>"+map.get("mmd_aff_date").get(1)+"日期格式不合法;<br/> 日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("mmd_aff_date", map.get("mmd_aff_date").get(1));
				        }
					}else{
			        	saveMap.put("mmd_aff_date", map.get("mmd_aff_date").get(1));
			        }
					saveMap.put("reper", map.get("reper").get(1));
					
					HrAdverseEvent type = hrAdverseEventMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/> 已存在相同的记录; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrAdverseEventMapper.addBatch(saveList);
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
	public List<Map<String, Object>> queryAdverseEventByPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		 List<Map<String,Object>> list = ChdJson.toListLower(hrAdverseEventMapper.queryAdverseEventByPrint(entityMap));
		 return list;
	}
	
	
}
