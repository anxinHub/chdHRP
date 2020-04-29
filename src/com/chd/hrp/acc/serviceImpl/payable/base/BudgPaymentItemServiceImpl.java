/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.serviceImpl.payable.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.base.BudgPaymentItemDictMapper;
import com.chd.hrp.acc.dao.payable.base.BudgPaymentItemMapper;
import com.chd.hrp.acc.entity.payable.BudgPaymentItem;
import com.chd.hrp.acc.entity.payable.BudgPaymentItemDict;
import com.chd.hrp.acc.service.payable.base.BudgPaymentItemService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 控制方式
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgPaymentItemService")
public class BudgPaymentItemServiceImpl implements BudgPaymentItemService {

	private static Logger logger = Logger.getLogger(BudgPaymentItemServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgPaymentItemMapper")
	private final BudgPaymentItemMapper budgPaymentItemMapper = null;
	
	@Resource(name = "budgPaymentItemDictMapper")
	private final BudgPaymentItemDictMapper budgPaymentItemDictMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
    
	/**
	 * @Description 
	 * 添加控制方式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		
		String payment_item_code = entityMap.get("payment_item_code").toString();
		
		String super_code = entityMap.get("super_code").toString();
		
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", entityMap.get("copy_code"));
		utilMap.put("field_table","BUDG_PAYMENT_ITEM");
		
		if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
			utilMap.put("field_key1", "payment_item_code");
			utilMap.put("field_value1", entityMap.get("super_code"));		
			int count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
			if (count ==0) {
				return "{\"error\":\"上级编码：[" + entityMap.get("super_code").toString() + "]不存在.\"}";
			}
		}
		
		
		utilMap.put("field_key1", "payment_item_code");
		utilMap.put("field_value1", entityMap.get("payment_item_code"));		
		int count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
		if (count >0) {
			return "{\"error\":\"支付项目编码：[" + entityMap.get("payment_item_code").toString() + "]重复.\"}";
		}
		
		BudgPaymentItem budgPaymentItem = queryByCode(entityMap);

		if (budgPaymentItem != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		
		String item_name_all = "";
		
		if(!entityMap.get("super_code").toString().equals("top")){
			entityMap.put("payment_item_code", entityMap.get("super_code"));
			item_name_all = queryBySuperCode(entityMap);
			entityMap.put("item_name_all", item_name_all);
		}
		
		try {
			
			entityMap.put("payment_item_code", payment_item_code);
			
			Long payment_item_id = budgPaymentItemMapper.queryPaymentItemSequence();
			
			Long payment_item_no = budgPaymentItemDictMapper.queryPaymentItemDictSequence();
			
			entityMap.put("payment_item_id", payment_item_id);
			
			entityMap.put("super_code", super_code);
		
			budgPaymentItemMapper.add(entityMap);
			
			entityMap.put("is_fresh",1);
			
			entityMap.put("payment_item_no", payment_item_no);
			
			budgPaymentItemDictMapper.add(entityMap);
			
			if(!entityMap.get("super_code").toString().equals("top")){
				Map<String,Object> isLastMap = new HashMap<String,Object>();
				isLastMap.put("is_last","0");
				isLastMap.put("group_id", entityMap.get("group_id"));
				isLastMap.put("hos_id", entityMap.get("hos_id"));
				isLastMap.put("copy_code", entityMap.get("copy_code"));
				isLastMap.put("payment_item_code", entityMap.get("super_code"));
				BudgPaymentItem temp = queryPaymentItemBySuperCode(isLastMap);
				BudgPaymentItemDict tempDict = budgPaymentItemDictMapper.queryByCode(isLastMap);
				isLastMap.put("payment_item_id", temp.getPayment_item_id());
				isLastMap.put("payment_item_no", tempDict.getPayment_item_no());
				budgPaymentItemMapper.updateIsLast(isLastMap);
				budgPaymentItemDictMapper.updateIsLast(isLastMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加控制方式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgPaymentItemMapper.addBatch(entityList);
			budgPaymentItemDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新控制方式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			String is_no = entityMap.get("is_no").toString();
			if(is_no.equals("true")){//保留变更记录时
				Long payment_item_no = budgPaymentItemDictMapper.queryPaymentItemDictSequence();
				
				entityMap.put("is_fresh","0");
				
				entityMap.put("is_stop",1);
				
				budgPaymentItemDictMapper.updateIsFresh(entityMap);
				
				entityMap.put("is_fresh",1);
				
				entityMap.put("is_stop","0");
				
				entityMap.put("payment_item_no", payment_item_no);
				
				budgPaymentItemDictMapper.add(entityMap);
				
			}else{
				budgPaymentItemDictMapper.update(entityMap);
			}
			
		  int state = budgPaymentItemMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新控制方式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			
		  budgPaymentItemMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除控制方式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgPaymentItemMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除控制方式<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgPaymentItemDictMapper.deleteBatch(entityList);
			budgPaymentItemMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加控制方式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象控制方式
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgPaymentItem> list = (List<BudgPaymentItem>)budgPaymentItemMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgPaymentItemMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgPaymentItemMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集控制方式<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
//		SysPage sysPage = new SysPage();
//		
//		sysPage = (SysPage) entityMap.get("sysPage");
//		
//		if (sysPage.getTotal()==-1){
//			
//			List<BudgPaymentItem> list = (List<BudgPaymentItem>)budgPaymentItemMapper.query(entityMap);
//			
//			return ChdJson.toJson(list);
//			
//		}else{
			
//			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgPaymentItem> list = (List<BudgPaymentItem>)budgPaymentItemMapper.query(entityMap);
			
//			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list);
			
		
		
	}
	
	/**
	 * @Description 
	 * 获取对象控制方式<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentItemMapper.queryByCode(entityMap);
	}
	
	@Override
	public String queryBySuperCode(Map<String,Object> entityMap)throws DataAccessException{
		String result = "";
		
		Integer item_level = Integer.parseInt(entityMap.get("item_level").toString());//级次
		
		
		for(int i = 0; i < item_level; i++){
			if(!entityMap.get("super_code").toString().equals("top")){
				entityMap.put("payment_item_code", entityMap.get("super_code"));
				String super_code = queryPaymentItemBySuperCode(entityMap).getSuper_code();
				if(!super_code.equals("top")){
					entityMap.put("super_code", super_code);
					result = result + queryPaymentItemBySuperCode(entityMap).getItem_name_all() + "-" + entityMap.get("payment_item_name").toString();
					queryBySuperCode(entityMap);
						
				}else{
					entityMap.put("super_code", "top");
					result = result + queryPaymentItemBySuperCode(entityMap).getItem_name_all() + "-" + entityMap.get("payment_item_name").toString();
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * @Description 
	 * 获取控制方式<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgPaymentItem
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentItemMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取控制方式<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgPaymentItem>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentItemMapper.queryExists(entityMap);
	}
	@Override
	public BudgPaymentItem queryPaymentItemBySuperCode(Map<String, Object> entityMap) {
		return budgPaymentItemMapper.queryByCode(entityMap);
	}
	@Override
	public Long queryPaymentItemSequence() throws DataAccessException {
		
		return budgPaymentItemMapper.queryPaymentItemSequence();
	}
	@Override
	public Long queryPaymentItemDictSequence() throws DataAccessException {
		
		return budgPaymentItemMapper.queryPaymentItemDictSequence();
	}
	@Override
	public void prcUpdateBudgItemALL(Map<String,Object> entityMap) throws DataAccessException {
		
		budgPaymentItemMapper.prcUpdateBudgItemALL(entityMap);
	}
}
