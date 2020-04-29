/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.med;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.med.BudgMedCostExecuteMapper;
import com.chd.hrp.budg.entity.BudgMedCostExecute;
import com.chd.hrp.budg.service.business.med.BudgMedCostExecuteService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医疗支出执行数据
 * @Table:
 * BUDG_MED_COST_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedCostExecuteService")
public class BudgMedCostExecuteServiceImpl implements BudgMedCostExecuteService {

	private static Logger logger = Logger.getLogger(BudgMedCostExecuteServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedCostExecuteMapper")
	private final BudgMedCostExecuteMapper budgMedCostExecuteMapper = null;
    
	/**
	 * @Description 
	 * 添加医疗支出执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医疗支出执行数据
		BudgMedCostExecute budgMedCostExecute = queryByCode(entityMap);

		if (budgMedCostExecute != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgMedCostExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医疗支出执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for( int i= 0 ; i<entityList.size() ; i++){
				
				addList.add(entityList.get(i));
				
				if(i%1000 == 0){
					
					budgMedCostExecuteMapper.addBatch(addList);
					
					addList.clear();
				}else if (i == (entityList.size() - 1) && addList.size() > 0){
					
					budgMedCostExecuteMapper.addBatch(addList);
					
					addList.clear();
				}
				
			}
			
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 医疗支出执行数据  采集 （财务取数）
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String collectData(Map<String,Object> entityMap)throws DataAccessException{
		
		
		try {
			
			//先删除  要采集  年月的 执行数据 
			budgMedCostExecuteMapper.deleteMonthData(entityMap) ;
			
			int state = budgMedCostExecuteMapper.collectData(entityMap);
			
			if(state > 0 ){
				
				return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"采集失败！未采集到财务辅助账数据.\",\"state\":\"false\"}";
				
			}
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\",\"state\":\"false\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 更新医疗支出执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgMedCostExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医疗支出执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgMedCostExecuteMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除医疗支出执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgMedCostExecuteMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医疗支出执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMedCostExecuteMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加医疗支出执行数据<BR> 
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
		//判断是否存在对象医疗支出执行数据
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedCostExecute> list = (List<BudgMedCostExecute>)budgMedCostExecuteMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedCostExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedCostExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医疗支出执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedCostExecute> list = (List<BudgMedCostExecute>)budgMedCostExecuteMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedCostExecute> list = (List<BudgMedCostExecute>)budgMedCostExecuteMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医疗支出执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedCostExecuteMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗支出执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedCostExecute
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedCostExecuteMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗支出执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedCostExecute>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedCostExecuteMapper.queryExists(entityMap);
	}
	
}
