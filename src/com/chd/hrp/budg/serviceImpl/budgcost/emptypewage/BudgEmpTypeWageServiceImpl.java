/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcost.emptypewage;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcost.emptypewage.BudgEmpTypeWageMapper;
import com.chd.hrp.budg.entity.BudgEmpTypeWage;
import com.chd.hrp.budg.service.budgcost.emptypewage.BudgEmpTypeWageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室职工平均工资
 * @Table:
 * BUDG_EMP_TYPE_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgEmpTypeWageService")
public class BudgEmpTypeWageServiceImpl implements BudgEmpTypeWageService {

	private static Logger logger = Logger.getLogger(BudgEmpTypeWageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgEmpTypeWageMapper")
	private final BudgEmpTypeWageMapper budgEmpTypeWageMapper = null;
    
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
		BudgEmpTypeWage budgEmpTypeWage = queryByCode(entityMap);

		if (budgEmpTypeWage != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgEmpTypeWageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\"}";

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
			
			budgEmpTypeWageMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\"}";

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
			
		  int state = budgEmpTypeWageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 update\"}";

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
			
		  budgEmpTypeWageMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 updateBatch\"}";

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
			
			int state = budgEmpTypeWageMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 ! 方法 delete\"}";

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
			
			budgEmpTypeWageMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatch\"}";

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
		
		List<BudgEmpTypeWage> list = (List<BudgEmpTypeWage>)budgEmpTypeWageMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgEmpTypeWageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgEmpTypeWageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdate\"}";

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
		List<Map<String,Object>> listDate = (List<Map<String, Object>>) budgEmpTypeWageMapper.query(entityMap);
		
		//查询动态加载表头数据
		List<Map<String,Object>> list = budgEmpTypeWageMapper.queryBudgWageItem(entityMap);
		
		//定义dateMap 接收数据
		Map<String,Map<String,Object>> dateMap = new HashMap<String, Map<String,Object>>();
		
		for (Map<String, Object> map : list) {
			
			for (Map<String, Object> item : listDate) {
				
				//根据必需主键  定义key
				String key = ""+ item.get("dept_id") + item.get("emp_type_code");
				
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
		return budgEmpTypeWageMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室职工工资总表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgEmpTypeWage
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpTypeWageMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室职工工资总表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgEmpTypeWage>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpTypeWageMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询工资项目
	 */
	@Override
	public String queryBudgWageItem(Map<String,Object> entityMap)throws DataAccessException{
		
		List<Map<String,Object>> list = budgEmpTypeWageMapper.queryBudgWageItem(entityMap);
		
		return ChdJson.toJson(list);
	}
	/**
	 * 根据主键查询数据信息
	 */
	@Override
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgEmpTypeWageMapper.queryDataByCode(mapVo);
	}
	@Override
	public String collectBudgEmpWageTotal(Map<String, Object> mapVo) throws DataAccessException {
		try {
			//清空传入年份下所有数据
			budgEmpTypeWageMapper.delete(mapVo);
			//查询科室职工工资总表数据  并汇总计算后插入平均工资表中
			budgEmpTypeWageMapper.insertDataFromTotal(mapVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败!\",\"state\":\"false\"}";

		}	
	}
	
}
