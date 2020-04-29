/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayModiMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWagePayModi;
import com.chd.hrp.acc.service.wagedata.AccWagePayModiService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资调整<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWagePayModiService")
public class AccWagePayModiServiceImpl implements AccWagePayModiService {

	private static Logger logger = Logger.getLogger(AccWagePayModiServiceImpl.class);
	
	@Resource(name = "accWagePayModiMapper")
	private final AccWagePayModiMapper accWagePayModiMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	/**
	 * @Description 
	 * 工资调整<BR> 添加AccWagePayModi
	 * @param AccWagePayModi entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWagePayModi(Map<String, Object> entityMap) throws DataAccessException {

		AccWageItems accWageItem = accWageItemsMapper.queryAccWageItemsByCode(entityMap);

		if (!"".equals(entityMap.get("money")) && entityMap.get("money") != null) {

			if ("1".equals(entityMap.get("flag"))) {

				String sql = accWageItem.getColumn_item() + "=" + accWageItem.getColumn_item() + "+"
						+ entityMap.get("money");

				entityMap.put("item", accWageItem.getColumn_item());

				entityMap.put("sqlValue", sql);

			} else {

				String sql = accWageItem.getColumn_item() + "=" + accWageItem.getColumn_item() + "-"
						+ entityMap.get("money");

				entityMap.put("item", accWageItem.getColumn_item());

				entityMap.put("sqlValue", sql);

			}

		} else {

			if ("1".equals(entityMap.get("flag"))) {

				String sql = accWageItem.getColumn_item() + "=" + accWageItem.getColumn_item() + "*"
						+ entityMap.get("rate");

				entityMap.put("item", accWageItem.getColumn_item());

				entityMap.put("sqlValue", sql);

			} else {

				String sql = accWageItem.getColumn_item() + "=" + accWageItem.getColumn_item() + "/"
						+ entityMap.get("rate");

				entityMap.put("item", accWageItem.getColumn_item());

				entityMap.put("sqlValue", sql);

			}

		}

		try {

			accWagePayModiMapper.addAccWagePayModi(entityMap);

			accWagePayMapper.updateAccWagePay(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 调整库异常 请联系管理员! 错误编码 addAccWagePayModi\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量添加AccWagePayModi
	 * @param  AccWagePayModi entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWagePayModi(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWagePayModiMapper.addBatchAccWagePayModi(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 调整库异常 请联系管理员! 错误编码 addBatchAccWagePayModi\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModi分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWagePayModi(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWagePayModi> list = accWagePayModiMapper.queryAccWagePayModi(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWagePayModi> list = accWagePayModiMapper.queryAccWagePayModi(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModiByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePayModi queryAccWagePayModiByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWagePayModiMapper.queryAccWagePayModiByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量删除AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWagePayModi(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWagePayModiMapper.deleteBatchAccWagePayModi(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 调整库异常 请联系管理员! 错误编码  deleteBatchAccWagePayModi\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 删除AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWagePayModi(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWagePayModiMapper.deleteAccWagePayModi(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 调整库异常 请联系管理员! 错误编码  deleteAccWagePayModi\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资调整<BR> 更新AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWagePayModiMapper.updateAccWagePayModi(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 调整库异常 请联系管理员! 错误编码  updateAccWagePayModi\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量更新AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWagePayModi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWagePayModiMapper.updateBatchAccWagePayModi(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 调整库异常 请联系管理员! 错误编码  updateAccWagePayModi\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资调整<BR> 导入AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 调整库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}


}
