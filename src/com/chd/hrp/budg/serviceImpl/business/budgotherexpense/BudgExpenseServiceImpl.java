/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.budgotherexpense;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.budgotherexpense.BudgExpenseMapper;
import com.chd.hrp.budg.entity.BudgExpense;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpenseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室其他费用预算
 * @Table:
 * BUDG_EXPENSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgExpenseService")
public class BudgExpenseServiceImpl implements BudgExpenseService {

	private static Logger logger = Logger.getLogger(BudgExpenseServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgExpenseMapper")
	private final BudgExpenseMapper budgExpenseMapper = null;
    
	/**
	 * @Description 
	 * 添加科室其他费用预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室其他费用预算
		BudgExpense budgExpense = queryByCode(entityMap);

		if (budgExpense != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgExpenseMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室其他费用预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgExpenseMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室其他费用预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgExpenseMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室其他费用预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgExpenseMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室其他费用预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgExpenseMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室其他费用预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgExpenseMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室其他费用预算<BR> 
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
		//判断是否存在对象科室其他费用预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgExpense> list = (List<BudgExpense>)budgExpenseMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgExpenseMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgExpenseMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室其他费用预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgExpense> list = (List<BudgExpense>)budgExpenseMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgExpense> list = (List<BudgExpense>)budgExpenseMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室其他费用预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgExpenseMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室其他费用预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgExpense
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgExpenseMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室其他费用预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgExpense>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgExpenseMapper.queryExists(entityMap);
	}
	
	/**
	 * 根据主键查询数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException {
		return budgExpenseMapper.queryDataByCode(mapVo);
	}
	
	/**
	 * @Description 
	 * 生成  根据年度生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String generateBudgExpense(Map<String, Object> mapVo) {
		try {
			String str = "";
			String month;
			//创建集合  封装生成所需数据
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			//查询支出项目在三张表中是否重复出现编制
			List<Map<String,Object>> paymentCountList = budgExpenseMapper.queryExistsPaymentItem(mapVo);
			for (Map<String, Object> map : paymentCountList) {
				//如果支出项目重复编制  则count值大于1 将支出项目名放入str 用作返回信息
				if(Integer.parseInt(map.get("count").toString()) > 1){
					str += map.get("payment_item_name").toString() + " ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"支出项目: "+str+"重复编制,请检查数据后操作!\",\"state\":\"false\"}";
			}
			//如果未重复编制 则各表数据均为单一存在  查询各表信息  并处理数据  
			List<Map<String, Object>> DataFromFixList = budgExpenseMapper.queryDataFromFix(mapVo);
			
			for (int i = 1; i <= 12; i++) {
				//处理月份数据  为定额数据  匹配月份
				if(i < 10){
					month = "0"+ i;
				}else{
					month = "" + i;
				}
				//遍历 定额数据 匹配月份数据 并放入assList之中
				for (Map<String, Object> map : DataFromFixList) {
					
					Map<String, Object> fixDataMap = new HashMap<String, Object>();
					map.put("month", month);
					
					fixDataMap.putAll(map);
					
					addList.add(fixDataMap);
				}
			}
			
			//查询申请及其他费用数据
			List<Map<String, Object>> DataFromApplyAndElseList = budgExpenseMapper.queryDataFromApplyAndElse(mapVo);
			
			addList.addAll(DataFromApplyAndElseList);
			
			budgExpenseMapper.delete(mapVo);
			budgExpenseMapper.addGenerateBatch(addList);
			
			return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
			
		} catch (DataAccessException e) {
			
			throw new SysException("{\"error\":\"生成失败!\"}");
		}
		
	}
	/**
	 * 查询 科室基本信息(根据code 匹配ID)
	 */
	@Override
	public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap)	throws DataAccessException {
		return budgExpenseMapper.queryDeptData(paraMap);
	}
	/**
	 * 查询 支出项目基本信息(根据code 匹配ID)
	 */
	@Override
	public List<Map<String, Object>> queryPayItemData(Map<String, Object> paraMap) throws DataAccessException {
		return budgExpenseMapper.queryPayItemData(paraMap);
	}
	
	/**
	 * 校验数据是否已存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo)throws DataAccessException {
		
		return budgExpenseMapper.queryDataExist(mapVo);
	}
	
	@Override
	public String queryBudgOtherExpenseCount(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgExpense> list = budgExpenseMapper.queryBudgOtherExpenseCount(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgExpense> list = budgExpenseMapper.queryBudgOtherExpenseCount(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String countBudgOtherExpense(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			
			budgExpenseMapper.countBudgOtherExpense(mapVo);
			return "{\"msg\":\"操作成功!\",\"state\":\"true\"}";
			
		} catch (DataAccessException e) {
			
			throw new SysException("{\"error\":\"生成失败!\"}");
		}
	}
	
}
