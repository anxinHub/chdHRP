
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
import com.chd.hrp.prm.dao.PrmHosKpiSectionMapper;
import com.chd.hrp.prm.entity.PrmHosKpiSection;
import com.chd.hrp.prm.service.PrmHosKpiSectionService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0205 指标区间法参数表
 * @Table:
 * PRM_HOS_KPI_SECTION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosKpiSectionService")
public class PrmHosKpiSectionServiceImpl implements PrmHosKpiSectionService {

	private static Logger logger = Logger.getLogger(PrmHosKpiSectionServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosKpiSectionMapper")
	private final PrmHosKpiSectionMapper prmHosKpiSectionMapper = null;
    
	/**
	 * @Description 
	 * 添加0205 指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmHosKpiSection(Map<String,Object> entityMap)throws DataAccessException{
		

		try {
			
			List<Map<String, Object>> dataUpdatedBatch = (List<Map<String, Object>>) entityMap.get("dataUpdatedBatch");
			
			List<Map<String, Object>> dataDeletedBatch = (List<Map<String, Object>>) entityMap.get("dataDeletedBatch");
			
			List<Map<String, Object>> dataAddedBatch = (List<Map<String, Object>>) entityMap.get("dataAddedBatch");
			
			prmHosKpiSectionMapper.addBatchPrmHosKpiSection(dataAddedBatch);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiSection\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0205 指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmHosKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			
			prmHosKpiSectionMapper.addBatchPrmHosKpiSection(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiSection\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0205 指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmHosKpiSection(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmHosKpiSectionMapper.updatePrmHosKpiSection(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiSection\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0205 指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmHosKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			
		  prmHosKpiSectionMapper.updateBatchPrmHosKpiSection(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiSection\"}";

		}	
		
	}
	
	@Override
	public String updateBatchPrmHosKpiSectionSection(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  prmHosKpiSectionMapper.updateBatchPrmHosKpiSectionSection(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiSectionSection\"}";

			}	
	}
	/**
	 * @Description 
	 * 删除0205 指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmHosKpiSection(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmHosKpiSectionMapper.deletePrmHosKpiSection(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiSection\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0205 指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmHosKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiSectionMapper.deleteBatchPrmHosKpiSection(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiSection\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0205 指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpiSection(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpiSection> list = prmHosKpiSectionMapper.queryPrmHosKpiSection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpiSection> list = prmHosKpiSectionMapper.queryPrmHosKpiSection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0205 指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpiSection queryPrmHosKpiSectionByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiSectionMapper.queryPrmHosKpiSectionByCode(entityMap);
	}

	
}
