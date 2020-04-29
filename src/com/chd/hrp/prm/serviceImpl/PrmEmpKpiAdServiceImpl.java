﻿
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
import com.chd.hrp.prm.dao.PrmEmpKpiAdMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiAdMapper;
import com.chd.hrp.prm.entity.PrmEmpKpiAd;
import com.chd.hrp.prm.entity.PrmEmpKpiAd;
import com.chd.hrp.prm.service.PrmEmpKpiAdService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0406 职工指标加扣分法参数表
 * @Table:
 * PRM_EMP_KPI_AD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmEmpKpiAdService")
public class PrmEmpKpiAdServiceImpl implements PrmEmpKpiAdService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiAdServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmEmpKpiAdMapper")
	private final PrmEmpKpiAdMapper prmEmpKpiAdMapper = null;
    
	/**
	 * @Description 
	 * 添加0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmEmpKpiAd(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0306 科室指标加扣分法参数表
		PrmEmpKpiAd prmEmpKpiAd = queryPrmEmpKpiAdByCode(entityMap);

		if (prmEmpKpiAd != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmEmpKpiAdMapper.addPrmEmpKpiAd(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiAd\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0306 科室指标加扣分法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmEmpKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiAdMapper.addBatchPrmEmpKpiAd(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpKpiAd\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmEmpKpiAd(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmEmpKpiAdMapper.updatePrmEmpKpiAd(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpiAd\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0306 科室指标加扣分法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmEmpKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmEmpKpiAdMapper.updateBatchPrmEmpKpiAd(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiAd\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmEmpKpiAd(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmEmpKpiAdMapper.deletePrmEmpKpiAd(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpiAd\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0306 科室指标加扣分法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmEmpKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiAdMapper.deleteBatchPrmEmpKpiAd(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpKpiAd\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmEmpKpiAd(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpKpiAd> list = prmEmpKpiAdMapper.queryPrmEmpKpiAd(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpKpiAd> list = prmEmpKpiAdMapper.queryPrmEmpKpiAd(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmEmpKpiAd queryPrmEmpKpiAdByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpKpiAdMapper.queryPrmEmpKpiAdByCode(entityMap);
	}
	
}