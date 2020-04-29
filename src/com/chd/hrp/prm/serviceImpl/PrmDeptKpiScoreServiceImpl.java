
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.text.DecimalFormat;
import java.util.*;

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
import com.chd.hrp.prm.dao.PrmDeptKpiScoreMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiScore;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmDeptKpiScoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0309 科室KPI指标考评计算表
 * @Table:
 * PRM_DEPT_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptKpiScoreService")
public class PrmDeptKpiScoreServiceImpl implements PrmDeptKpiScoreService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiScoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptKpiScoreMapper")
	private final PrmDeptKpiScoreMapper prmDeptKpiScoreMapper = null;
	
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
	public String addPrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0309 科室KPI指标考评计算表
		PrmDeptKpiScore prmDeptKpiScore = queryPrmDeptKpiScoreByCode(entityMap);

		if (prmDeptKpiScore != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmDeptKpiScoreMapper.addPrmDeptKpiScore(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKpiScore\"}";

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
	public String addBatchPrmDeptKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiScoreMapper.addBatchPrmDeptKpiScore(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiScore\"}";

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
	public String updatePrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptKpiScoreMapper.updatePrmDeptKpiScore(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiScore\"}";

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
	public String updateBatchPrmDeptKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptKpiScoreMapper.updateBatchPrmDeptKpiScore(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiScore\"}";

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
    public String deletePrmDeptKpiScore(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptKpiScoreMapper.deletePrmDeptKpiScore(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiScore\"}";

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
	public String deleteBatchPrmDeptKpiScore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiScoreMapper.deleteBatchPrmDeptKpiScore(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiScore\"}";

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
	public String queryPrmDeptKpiScore(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScore(entityMap, rowBounds);
			
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
	public PrmDeptKpiScore queryPrmDeptKpiScoreByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKpiScoreMapper.queryPrmDeptKpiScoreByCode(entityMap);
	}
	@Override
	public String queryPrmDeptKpiScoreDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
           SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScoreDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScoreDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String auditPrmDeptKpiScore(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = 	prmDeptKpiScoreMapper.auditPrmDeptKpiScore(entityMap);
				
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
	public String reAuditPrmDeptKpiScore(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			 
			
			  int state = 	prmDeptKpiScoreMapper.auditPrmDeptKpiScore(entityMap);
				
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
	public String collectDeptKpiScore(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		prmDeptKpiScoreMapper.collectDeptKpiScore(entityMap);
		
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
	public String queryPrmDeptKpiScoreByScheme(Map<String, Object> entityMap) throws DataAccessException {
		   SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScoreByScheme(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScoreByScheme(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	@Override
	public String queryPrmDeptKpiScoreBySchemeTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder json = new StringBuilder();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		try {
			entityMap.put("user_id", SessionManager.getUserId());
			
			entityMap.put("mod_code", SessionManager.getModCode());
			
			entityMap.put("prem_data", "true");
			List<PrmDeptKpiScore> list_hos_kpi = prmDeptKpiScoreMapper.queryPrmDeptKpiScoreBySchemeTree(entityMap);

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

				for (PrmDeptKpiScore p : list_hos_kpi) {

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
	public List<Map<String, Object>> queryPrmDeptKpiScoreDeptPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmDeptKpiScore> list = prmDeptKpiScoreMapper.queryPrmDeptKpiScoreDept(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
}
