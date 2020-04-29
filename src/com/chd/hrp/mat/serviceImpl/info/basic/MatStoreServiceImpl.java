/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.mat.entity.MatStore;
import com.chd.hrp.mat.service.info.basic.MatStoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04107 库房附属表
 * @Table:
 * MAT_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matStoreService")
public class MatStoreServiceImpl implements MatStoreService {

	private static Logger logger = Logger.getLogger(MatStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;
    
	/**
	 * @Description 
	 * 添加04107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		
		//获取对象04107 库房附属表
		int flag = 0;
		flag  = matStoreMapper.queryIsExistsByCode(entityMap);

		if (flag > 0) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matStoreMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStore\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04107 库房附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂时没有该业务
			//matStoreMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStore\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matStoreMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatStore\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04107 库房附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂时没有该业务
		  //matStoreMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatStore\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matStoreMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatStore\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04107 库房附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matStoreMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStore\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04107 库房附属表
		MatStore matStore = queryByCode(entityMap);

		if (matStore != null) {

			int state = matStoreMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matStoreMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatStore\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) matStoreMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) matStoreMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			//System.out.println("***** :"+ChdJson.toJson(list, page.getTotal()));
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04107 库房附属表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String,Object> matStroeMap = new HashMap<String,Object>();
		int flag = 0;
		flag  = matStoreMapper.queryIsExistsByCode(entityMap);
		//查看是否在mat_store中存在
		if(flag > 0){
			//存在  返回hos_store 和mat_store中信息
			matStroeMap  = matStoreMapper.queryByCode(entityMap);
			matStroeMap.put("flag",1);
			
		}else{
			//不存在  则只返回主表信息
			matStroeMap  = matStoreMapper.queryHosStoreByCode(entityMap);
			
			matStroeMap.put("flag",0);
		}
		
		return (T) matStroeMap;
		
	}
	
	/**
	 * @Description 
	 * 获取04107 库房附属表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStore
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matStoreMapper.queryByUniqueness(entityMap);
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String queryMatStoreDict(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String,Object>> list = (List<Map<String,Object>>) matStoreMapper.queryMatStoreDict(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		//System.out.println("***** :"+ChdJson.toJson(list, page.getTotal()));
		return ChdJson.toJson(list, page.getTotal());
	}
	/**
	 * 
	 */
	@Override
	public Map<String, Object> queryIsExistsByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
