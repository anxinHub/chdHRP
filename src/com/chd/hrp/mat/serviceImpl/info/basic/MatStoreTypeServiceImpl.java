/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.info.basic.MatStoreTypeMapper;
import com.chd.hrp.mat.entity.MatStoreType;
import com.chd.hrp.mat.service.info.basic.MatStoreTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04111 仓库类别信息
 * @Table:
 * MAT_STORE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matStoreTypeService")
public class MatStoreTypeServiceImpl implements MatStoreTypeService {

	private static Logger logger = Logger.getLogger(MatStoreTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matStoreTypeMapper")
	private final MatStoreTypeMapper matStoreTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatStoreType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04111 仓库类别信息
		MatStoreType matStoreType = queryMatStoreTypeByCode(entityMap);

		if (matStoreType != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matStoreTypeMapper.addMatStoreType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStoreType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04111 仓库类别信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matStoreTypeMapper.addBatchMatStoreType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStoreType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatStoreType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matStoreTypeMapper.updateMatStoreType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatStoreType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04111 仓库类别信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matStoreTypeMapper.updateBatchMatStoreType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatStoreType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatStoreType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matStoreTypeMapper.deleteMatStoreType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatStoreType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04111 仓库类别信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matStoreTypeMapper.deleteBatchMatStoreType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStoreType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatStoreType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04111 仓库类别信息
		MatStoreType matStoreType = queryMatStoreTypeByCode(entityMap);

		if (matStoreType != null) {

			int state = matStoreTypeMapper.updateMatStoreType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matStoreTypeMapper.addMatStoreType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatStoreType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatStoreType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatStoreType> list = matStoreTypeMapper.queryMatStoreType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatStoreType> list = matStoreTypeMapper.queryMatStoreType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04111 仓库类别信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatStoreType queryMatStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matStoreTypeMapper.queryMatStoreTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04111 仓库类别信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStoreType
	 * @throws DataAccessException
	*/
	@Override
	public MatStoreType queryMatStoreTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matStoreTypeMapper.queryMatStoreTypeByUniqueness(entityMap);
	}
	/**
	 * 查询出物资类别字典表 MAT_TYPE中IS_STOP=0的所有物资类别记录
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreType(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matStoreTypeMapper.queryStoreType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matStoreTypeMapper.queryStoreType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 根据仓库ID 查询是否存在 对应对应关系数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryExist(Map<String, Object> mapVo) throws DataAccessException{
		return matStoreTypeMapper.queryExist(mapVo);
	}
	
	
	/**
	 * 查询出物资类别字典表 MAT_TYPE中IS_STOP=0的所有物资类别记录
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatTypeByStore(Map<String, Object> entityMap) throws DataAccessException{

			
			List<Map<String,Object>> list = matStoreTypeMapper.queryMatTypeByStore(entityMap);
			
			return ChdJson.toJson(list);
			
		
	}
	
	
	/**
	 * @Description 
	 * 批量添加04110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatTypeByStore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		try 
		{
			matStoreTypeMapper.deleteBatchMatTypeByStore(entityList);
			//public int deleteBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
			
			
			matStoreTypeMapper.addBatchMatTypeByStore(entityList);
			//public int addBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
			
		
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStoreInv\"}";
		}
		
	}
	
	
}
