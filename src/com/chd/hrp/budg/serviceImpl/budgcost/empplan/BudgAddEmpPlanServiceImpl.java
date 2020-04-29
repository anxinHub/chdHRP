/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcost.empplan;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.budg.dao.budgcost.empplan.BudgAddEmpDetailMapper;
import com.chd.hrp.budg.dao.budgcost.empplan.BudgAddEmpPlanMapper;
import com.chd.hrp.budg.entity.BudgAddEmpPlan;
import com.chd.hrp.budg.service.budgcost.empplan.BudgAddEmpPlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 状态（STATE），“01新建、02已审核”
 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
 * @Table:
 * BUDG_ADD_EMP_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgAddEmpPlanService")
public class BudgAddEmpPlanServiceImpl implements BudgAddEmpPlanService {

	private static Logger logger = Logger.getLogger(BudgAddEmpPlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgAddEmpPlanMapper")
	private final BudgAddEmpPlanMapper budgAddEmpPlanMapper = null;
	@Resource(name = "budgAddEmpDetailMapper")
	private final BudgAddEmpDetailMapper budgAddEmpDetailMapper = null;
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	/**
	 * @Description 
	 * 添加状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		try {	
			
			String str = "";
			
			//明细数据  添加用List
			List<Map<String,Object>> planList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("planData")));
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				//封装调整明细 数据信息
				Map<String,Object> planMap = new HashMap<String,Object>();
				
				planMap.put("group_id", String.valueOf(entityMap.get("group_id")));
				planMap.put("hos_id", String.valueOf(entityMap.get("hos_id")));
				planMap.put("copy_code", String.valueOf(entityMap.get("copy_code")));
				planMap.put("plan_year",String.valueOf(entityMap.get("plan_year")));//增员年度
				planMap.put("plan_code", String.valueOf(entityMap.get("plan_code")));//增员单号
				planMap.put("dept_id",String.valueOf(jsonObj.get("dept_id")).split(",")[0]);//部门ID
				planMap.put("dept_no",String.valueOf(jsonObj.get("dept_id")).split(",")[1]);//部门变更ID
				planMap.put("emp_type_code",String.valueOf(jsonObj.get("emp_type_code")));//职工类别
				
				planMap.put("add_num",  String.valueOf(jsonObj.get("add_num")));//增加人数
				planMap.put("in_month",  String.valueOf(jsonObj.get("in_month")));//计划到岗日期
				//在岗人数
				if(jsonObj.get("in_num")==null){
					planMap.put("in_num","");
				}else{
					planMap.put("in_num",  String.valueOf(jsonObj.get("in_num")));//在岗人数
				}
				//增员原因
				if(jsonObj.get("reason")==null){
					planMap.put("reason","");
				}else{
					planMap.put("reason",  String.valueOf(jsonObj.get("reason")));
				}
				
				planList.add(planMap);
			}	
			
			budgAddEmpPlanMapper.add(entityMap);
			 
			budgAddEmpDetailMapper.addBatch(planList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败.\",\"state\":\"false\"}");

		}
	}
	/**
	 * @Description 
	 * 批量添加状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgAddEmpPlanMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgAddEmpPlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgAddEmpPlanMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgAddEmpPlanMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		try {
			
			for (Map<String, Object> map : entityList) {
				//查询数据状态
				String state = budgAddEmpPlanMapper.queryState(map);
				//01 新建  02 已审核
				if("02".equals(state)){
					str += map.get("plan_code")+" ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"'单号为"+ str + "数据已审核,不可删除!\",\"state\":\"false\"}";
			}
			
			budgAddEmpDetailMapper.deleteBatch(entityList);
			
			budgAddEmpPlanMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败!  方法 deleteBatch\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{

		try{
			//查询数据状态
			String state = budgAddEmpPlanMapper.queryState(entityMap);
			//01 新建  02 已审核
			if("02".equals(state)){
				return "{\"error\":\"'单号为"+ entityMap.get("plan_code") + "数据已审核,不可修改!\",\"state\":\"false\"}";
			}
			
			//明细数据  添加用List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("planData")));
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				//封装调整明细 数据信息
				Map<String,Object> planMap = new HashMap<String,Object>();
				
				planMap.put("group_id", String.valueOf(entityMap.get("group_id")));
				planMap.put("hos_id", String.valueOf(entityMap.get("hos_id")));
				planMap.put("copy_code", String.valueOf(entityMap.get("copy_code")));
				planMap.put("plan_year",String.valueOf(entityMap.get("plan_year")));//增员年度
				planMap.put("plan_code", String.valueOf(entityMap.get("plan_code")));//增员单号
				planMap.put("dept_id",String.valueOf(jsonObj.get("dept_id")).split(",")[0]);//部门ID
				planMap.put("dept_no",String.valueOf(jsonObj.get("dept_id")).split(",")[1]);//部门变更ID
				planMap.put("emp_type_code",String.valueOf(jsonObj.get("emp_type_code")));//职工类别
				
				planMap.put("add_num",  String.valueOf(jsonObj.get("add_num")));//增加人数
				planMap.put("in_month",  String.valueOf(jsonObj.get("in_month")));//计划到岗日期
				//在岗人数
				if(jsonObj.get("in_num")==null){
					planMap.put("in_num","");
				}else{
					planMap.put("in_num",  String.valueOf(jsonObj.get("in_num")));//在岗人数
				}
				//增员原因
				if(jsonObj.get("reason")==null){
					planMap.put("reason","");
				}else{
					planMap.put("reason",  String.valueOf(jsonObj.get("reason")));
				}
				addList.add(planMap);
			}	
			
			budgAddEmpPlanMapper.update(entityMap);
			
			budgAddEmpDetailMapper.delete(entityMap); 
			budgAddEmpDetailMapper.addBatch(addList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	
		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
	
		}
	}
	/**
	 * @Description 
	 * 查询结果集状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgAddEmpPlan> list = (List<BudgAddEmpPlan>)budgAddEmpPlanMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgAddEmpPlan> list = (List<BudgAddEmpPlan>)budgAddEmpPlanMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgAddEmpPlanMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgAddEmpPlan
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgAddEmpPlanMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgAddEmpPlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgAddEmpPlanMapper.queryExists(entityMap);
	}
	
	/**
	 * 职工类别下拉框
	 */
	@Override
	public String queryBudgHosDept(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgAddEmpPlanMapper.queryBudgHosDept(mapVo));
	}
	
	/**
	 * 审核  销审 更新状态
	 */
	@Override
	public String updateReviewState(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {
			for (Map<String, Object> map : entityList) {
				
				String str = "";	
				
				String plan_code = String.valueOf(map.get("plan_code"));//获取单号 
				
				String state = budgAddEmpPlanMapper.queryState(map);
				//01 新建状态  下允许审核
				if(!"01".equals(state)){
					
					str += (plan_code+" ");
					
				}else{
					//审核时  更改审核人  审核日期 为 当前操作人  操作日期
					map.put("checker", SessionManager.getUserId());
					//获取当前日期  Date类型
					Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
					map.put("check_date", date);
					//销审时  更改状态为已审核
					map.put("state", "02");
				}
			}
			budgAddEmpPlanMapper.updateReviewState(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员!\"}");
		}	
	}
	
	
	/**
	 * 销审    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateCancelBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			for (Map<String, Object> map : entityList) {
				String str = "";	
				//更新年度明细后  更改自身数据状态 为已审核
				String plan_code = String.valueOf(map.get("plan_code"));//获取单号 
				
				String state = budgAddEmpPlanMapper.queryState(map);
				//03 审核状态  下允许销审
				if(!"02".equals(state)){
					
					str += (plan_code+" ");
					
				}else{
					//销审时  更改审核人  审核日期 为 ""
					map.put("checker","");
					
					map.put("check_date", "");
					
					//销审时  更改状态为已审核
					map.put("state", "01");
				}
			}
			
			budgAddEmpPlanMapper.updateReviewState(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员!\"}");

		}	
	}
	@Override
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) {
		
		return budgAddEmpPlanMapper.queryDataByCode(mapVo);
	}
	
}
