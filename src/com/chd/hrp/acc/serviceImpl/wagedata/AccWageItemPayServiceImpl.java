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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageItemPayMapper;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.service.wagedata.AccWageItemPayService;
import com.github.pagehelper.PageInfo;
 
/**
* @Title. @Description.
* 工资数据<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageItemPayService")
public class AccWageItemPayServiceImpl implements AccWageItemPayService {

	private static Logger logger = Logger.getLogger(AccWageItemPayServiceImpl.class);
	
	@Resource(name = "accWageItemPayMapper")
	private final AccWageItemPayMapper accWageItemPayMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	/**
	 * @Description 
	 * 工资数据<BR> 添加AccWageItemPay
	 * @param AccWageItemPay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWagePay accWageItemPay = queryAccWageItemPayByCode(entityMap);

		if (accWageItemPay != null) {

			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";

		}
		
		try {
			
			accWageItemPayMapper.addAccWageItemPay(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageItemPay\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量添加AccWageItemPay
	 * @param  AccWageItemPay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageItemPay(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageItemPayMapper.addBatchAccWageItemPay(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageItemPay\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemPay分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageItemPay(Map<String,Object> entityMap) throws DataAccessException{
		
		if("0".equals(entityMap.get("query_para"))){
			
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage)entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<Map<String,Object>> list = accWageItemPayMapper.queryAccWageItemPay(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = accWageItemPayMapper.queryAccWageItemPay(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list,page.getTotal());
			}
			
		}else{
			
			List<Map<String,Object>> list = accWageItemPayMapper.queryAccWageItemPaySum(entityMap);
			
			return ChdJson.toJson(list);
		}
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemPayByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePay queryAccWageItemPayByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageItemPayMapper.queryAccWageItemPayByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量删除AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageItemPay(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageItemPayMapper.deleteBatchAccWageItemPay(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageItemPay\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 删除AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageItemPay(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageItemPayMapper.deleteAccWageItemPay(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageItemPay\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageItemPayMapper.updateAccWageItemPay(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageItemPay\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量更新AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageItemPay(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageItemPayMapper.updateBatchAccWageItemPay(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageItemPay\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资数据<BR> 导入AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	@Override
	public List<Map<String,Object>> queryAccWageItemPayPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
	       
		entityMap.put("hos_id", SessionManager.getHosId());
        
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		if(entityMap.get("emp_code") != null){
			//用来转换大写
			entityMap.put("emp_code",entityMap.get("emp_code").toString().toUpperCase());
		}
		
		if("0".equals(entityMap.get("query_para"))){
			
			List<Map<String,Object>> list = accWageItemPayMapper.queryAccWageItemPay(entityMap);
				
			return list;
		}else{
			
			List<Map<String,Object>> list = accWageItemPayMapper.queryAccWageItemPaySum(entityMap);
			
			return list;
		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccBankNetWage(Map<String,Object> entityMap) throws DataAccessException{
		
		accWageItemPayMapper.queryAccBankNetWage(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("objdata"));
			
	}

}
