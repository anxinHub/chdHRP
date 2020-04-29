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
import com.chd.hrp.hr.dao.medicalmanagement.HrInnovationMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrTechDocWorkMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrHeartDocWork;
import com.chd.hrp.hr.entity.medicalmanagement.HrInnovation;
import com.chd.hrp.hr.service.medicalmanagement.HrInnovationService;
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
 


@Service("hrInnovationService")
public class HrInnovationServiceImpl implements HrInnovationService {

	private static Logger logger = Logger.getLogger(HrInnovationServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrInnovationMapper")
	private final HrInnovationMapper hrInnovationMapper = null;
	
	//引入DAO操作
		@Resource(name = "hrTechDocWorkMapper")
		private final HrTechDocWorkMapper hrTechDocWorkMapper = null;
    
	@Override
	public String addInnovation(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			/**
			 * 先删除
			 */
			//hrInnovationMapper.deletehrInnovation(entityMap);
			List<HrInnovation> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrInnovation.class);
			
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				
				for (HrInnovation hrInnovation : alllistVo) {
			
					hrInnovation.setGroup_id(Integer.parseInt((entityMap.get("group_id").toString())));
					hrInnovation.setHos_id(Integer.parseInt(entityMap.get("hos_id").toString()));
					if (hrInnovation.getIs_commit()==null) {
						hrInnovation.setIs_commit(0);
					}
			
				}}
		
			hrInnovationMapper.deleteInnovati(alllistVo);
			hrInnovationMapper.addBatch(alllistVo);
			return "{\"msg\":\"保存成功\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}






	@Override
	public String queryInnovation(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrInnovation> list = (List<HrInnovation>)hrInnovationMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrInnovation> list = (List<HrInnovation>)hrInnovationMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}






	@Override
	public String deleteInnovation(List<HrInnovation> entityList)
			throws DataAccessException {

		try {
			if (entityList !=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrInnovationMapper.deleteInnovation(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}






	@Override
	public String importInnovation(Map<String, Object> entityMap)
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
		boolean dateFlag=true;
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("nint", map.get("nint").get(1));
				
					if(map.get("nint_date").get(1).toString()!=null || map.get("nint_date").get(1).toString() !=""){
						dateFlag=  DateUtil.CheckDate(map.get("nint_date").get(1).toString());
				        if (!dateFlag) {
				        	failureMsg.append(map.get("nint_date").get(1)+"<br/> 日期格式不合法;日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
				        }else{
				        	saveMap.put("nint_date", map.get("nint_date").get(1));
				        }	
					}else{
						saveMap.put("nint_date", map.get("nint_date").get(1));
					}
					saveMap.put("role", map.get("role").get(1));
					saveMap.put("emp", map.get("emp").get(1));
					saveMap.put("case_num", map.get("case_num").get(1));
					saveMap.put("case_tol", map.get("case_tol").get(1));
					saveMap.put("prize", map.get("prize").get(1));
					saveMap.put("is_commit", commitMap.get(map.get("is_commit").get(1))==null ? 0:commitMap.get(map.get("is_commit").get(1)));
					
					HrInnovation type = hrInnovationMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/> 已存在相同的记录; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrInnovationMapper.addBatchImport(saveList);
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
	public HrInnovation queryByCode(HrInnovation entityMap)
			throws DataAccessException {
		return hrInnovationMapper.queryByCodeInnovation(entityMap);
	}






	@Override
	public String confirmInnovation(HrInnovation hrPlanit) throws DataAccessException {
		try {
	        
			hrInnovationMapper.confirmInnovation(hrPlanit);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}






	@Override
	public String reConfirmInnovation(HrInnovation hrPlanit) throws DataAccessException {
		try {
	        
			hrInnovationMapper.reConfirmInnovation(hrPlanit);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}






	@Override
	public List<Map<String, Object>> queryInnovationByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrInnovationMapper.queryInnovationByPrint(entityMap));
		 return list;
	}
	
	
}
