
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
import com.chd.hrp.prm.dao.PrmEmpKpiScoreMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmEmpKpiScore;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmEmpKpiScoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0409 职工KPI指标考评计算表
 * @Table:
 * PRM_EMP_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmEmpKpiScoreService")
public class PrmEmpKpiScoreServiceImpl implements PrmEmpKpiScoreService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiScoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmEmpKpiScoreMapper")
	private final PrmEmpKpiScoreMapper prmEmpKpiScoreMapper = null;
	
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
	public String addPrmEmpKpiScore(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0309 科室KPI指标考评计算表
		PrmEmpKpiScore prmEmpKpiScore = queryPrmEmpKpiScoreByCode(entityMap);

		if (prmEmpKpiScore != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmEmpKpiScoreMapper.addPrmEmpKpiScore(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiScore\"}";

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
	public String addBatchPrmEmpKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiScoreMapper.addBatchPrmEmpKpiScore(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpKpiScore\"}";

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
	public String updatePrmEmpKpiScore(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmEmpKpiScoreMapper.updatePrmEmpKpiScore(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpiScore\"}";

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
	public String updateBatchPrmEmpKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmEmpKpiScoreMapper.updateBatchPrmEmpKpiScore(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiScore\"}";

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
    public String deletePrmEmpKpiScore(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmEmpKpiScoreMapper.deletePrmEmpKpiScore(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpiScore\"}";

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
	public String deleteBatchPrmEmpKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiScoreMapper.deleteBatchPrmEmpKpiScore(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpKpiScore\"}";

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
	public String queryPrmEmpKpiScore(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScore(entityMap, rowBounds);
			
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
	public PrmEmpKpiScore queryPrmEmpKpiScoreByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpKpiScoreMapper.queryPrmEmpKpiScoreByCode(entityMap);
	}
	@Override
	public String queryPrmEmpKpiScoreEmp(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
           SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScoreEmp(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScoreEmp(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String auditPrmEmpKpiScore(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = 	prmEmpKpiScoreMapper.auditPrmEmpKpiScore(entityMap);
				
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
	public String reAuditPrmEmpKpiScore(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = 	prmEmpKpiScoreMapper.auditPrmEmpKpiScore(entityMap);
				
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
	public String collectEmpKpiScore(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		prmEmpKpiScoreMapper.collectEmpKpiScore(entityMap);
		
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
	public String queryPrmEmpKpiScoreByScheme(Map<String, Object> entityMap) throws DataAccessException {
		   SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScoreByScheme(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScoreByScheme(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	@Override
	public String queryPrmEmpKpiScoreBySchemeTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder json = new StringBuilder();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		try {
			entityMap.put("user_id", SessionManager.getUserId());
			
			entityMap.put("mod_code", SessionManager.getModCode());
			
			entityMap.put("prem_data", "true");
			List<PrmEmpKpiScore> list_hos_kpi = prmEmpKpiScoreMapper.queryPrmEmpKpiScoreBySchemeTree(entityMap);

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

				for (PrmEmpKpiScore p : list_hos_kpi) {

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
	public List<Map<String,Object>> queryPrmEmpKpiScoreEmpPrint(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmEmpKpiScore> list = prmEmpKpiScoreMapper.queryPrmEmpKpiScoreEmp(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}

}
