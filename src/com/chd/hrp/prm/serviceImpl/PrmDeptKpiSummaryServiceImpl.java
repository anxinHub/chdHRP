
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmDeptKpiSummaryMapper;
import com.chd.hrp.prm.dao.PrmLedMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiSummary;
import com.chd.hrp.prm.entity.PrmHosKpiSummary;
import com.chd.hrp.prm.entity.PrmLed;
import com.chd.hrp.prm.service.PrmDeptKpiSummaryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0310 科室KPI考评总结表
 * @Table:
 * PRM_DEPT_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptKpiSummaryService")
public class PrmDeptKpiSummaryServiceImpl implements PrmDeptKpiSummaryService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiSummaryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptKpiSummaryMapper")
	private final PrmDeptKpiSummaryMapper prmDeptKpiSummaryMapper = null;
	//引入DAO操作
			@Resource(name = "prmLedMapper")
			private final PrmLedMapper prmLedMapper = null;
	/**
	 * @Description 
	 * 添加0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException{
		/*
		//获取对象0310 科室KPI考评总结表
		PrmDeptKpiSummary prmDeptKpiSummary = queryPrmDeptKpiSummaryByCode(entityMap);

		if (prmDeptKpiSummary != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		*/
		List<Map<String, Object>>  listUpdate = (List<Map<String, Object>>) entityMap.get("listUpdateVo");
		
		List<Map<String, Object>>  listAdd = (List<Map<String, Object>>) entityMap.get("listAddVo");
		
		if(listUpdate.size() > 0){
			
			prmDeptKpiSummaryMapper.updateBatchPrmDeptKpiSummary(listUpdate);
			
			return  "{\"msg\":\"更新成功 \"}";
			
		}
		
		if(listAdd.size() > 0){
			
			prmDeptKpiSummaryMapper.addBatchPrmDeptKpiSummary(listAdd);
			
			return "{\"msg\":\"添加成功\"}";
			
		}

		return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addPrmHosKpiSummary\"}";

		
		
	}
	/**
	 * @Description 
	 * 批量添加0310 科室KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmDeptKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiSummaryMapper.addBatchPrmDeptKpiSummary(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiSummary\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptKpiSummaryMapper.updatePrmDeptKpiSummary(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiSummary\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0310 科室KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmDeptKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptKpiSummaryMapper.updateBatchPrmDeptKpiSummary(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiSummary\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmDeptKpiSummary(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptKpiSummaryMapper.deletePrmDeptKpiSummary(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiSummary\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0310 科室KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmDeptKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiSummaryMapper.deleteBatchPrmDeptKpiSummary(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiSummary\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmDeptKpiSummary(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiSummary> list = prmDeptKpiSummaryMapper.queryPrmDeptKpiSummaryDeptDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptKpiSummary> listDept = prmDeptKpiSummaryMapper.queryPrmDeptKpiSummaryDeptDict(entityMap, rowBounds);
			 
			List<PrmLed> listLed  =  prmLedMapper.queryPrmLed(entityMap);

			// 拼接SQL
			StringBuilder json = new StringBuilder();
			
			json.append("{Rows:[");
			
			for( int i =0 ;i < listDept.size(); i++){
				
				PrmDeptKpiSummary p = (PrmDeptKpiSummary)listDept.get(i);
				
				json.append("{");
				json.append("\"group_id\":\""+p.getGroup_id()+"\",");
				json.append("\"hos_id\":\""+p.getHos_id()+"\",");
				json.append("\"copy_code\":\""+p.getCopy_code()+"\",");
				json.append("\"goal_code\":\""+p.getGoal_code()+"\",");
				json.append("\"dept_no\":\""+p.getDept_no()+"\",");
				json.append("\"dept_id\":\""+p.getDept_id()+"\",");
				json.append("\"acc_year\":\""+p.getAcc_year()+"\",");
				json.append("\"acc_month\":\""+p.getAcc_month()+"\",");
				json.append("\"dept_kind_name\":"+"\""+p.getDept_kind_name()+"\",");
				json.append("\"dept_code\":\""+p.getDept_code()+"\",");
				json.append("\"dept_name\":\""+p.getDept_name()+"\",");
				json.append("\"kpi_score\":"+"\""+p.getKpi_score()+"\",");
				json.append("\"led_path\":\""+getLedPath(listLed,p.getKpi_score())+"\"");
				
				json.append("},");
				
			}

			json.append("]");
			
			json.append(",Total:"+listDept.size()+"}"); 

	        return json.toString(); 
			
		}
		
	}
	
	
	public String getLedPath(List list,double score){
		
		Map<String,String> map_led = new HashMap<String,String>();
		
		for(int i=0; i<list.size(); i++){
			
			PrmLed pl=(PrmLed)list.get(i);
			
			if(score < pl.getKpi_end_score() && score >= pl.getKpi_beg_score()){
				
				return pl.getLed_path();
						
			}
			
		}
		return "";
		
	}
	
	/**
	 * @Description 
	 * 获取对象0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmDeptKpiSummary queryPrmDeptKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKpiSummaryMapper.queryPrmDeptKpiSummaryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象0310 科室KPI考评总结表<BR>  left join aphi_dept_dict 和  left join aphi_dept_kind
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmDeptKpiSummary queryPrmDeptKpiSummaryDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKpiSummaryMapper.queryPrmDeptKpiSummaryDeptDictByCode(entityMap);
	}
}
