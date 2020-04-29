/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;
 
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.service.AccWageItemsService;

/**
* @Title. @Description.
* 工资项目表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageItemsService")
public class AccWageItemsServiceImpl implements AccWageItemsService {

	private static Logger logger = Logger.getLogger(AccWageItemsServiceImpl.class);
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	/**
	 * @Description 
	 * 工资项目表<BR> 添加AccWageItems
	 * @param AccWageItems entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String addAccWageItems(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWageItems accWageItems = queryAccWageItemsByCode(entityMap);
		
		//AccWageItems sort_code= querySortcodeByWageCode(entityMap);
	
		if (accWageItems != null) {

			//return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
			
			accWageItemsMapper.updateAccWageItems(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		
		try {
			
			/*if(sort_code != null){
				
				sort_code+=10;
				
			}else{
				
				sort_code=10;
				
			}
			
			entityMap.put("sort_code", sort_code);*/
			accWageItemsMapper.addAccWageItems(entityMap);
			//添加一条工资项目，需要动态向工资发放表中添加一个字段
			accWageItemsMapper.queryAccWageItemsByAlter(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageItems\"}");
		}

	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量添加AccWageItems
	 * @param  AccWageItems entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageItems(List<Map<String,Object>> entityList)throws DataAccessException{
		//根据wagecode查询出当前工资套中的最大排序号
		AccWageItems accWageItems= querySortcodeByWageCode(entityList.get(0));
		
		String sql ="";
		
		Integer sort_code= 0;
		
		try {
			
			//accWageItemsMapper.addBatchAccWageItems(entityList);
			
			for (int i = 0; i < entityList.size(); i++) {
				
				if(accWageItems != null){
					
					sort_code+=10;
					
				}else{
					
					sort_code=10;
					
				}
				
				entityList.get(i).put("sort_code", sort_code);
				sql+=addAccWageItems(entityList.get(i));
				
			}
			
			if(sql.indexOf("error")>0){
				
				return "{\"msg\":\"维护失败.\",\"state\":\"false\"}";
				
			}
			
				return "{\"msg\":\"维护成功.\",\"state\":\"true\"}";
				
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"维护失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageItems\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItems分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageItems(Map<String,Object> entityMap) throws DataAccessException{
 
			List<AccWageItems> list = accWageItemsMapper.queryAccWageItems(entityMap);
			
			return ChdJson.toJson(list);
	 	
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItemsByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageItems queryAccWageItemsByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageItemsMapper.queryAccWageItemsByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItemsByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccWageItems  querySortcodeByWageCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageItemsMapper.querySortcodeByWageCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量删除AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageItems(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageItemsMapper.deleteBatchAccWageItems(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageItems\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 删除AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageItems(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accWageItemsMapper.deleteAccWageItems(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageItems\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资项目表<BR> 更新AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageItems(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageItemsMapper.updateAccWageItems(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageItems\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量更新AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageItems(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageItemsMapper.updateBatchAccWageItems(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageItems\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资项目表<BR> 导入AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageItems(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccWageItems(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			
		   int count=accWageItemsMapper.extendAccWageItems(entityMap);
			
			if(count==0){
				
				return "{\"msg\":\""+(Integer.parseInt(entityMap.get("acc_year").toString())-1)+"年没有可以继承的工资项目.\",\"state\":\"false\"}";
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 extendAccWageItems\"}";

		}
	}
	
	public String updateSortcodeByItemcode(List<Map<String, Object>> listVo) {
		 
		try {

			accWageItemsMapper.updateSortcodeByItemcode(listVo);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}" ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"序列保存失败! 错误编码 updateSortcodeByItemcode\"}" ;
		}
		 
	 
		
	}

	public String updateoraddByItemcode(Map<String, Object> mapData) {
		AccWageItems accWageItems=accWageItemsMapper.queryAccWageItemsByItemcode(mapData);
		if(accWageItems!=null){
			try {
				int a =accWageItemsMapper.updateByItemcode(mapData);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}" ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			return "{\"msg\":\"修改失败.\",\"state\":\"true\"}" ;	 
			}
			
		}else{
			
			Integer sort_code=0;
			
			AccWageItems num=querySortcodeByWageCode(mapData);
			
			if(num != null){
					
				sort_code=Integer.parseInt(querySortcodeByWageCode(mapData).getSort_code())+10;
				
			}else{
				
				sort_code=10;
			}
			
			mapData.put("sort_code", sort_code);
			accWageItemsMapper.addAccWageItems(mapData);
			accWageItemsMapper.queryAccWageItemsByAlter(mapData);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}" ;
		}
		 
	}

	@Override
	public List<Map<String, Object>> queryAccWageItemsPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
