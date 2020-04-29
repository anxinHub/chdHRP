/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcash;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.budg.dao.budgcash.BudgCashPlanMapper;
import com.chd.hrp.budg.entity.BudgCashInPlan;
import com.chd.hrp.budg.service.budgcash.BudgCashPlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 现金流入计划
 * @Table:
 * BUDG_CASH_IN_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgCashPlanService")
public class BudgCashPlanServiceImpl implements BudgCashPlanService {

	private static Logger logger = Logger.getLogger(BudgCashPlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCashPlanMapper")
	private final BudgCashPlanMapper budgCashPlanMapper = null;
    
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	
	/**
	 * @Description 
	 * 添加现金流入计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象现金流入计划
		BudgCashInPlan budgCashInPlan = queryByCode(entityMap);

		if (budgCashInPlan != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgCashPlanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加现金流入计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String str = "";
			String errStr = "";
			String monthErr = "";
			//创建map 接收资金计划主表
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			//组装 生成 预算调整单号 参数Map
			Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();
			
			for(Map<String,Object> item : entityList){
				
				//创建map 封装主键数据 用于查询月份数据是否已结转
				Map<String, Object> flagMap = new HashMap<String, Object>();
				flagMap.put("group_id", item.get("group_id"))   ;
				flagMap.put("hos_id", item.get("hos_id"))   ;
				flagMap.put("copy_code", item.get("copy_code"))   ;
				flagMap.put("year", item.get("budg_year"))   ;
				flagMap.put("month", item.get("month"))   ;
				
				//查询当前月份数据是否已结转
				String cashFlag = budgCashPlanMapper.queryMonthFlag(flagMap);
				
				if(cashFlag != null && "1".equals(cashFlag) && !"".equals(item.get("amount"))){
					monthErr += "现金流量项目 :"+item.get("cash_item_name")+"  "+ item.get("month")+"月份 ;";
				}
				
				//查询系统启用日期  (年度)  进行比较  小于系统启用日期则返回错误信息
				String startYear = budgCashPlanMapper.querySysYear(flagMap);
				//查询系统启用日期  (月份)  进行比较  小于系统启用日期则返回错误信息
				String startMonth = budgCashPlanMapper.querySysMonth(flagMap);
				
				int systYear = Integer.parseInt(startYear);
				
				int budg_year = Integer.parseInt(String.valueOf(item.get("budg_year")));
				
				if(startYear != null){
					if(budg_year < systYear){
						return "{\"error\":\"当前年度在系统启用之前,请检查后操作!!!\"}";
					}
				}
				
				if(startMonth != null){
					int sysMonth = Integer.parseInt(startMonth);
					
					int month = Integer.parseInt(String.valueOf(item.get("month")));
					
					if(budg_year == systYear && month < sysMonth && !"".equals(item.get("amount")) && Double.parseDouble(String.valueOf(item.get("amount"))) > 0){
						errStr += "现金流量项目 :"+item.get("cash_item_name")+"  "+ item.get("month")+"月份 ;";
					}
					
				}
				
				int count = budgCashPlanMapper.queryEventDataExist(item);
				
				if (count > 0 ) {

					str += item.get("budg_year")+"年 "+" 资金流动事项:"+item.get("event")+";"	;
				}
				
				mapVo.put("group_id", item.get("group_id"))   ;
				mapVo.put("hos_id", item.get("hos_id"))   ;
				mapVo.put("copy_code", item.get("copy_code"))   ;
				mapVo.put("budg_year", item.get("budg_year"))   ;
				mapVo.put("plan_code", item.get("plan_code"))   ;
				mapVo.put("event", item.get("event"))   ;
				mapVo.put("remark", item.get("remark"))   ;
				mapVo.put("maker", item.get("maker"))   ;
				mapVo.put("make_date", item.get("make_date"))   ;
				mapVo.put("checker", item.get("checker"))   ;
				mapVo.put("check_date", item.get("check_date"))   ;
				mapVo.put("state", item.get("state"))   ;
				
			}
			
			// 生成 资金计划单号
			if("系统自动生成".equals(mapVo.get("plan_code"))){
				mapForAdjCode.put("group_id", mapVo.get("group_id")) ;
				
				mapForAdjCode.put("hos_id", mapVo.get("hos_id")) ;
				
				mapForAdjCode.put("copy_code", mapVo.get("copy_code")) ;
				
				mapForAdjCode.put("year", mapVo.get("budg_year")) ;
			}
			
			//获取生成单号  放入主表
			String planCode = setBudgApplyCode(mapForAdjCode);
			mapVo.put("plan_code",planCode);
			
			//遍历  entityList   将生成的单号放入各明细数据中
			for(Map<String,Object> item : entityList){
				item.put("plan_code",planCode);
			}
			
			if(!"".equals(monthErr)){
				return "{\"error\":\"添加失败！"+monthErr.substring(0, monthErr.length()-1)+""+"该月份数据已结转,不可编辑该数据.\",\"state\":\"false\"}";
			}
			
			if(!"".equals(errStr)){
				return "{\"error\":\"添加失败！"+errStr.substring(0, errStr.length()-1)+""+"月份在系统启用日期之前,不可编辑该数据.\",\"state\":\"false\"}";
			}
			
			if(!"".equals(str)){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"已有数据存在.\",\"state\":\"false\"}";
			
			}else{
				
				budgCashPlanMapper.add(mapVo);
				budgCashPlanMapper.addPLanDetBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新现金流入计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgCashPlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新现金流入计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String str = "";
			String errStr = "";
			String monthErr = "";
			//创建map 接收资金计划主表
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			//组装 生成 预算调整单号 参数Map
			Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();
			
			for(Map<String,Object> item : entityList){
				
				//创建map 封装主键数据 用于查询月份数据是否已结转
				Map<String, Object> flagMap = new HashMap<String, Object>();
				flagMap.put("group_id", item.get("group_id"))   ;
				flagMap.put("hos_id", item.get("hos_id"))   ;
				flagMap.put("copy_code", item.get("copy_code"))   ;
				flagMap.put("year", item.get("budg_year"))   ;
				flagMap.put("month", item.get("month"))   ;
				
				//查询当前月份数据是否已结转
				String cashFlag = budgCashPlanMapper.queryMonthFlag(flagMap);
				
				if("1".equals(cashFlag) && !"".equals(item.get("amount"))){
					monthErr += "现金流量项目 :"+item.get("cash_item_name")+"  "+ item.get("month")+"月份 ;";
				}
				
				//查询系统启用日期  (月份)  进行比较 
				String startMonth = budgCashPlanMapper.querySysMonth(flagMap);
				//查询系统启用日期  (年度)  进行比较  小于系统启用日期则返回错误信息
				String startYear = budgCashPlanMapper.querySysYear(flagMap);
				
				int systYear = Integer.parseInt(startYear);
				
				int budg_year = Integer.parseInt(String.valueOf(item.get("budg_year")));
				
				if(startMonth != null ){
					int sysMonth = Integer.parseInt(startMonth);
					
					int month = Integer.parseInt(String.valueOf(item.get("month")));
					
					if(budg_year == systYear && month < sysMonth && !"".equals(item.get("amount")) && Double.parseDouble(String.valueOf(item.get("amount"))) > 0){
						errStr += "现金流量项目 :"+item.get("cash_item_name")+"  "+ item.get("month")+"月份 ;";
					}
					
				}
				
				int count = budgCashPlanMapper.queryEventDataExistForupdate(item);
				
				if (count > 0 ) {

					str += item.get("budg_year")+"年 "+" 资金流动事项:"+item.get("event")+";"	;
				}
				
				mapVo.put("group_id", item.get("group_id"))   ;
				mapVo.put("hos_id", item.get("hos_id"))   ;
				mapVo.put("copy_code", item.get("copy_code"))   ;
				mapVo.put("budg_year", item.get("budg_year"))   ;
				mapVo.put("plan_code", item.get("plan_code"))   ;
				mapVo.put("event", item.get("event"))   ;
				mapVo.put("remark", item.get("remark"))   ;
				mapVo.put("maker", item.get("maker"))   ;
				mapVo.put("make_date", item.get("make_date"))   ;
				mapVo.put("checker", item.get("checker"))   ;
				mapVo.put("check_date", item.get("check_date"))   ;
				mapVo.put("state", item.get("state"))   ;
				
			}
			
			if(!"".equals(monthErr)){
				return "{\"error\":\"更新失败！"+monthErr.substring(0, monthErr.length()-1)+""+"该月份数据已结转,不可编辑该数据.\",\"state\":\"false\"}";
			}
			
			if(!"".equals(errStr)){
				return "{\"error\":\"更新失败！"+errStr.substring(0, errStr.length()-1)+""+"月份在系统启用日期之前,不可编辑该数据.\",\"state\":\"false\"}";
			}
			
			if(!"".equals(str)){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"已有数据存在.\",\"state\":\"false\"}";
			
			}else{
				
				budgCashPlanMapper.update(mapVo);
				//先删除   后添加
				budgCashPlanMapper.deletePlanDet(mapVo);
				budgCashPlanMapper.addPLanDetBatch(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 删除现金流入计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgCashPlanMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除现金流入计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCashPlanMapper.deletePlanDetBatch(entityList);
			
			budgCashPlanMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加现金流入计划<BR> 
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
		//判断是否存在对象现金流入计划
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgCashInPlan> list = (List<BudgCashInPlan>)budgCashPlanMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgCashPlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgCashPlanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集现金流入计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCashInPlan> list = (List<BudgCashInPlan>)budgCashPlanMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCashInPlan> list = (List<BudgCashInPlan>)budgCashPlanMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果集资金计划 明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgCashPlanDet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCashInPlan> list = (List<BudgCashInPlan>)budgCashPlanMapper.queryBudgCashPlanDet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCashInPlan> list = (List<BudgCashInPlan>)budgCashPlanMapper.queryBudgCashPlanDet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象现金流入计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashPlanMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金流入计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgCashInPlan
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashPlanMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金流入计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgCashInPlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashPlanMapper.queryExists(entityMap);
	}
	
	/**
	 * @Description 
	 * 查询数据 现金流量项目下拉框
	 */
	@Override
	public String queryCashItem(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = budgCashPlanMapper.queryCashItem(mapVo);
		return JSON.toJSONString(list);
	}
	
	/**
	 * @Description 
	 * 生成 预算调整单号
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String setBudgApplyCode(Map<String,Object> mapVo) throws DataAccessException{
		
		mapVo.put("month", "");
		mapVo.put("table_code", "BUDG_CASH_PLAN") ;
		mapVo.put("table_name", "资金计划") ;
		mapVo.put("prefixe", "ZJJH") ;
		mapVo.put("seq_no", 4);
		
		BudgNoManager budgNoManager = budgNoManagerMapper.queryBudgNoManagerByCode(mapVo);
		
		String pref = "ZJJH";

		String max_no = "1";
		
		String no = "1" ;
		if(budgNoManager == null){
			
			mapVo.put("max_no", 1);
			
			budgNoManagerMapper.addBudgNoManager(mapVo);
			
		}else{
			
			max_no = String.valueOf(budgNoManager.getMax_no());
			
			no = max_no ;
			
			budgNoManagerMapper.updateBudgNoManagerMaxNo(mapVo);
		}
		
		for(int i= 0 ; i< 4- no.length() ; i++){
			
			max_no = "0"+ max_no ;
			
		}
		
		return pref + mapVo.get("year") + max_no;
	}
	
	/**
	 * 审核  销审 更新状态
	 */
	@Override
	public String updateReviewState(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {
			//定义字符串  拼接错误信息
			String str = "";
			
			for (Map<String, Object> map : entityList) {
				
				//更新年度明细后  更改自身数据状态 为已审核
				String planCode = String.valueOf(map.get("plan_code"));//获取单号 
				
				String state = budgCashPlanMapper.queryState(map);
				//01 新建状态  下允许审核
				if(!"01".equals(state)){
					
					str += planCode+" ";
					
				}else{
					//审核时  更改审核人  审核日期 为 当前操作人  操作日期
					map.put("checker", SessionManager.getUserId());
					
					//获取当前日期  Date类型
					Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
					map.put("check_date", date);
					
					//新建状态时  更改状态为已审核
					map.put("state", "02");
				}
				
			}
			
			if(str != ""){
				return "{\"error\":\"调整单号:"+str+"的数据状态不是新建状态 , 不能执行审核操作!\"}";
			}
			
			budgCashPlanMapper.updateReviewState(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败! \"}");

		}	
		
	}
	
	
	/**
	 * 销审    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateCancelBatch(List<Map<String, Object>> entityList)
			throws DataAccessException {
			try {
				//定义字符串  拼接错误信息
				String str = "";
				
				for (Map<String, Object> map : entityList) {
					
					//获取单号 
					String planCode = String.valueOf(map.get("plan_code"));
					
					String state = budgCashPlanMapper.queryState(map);
					//02 审核状态  下允许销审
					if(!"02".equals(state)){
						
						str += (planCode+" ");
						
					}else{
						//销审时  更改审核人  审核日期 为 ""
						map.put("checker","");
						
						map.put("check_date", "");
						
						//销审时  更改状态为新建
						map.put("state", "01");
					}
					
				}
				if(str != ""){
					return "{\"error\":\"调整单号:"+str+"的数据状态不是审核状态 , 不能执行销审操作!\"}";
				}
				
				budgCashPlanMapper.updateReviewState(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	
			}catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				throw new SysException("{\"error\":\"操作失败!\"}");
	
			}	
	}
	
	/**
	 *最新版导入
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String importBudgCashPlan(Map<String, Object> map) throws DataAccessException {
		try{
			
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("is_stop", "0");// 选取未停用的现金流量项目
			
			// 查询现金流量项目表中所有未停用的现金流量项目id code等基本信息   匹配数据用
			List<Map<String,Object>> listDict = budgCashPlanMapper.queryBudgCashFlowBeginDict(entityMap);
			
			// 判断现金流量项目编码是否存在    用 map key为现金流量项目code ， value : 现金流量项目id、code等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			
			for(Map<String, Object> dict : listDict){
				if(dict.get("cash_item_code") != null){
					dictMap.put(dict.get("cash_item_code").toString(), dict);
				}
			}
			
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
				
				
				List<String> cash_item_code = item.get("现金流量项目编码") ;
				
				List<String> month = item.get("月份") ;
				
				List<String> amount = item.get("金额(元)") ;
				
				List<String> remark = item.get("说明(可空)") ;
				
				if(cash_item_code == null){
					
					return "{\"warn\":\"" + cash_item_code.get(0) + ",现金流量项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + cash_item_code.get(0) + "\"}";
					
				}
				if(month == null){
					
					return "{\"warn\":\"" + month.get(0) + ",月份为空！\",\"state\":\"false\",\"row_cell\":\"" + month.get(0) + "\"}";
					
				}
				
				if(amount == null){
					
					return "{\"warn\":\"" + amount.get(0) + ",金额(元)为空！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
					
				}else{
					
					 try{
						    Integer.parseInt(amount.get(1));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + amount.get(0) + ",金额(元)输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
					  }
					
				}
				
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("group_id", SessionManager.getGroupId());
				
				returnMap.put("hos_id", SessionManager.getHosId());
				
				returnMap.put("copy_code", SessionManager.getCopyCode());
				
				returnMap.putAll(dictMap.get(cash_item_code.get(1))); // 存放 现金流量项目id、code等基本信息
				
				returnMap.put("month", month.get(1));
				
				returnMap.put("amount", amount.get(1));
				
				if(remark != null ){
					
					returnMap.put("remark", remark.get(1));
					
				}else{
					
					returnMap.put("remark", "");
				}
				returnList.add(returnMap);
					
			}
			
			StringBuffer err_sb = new StringBuffer();
			
			//校验 数据重复
			for( int i = 1; i < returnList.size(); i++ ){
				
				for(int j = i + 1 ; j < returnList.size(); j++){
					
					
					if(returnList.get(i).get("cash_item_code").equals(returnList.get(j).get("cash_item_code"))){
						
						err_sb.append(returnList.get(i).get("cash_item_code")+"现金流量项目编码");
					}
				}
				
			}
			
			if( err_sb.length() > 0){
				
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				
			}else{
				
				String str = addBatch(returnList);
				
				if("false".equals(JSONObject.parseObject(str).get("state"))){
					
					return str ;
				}else{
					
					return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
}
