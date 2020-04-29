
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
import com.chd.hrp.prm.dao.PrmEmpKpiSummaryMapper;
import com.chd.hrp.prm.dao.PrmLedMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiSummary;
import com.chd.hrp.prm.entity.PrmEmpKpiSummary;
import com.chd.hrp.prm.entity.PrmLed;
import com.chd.hrp.prm.service.PrmEmpKpiSummaryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0410 职工KPI考评总结表
 * @Table:
 * PRM_EMP_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmEmpKpiSummaryService")
public class PrmEmpKpiSummaryServiceImpl implements PrmEmpKpiSummaryService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiSummaryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmEmpKpiSummaryMapper")
	private final PrmEmpKpiSummaryMapper prmEmpKpiSummaryMapper = null;
	//引入DAO操作
	@Resource(name = "prmLedMapper")
	private final PrmLedMapper prmLedMapper = null;
	/**
	 * @Description 
	 * 添加0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException{ 
		
		List<Map<String, Object>>  listUpdate = (List<Map<String, Object>>) entityMap.get("listUpdateVo");
		
		List<Map<String, Object>>  listAdd = (List<Map<String, Object>>) entityMap.get("listAddVo");
		
		if(listUpdate.size()>0){
			
			prmEmpKpiSummaryMapper.updateBatchPrmEmpKpiSummary(listUpdate);
			
			return  "{\"msg\":\"更新成功 \"}";
			
		}
		
		if(listAdd.size()>0){
			
			prmEmpKpiSummaryMapper.addBatchPrmEmpKpiSummary(listAdd);
			
			return "{\"msg\":\"添加成功\"}";
			
		}

		return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addPrmHosKpiSummary\"}";

		
	}
	/**
	 * @Description 
	 * 批量添加0410 职工KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmEmpKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiSummaryMapper.addBatchPrmEmpKpiSummary(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpKpiSummary\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmEmpKpiSummaryMapper.updatePrmEmpKpiSummary(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpiSummary\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0410 职工KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmEmpKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmEmpKpiSummaryMapper.updateBatchPrmEmpKpiSummary(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiSummary\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmEmpKpiSummary(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmEmpKpiSummaryMapper.deletePrmEmpKpiSummary(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpiSummary\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0410 职工KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmEmpKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiSummaryMapper.deleteBatchPrmEmpKpiSummary(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpKpiSummary\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmEmpKpiSummary(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpKpiSummary> list = prmEmpKpiSummaryMapper.queryPrmEmpKpiSummaryEmpDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpKpiSummary> listEmp = prmEmpKpiSummaryMapper.queryPrmEmpKpiSummaryEmpDict(entityMap, rowBounds);
			 
			List<PrmLed> listLed  =  prmLedMapper.queryPrmLed(entityMap);

			// 拼接SQL
			StringBuilder json = new StringBuilder();
			
			json.append("{Rows:[");
			
			for( int i =0 ;i < listEmp.size(); i++){
				
				PrmEmpKpiSummary p = (PrmEmpKpiSummary)listEmp.get(i);
				
				json.append("{");
				json.append("\"group_id\":\""+p.getGroup_id()+"\",");
				json.append("\"hos_id\":\""+p.getHos_id()+"\",");
				json.append("\"copy_code\":\""+p.getCopy_code()+"\",");
				json.append("\"goal_code\":\""+p.getGoal_code()+"\",");
				json.append("\"emp_no\":\""+p.getEmp_no()+"\",");
				json.append("\"emp_id\":\""+p.getEmp_id()+"\",");
				json.append("\"acc_year\":\""+p.getAcc_year()+"\",");
				json.append("\"acc_month\":\""+p.getAcc_month()+"\",");   
				json.append("\"dept_name\":\""+p.getDept_name()+"\",");
				json.append("\"emp_code\":\""+p.getEmp_code()+"\",");   
				json.append("\"emp_name\":\""+p.getEmp_name()+"\","); 
				json.append("\"kpi_score\":"+"\""+p.getKpi_score()+"\",");
				json.append("\"led_path\":\""+getLedPath(listLed,p.getKpi_score())+"\"");
				
				json.append("},");
				
			}
			if(listEmp.size() > 0){
				json.setCharAt(json.length() - 1, ']');
			}else{
				json.append("]");
			}
			
			
			json.append(",Total:"+listEmp.size()+"}");
			
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
	 * 获取对象0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmEmpKpiSummary queryPrmEmpKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpKpiSummaryMapper.queryPrmEmpKpiSummaryByCode(entityMap);
	}
	/**
	 * @Description 
	 * 获取对象0410 职工KPI考评总结表<BR>  left join  aphi_dept_dict 和 prm_emp_dict 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmEmpKpiSummary queryPrmEmpKpiSummaryEmpDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpKpiSummaryMapper.queryPrmEmpKpiSummaryEmpDictByCode(entityMap);
	}
}
