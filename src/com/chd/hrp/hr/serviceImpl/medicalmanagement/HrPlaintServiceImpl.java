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
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.medicalmanagement.HrPlaintMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrTechDocWorkMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrPlaint;
import com.chd.hrp.hr.service.medicalmanagement.HrPlaintService;
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
 


@Service("hrPlaintService")
public class HrPlaintServiceImpl implements HrPlaintService {

	private static Logger logger = Logger.getLogger(HrPlaintServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrPlaintMapper")
	private final HrPlaintMapper hrPlaintMapper = null;
	
	//引入DAO操作
		@Resource(name = "hrTechDocWorkMapper")
		private final HrTechDocWorkMapper hrTechDocWorkMapper = null;
    
	@Override
	public String addPlaint(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			/**
			 * 先删除
			 */
			List<HrPlaint> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrPlaint.class);
			
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				
				for (HrPlaint hrPlaint : alllistVo) {
			
					if (hrPlaint.getIs_commit()==null) {
						hrPlaint.setIs_commit(0);
					}
				}
				}
			hrPlaintMapper.deletePlaint(alllistVo,entityMap);
			int state = hrPlaintMapper.addBatchPlaint(alllistVo,entityMap);

		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}






	@Override
	public String queryPlaint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrPlaint> list = (List<HrPlaint>)hrPlaintMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrPlaint> list = (List<HrPlaint>)hrPlaintMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}






	@Override
	public String deletePlaint(List<HrPlaint> entityList)
			throws DataAccessException {

		try {
			if (entityList !=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrPlaintMapper.deletePlaint(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}






	@Override
	public String importPlaint(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		Map<String, Object> commitMap= new HashMap<String, Object>();
		whetherMap.put("男", "1");
		whetherMap.put("女", "2");
		whetherMap.put("1", "1");
		whetherMap.put("2", "2");
		commitMap.put("未提交", "0");
		commitMap.put("已提交", "1");
		commitMap.put("0", "0");
		commitMap.put("1", "1");
		boolean dateFlag=true;
		 String str = "";
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if (map.get("in_hos_no").get(1) == null || map.get("occ_date").get(1) == null|| map.get("plaint_date").get(1) == null ) {
						continue;
					}
					if ("".equals(map.get("in_hos_no").get(1))|| "".equals(map.get("occ_date").get(1)) || "".equals(map.get("plaint_date").get(1)) ) {
						continue;
					}
					if ("null".equals(map.get("in_hos_no").get(1))|| "null".equals(map.get("occ_date").get(1)) || "null".equals(map.get("plaint_date").get(1))) {
						continue;
					}
					SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
					
                       
				      
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					   dateFlag=  DateUtil.CheckDate(map.get("occ_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append("<br/>"+map.get("occ_date").get(1)+" 日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("occ_date", map.get("occ_date").get(1));
				        }
					
				        dateFlag  =  DateUtil.CheckDate(map.get("plaint_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append("<br/>"+map.get("plaint_date").get(1)+" 日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("plaint_date", map.get("plaint_date").get(1));
				        }
					saveMap.put("in_hos_no", map.get("in_hos_no").get(1));
					saveMap.put("patient", map.get("patient").get(1));
					saveMap.put("sex_code", whetherMap.get(map.get("sex_code").get(1)));
					saveMap.put("age", map.get("age").get(1));
					saveMap.put("plainter", map.get("plainter").get(1));
					saveMap.put("plaint_tel", map.get("plaint_tel").get(1));
					saveMap.put("patient_ref", map.get("patient_ref").get(1));
					saveMap.put("content", map.get("content").get(1));
					saveMap.put("is_commit", (map.get("is_commit").get(1) == null ? "0" : map.get("is_commit").get(1)));
					
					HrPlaint type = hrPlaintMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/> 已存在相同的记录; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrPlaintMapper.addBatch(saveList);
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
	public HrPlaint queryByCode(HrPlaint entityMap)
			throws DataAccessException {
		return hrPlaintMapper.queryByCodePlaint(entityMap);
	}
	
	@Override
	public String confirmPlaint(List<HrPlaint> list) throws DataAccessException {
		try {
			if (list == null || list.size() == 0) {
				return "{\"msg\":\"提交失败,请选择行\",\"state\":\"false\"}";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 1);
		/*	if (hrPlaintMapper.queryByIsCommit(map, list) > 0) {
				return "{\"error\":\"只能包含未提交的数据！\",\"state\":\"false\"}";
			}*/

			for (HrPlaint hp : list) {
				hp.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hp.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hp.setIs_commit(1);
			}
			hrPlaintMapper.updateIsCommitBatch(list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String reConfirmPlaint(List<HrPlaint> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败，请选择行.\",\"state\":\"false\"}";
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 0);
		/*	if (hrPlaintMapper.queryByIsCommit(map, list) > 0) {
				return "{\"error\":\"只能包含已提交的数据！\",\"state\":\"false\"}";
			}*/
			
			for (HrPlaint hp : list) {
				hp.setIs_commit(0);
				hp.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hp.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			hrPlaintMapper.updateIsCommitBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryPlaintByPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		 List<Map<String,Object>> list = ChdJson.toListLower(hrPlaintMapper.queryPlaintByPrint(entityMap));
		 return list;
	}

}
