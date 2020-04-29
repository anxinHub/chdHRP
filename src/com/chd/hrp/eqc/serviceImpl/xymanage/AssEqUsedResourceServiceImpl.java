
/*
 *
 */
 package com.chd.hrp.eqc.serviceImpl.xymanage;

import java.util.ArrayList;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.eqc.dao.xymanage.AssEqUsedResourceMapper;
import com.chd.hrp.eqc.service.xymanage.AssEqUsedResourceService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 15其他设备月度资源消耗 ASS_EqUsedResource Service实现类
*/
@Service("assEqUsedResourceService")
public class AssEqUsedResourceServiceImpl implements AssEqUsedResourceService {

	private static Logger logger = Logger.getLogger(AssEqUsedResourceServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assEqUsedResourceMapper")
	private final AssEqUsedResourceMapper assEqUsedResourceMapper = null;
    
	/**
	 * @Description 
	 * 添加15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象15其他设备月度资源消耗 ASS_EqUsedResource
		Map<String,Object> assEqUsedResource = queryByCode(entityMap);

		if (assEqUsedResource != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		try{
			
			int state = assEqUsedResourceMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqUsedResourceMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assEqUsedResourceMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assEqUsedResourceMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assEqUsedResourceMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqUsedResourceMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqUsedResourceMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqUsedResourceMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象15其他设备月度资源消耗 ASS_EqUsedResource<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assEqUsedResourceMapper.queryByCode(entityMap);
	}
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException {
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				item.put("add_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
				
				item.put("add_time", DateUtil.getCurrenDate("yyyy-MM-dd HH:mm:ss"));
				
				item.put("add_user", SessionManager.getUserId());
				
				addList.add(item) ;
				
			}else{ //修改
				
				item.put("update_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
				
				item.put("update_time", DateUtil.getCurrenDate("yyyy-MM-dd HH:mm:ss"));
				
				item.put("update_user", SessionManager.getUserId());
				
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				//查询添加数据是否已存在
				String  str = assEqUsedResourceMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					int count = assEqUsedResourceMapper.addBatch(addList);
					
				}else{
					
					return "{\"message\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = assEqUsedResourceMapper.updateBatch(updateList);
			}
			
			return "{\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
