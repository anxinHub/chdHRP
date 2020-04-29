
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
import com.chd.hrp.prm.dao.PrmHosKpiAdMapper;
import com.chd.hrp.prm.entity.PrmHosKpiAd;
import com.chd.hrp.prm.service.PrmHosKpiAdService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0206 指标加扣分法参数表
 * @Table:
 * PRM_HOS_KPI_AD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosKpiAdService")
public class PrmHosKpiAdServiceImpl implements PrmHosKpiAdService {

	private static Logger logger = Logger.getLogger(PrmHosKpiAdServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosKpiAdMapper")
	private final PrmHosKpiAdMapper prmHosKpiAdMapper = null;
    
	/**
	 * @Description 
	 * 添加0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmHosKpiAd(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0206 指标加扣分法参数表
		PrmHosKpiAd prmHosKpiAd = queryPrmHosKpiAdByCode(entityMap);

		if (prmHosKpiAd != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmHosKpiAdMapper.addPrmHosKpiAd(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiAd\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0206 指标加扣分法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmHosKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiAdMapper.addBatchPrmHosKpiAd(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiAd\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmHosKpiAd(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmHosKpiAdMapper.updatePrmHosKpiAd(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiAd\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0206 指标加扣分法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmHosKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmHosKpiAdMapper.updateBatchPrmHosKpiAd(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiAd\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmHosKpiAd(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmHosKpiAdMapper.deletePrmHosKpiAd(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiAd\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0206 指标加扣分法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmHosKpiAd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiAdMapper.deleteBatchPrmHosKpiAd(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiAd\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpiAd(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpiAd> list = prmHosKpiAdMapper.queryPrmHosKpiAd(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpiAd> list = prmHosKpiAdMapper.queryPrmHosKpiAd(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpiAd queryPrmHosKpiAdByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiAdMapper.queryPrmHosKpiAdByCode(entityMap);
	}
	
}
