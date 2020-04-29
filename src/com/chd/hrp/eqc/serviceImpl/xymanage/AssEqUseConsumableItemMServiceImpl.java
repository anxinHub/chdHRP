
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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.eqc.dao.xymanage.AssEqUseConsumableItemMMapper;
import com.chd.hrp.eqc.service.xymanage.AssEqUseConsumableItemMService;
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
* 23 设备使用消耗资源记录ASS_EQ_Use_Consum_ItemD Service实现类
*/
@Service("assEqUseConsumableItemMService")
public class AssEqUseConsumableItemMServiceImpl implements AssEqUseConsumableItemMService {

	private static Logger logger = Logger.getLogger(AssEqUseConsumableItemMServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assEqUseConsumableItemMMapper")
	private final AssEqUseConsumableItemMMapper assEqUseConsumableItemMMapper = null;
    
	/**
	 * @Description 
	 * 添加14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象14设备使用消耗资源记录 ASS_EQUseConsumableItem
		Map<String,Object> assEqUseConsumableItemM = queryByCode(entityMap);

		if (assEqUseConsumableItemM != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assEqUseConsumableItemMMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqUseConsumableItemMMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assEqUseConsumableItemMMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assEqUseConsumableItemMMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assEqUseConsumableItemMMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqUseConsumableItemMMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqUseConsumableItemMMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqUseConsumableItemMMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象14设备使用消耗资源记录 ASS_EQUseConsumableItem<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assEqUseConsumableItemMMapper.queryByCode(entityMap);
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
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException {
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("-1".equals(item.get("uci_userecorddr"))){ //添加
				
				item.put("uci_userecorddr", UUIDLong.absStringUUID());
				
				addList.add(item) ;
				
			}else{ //修改
				
				
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
					
				int count = assEqUseConsumableItemMMapper.addBatch(addList);
			}
			
			if(updateList.size()>0){
				
				int state = assEqUseConsumableItemMMapper.updateBatch(updateList);
			}
			
			return "{\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
	}
	
}
