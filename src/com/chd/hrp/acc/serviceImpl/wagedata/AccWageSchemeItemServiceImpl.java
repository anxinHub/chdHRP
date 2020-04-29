/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
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
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeItemMapper;
import com.chd.hrp.acc.entity.AccWageSchemeItem;
import com.chd.hrp.acc.service.wagedata.AccWageSchemeItemService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资方案项目<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageSchemeItemService")
public class AccWageSchemeItemServiceImpl implements AccWageSchemeItemService {

	private static Logger logger = Logger.getLogger(AccWageSchemeItemServiceImpl.class);
	
	@Resource(name = "accWageSchemeItemMapper")
	private final AccWageSchemeItemMapper accWageSchemeItemMapper = null;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 添加AccWageSchemeItem
	 * @param AccWageSchemeItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWageSchemeItem AccWageSchemeItem = queryAccWageSchemeItemByCode(entityMap);

		if (AccWageSchemeItem != null) {

			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";

		}
		
		try {
			
			accWageSchemeItemMapper.addAccWageSchemeItem(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageSchemeItem\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量添加AccWageSchemeItem
	 * @param  AccWageSchemeItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageSchemeItem(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageSchemeItemMapper.addBatchAccWageSchemeItem(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageSchemeItem\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageSchemeItem(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageSchemeItem> list = accWageSchemeItemMapper.queryAccWageSchemeItem(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageSchemeItem> list = accWageSchemeItemMapper.queryAccWageSchemeItem(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageSchemeItem queryAccWageSchemeItemByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageSchemeItemMapper.queryAccWageSchemeItemByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量删除AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageSchemeItem(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageSchemeItemMapper.deleteBatchAccWageSchemeItem(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageSchemeItem\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 删除AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageSchemeItem(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageSchemeItemMapper.deleteAccWageSchemeItem(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageSchemeItem\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 更新AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageSchemeItemMapper.updateAccWageSchemeItem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageSchemeItem\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量更新AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageSchemeItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageSchemeItemMapper.updateBatchAccWageSchemeItem(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageSchemeItem\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 导入AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

}