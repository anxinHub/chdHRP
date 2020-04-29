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
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcash.BudgCarryMapper;
import com.chd.hrp.budg.dao.budgincome.elseincome.BudgElseIncomeExecuteMapper;
import com.chd.hrp.budg.dao.budgincome.execute.BudgMedInExecuteMapper;
import com.chd.hrp.budg.entity.BudgElseIncomeExecute;
import com.chd.hrp.budg.service.budgincome.elseincome.BudgElseIncomeExecuteService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 其他收入预算执行
 * @Table:
 * BUDG_ELSE_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgElseIncomeExecuteService")
public class BudgElseIncomeExecuteServiceImpl implements BudgElseIncomeExecuteService {

	private static Logger logger = Logger.getLogger(BudgElseIncomeExecuteServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgElseIncomeExecuteMapper")
	private final BudgElseIncomeExecuteMapper budgElseIncomeExecuteMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "budgMedInExecuteMapper")
	private final BudgMedInExecuteMapper budgMedInExecuteMapper = null;
	
	/**
	 * 保存数据 
	 */
	@Override
	public String saveBudgElseIncomeExecute(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				//查询添加数据是否已存在
				String  str = budgElseIncomeExecuteMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					int count = budgElseIncomeExecuteMapper.addBatch(addList);
					
				}else{
					
					return "{\"message\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = budgElseIncomeExecuteMapper.updateBatch(updateList);
			}
			
			return "{\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"message\":\"保存失败.\",\"state\":\"false\"}");
			
			//return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 添加  其他收入预算执行   
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		//获取对象 其他收入预算执行   
		Map<String,Object> budgElseIncomeExecute = queryByCode(entityMap);

		if (budgElseIncomeExecute != null) {
			
			return "{\"error\":\"数据已存在,请重新添加.\",\"state\":\"false\"}";
		}
		
		try {
			
			int state = budgElseIncomeExecuteMapper.add(entityMap);
			
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
			
			budgElseIncomeExecuteMapper.addBatch(entityList);
			
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
			
			int state = budgElseIncomeExecuteMapper.update(entityMap);
			
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
			
			budgElseIncomeExecuteMapper.updateBatch(entityList);
			
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
    		
			
			int state = budgElseIncomeExecuteMapper.delete(entityMap);
			
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
			
			budgElseIncomeExecuteMapper.deleteBatch(entityList);
			
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
		
		List<BudgElseIncomeExecute> list = (List<BudgElseIncomeExecute>)budgElseIncomeExecuteMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgElseIncomeExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgElseIncomeExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 ! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集  其他收入预算执行    
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		//查询系统参数  当前预算系统是否区分账套  0 不区分   1 区分
		int paraValue = budgMedInExecuteMapper.queryParaValue(entityMap);
		
		entityMap.put("para_value", paraValue);
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgElseIncomeExecuteMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgElseIncomeExecuteMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象  其他收入预算执行   
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseIncomeExecuteMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  其他收入预算执行   
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgElseIncomeExecute
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseIncomeExecuteMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  其他收入预算执行   
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgElseIncomeExecute>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgElseIncomeExecuteMapper.queryExists(entityMap);
	}
	
	/**
	 * 判断收入预算科目是否存在
	 */
	@Override
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException {
		return budgElseIncomeExecuteMapper.querySubjCodeExist(entityMap);
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
		
		return JSON.toJSONString(budgElseIncomeExecuteMapper.queryBudgIncomeSubj(mapVo, rowBounds));
	}
	
	/**
	 * @Description 
	 * 财务取数
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String getDatafromAcc(Map<String,Object> entityMap) throws DataAccessException{
		try {
			//查询结转表中  该年度 月份  数据是否已经结转 如果已结转  不允许操作此功能
			String flag = budgElseIncomeExecuteMapper.queryIncomeFlag(entityMap);
			
			if("1".equals(flag)){
				return "{\"error\":\"该年度月份下的数据已经结转,不可执行财务取数操作!\"}";
			}
			
			//查询系统参数  当前预算系统是否区分账套  0 不区分   1 区分
			int paraValue = budgMedInExecuteMapper.queryParaValue(entityMap);
			
			entityMap.put("para_value", paraValue);
			
			//根据年度,月份,预算科目和会计科目对应关系  从财务中取出预算科目所对应会计科目的本期贷方发生额  作为执行数据金额
			List<Map<String,Object>> dataList = budgElseIncomeExecuteMapper.getDatafromAcc(entityMap);
			
			if(dataList == null || dataList.isEmpty()){
				return "{\"error\":\""+entityMap.get("budg_year")+"年度下,没有财务数据或科目对应关系年度与财务数据年度不对应,请检查数据后操作!\"}";
			}
			
			//将查询出的数据  覆盖插入其他收入执行数据表中
			budgElseIncomeExecuteMapper.deleteBatch(dataList);
			
			budgElseIncomeExecuteMapper.addBatchData(dataList);
			
			return "{\"msg\":\"数据采集成功!\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"数据采集失败!\"}";
		}
	}
	
	@Override
	public String getDatafromAcc2(Map<String,Object> entityMap) throws DataAccessException{
		try {
			
			if(entityMap.get("copy_codes") != null && entityMap.get("copy_codes").toString().split(",").length > 0){
				entityMap.put("copy_codes", entityMap.get("copy_codes").toString().split(","));
			}
			
			//查询结转表中  该年度 月份  数据是否已经结转 如果已结转  不允许操作此功能
			Integer flag = budgElseIncomeExecuteMapper.queryIncomeFlag2(entityMap);
			
			if(flag > 0){
				return "{\"error\":\"该年度月份下的数据已经结转,不可执行财务取数操作!\"}";
			}
			
			//根据年度,月份,预算科目和会计科目对应关系  从财务中取出预算科目所对应会计科目的本期贷方发生额  作为执行数据金额
			List<Map<String,Object>> dataList = budgElseIncomeExecuteMapper.getDatafromAcc2(entityMap);
			
			if(dataList == null || dataList.isEmpty()){
				return "{\"error\":\""+entityMap.get("budg_year")+"年度下,没有财务数据或科目对应关系年度与财务数据年度不对应,请检查数据后操作!\"}";
			}
			
			//将查询出的数据  覆盖插入其他收入执行数据表中
			budgElseIncomeExecuteMapper.deleteBatch(dataList);
			
			budgElseIncomeExecuteMapper.addBatchData(dataList);
			
			return "{\"msg\":\"数据采集成功!\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"数据采集失败!\"}";
		}
	}
}
