/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcost.eslecost;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcost.elsecost.BudgElseCostMapper;
import com.chd.hrp.budg.entity.BudgElseCost;
import com.chd.hrp.budg.service.budgcost.eslecost.BudgElseCostService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 其他支出预算
 * @Table:
 * BUDG_ELSE_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgElseCostService")
public class BudgElseCostServiceImpl implements BudgElseCostService {

	private static Logger logger = Logger.getLogger(BudgElseCostServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgElseCostMapper")
	private final BudgElseCostMapper budgElseCostMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * 保存数据 
	 */
	@Override
	public String saveBudgElseCost(List<Map<String, Object>> listVo) throws DataAccessException{
		
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
				String  str = budgElseCostMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					int count = budgElseCostMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = budgElseCostMapper.updateBatch(updateList);
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
	 * 添加  其他支出预算   
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		//获取对象 其他支出预算   
		Map<String,Object> budgElseCost = queryByCode(entityMap);

		if (budgElseCost != null) {
			
			return "{\"error\":\"数据已存在,请重新添加.\",\"state\":\"false\"}";
		}
		
		try {
			
			int state = budgElseCostMapper.add(entityMap);
			
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
			
			budgElseCostMapper.addBatch(entityList);
			
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
			
			int state = budgElseCostMapper.update(entityMap);
			
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
			
			budgElseCostMapper.updateBatch(entityList);
			
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
    		
			
			int state = budgElseCostMapper.delete(entityMap);
			
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
			
			budgElseCostMapper.deleteBatch(entityList);
			
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
		
		List<BudgElseCost> list = (List<BudgElseCost>)budgElseCostMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgElseCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgElseCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 ! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集  其他支出预算    
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgElseCostMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgElseCostMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象  其他支出预算   
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseCostMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  其他支出预算   
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgElseCost
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseCostMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  其他支出预算   
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgElseCost>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseCostMapper.queryExists(entityMap);
	}
	
	/**
	 * 判断支出预算科目是否存在
	 */
	@Override
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException {
		return budgElseCostMapper.querySubjCodeExist(entityMap);
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	//支出预算科目下拉框
	@Override
	public String queryBudgCostSubj(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgElseCostMapper.queryBudgCostSubj(mapVo, rowBounds));
	}

	@Override
	public String setLastCost(Map<String, Object> mapVo) throws DataAccessException {

		String last_cost = budgElseCostMapper.setLastCost(mapVo) ;
		
		if(last_cost == null ){
			
			return "{\"last_cost\":\"0.00\",\"state\":\"true\"}"; 
		}else{
			
			return "{\"last_cost\":\""+last_cost+"\",\"state\":\"true\"}";
		}
		
		
	}
	
	/**
	 * 生成 （根据上年执行数据生成）
	 */
	@Override
	public String addBudgElseCost(Map<String, Object> mapVo) throws DataAccessException {
		
		// 年度计算
		Integer last_year = Integer.parseInt(String.valueOf(mapVo.get("budg_year"))) -1;
		
		mapVo.put("last_year", last_year) ;
		
		int count = budgElseCostMapper.addBudgElseCost(mapVo);
		
		if(count == 0){
			
			return "{\"error\":\"生成失败!上年执行数据不存在或数据已全部生成.\",\"state\":\"false\"}";
			
		}else{
			
			return "{\"msg\":\"生成成功!共生成"+count+"条数据\",\"state\":\"true\"}";
			
		}
		
	}

	
	/*
	 * 其他支出预算报表
	 */
	@Override
	public String queryElseCostReport(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = budgElseCostMapper.queryElseCostReport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = budgElseCostMapper.queryElseCostReport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
}
