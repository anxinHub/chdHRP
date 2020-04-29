
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
import com.chd.hrp.prm.dao.PrmDeptKpiAdMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiAd;
import com.chd.hrp.prm.service.PrmDeptKpiAdService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0306 科室指标加扣分法参数表
 * @Table:
 * PRM_DEPT_KPI_AD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptKpiAdService")
public class PrmDeptKpiAdServiceImpl implements PrmDeptKpiAdService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiAdServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptKpiAdMapper")
	private final PrmDeptKpiAdMapper prmDeptKpiAdMapper = null;
    
	/**
	 * @Description 
	 * 添加0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmDeptKpiAd(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0306 科室指标加扣分法参数表
		PrmDeptKpiAd prmDeptKpiAd = queryPrmDeptKpiAdByCode(entityMap);

		if (prmDeptKpiAd != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmDeptKpiAdMapper.addPrmDeptKpiAd(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKpiAd\"}";

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
	public String addBatchPrmDeptKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiAdMapper.addBatchPrmDeptKpiAd(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiAd\"}";

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
	public String updatePrmDeptKpiAd(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptKpiAdMapper.updatePrmDeptKpiAd(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiAd\"}";

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
	public String updateBatchPrmDeptKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptKpiAdMapper.updateBatchPrmDeptKpiAd(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiAd\"}";

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
    public String deletePrmDeptKpiAd(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptKpiAdMapper.deletePrmDeptKpiAd(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiAd\"}";

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
	public String deleteBatchPrmDeptKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiAdMapper.deleteBatchPrmDeptKpiAd(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiAd\"}";

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
	public String queryPrmDeptKpiAd(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiAd> list = prmDeptKpiAdMapper.queryPrmDeptKpiAd(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKpiAd> list = prmDeptKpiAdMapper.queryPrmDeptKpiAd(entityMap, rowBounds);
			
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
	public PrmDeptKpiAd queryPrmDeptKpiAdByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKpiAdMapper.queryPrmDeptKpiAdByCode(entityMap);
	}
	
}
