
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmHosKpiScoreMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpiScore;
import com.chd.hrp.prm.service.PrmHosKpiScoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0209 医院KPI指标考评计算表
 * @Table:
 * PRM_HOS_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosKpiScoreService")
public class PrmHosKpiScoreServiceImpl implements PrmHosKpiScoreService {

	private static Logger logger = Logger.getLogger(PrmHosKpiScoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosKpiScoreMapper")
	private final PrmHosKpiScoreMapper prmHosKpiScoreMapper = null;
	
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
    
	/**
	 * @Description 
	 * 添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmHosKpiScore(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0309 科室KPI指标考评计算表
		PrmHosKpiScore prmHosKpiScore = queryPrmHosKpiScoreByCode(entityMap);

		if (prmHosKpiScore != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmHosKpiScoreMapper.addPrmHosKpiScore(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiScore\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmHosKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiScoreMapper.addBatchPrmHosKpiScore(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiScore\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmHosKpiScore(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmHosKpiScoreMapper.updatePrmHosKpiScore(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiScore\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmHosKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmHosKpiScoreMapper.updateBatchPrmHosKpiScore(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiScore\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmHosKpiScore(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmHosKpiScoreMapper.deletePrmHosKpiScore(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiScore\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmHosKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiScoreMapper.deleteBatchPrmHosKpiScore(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiScore\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpiScore(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpiScore queryPrmHosKpiScoreByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiScoreMapper.queryPrmHosKpiScoreByCode(entityMap);
	}
	@Override
	public String queryPrmHosKpiScoreHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
           SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScoreHos(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScoreHos(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String auditPrmHosKpiScore(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = 	prmHosKpiScoreMapper.auditPrmHosKpiScore(entityMap);
				
			  if(state > 0){
				  
				  return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			  }else{
				  return "{\"msg\":\"没有数据.\",\"state\":\"true\"}";
			  }
				

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"审核失败 数据库异常 请联系管理员!\"}";

		
			}
	}
	@Override
	public String reAuditPrmHosKpiScore(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = 	prmHosKpiScoreMapper.auditPrmHosKpiScore(entityMap);
				
	               if(state > 0){
				  
				  return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";
				  
			  }else{
				  
				  return "{\"msg\":\"没有数据.\",\"state\":\"true\"}";
				  
			  }

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}";

		
			}
	}
	
	@Override
	public String collectHosKpiScore(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		prmHosKpiScoreMapper.collectHosKpiScore(entityMap);
		
		String prm_AppCode = entityMap.get("prm_AppCode").toString();
		
		String prm_ErrTxt="";
		
		if("0".equals(prm_AppCode)){
			//计算成功！提交事务
			transactionManager.commit(status);
			
			return "{\"msg_text\":\"计算成功\",\"is_ok\":\""+prm_AppCode+"\"}";
			
		}else if("-1".equals(prm_AppCode)||"100".equals(prm_AppCode)){
			//计算失败！回滚事务
			transactionManager.rollback(status);
			
		}
		
		if(!"".equals(entityMap.get("prm_ErrTxt").toString()) && null != entityMap.get("prm_ErrTxt").toString()){
			
			prm_ErrTxt = entityMap.get("prm_ErrTxt").toString();
			
		}
		
		return "{\"msg_text\":\""+prm_ErrTxt+"\",\"is_ok\":\""+prm_AppCode+"\"}";
		
	}
	@Override
	public String queryPrmHosKpiScoreByScheme(Map<String, Object> entityMap) throws DataAccessException {
		   SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScoreByScheme(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScoreByScheme(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	@Override
	public String queryPrmHosKpiScoreBySchemeTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder json = new StringBuilder();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		try {
			entityMap.put("user_id", SessionManager.getUserId());
			
			entityMap.put("mod_code", SessionManager.getModCode());
			
			entityMap.put("prem_data", "true");
			List<PrmHosKpiScore> list_hos_kpi = prmHosKpiScoreMapper.queryPrmHosKpiScoreBySchemeTree(entityMap);

			List<PrmGoal> list_goal = prmGoalMapper.queryPrmGoalByTree(entityMap);

			if (list_hos_kpi.size() == 0 && list_goal.size() == 0) {

				json.append("{Rows:[]}");

				return json.toString();

			}
			json.append("{Rows:[");

			for (PrmGoal goal : list_goal) {
				json.append("{");

				json.append("\"pid\":\"-1\"," + "\"id\":\"" + goal.getGoal_code() + "goal" + "\"," + "\"text\":" + "\""
						+ goal.getGoal_name() + "\"");

				json.append("},");
				Map<String, Object> map = new HashMap<String, Object>();

				for (PrmHosKpiScore p : list_hos_kpi) {

					if (p.getGoal_code().equals(goal.getGoal_code())) {

						String key = p.getKpi_code();

						if (map.get(key) == null) {// 去除重复维度

							if (p.getKpi_code() != null) {

								json.append("{");
								String pidStr = "";
								if (p.getSuper_kpi_code().equals("TOP")) {
									pidStr = goal.getGoal_code() + "goal";
								} else {
									pidStr = p.getSuper_kpi_code();
								}
								String ratioStr = "";
								if (p.getRatio() > 0) {
									String ratio = decimalFormat.format(p.getRatio() * 100);
									ratioStr = p.getKpi_name() + "(" + ratio + "%)";
								} else {
									ratioStr = p.getKpi_name();
								}

								json.append("\"pid\":\"" + pidStr + "\"," + "\"id\":\"" + p.getKpi_code() + "\","
										+ "\"text\":" + "\"" + ratioStr + "\"");

								json.append("},");

								map.put(key, key);
							}
						}
					}
				}
			}


			json.setCharAt(json.length() - 1, ']');

			json.append("}");

		} catch (Exception e) {

			json.append("{Rows:[]}");

			return json.toString();

		}

		return json.toString();
	}
	
	
	
	@Override
	public List<Map<String, Object>> queryPrmHosKpiScoreHosPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		
		List<PrmHosKpiScore> list = prmHosKpiScoreMapper.queryPrmHosKpiScoreHos(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}

}
