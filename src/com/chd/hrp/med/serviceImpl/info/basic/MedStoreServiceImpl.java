/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedStoreMapper;
import com.chd.hrp.med.entity.MedStore;
import com.chd.hrp.med.service.info.basic.MedStoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08107 库房附属表
 * @Table:
 * MED_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStoreService")
public class MedStoreServiceImpl implements MedStoreService {

	private static Logger logger = Logger.getLogger(MedStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStoreMapper")
	private final MedStoreMapper medStoreMapper = null;
    
	/**
	 * @Description 
	 * 添加08107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		
		//获取对象08107 库房附属表
		int flag = 0;
		flag  = medStoreMapper.queryIsExistsByCode(entityMap);

		if (flag > 0) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medStoreMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStore\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08107 库房附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂时没有该业务
			//medStoreMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStore\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medStoreMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedStore\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08107 库房附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂时没有该业务
		  //medStoreMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedStore\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medStoreMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStore\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08107 库房附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStoreMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStore\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08107 库房附属表
		MedStore medStore = queryByCode(entityMap);

		if (medStore != null) {

			int state = medStoreMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medStoreMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStore\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08107 库房附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) medStoreMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>) medStoreMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			//System.out.println("***** :"+ChdJson.toJson(list, page.getTotal()));
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08107 库房附属表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String,Object> medStroeMap = new HashMap<String,Object>();
		int flag = 0;
		flag  = medStoreMapper.queryIsExistsByCode(entityMap);
		//查看是否在med_store中存在
		if(flag > 0){
			//存在  返回hos_store 和med_store中信息
			medStroeMap  = medStoreMapper.queryByCode(entityMap);
			medStroeMap.put("flag",1);
			
		}else{
			//不存在  则只返回主表信息
			medStroeMap  = medStoreMapper.queryHosStoreByCode(entityMap);
			
			medStroeMap.put("flag",0);
		}
		
		return (T) medStroeMap;
		
	}
	
	/**
	 * @Description 
	 * 获取08107 库房附属表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStore
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreMapper.queryByUniqueness(entityMap);
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String queryMedStoreDict(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String,Object>> list = (List<Map<String,Object>>) medStoreMapper.queryMedStoreDict(entityMap, rowBounds);
		
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
