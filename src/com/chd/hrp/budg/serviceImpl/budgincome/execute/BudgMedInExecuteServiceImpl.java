/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.execute;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.elseincome.BudgElseIncomeExecuteMapper;
import com.chd.hrp.budg.dao.budgincome.execute.BudgMedInExecuteMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeExecute;
import com.chd.hrp.budg.service.budgincome.execute.BudgMedInExecuteService;
import com.chd.hrp.hip.dao.HrpHipSelectMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医疗收入执行数据
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedInExecuteService")
public class BudgMedInExecuteServiceImpl implements BudgMedInExecuteService {

	private static Logger logger = Logger.getLogger(BudgMedInExecuteServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedInExecuteMapper")
	private final BudgMedInExecuteMapper budgMedInExecuteMapper = null;
    
	@Resource(name = "budgElseIncomeExecuteMapper")
	private final BudgElseIncomeExecuteMapper budgElseIncomeExecuteMapper = null;
	
	@Resource(name = "hrpHipSelectMapper")
	private final HrpHipSelectMapper hrpHipSelectMapper = null;
	
	/**
	 * @Description 
	 * 添加医疗收入执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医疗收入执行数据
		BudgMedIncomeExecute budgMedInExecute = queryByCode(entityMap);

		if (budgMedInExecute != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgMedInExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医疗收入执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for( int i =  0 ; i< entityList.size();i++){
				
				addList.add(entityList.get(i));
				
				if( i%1000 == 0){
					
					budgMedInExecuteMapper.addBatch(addList);
					
					addList.clear();
					
				}else if ( i == (entityList.size()-1) && addList.size()> 0){
					
					budgMedInExecuteMapper.addBatch(addList);
					
					addList.clear();
				}
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新医疗收入执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgMedInExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医疗收入执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgMedInExecuteMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除医疗收入执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgMedInExecuteMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医疗收入执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMedInExecuteMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加医疗收入执行数据<BR> 
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
		//判断是否存在对象医疗收入执行数据
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeExecute> list = (List<BudgMedIncomeExecute>)budgMedInExecuteMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedInExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedInExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医疗收入执行数据<BR> 
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
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInExecuteMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedInExecuteMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医疗收入执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedInExecuteMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗收入执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedInExecute
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedInExecuteMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗收入执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedInExecute>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedInExecuteMapper.queryExists(entityMap);
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
			List<Map<String,Object>> dataList = budgMedInExecuteMapper.getDatafromAcc(entityMap);
			
			if(dataList == null || dataList.isEmpty()){
				return "{\"error\":\""+entityMap.get("budg_year")+"年度下,没有财务数据或科目对应关系年度与财务数据年度不对应,请检查数据后操作!\"}";
			}
			
			//将查询出的数据  覆盖插入其他收入执行数据表中
			budgMedInExecuteMapper.deleteBatch(dataList);
			
			budgMedInExecuteMapper.addBatchData(dataList);
			
			return "{\"msg\":\"数据采集成功!\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"数据采集失败!\"}";
		}
	}
	
	@Override
	public String getDatafromAcc2(Map<String, Object> entityMap) throws DataAccessException {
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
			List<Map<String,Object>> dataList = budgMedInExecuteMapper.getDatafromAcc2(entityMap);
			
			if(dataList == null || dataList.isEmpty()){
				return "{\"error\":\""+entityMap.get("budg_year")+"年度下,没有财务数据或科目对应关系年度与财务数据年度不对应,请检查数据后操作!\"}";
			}
			
			//将查询出的数据  覆盖插入其他收入执行数据表中
			budgMedInExecuteMapper.deleteBatch(dataList);
			
			budgMedInExecuteMapper.addBatchData(dataList);
			
			return "{\"msg\":\"数据采集成功!\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"数据采集失败!\"}";
		}
	}
	
	
	/**
	 * @Description 
	 * his收入数据 采集
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveHisExecuteData(Map<String, Object> entityMap)throws DataAccessException{
		
		try { 
			
			//查询是否存在同名的dblink，如果存在下拉列表优先从dblink中读取数据
			int is_exists = hrpHipSelectMapper.existsDblink(entityMap);
			
			if(is_exists >= 0){
				entityMap.put("dblink", entityMap.get("ds_code"));
			}
			
			// 查询对应关系 是否全部维护（以防新加科室或诊疗组后  造成采集数据错误 ）
			String errStr = budgMedInExecuteMapper.queryRelationExist(entityMap) ;
			
			if(errStr == null){
				// 删除 采集年度月份 执行数据
				budgMedInExecuteMapper.deleteExecuteData(entityMap);
				
				int count  = budgMedInExecuteMapper.saveHisExecuteData(entityMap);
				
				if(count > 0 ){
					return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
				}else{
					return "{\"error\":\"未采集到数据,请检查【收费类别与会计科目对应关系】与【收入预算科目与会计科目对应关系】数据\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"科室"+errStr+"信息未配置.请切换至【系统平台】在 【集成平台-基础信息转发配置-科室信息配置(成本科室匹配)】页面配置\",\"state\":\"false\"}";
			}
			
			
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");
		}
	}
	
}
