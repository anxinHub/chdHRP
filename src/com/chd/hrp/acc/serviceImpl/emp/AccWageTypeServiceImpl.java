/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.emp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.emp.AccWageTypeMapper;
import com.chd.hrp.acc.entity.AccWageType;
import com.chd.hrp.acc.service.emp.AccWageTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 账户类别<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageTypeService")
public class AccWageTypeServiceImpl implements AccWageTypeService {

	private static Logger logger = Logger.getLogger(AccWageTypeServiceImpl.class);
	
	@Resource(name = "accWageTypeMapper")
	private final AccWageTypeMapper accWageTypeMapper = null;
    
	/**
	 * @Description 
	 * 账户类别<BR> 添加AccWageType
	 * @param AccWageType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageType(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWageType accWageType = queryAccWageTypeByCode(entityMap);

		if (accWageType != null) {

			return "{\"error\":\"编码：" + entityMap.get("type_id").toString() + "重复.\"}";

		}
		
		try {
			
			accWageTypeMapper.addAccWageType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量添加AccWageType
	 * @param  AccWageType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageTypeMapper.addBatchAccWageType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageType> list = accWageTypeMapper.queryAccWageType(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageType> list = accWageTypeMapper.queryAccWageType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageType queryAccWageTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageTypeMapper.queryAccWageTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量删除AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageTypeMapper.deleteBatchAccWageType(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 账户类别<BR> 删除AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageTypeMapper.deleteAccWageType(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 账户类别<BR> 更新AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageTypeMapper.updateAccWageType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量更新AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageTypeMapper.updateBatchAccWageType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageType\"}";

		}
		
	}

	@Override
	public AccWageType queryAccWageTypeByUpdate(Map<String, Object> entityMap)
			throws DataAccessException {

		return accWageTypeMapper.queryAccWageTypeByUpdate(entityMap);
	}
	/**
	 * 打印
	 */
	@Override
	public List<Map<String,Object>> queryAccWageTypePrint(Map<String,Object> entityMap) throws DataAccessException{
		
			entityMap.put("group_id", SessionManager.getGroupId());
			       
			entityMap.put("hos_id", SessionManager.getHosId());
		        
			entityMap.put("copy_code", SessionManager.getCopyCode());
				
			List<Map<String,Object>> list = accWageTypeMapper.queryAccWageTypePrint(entityMap);
			
			return list;
		
	}
	
}
