
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

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.eqc.dao.xymanage.AssEqPublicConsumInitialMapper;
import com.chd.hrp.eqc.service.xymanage.AssEqPublicConsumInitialService;
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
* 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial Service实现类
*/
@Service("assEqPublicConsumInitialService")
public class AssEqPublicConsumInitialServiceImpl implements AssEqPublicConsumInitialService {

	private static Logger logger = Logger.getLogger(AssEqPublicConsumInitialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assEqPublicConsumInitialMapper")
	private final AssEqPublicConsumInitialMapper assEqPublicConsumInitialMapper = null;
    
	/**
	 * @Description 
	 * 添加15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象15服务公共消耗资源期初表 ASS_EQPublicConsumInitial
		Map<String,Object> assEqPublicConsumInitial = queryByCode(entityMap);

		if (assEqPublicConsumInitial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assEqPublicConsumInitialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqPublicConsumInitialMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assEqPublicConsumInitialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assEqPublicConsumInitialMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assEqPublicConsumInitialMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqPublicConsumInitialMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqPublicConsumInitialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqPublicConsumInitialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象15服务公共消耗资源期初表 ASS_EQPublicConsumInitial<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assEqPublicConsumInitialMapper.queryByCode(entityMap);
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
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				//查询添加数据是否已存在
				String  str = assEqPublicConsumInitialMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					int count = assEqPublicConsumInitialMapper.addBatch(addList);
					
				}else{
					
					return "{\"message\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = assEqPublicConsumInitialMapper.updateBatch(updateList);
			}
			
			return "{\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
	}
	
}
