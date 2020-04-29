/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccPeopleWageItemMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.service.wagedata.AccPeopleWageItemService;
import com.github.pagehelper.PageInfo;
 
/**
* @Title. @Description.
* 个人工资查询<BR>  
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accPeopleWageItemService")
public class AccPeopleWageItemServiceImpl implements AccPeopleWageItemService {

	private static Logger logger = Logger.getLogger(AccPeopleWageItemServiceImpl.class);
	
	@Resource(name = "accPeopleWageItemMapper")
	private final AccPeopleWageItemMapper accPeopleWageItemMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccPeopleWageItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccPeopleWageItem(Map<String,Object> entityMap) throws DataAccessException{
		
		AccWagePay accWagePay = accPeopleWageItemMapper.queryAccPeopleWageItemByUserId(entityMap);
		
		if(accWagePay != null){
			
			entityMap.put("emp_id", accWagePay.getEmp_id());
			
			entityMap.put("emp_no", accWagePay.getEmp_no());
			
		}else{
			return "{\"error\":\"当前用户没有绑定职工！\"}";
			
		}
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accPeopleWageItemMapper.queryAccPeopleWageItem(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = accPeopleWageItemMapper.queryAccPeopleWageItem(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccPeopleWageItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePay queryAccPeopleWageItemByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accPeopleWageItemMapper.queryAccPeopleWageItemByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 更新AccPeopleWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccPeopleWageItem(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accPeopleWageItemMapper.updateAccPeopleWageItem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccPeopleWageItem\"}";

		}
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 批量更新AccPeopleWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccPeopleWageItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accPeopleWageItemMapper.updateBatchAccPeopleWageItem(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccPeopleWageItem\"}";

		}
		
	}

	/**
	 * 【工资管理-工资数据-员工工资查询】：页面主查询
	 */
	@Override
	public String queryAccPeoplePay(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer sbf = new StringBuffer("");
		List<AccWageItems> itemList = accWageItemsMapper.queryAccWageItems(entityMap);
		if(itemList.size() == 0){
			return "{\"Total\":0,\"Rows\":[]}";
		}
		for (AccWageItems accWageItems : itemList) {
			sbf.append(accWageItems.getColumn_item()+",");
		}
		entityMap.put("column_item", sbf.substring(0, sbf.length()-1));
		List<Map<String,Object>> list = accPeopleWageItemMapper.queryAccPeoplePay(entityMap);
		return ChdJson.toJson(list);
	}
	
	@Override
	public List<Map<String,Object>> queryAccPeopleWageItemPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		if (entityMap.get("group_id") == null) {

			entityMap.put("group_id", SessionManager.getGroupId());

		}

		if (entityMap.get("hos_id") == null) {

			entityMap.put("hos_id", SessionManager.getHosId());

		}

		if (entityMap.get("copy_code") == null) {

			entityMap.put("copy_code", SessionManager.getCopyCode());

		}
		
		AccWagePay accWagePay = accPeopleWageItemMapper.queryAccPeopleWageItemByUserId(entityMap);
		
		if(accWagePay != null){
			
			entityMap.put("emp_id", accWagePay.getEmp_id());
			
			entityMap.put("emp_no", accWagePay.getEmp_no());
			
		}else{
			
			entityMap.put("emp_id", "00");
			
			entityMap.put("emp_no", "00");
			
		}
		
			
		List<Map<String,Object>> list = accPeopleWageItemMapper.queryAccPeopleWageItem(entityMap);
		
		return list;
		
	}
	
	@Override
	public List<Map<String,Object>> queryAccPeoplePayPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		if (entityMap.get("group_id") == null) {

			entityMap.put("group_id", SessionManager.getGroupId());

		}

		if (entityMap.get("hos_id") == null) {

			entityMap.put("hos_id", SessionManager.getHosId());

		}

		if (entityMap.get("copy_code") == null) {

			entityMap.put("copy_code", SessionManager.getCopyCode());

		}
		
		StringBuffer sbf = new StringBuffer();

		List<AccWageItems> itemList = accWageItemsMapper.queryAccWageItems(entityMap);
		
		for (AccWageItems accWageItems : itemList) {
			
			sbf.append(accWageItems.getColumn_item()+",");
			
		}
		
		entityMap.put("column_item", sbf.substring(0, sbf.length()-1));
		
		List<Map<String,Object>> list = accPeopleWageItemMapper.queryAccPeoplePay(entityMap);
			
		return list;
	}
	
	@Override
	public String queryAccPeoplePayMeals(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuffer sbf = new StringBuffer();

		List<AccWageItems> itemList = accWageItemsMapper.queryAccWageItems(entityMap);
		
		if (itemList.size()==0) {
			return ChdJson.toJson(new ArrayList<Map<String,Object>>());
		}
		
		for (AccWageItems accWageItems : itemList) {
			
			sbf.append(accWageItems.getColumn_item()+",");
			
		}
		
		entityMap.put("column_item", sbf.substring(0, sbf.length()-1));
		
		List<Map<String,Object>> list = accPeopleWageItemMapper.queryAccPeoplePayMeals(entityMap);
			
		return ChdJson.toJson(list);
	}
}
