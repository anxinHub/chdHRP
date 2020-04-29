/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.elseincome;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.budg.dao.budgincome.elseincome.BudgElseIncomeMapper;
import com.chd.hrp.budg.entity.BudgElseIncome;
import com.chd.hrp.budg.service.budgincome.elseincome.BudgElseIncomeService;
import com.chd.hrp.hr.util.RegExpValidatorUtils;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 其他收入预算
 * @Table:
 * BUDG_ELSE_INCOME
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgElseIncomeService")
public class BudgElseIncomeServiceImpl implements BudgElseIncomeService {

	private static Logger logger = Logger.getLogger(BudgElseIncomeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgElseIncomeMapper")
	private final BudgElseIncomeMapper budgElseIncomeMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * 保存数据 
	 */
	@Override
	public String saveBudgElseIncome(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				item.put("last_month_carried", "");
				
				item.put("carried_next_month", "");
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				//查询添加数据是否已存在
				String  str = budgElseIncomeMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					int count = budgElseIncomeMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = budgElseIncomeMapper.updateBatch(updateList);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
			
			//return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 添加  其他收入预算   
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		//获取对象 其他收入预算   
		Map<String,Object> budgElseIncome = queryByCode(entityMap);

		if (budgElseIncome != null) {
			
			return "{\"error\":\"数据已存在,请重新添加.\",\"state\":\"false\"}";
		}
		
		try {
			
			int state = budgElseIncomeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加     
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgElseIncomeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新      
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = budgElseIncomeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新     
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgElseIncomeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除     
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
    		
			
			int state = budgElseIncomeMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 ! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除     
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgElseIncomeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加     
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
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
		
		List<BudgElseIncome> list = (List<BudgElseIncome>)budgElseIncomeMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgElseIncomeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgElseIncomeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 ! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集  其他收入预算    
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgElseIncomeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgElseIncomeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象  其他收入预算   
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseIncomeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  其他收入预算   
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgElseIncome
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseIncomeMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  其他收入预算   
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgElseIncome>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseIncomeMapper.queryExists(entityMap);
	}
	
	/**
	 * 判断收入预算科目是否存在
	 */
	@Override
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException {
		return budgElseIncomeMapper.querySubjCodeExist(entityMap);
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	//收入预算科目下拉框
	@Override
	public String queryBudgIncomeSubj(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgElseIncomeMapper.queryBudgIncomeSubj(mapVo, rowBounds));
	}

	@Override
	public String setLastIncome(Map<String, Object> mapVo) throws DataAccessException {

		String last_income = budgElseIncomeMapper.setLastIncome(mapVo) ;
		
		if(last_income == null ){
			
			return "{\"last_income\":\"0.00\",\"state\":\"true\"}"; 
		}else{
			
			return "{\"last_income\":\""+last_income+"\",\"state\":\"true\"}";
		}
		
		
	}
	
	/**
	 * 生成 （根据上年执行数据生成）
	 */
	@Override
	public String addBudgElseIncome(Map<String, Object> mapVo) throws DataAccessException {
		
		// 年度计算
		Integer last_year = Integer.parseInt(String.valueOf(mapVo.get("budg_year"))) -1;
		
		mapVo.put("last_year", last_year) ;
		
		int count = budgElseIncomeMapper.addBudgElseIncome(mapVo);
		
		if(count == 0){
			
			return "{\"error\":\"生成失败!上年执行数据不存在或数据已全部生成.\",\"state\":\"false\"}";
			
		}else{
			
			return "{\"msg\":\"生成成功!共生成"+count+"条数据\",\"state\":\"true\"}";
			
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String importExcel(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

		Map<String, Object> whereMap = new HashMap<String, Object>(3);
		whereMap.put("group_id", SessionManager.getGroupId());
		whereMap.put("hos_id", SessionManager.getHosId());
		whereMap.put("copy_code", SessionManager.getCopyCode());
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("budg_year", map.get("budg_year").get(1));
					saveMap.put("month", formatMonth(map.get("month").get(1)));
					
					//其它收入预算科目
					whereMap.put("budg_year", map.get("budg_year").get(1));
					whereMap.put("subj_name", map.get("subj_name").get(1));
					Map<String,Object> subjMap = budgElseIncomeMapper.queryBudgElseIncomeSubj(whereMap);;
					if (subjMap == null || subjMap.get("subj_code") == null) {
						failureMsg.append("<br/>科目名称:" + map.get("subj_name") + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("subj_code", subjMap.get("subj_code"));
					
					//预算值
					if (!RegExpValidatorUtils.IsNumber(map.get("budg_value").get(1))) {
						failureMsg.append("<br/>预算值:" + map.get("budg_value") + " 非法数字; ");
						failureNum++;
						continue;
					}
					saveMap.put("budg_value", map.get("budg_value").get(1));
					
					saveMap.put("last_income", "");
					saveMap.put("grow_rate", "");
					saveMap.put("grow_value", "");
					saveMap.put("last_month_carried", "");
					saveMap.put("carried_next_month", "");
					
					saveMap.put("remark", map.get("remark").get(1) == null ? "" : map.get("remark").get(1));

					List<BudgElseIncome> listData = (List<BudgElseIncome>) budgElseIncomeMapper.queryExists(saveMap);
					
					if (listData != null && listData.size() > 0) {
						failureMsg.append("<br/>预算年度:" + map.get("budg_year").get(1) + " 月份:" + map.get("month").get(1) + " 科目名称:" + map.get("subj_name").get(1) + " 已存在; ");
						failureNum++;
						continue;
					}
					
					successNum++;
					saveList.add(saveMap);
					
				}
				if (saveList.size() > 0) {
					budgElseIncomeMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}
	
	public static String formatMonth(String month) {
		if (month.length() < 2) {
			return "0" + month;
		}
		return month;
	}

}
