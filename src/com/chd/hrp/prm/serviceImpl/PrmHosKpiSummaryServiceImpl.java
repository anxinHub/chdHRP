
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
import com.chd.hrp.prm.dao.PrmHosKpiSummaryMapper;
import com.chd.hrp.prm.dao.PrmLedMapper;
import com.chd.hrp.prm.entity.PrmHosKpiSummary;
import com.chd.hrp.prm.entity.PrmHosKpiValue;
import com.chd.hrp.prm.entity.PrmLed;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmHosKpiSummaryService;
import com.chd.hrp.sys.entity.Info;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0210 医院KPI考评总结表
 * @Table:
 * PRM_HOS_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosKpiSummaryService")
public class PrmHosKpiSummaryServiceImpl implements PrmHosKpiSummaryService {

	private static Logger logger = Logger.getLogger(PrmHosKpiSummaryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosKpiSummaryMapper")
	private final PrmHosKpiSummaryMapper prmHosKpiSummaryMapper = null;
	//引入DAO操作
		@Resource(name = "prmLedMapper")
		private final PrmLedMapper prmLedMapper = null;
    
	/**
	 * @Description 
	 * 添加0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException{
		
		
		List<Map<String, Object>>  listUpdate = (List<Map<String, Object>>) entityMap.get("listUpdateVo");
		
		List<Map<String, Object>>  listAdd = (List<Map<String, Object>>) entityMap.get("listAddVo");
		
		if(listUpdate.size()>0){
			
			prmHosKpiSummaryMapper.updateBatchPrmHosKpiSummary(listUpdate);
			
			return  "{\"msg\":\"更新成功 \"}";
			
		}
		
		if(listAdd.size()>0){
			
			prmHosKpiSummaryMapper.addBatchPrmHosKpiSummary(listAdd);
			
			return "{\"msg\":\"添加成功 \"}";
			
		}

		return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addPrmHosKpiSummary\"}";

		
		
	}
	/**
	 * @Description 
	 * 批量添加0210 医院KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmHosKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {  
			 
			prmHosKpiSummaryMapper.addBatchPrmHosKpiSummary(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiSummary\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmHosKpiSummaryMapper.updatePrmHosKpiSummary(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiSummary\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0210 医院KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmHosKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmHosKpiSummaryMapper.updateBatchPrmHosKpiSummary(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiSummary\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmHosKpiSummary(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmHosKpiSummaryMapper.deletePrmHosKpiSummary(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiSummary\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0210 医院KPI考评总结表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmHosKpiSummary(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiSummaryMapper.deleteBatchPrmHosKpiSummary(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiSummary\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpiSummary(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			 
			
			List<PrmHosKpiSummary> list = prmHosKpiSummaryMapper.queryPrmHosKpiSummaryHosInfo(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosKpiSummary> listHos = prmHosKpiSummaryMapper.queryPrmHosKpiSummaryHosInfo(entityMap,rowBounds);
			 
			List<PrmLed> listLed  =  prmLedMapper.queryPrmLed(entityMap);

			// 拼接SQL
			StringBuilder json = new StringBuilder();
			
			json.append("{Rows:[");
			
			for( int i =0 ;i < listHos.size(); i++){
				
				PrmHosKpiSummary p = (PrmHosKpiSummary)listHos.get(i);
				
				json.append("{");
				json.append("\"group_id\":\""+p.getGroup_id()+"\",");
				json.append("\"hos_id\":\""+p.getHos_id()+"\",");
				json.append("\"copy_code\":\""+p.getCopy_code()+"\",");
				json.append("\"goal_code\":\""+p.getGoal_code()+"\",");
				json.append("\"check_hos_id\":\""+p.getCheck_hos_id()+"\",");
				json.append("\"acc_year\":\""+p.getAcc_year()+"\",");
				json.append("\"acc_month\":\""+p.getAcc_month()+"\",");
				json.append("\"hos_code\":"+"\""+p.getHos_code()+"\",");
				json.append("\"hos_name\":\""+p.getHos_name()+"\",");
				json.append("\"kpi_score\":"+"\""+p.getKpi_score()+"\",");
				json.append("\"led_path\":\""+getLedPath(listLed,p.getKpi_score())+"\"");
				
				json.append("},");
				
			}
			if(listHos.size() > 0){
				json.setCharAt(json.length() - 1, ']');
			}else{
				json.append("]");
			}
			
			json.append(",Total:"+listHos.size()+"}");

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
	 * 获取对象0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpiSummary queryPrmHosKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiSummaryMapper.queryPrmHosKpiSummaryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象0210 医院KPI考评总结表<BR>  left join hos_info
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpiSummary queryPrmHosKpiSummaryHosInfoByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiSummaryMapper.queryPrmHosKpiSummaryHosInfoByCode(entityMap);
	}
}
