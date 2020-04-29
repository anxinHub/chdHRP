
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmGoalDetailKpiMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmGoalDetail;
import com.chd.hrp.prm.entity.PrmGoalDetailKpi;
import com.chd.hrp.prm.service.PrmGoalDetailKpiService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0103 目标管理明细指标表
 * @Table:
 * PRM_GOAL_DETAIL_KPI
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmGoalDetailKpiService")
public class PrmGoalDetailKpiServiceImpl implements PrmGoalDetailKpiService {

	private static Logger logger = Logger.getLogger(PrmGoalDetailKpiServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmGoalDetailKpiMapper")
	private final PrmGoalDetailKpiMapper prmGoalDetailKpiMapper = null;
    
	/**
	 * @Description 
	 * 添加0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0103 目标管理明细指标表
		
			PrmGoalDetailKpi prmGoalDetailKpi = queryPrmGoalDetailKpiByCode(entityMap);
				
				if (prmGoalDetailKpi != null) {

					return "{\"error\":\"数据重复,请重新添加.\"}";

				} 
		
				try {
					
					prmGoalDetailKpiMapper.addPrmGoalDetailKpi(entityMap);

					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

				} catch (Exception e) {

					logger.error(e.getMessage(), e);

					return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmGoal\"}";

				} 
		
		
	}
	/**
	 * @Description 
	 * 批量添加0103 目标管理明细指标表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmGoalDetailKpi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmGoalDetailKpiMapper.deleteBatchPrmGoalDetailKpi(entityList);
			
			prmGoalDetailKpiMapper.addBatchPrmGoalDetailKpi(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			
			throw new SysException("{\"error\":\"操作失败 \"} ");
		}
		
	}
	
		/**
	 * @Description 
	 * 更新0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmGoalDetailKpiMapper.updatePrmGoalDetailKpi(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmGoalDetailKpi\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0103 目标管理明细指标表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmGoalDetailKpi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmGoalDetailKpiMapper.updateBatchPrmGoalDetailKpi(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmGoalDetailKpi\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmGoalDetailKpi(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmGoalDetailKpiMapper.deletePrmGoalDetailKpi(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmGoalDetailKpi\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0103 目标管理明细指标表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmGoalDetailKpi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmGoalDetailKpiMapper.deleteBatchPrmGoalDetailKpi(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmGoalDetailKpi\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmGoalDetailKpi(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmGoalDetailKpi> list = prmGoalDetailKpiMapper.queryPrmGoalDetailKpi(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmGoalDetailKpi> list = prmGoalDetailKpiMapper.queryPrmGoalDetailKpi(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmGoalDetailKpi queryPrmGoalDetailKpiByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmGoalDetailKpiMapper.queryPrmGoalDetailKpiByCode(entityMap);
	}
	
}
