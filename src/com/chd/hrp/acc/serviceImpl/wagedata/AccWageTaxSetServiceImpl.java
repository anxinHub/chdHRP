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
import com.chd.hrp.acc.dao.wagedata.AccWageTaxSetMapper;
import com.chd.hrp.acc.entity.AccWageTaxSet;
import com.chd.hrp.acc.service.wagedata.AccWageTaxSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套合并日志<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageTaxSetService")
public class AccWageTaxSetServiceImpl implements AccWageTaxSetService {

	private static Logger logger = Logger.getLogger(AccWageTaxSetServiceImpl.class);
	
	@Resource(name = "accWageTaxSetMapper")
	private final AccWageTaxSetMapper accWageTaxSetMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	/**
	 * @Description 
	 * 工资套合并日志<BR> 添加AccWageTaxSet
	 * @param AccWageTaxSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException{
		
		/*AccWageTaxSet accWageTaxSet = queryAccWageTaxSetByCode(entityMap);

		if (accWageTaxSet != null) {

			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";

		}*/
		
		try {
			
			accWageTaxSetMapper.deleteAccWageTaxSet(entityMap);
			
			accWageTaxSetMapper.addAccWageTaxSet(entityMap);
			
			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"设置失败 数据库异常 请联系管理员! 错误编码 addAccWageTaxSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量添加AccWageTaxSet
	 * @param  AccWageTaxSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageTaxSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageTaxSetMapper.addBatchAccWageTaxSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageTaxSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageTaxSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageTaxSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageTaxSet> list = accWageTaxSetMapper.queryAccWageTaxSet(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageTaxSet> list = accWageTaxSetMapper.queryAccWageTaxSet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageTaxSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageTaxSet queryAccWageTaxSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageTaxSetMapper.queryAccWageTaxSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量删除AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageTaxSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageTaxSetMapper.deleteBatchAccWageTaxSet(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageTaxSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 删除AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageTaxSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageTaxSetMapper.deleteAccWageTaxSet(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageTaxSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 更新AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageTaxSetMapper.updateAccWageTaxSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageTaxSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量更新AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageTaxSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageTaxSetMapper.updateBatchAccWageTaxSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageTaxSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 导入AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}


}
