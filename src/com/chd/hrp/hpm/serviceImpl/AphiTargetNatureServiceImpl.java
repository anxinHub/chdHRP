
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiTargetNatureMapper;
import com.chd.hrp.hpm.entity.AphiTargetNature;
import com.chd.hrp.hpm.service.AphiTargetNatureService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9902 指标性质字典表
 * @Table:
 * PRM_TARGET_NATURE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("aphiTargetNatureService")
public class AphiTargetNatureServiceImpl implements AphiTargetNatureService {

	private static Logger logger = Logger.getLogger(AphiTargetNatureServiceImpl.class);
	//引入DAO操作
	@Resource(name = "aphiTargetNatureMapper")
	private final AphiTargetNatureMapper aphiTargetNatureMapper = null;
    
	/**
	 * @Description 
	 * 添加9902 指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmTargetNature(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9902 指标性质字典表
		AphiTargetNature prmTargetNature = queryPrmTargetNatureByCode(entityMap);

		if (prmTargetNature != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = aphiTargetNatureMapper.addPrmTargetNature(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmTargetNature\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9902 指标性质字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmTargetNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiTargetNatureMapper.addBatchPrmTargetNature(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmTargetNature\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9902 指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmTargetNature(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = aphiTargetNatureMapper.updatePrmTargetNature(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmTargetNature\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9902 指标性质字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmTargetNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  aphiTargetNatureMapper.updateBatchPrmTargetNature(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmTargetNature\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9902 指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmTargetNature(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = aphiTargetNatureMapper.deletePrmTargetNature(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmTargetNature\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9902 指标性质字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmTargetNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiTargetNatureMapper.deleteBatchPrmTargetNature(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmTargetNature\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9902 指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmTargetNature(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiTargetNature> list = aphiTargetNatureMapper.queryPrmTargetNature(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiTargetNature> list = aphiTargetNatureMapper.queryPrmTargetNature(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9902 指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AphiTargetNature queryPrmTargetNatureByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiTargetNatureMapper.queryPrmTargetNatureByCode(entityMap);
	}
	
}
