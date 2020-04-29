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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageTaxMapper;
import com.chd.hrp.acc.entity.AccWageTax;
import com.chd.hrp.acc.service.wagedata.AccWageTaxService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套合并日志<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageTaxService")
public class AccWageTaxServiceImpl implements AccWageTaxService {

	private static Logger logger = Logger.getLogger(AccWageTaxServiceImpl.class);
	
	@Resource(name = "accWageTaxMapper")
	private final AccWageTaxMapper accWageTaxMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	/**
	 * @Description 
	 * 工资套合并日志<BR> 添加AccWageTax
	 * @param AccWageTax entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageTax(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWageTax accWageTax = queryAccWageTaxByCode(entityMap);

		if (accWageTax != null) {

			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";

		}
		
		try {
			
			accWageTaxMapper.addAccWageTax(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageTax\"}";

		}

	}
	
	/**
	 * @Description 
	 * <BR> 批量添加AccWageTax
	 * @param  AccWageTax entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageTax(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageTaxMapper.deleteBatchAccWageTax(entityList);
			
			accWageTaxMapper.addBatchAccWageTax(entityList);
			
			List<AccWageTax> accWageTaxList=queryAccWageTaxEnds(entityList.get(0));
			
			if(accWageTaxList.size()>0&& "0".equals(accWageTaxList.get(0))){
				
				return "{\"msg\":\"添加失败,终点数已是正无穷大.\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageTax分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageTax(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageTax> list = accWageTaxMapper.queryAccWageTax(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageTax> list = accWageTaxMapper.queryAccWageTax(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageTaxByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageTax queryAccWageTaxByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageTaxMapper.queryAccWageTaxByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量删除AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageTax(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageTaxMapper.deleteBatchAccWageTax(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageTax\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 删除AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageTax(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageTaxMapper.deleteAccWageTax(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageTax\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 更新AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageTax(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageTaxMapper.updateAccWageTax(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageTax\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量更新AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageTax(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageTaxMapper.updateBatchAccWageTax(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageTax\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 导入AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageTax(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public List<AccWageTax> queryAccWageTaxEnds(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accWageTaxMapper.queryAccWageTaxEnds(entityMap);
	}


}
