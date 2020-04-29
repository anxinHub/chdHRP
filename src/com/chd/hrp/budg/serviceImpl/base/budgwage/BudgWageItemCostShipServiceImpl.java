/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgwage;

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
import com.chd.hrp.budg.dao.base.budgwage.BudgWageItemCostShipMapper;
import com.chd.hrp.budg.entity.BudgWageItemCostShip;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 工资项目与预算支出科目对应关系
 * @Table:
 * BUDG_WAGE_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWageItemCostShipService")
public class BudgWageItemCostShipServiceImpl implements BudgWageItemCostShipService {

	private static Logger logger = Logger.getLogger(BudgWageItemCostShipServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWageItemCostShipMapper")
	private final BudgWageItemCostShipMapper budgWageItemCostShipMapper = null;
    
	/**
	 * @Description 
	 * 添加工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象工资项目与预算支出科目对应关系
		BudgWageItemCostShip budgWageItemCostShip = queryByCode(entityMap);

		if (budgWageItemCostShip != null) {

			return "{\"error\":\"添加失败,工资项目:"+entityMap.get("wage_item_name")+",与其他预算支出科目存在对应关系。多个预算支出科目 不允许对应同一工资项目!!\",\"state\":\"false\"}";

		}
		
		try {
			
			budgWageItemCostShipMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败 ! 方法 add\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加工资项目与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWageItemCostShipMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			if(!entityMap.get("wage_item_code").equals(entityMap.get("wage_item_codeOld"))){
				BudgWageItemCostShip data = budgWageItemCostShipMapper.queryByCode(entityMap);
				
				if(data != null){
					return "{\"error\":\"更新失败,工资项目:"+entityMap.get("wage_item_name")+",与其他预算支出科目存在对应关系。多个预算支出科目 不允许对应同一工资项目!!\",\"state\":\"false\"}";
				}
			}
			
			List<BudgWageItemCostShip> list = (List<BudgWageItemCostShip>) budgWageItemCostShipMapper.queryExists(entityMap);
            if(list.size() >0 ){
            	
            	return "{\"error\":\"工资项目:"+entityMap.get("wage_item_name")+"与预算支出科目:" +entityMap.get("subj_name")+"对应关系已存在!\",\"state\":\"false\"}";
			}
            	
			//
			int state = budgWageItemCostShipMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新工资项目与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			
			budgWageItemCostShipMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgWageItemCostShipMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除工资项目与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWageItemCostShipMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加工资项目与预算支出科目对应关系<BR> 
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
		//判断是否存在对象工资项目与预算支出科目对应关系
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWageItemCostShip> list = (List<BudgWageItemCostShip>)budgWageItemCostShipMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWageItemCostShipMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWageItemCostShipMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgWageItemCostShipMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgWageItemCostShipMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWageItemCostShipMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWageItemCostShip
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWageItemCostShipMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取工资项目与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWageItemCostShip>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWageItemCostShipMapper.queryExists(entityMap);
	}
	
	/**
	 * 工资项目下拉框
	 */
	@Override
	public String queryWageItem(Map<String, Object> mapVo) throws DataAccessException {
		 RowBounds rowBounds = new RowBounds(0, 20);
		 if(mapVo.get("pageSize")!=null){
			 rowBounds=new RowBounds(0,(Integer) mapVo.get("pageSize"));
			 
		 }else{
		 }
		 return JSON.toJSONString(budgWageItemCostShipMapper.queryWageItem(mapVo, rowBounds));
	}
	@Override
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException {
		return budgWageItemCostShipMapper.queryCostSubjByCode(mapVo);
	}
}
