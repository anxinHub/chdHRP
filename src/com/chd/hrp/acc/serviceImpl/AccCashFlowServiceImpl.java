/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccCashFlowMapper;
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.service.AccCashFlowService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 现金流量标注<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accCashFlowService")
public class AccCashFlowServiceImpl implements AccCashFlowService {

	private static Logger logger = Logger.getLogger(AccCashFlowServiceImpl.class);
	
	@Resource(name = "accCashFlowMapper")
	private final AccCashFlowMapper accCashFlowMapper = null;
	
    
	/**
	 * @Description 
	 * 现金流量标注<BR> 添加AccCashFlow
	 * @param AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccCashFlow(Map<String,Object> entityMap)throws DataAccessException{
		
		AccCashFlow accCashFlow = queryAccCashFlowByCode(entityMap);

		if (accCashFlow != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accCashFlowMapper.addAccCashFlow(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCashFlow\"}";

		}

	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量添加AccCashFlow
	 * @param  AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCashFlow(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accCashFlowMapper.addBatchAccCashFlow(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCashFlow\"}";

		}
	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlow分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCashFlow(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCashFlow> list = accCashFlowMapper.queryAccCashFlow(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlowByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCashFlow queryAccCashFlowByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accCashFlowMapper.queryAccCashFlowByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCashFlow(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accCashFlowMapper.deleteBatchAccCashFlow(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCashFlow\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCashFlow(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accCashFlowMapper.deleteAccCashFlow(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCashFlow\"}";

		}
    }
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCashFlow(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accCashFlowMapper.updateAccCashFlow(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashFlow\"}";

		}
	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCashFlow(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accCashFlowMapper.updateBatchAccCashFlow(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashFlow\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 导入AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCashFlow(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
