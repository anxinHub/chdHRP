
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmKpiDimMapper;
import com.chd.hrp.prm.entity.PrmKpiDim;
import com.chd.hrp.prm.service.PrmKpiDimService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0501 KPI指标维度表
 * @Table:
 * PRM_KPI_DIM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmKpiDimService")
public class PrmKpiDimServiceImpl implements PrmKpiDimService {

	private static Logger logger = Logger.getLogger(PrmKpiDimServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmKpiDimMapper")
	private final PrmKpiDimMapper prmKpiDimMapper = null;
    
	/**
	 * @Description 
	 * 添加0501 KPI指标维度表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmKpiDim(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0501 KPI指标维度表
		PrmKpiDim prmKpiDim = queryPrmKpiDimByCode(entityMap);

		if (prmKpiDim != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmKpiDimMapper.addPrmKpiDim(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmKpiDim\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0501 KPI指标维度表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmKpiDim(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmKpiDimMapper.addBatchPrmKpiDim(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmKpiDim\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0501 KPI指标维度表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmKpiDim(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmKpiDimMapper.updatePrmKpiDim(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmKpiDim\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0501 KPI指标维度表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmKpiDim(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmKpiDimMapper.updateBatchPrmKpiDim(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmKpiDim\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0501 KPI指标维度表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmKpiDim(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmKpiDimMapper.deletePrmKpiDim(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmKpiDim\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0501 KPI指标维度表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmKpiDim(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmKpiDimMapper.deleteBatchPrmKpiDim(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmKpiDim\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0501 KPI指标维度表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmKpiDim(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmKpiDim> list = prmKpiDimMapper.queryPrmKpiDim(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmKpiDim> list = prmKpiDimMapper.queryPrmKpiDim(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0501 KPI指标维度表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmKpiDim queryPrmKpiDimByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmKpiDimMapper.queryPrmKpiDimByCode(entityMap);
	}
	
	
	@Override
	public List<Map<String, Object>> queryPrmKpiDimPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmKpiDim> list = prmKpiDimMapper.queryPrmKpiDim(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
	
}
