/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgFunTypeMapper;
import com.chd.hrp.budg.entity.BudgFunType;
import com.chd.hrp.budg.service.common.BudgFunTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 函数分类表
 * @Table:
 * FUN_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("funTypeService")
public class BudgFunTypeServiceImpl implements BudgFunTypeService {

	private static Logger logger = Logger.getLogger(BudgFunTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFunTypeMapper")
	private final BudgFunTypeMapper budgFunTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		//获取对象函数分类表
		BudgFunType funType = budgFunTypeMapper.queryByCode(entityMap);
		String str = "";
		if (funType != null) {

			str += "分类编码:"+entityMap.get("type_code")+";";

		}
		
		int count  = budgFunTypeMapper.queryNameExist(entityMap);
		
		if(count > 0 ){
			str += "分类名称:"+entityMap.get("type_name")+";";
		}
		
		if( str != ""){
			return "{\"error\":\""+str+"已被占用,请重新添加.\"}";
		}
		
		try {
			
			int state = budgFunTypeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addFunType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加函数分类表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunTypeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchFunType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			int count  = budgFunTypeMapper.queryNameExist(entityMap);
			
			if(count > 0 ){
				return "{\"error\":\"分类名称已被占用,请重新填写.\"}";
			}
			
			int state = budgFunTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateFunType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新函数分类表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFunTypeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchFunType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFunTypeMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteFunType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除函数分类表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunTypeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchFunType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象函数分类表
		BudgFunType funType = budgFunTypeMapper.queryByCode(entityMap);

		if (funType != null) {

			int state = budgFunTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFunTypeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateFunType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFunType> list = (List<BudgFunType>) budgFunTypeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFunType> list = (List<BudgFunType>) budgFunTypeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象函数分类表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunTypeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取函数分类表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return FunType
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunTypeMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取函数分类表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFunType>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunTypeMapper.queryExists(entityMap);
	}
	@Override
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgFunTypeMapper.queryNameExist(mapVo);
	}
}
