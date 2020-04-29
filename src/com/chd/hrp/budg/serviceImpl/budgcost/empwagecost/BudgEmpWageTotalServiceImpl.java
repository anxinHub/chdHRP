/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcost.empwagecost;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcost.empwagecost.BudgEmpWageTotalMapper;
import com.chd.hrp.budg.entity.BudgEmpWageTotal;
import com.chd.hrp.budg.service.budgcost.empwagecost.BudgEmpWageTotalService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室职工工资总表
 * @Table:
 * BUDG_EMP_WAGE_TOTAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgEmpWageTotalService")
public class BudgEmpWageTotalServiceImpl implements BudgEmpWageTotalService {

	private static Logger logger = Logger.getLogger(BudgEmpWageTotalServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgEmpWageTotalMapper")
	private final BudgEmpWageTotalMapper budgEmpWageTotalMapper = null;
    
	/**
	 * @Description 
	 * 添加科室职工工资总表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室职工工资总表
		BudgEmpWageTotal budgEmpWageTotal = queryByCode(entityMap);

		if (budgEmpWageTotal != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgEmpWageTotalMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室职工工资总表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgEmpWageTotalMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室职工工资总表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgEmpWageTotalMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室职工工资总表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgEmpWageTotalMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室职工工资总表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgEmpWageTotalMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室职工工资总表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgEmpWageTotalMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室职工工资总表<BR> 
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
		//判断是否存在对象科室职工工资总表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgEmpWageTotal> list = (List<BudgEmpWageTotal>)budgEmpWageTotalMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgEmpWageTotalMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgEmpWageTotalMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室职工工资总表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		//定义returnList  用于接收页面端所需数据并 返回
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
				
		//查询页面端所需数据
		List<Map<String,Object>> listDate = (List<Map<String, Object>>) budgEmpWageTotalMapper.query(entityMap);
		
		//查询动态加载表头数据
		List<Map<String,Object>> list = budgEmpWageTotalMapper.queryBudgWageItem(entityMap);
		
		//定义dateMap 接收数据
		Map<String,Map<String,Object>> dateMap = new HashMap<String, Map<String,Object>>();
		
		for (Map<String, Object> map : list) {
			
			for (Map<String, Object> item : listDate) {
				
				//根据必需主键  定义key
				String key = ""+ item.get("month") +  item.get("emp_id") + item.get("emp_type_code");
				
				if(map.get("wage_item_code").toString().equals(item.get("wage_item_code"))){
					
					if(dateMap.get(key) == null){
						item.put(item.get("wage_item_code").toString(), item.get("amount"));
						dateMap.put(key, item);
					}else{
						dateMap.get(key).put(item.get("wage_item_code").toString(), item.get("amount"));
					}
					
				}
			}
		}
		
		for (String key : dateMap.keySet()) {
			returnList.add(dateMap.get(key));
		}
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			return ChdJson.toJson(returnList);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			PageInfo page = new PageInfo(returnList);
			
			return ChdJson.toJson(returnList, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室职工工资总表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpWageTotalMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室职工工资总表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgEmpWageTotal
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpWageTotalMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室职工工资总表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgEmpWageTotal>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpWageTotalMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询工资项目
	 */
	@Override
	public String queryBudgWageItem(Map<String,Object> entityMap)throws DataAccessException{
		
		List<Map<String,Object>> list = budgEmpWageTotalMapper.queryBudgWageItem(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * 根据主键查询数据信息
	 */
	@Override
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgEmpWageTotalMapper.queryDataByCode(mapVo);
	}
	/**
	 * 查询所有工资项目信息
	 */
	@Override
	public List<Map<String, Object>> queryBudgWageItemList(Map<String, Object> entityMap) throws DataAccessException {

		return budgEmpWageTotalMapper.queryBudgWageItemList(entityMap);
	}
	
	/**
	 * 查询 科室基本信息(根据编码 匹配ID用)
	 */
	@Override
	public List<Map<String, Object>> queryDeptData(Map<String, Object> map)	throws DataAccessException {
		
		return budgEmpWageTotalMapper.queryDeptData(map);
	}
	/**
	 * 查询 所有职工信息
	 */
	@Override
	public List<Map<String, Object>> queryEmpData(Map<String, Object> map) throws DataAccessException {

		return budgEmpWageTotalMapper.queryEmpData(map);
	}
	/**
	 * 查询所有职工类别信息
	 */
	@Override
	public List<Map<String, Object>> queryBudgEmpType(Map<String, Object> map) throws DataAccessException {
		
		return budgEmpWageTotalMapper.queryBudgEmpType(map);
	}
	@Override
	public int queryDataExist(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgEmpWageTotalMapper.queryDataExist(map);
	}
	
}
