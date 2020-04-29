/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.hr.dao.medicalmanagement.HrClincMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrTechDocWorkMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrHeartDocWork;
import com.chd.hrp.hr.entity.medicalmanagement.HrClinc;
import com.chd.hrp.hr.service.medicalmanagement.HrClincService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * 门诊能力
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrClincService")
public class HrClincServiceImpl implements HrClincService {

	private static Logger logger = Logger.getLogger(HrClincServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrClincMapper")
	private final HrClincMapper hrClincMapper = null;
	
	//引入DAO操作
		@Resource(name = "hrTechDocWorkMapper")
		private final HrTechDocWorkMapper hrTechDocWorkMapper = null;
    
	@Override
	public String addClinc(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			entityMap.put("is_commit", 0);
			List<HrClinc> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrClinc.class);
			
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				
/*				for (HrClinc hrClinc : alllistVo) {
			
					hrClinc.setGroup_id(Integer.parseInt((entityMap.get("group_id").toString())));
					hrClinc.setHos_id(Integer.parseInt(entityMap.get("hos_id").toString()));
					if (hrClinc.getIs_commit()==null) {
						hrClinc.setIs_commit(0);
					}
				}
				}
*/			/**
			 * 先删除
			 */
			hrClincMapper.deleteClinc(alllistVo,entityMap);
			hrClincMapper.addBatchClinc(alllistVo,entityMap);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}






	@Override
	public String queryClinc(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrClinc> list = (List<HrClinc>)hrClincMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrClinc> list = (List<HrClinc>)hrClincMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}






	@Override
	public String deleteClinc(List<HrClinc> entityList)
			throws DataAccessException {

		try {
			if (entityList !=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrClincMapper.deleteClinc(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}






	@Override
	public String importClinc(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		Map<String, Object> commitMap= new HashMap<String, Object>();
		commitMap.put("未提交", "0");
		commitMap.put("已提交", "1");
		commitMap.put("0", "0");
		commitMap.put("1", "1");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if (map.get("dept_id").get(1) == null || map.get("emp_id").get(1) == null ) {
						continue;
					}
					if ("".equals(map.get("dept_id").get(1))|| "".equals(map.get("emp_id").get(1)) ) {
						continue;
					}
					if ("null".equals(map.get("dept_id").get(1))|| "null".equals(map.get("emp_id").get(1)) ) {
						continue;
					}
					SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
					Map<String,Object> saveMap = new HashMap<String,Object>();
					// 科室
					Map<String, Object> useDeptDict = null;
					Map<String, Object> useDeptMap = new HashMap<String, Object>();
					Map<String, Object> deptMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("group_id", SessionManager.getGroupId());
					deptMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("dept_code", map.get("dept_id").get(1));
					deptMap.put("dept_name", map.get("dept_id").get(1));
					useDeptDict = hrTechDocWorkMapper
							.queryDeptDictByCodeOrName(deptMap);
					if (useDeptDict == null
							|| useDeptDict.get("dept_id") == null) {
						failureMsg.append(map.get("dept_id").get(0)
								+ ":科室不存在！<br/>");
						failureNum++;
						continue;
					}
					saveMap.put("dept_id", useDeptDict.get("dept_id"));
					// 人员
					Map<String, Object> hos_emp = null;
					Map<String, Object> emp = new HashMap<String, Object>();
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
						failureMsg.append("在"
								+ useDeptDict.get("dept_name") + "科中"
								+ map.get("emp_id").get(0) + "人员不存在！<br/>");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", hos_emp.get("emp_id"));
					saveMap.put("out_days", map.get("out_days").get(1));
					saveMap.put("out_ratio", map.get("out_ratio").get(1));
					saveMap.put("order_ratio", map.get("order_ratio").get(1));
					saveMap.put("good_eval", map.get("good_eval").get(1));
					saveMap.put("bad_eval", map.get("bad_eval").get(1));
					saveMap.put("per_charge", map.get("per_charge").get(1));
					saveMap.put("med_ratio", map.get("med_ratio").get(1));
					saveMap.put("is_commit", commitMap.get(map.get("is_commit").get(1))==null ? 0 : 1);
					
					HrClinc type = hrClincMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/> 已存在相同的记录; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrClincMapper.addBatch(saveList);
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
	public HrClinc queryByCode(HrClinc entityMap)
			throws DataAccessException {
		return hrClincMapper.queryByCodeClinc(entityMap);
	}






	@Override
	public String confirmClinc(HrClinc hrPlanit) throws DataAccessException {
		try {
	        
			hrClincMapper.confirmClinc(hrPlanit);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}






	@Override
	public String reConfirmClinc(HrClinc hrPlanit) throws DataAccessException {
		try {
	        
			hrClincMapper.reConfirmClinc(hrPlanit);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}






	@Override
	public List<Map<String, Object>> queryClincByPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrClincMapper.queryClincByPrint(entityMap));
		 return list;
	}
	
	
}
