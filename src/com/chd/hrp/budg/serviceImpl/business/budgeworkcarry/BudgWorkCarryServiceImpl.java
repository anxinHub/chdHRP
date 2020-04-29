/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.budgeworkcarry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.dao.budgcash.BudgCarryMapper;
import com.chd.hrp.budg.dao.business.budgeworkcheck.BudgWorkCheckMapper;
import com.chd.hrp.budg.dao.business.compilationplan.budg.BudgWorkDeptMonthMapper;
import com.chd.hrp.budg.dao.business.compilationplan.budg.BudgWorkHosMonthMapper;
import com.chd.hrp.budg.entity.BudgCarry;
import com.chd.hrp.budg.service.business.budgeworkcarry.BudgWorkCarryService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;

/**
 * 
 * @Description:
 * 业务预算结转
 * @Table:
 * BUDG_WORK_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkCarryService")
public class BudgWorkCarryServiceImpl implements BudgWorkCarryService{

	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(BudgWorkCarryServiceImpl.class);
	
	
	@Resource(name = "budgCarryMapper")
	private final BudgCarryMapper budgCarryMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "budgWorkCheckMapper")
	private final BudgWorkCheckMapper budgWorkCheckMapper = null;
	
	@Resource(name = "budgWorkDeptMonthMapper")
	private final BudgWorkDeptMonthMapper budgWorkDeptMonthMapper = null;
	
	@Resource(name = "budgWorkHosMonthMapper")
	private final BudgWorkHosMonthMapper budgWorkHosMonthMapper = null;
    
	/**
	 * 查询结转期间
	 */
	@Override
	public String queryYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		
		String pre_year_month = "";//已结转年月(上一结转月)
		String cur_year_month = "";//当前结转年月
		
		//1.查询预算结账表数据:业务预算已结账数据
		entityMap.put("work_flag", 1);
		List<Map<String, Object>> budgCarryList = budgCarryMapper.queryBudgCarry(entityMap);
		
		//2.查询当前模块启用年度
		entityMap.put("mod_code", "0201");//业务预算模块编码
		cur_year_month = budgCarryMapper.queryYearMonthStart(entityMap);//获取业务预算启用年月
		//2.1未启用,返回空
		if(cur_year_month == null){
			return "{\"cur_year_month\":\"\"," + "\"pre_year_month\":\"\"}";
		}
		
		//3.判断账表是否有数据,无数据,当前结转月为系统模块启用年月
		if(budgCarryList.size() == 0){
			return "{\"cur_year_month\":\"" + cur_year_month + "\"," + "\"pre_year_month\":\"" + pre_year_month + "\"}";
		}
		
		//4.取已结转月(上一结转月)
		Map<String, Object> budgCarryMap = budgCarryList.get(budgCarryList.size()-1);
		pre_year_month = String.valueOf(budgCarryMap.get("year")) + String.valueOf(budgCarryMap.get("month"));
		
		//5.取待结转月
		cur_year_month = DateUtil.getNextYear_Month(pre_year_month);
		
		return "{\"cur_year_month\":\"" + cur_year_month + "\"," + "\"pre_year_month\":\"" + pre_year_month + "\"}";
		
	}
	
	
	/**
	 * 结转
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String chargeBudgWorkCarry(Map<String,Object> entityMap) throws DataAccessException {
		
		try {
			
			//1.判断预算管理系统模块是否启用
			entityMap.put("mod_code", "02");
			ModStart budgModStart = modStartMapper.queryModStartByCode(entityMap);
			
			//1.1判断是否增加模块内置数据
			if(budgModStart == null){
				return "{\"warn\":\"未添加模块内置数据,请联系管理员 \"}";
			}
			
			//1.2判断预算管理系统是否启用
			if(budgModStart.getCreate_user() == null){
				return "{\"warn\":\"预算管理系统模块未启用 \"}";
			}
			
			//1.3判断业务预算模块是否启用
			entityMap.put("mod_code", "0201");
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if(modStart == null){
				return "{\"warn\":\"未启用业务预算管理模块 \"}";
			}
			
			//2.判断是当前年月是否已经存在账表数据
			BudgCarry budgCarry = budgCarryMapper.queryBudgCarryByCode(entityMap);
			if(budgCarry != null){
				//2.1判断是否已经结转
				if(budgCarry.getWork_flag() == 1){
					return "{\"warn\":\"当前年月已经结转,不能重复结转 \"}";
				}
			}
			
			entityMap.put("is_state", "04");
			//3.判断当前年度预算是否存在未审批下达数据(budg_work_check)
			List<Map<String,Object>> workCheckList = (List<Map<String,Object>>)budgWorkCheckMapper.query(entityMap);
			if(workCheckList.size() > 0 ){
				return "{\"warn\":\"业务预算未下达,不可结转 \"}";
			}
			
			//组装账表默认数据
			Map<String,Object> budgCarryMap = new HashMap<String,Object>();
			
			budgCarryMap.put("group_id",entityMap.get("group_id"));
			budgCarryMap.put("hos_id", entityMap.get("hos_id"));
			budgCarryMap.put("copy_code", entityMap.get("copy_code"));
			budgCarryMap.put("year", entityMap.get("year"));
			budgCarryMap.put("month", entityMap.get("month"));
			budgCarryMap.put("work_flag", "1");
			budgCarryMap.put("work_user",SessionManager.getUserCode());
			budgCarryMap.put("work_date",DateUtil.getCurrenDate());
			budgCarryMap.put("income_flag", "0");
			budgCarryMap.put("income_user", "");
			budgCarryMap.put("income_date", "");
			budgCarryMap.put("cost_flag", "0");
			budgCarryMap.put("cost_user", "");
			budgCarryMap.put("cost_date", "");
			budgCarryMap.put("pur_flag", "0");
			budgCarryMap.put("pur_user", "");
			budgCarryMap.put("pur_date", "");
			budgCarryMap.put("proj_flag", "0");
			budgCarryMap.put("proj_user", "");
			budgCarryMap.put("proj_date", "");
			budgCarryMap.put("cash_flag", "0");
			budgCarryMap.put("cash_user", "");
			budgCarryMap.put("cash_date", "");
			
			if("12".equals(String.valueOf(entityMap.get("month")))){
				
				if(budgCarry == null){
					budgCarryMapper.addBudgCarry(budgCarryMap);
				}else{
					entityMap.put("work_flag", "1");
					entityMap.put("work_user",SessionManager.getUserCode());
					entityMap.put("work_date",DateUtil.getCurrenDate());
					budgCarryMapper.updateBudgCarry(entityMap);
				}
				return "{\"msg\":\"结转成功\",\"state\":\"true\"}";
			}
			
			String next_year_month = DateUtil.getNextYear_Month(String.valueOf(entityMap.get("cur_year_month")));//获取下月
			
			//4.查询医院月份业务预算
			List<Map<String, Object>> hosMonthList = budgWorkHosMonthMapper.queryBudgWorkHosMonthByYearMonth(entityMap);
			List<Map<String,Object>> hosMonthUpdateList = new ArrayList<Map<String,Object>>();
			
			//4.1处理医院业务收入数据
			//组装本月要修改下月结转的数据
			//组装下月要修改上月结转的数据
			for(Map<String,Object> obj : hosMonthList){
				if("0".equals(obj.get("is_carried"))){//如果指标结转类型为否
					continue;
				}
				
				Double last_month_carried = Double.parseDouble(String.valueOf(obj.get("last_month_carried")));//上月结转
				Double budg_value = Double.parseDouble(String.valueOf(obj.get("budg_value")));//本月预算
				Double execute_value = Double.parseDouble(String.valueOf(obj.get("execute_value")));//本月执行
				
				Double carried_next_month = last_month_carried + budg_value - execute_value;//下月结转值=上月结转+本月预算-本月执行
				
				if(carried_next_month < 0){
					return "{\"warn\":\"本月结转值小于0,不能结转 \"}";
				}
				
				//修改当前月数据的下月结转值Map
				Map<String,Object> hosMonthCurMap = new HashMap<String,Object>();
				hosMonthCurMap.put("group_id", obj.get("group_id"));
				hosMonthCurMap.put("hos_id", obj.get("hos_id"));
				hosMonthCurMap.put("copy_code", obj.get("copy_code"));
				hosMonthCurMap.put("year", obj.get("year"));
				hosMonthCurMap.put("month", obj.get("month"));
				hosMonthCurMap.put("index_code", obj.get("index_code"));
				hosMonthCurMap.put("carried_next_month", carried_next_month);
				
				hosMonthUpdateList.add(hosMonthCurMap);
				
				//修改下月数据的上月结转值Map
				Map<String,Object> hosMonthPreMap = new HashMap<String,Object>();
				hosMonthPreMap.put("group_id", obj.get("group_id"));
				hosMonthPreMap.put("hos_id", obj.get("hos_id"));
				hosMonthPreMap.put("copy_code", obj.get("copy_code"));
				hosMonthPreMap.put("year", next_year_month.substring(0, 4));
				hosMonthPreMap.put("month", next_year_month.substring(4, 6));
				hosMonthPreMap.put("index_code", obj.get("index_code"));
				hosMonthPreMap.put("last_month_carried", carried_next_month);
				hosMonthUpdateList.add(hosMonthPreMap);
			}
			
			//5.查询科室月份业务预算
			List<Map<String,Object>> deptMonthList  = budgWorkDeptMonthMapper.queryBudgWorkDeptMonthByYearMonth(entityMap);
			
			List<Map<String,Object>> deptMonthUpdateList = new ArrayList<Map<String,Object>>();
			
			//5.1处理科室业务收入数据
			//组装本月要修改下月结转的数据
			//组装下月要修改上月结转的数据
			for(Map<String,Object> deptMonthMap : deptMonthList){
				if("0".equals(deptMonthMap.get("is_carried"))){//如果指标结转类型为否
					continue;
				}
				
				Double last_month_carried = Double.parseDouble(String.valueOf(deptMonthMap.get("last_month_carried")));//上月结转
				Double budg_value = Double.parseDouble(String.valueOf(deptMonthMap.get("budg_value")));//本月预算
				Double execute_value = Double.parseDouble(String.valueOf(deptMonthMap.get("execute_value")));//本月执行
				
				Double carried_next_month = last_month_carried + budg_value - execute_value;//下月结转值=上月结转+本月预算-本月执行
				
				if(carried_next_month < 0){
					return "{\"warn\":\"本月结转值小于0,不能结转 \"}";
				}
				
				//修改当前月数据的下月结转值Map
				Map<String,Object> deptMonthCurMap = new HashMap<String,Object>();
				deptMonthCurMap.put("group_id", deptMonthMap.get("group_id"));
				deptMonthCurMap.put("hos_id", deptMonthMap.get("hos_id"));
				deptMonthCurMap.put("copy_code", deptMonthMap.get("copy_code"));
				deptMonthCurMap.put("year", deptMonthMap.get("year"));
				deptMonthCurMap.put("month", deptMonthMap.get("month"));
				deptMonthCurMap.put("dept_id", deptMonthMap.get("dept_id"));
				deptMonthCurMap.put("index_code", deptMonthMap.get("index_code"));
				deptMonthCurMap.put("carried_next_month", carried_next_month);
				
				deptMonthUpdateList.add(deptMonthCurMap);
				
				//修改下月数据的上月结转值Map
				Map<String,Object> deptMonthPreMap = new HashMap<String,Object>();
				deptMonthPreMap.put("group_id", deptMonthMap.get("group_id"));
				deptMonthPreMap.put("hos_id", deptMonthMap.get("hos_id"));
				deptMonthPreMap.put("copy_code", deptMonthMap.get("copy_code"));
				deptMonthPreMap.put("year", next_year_month.substring(0, 4));
				deptMonthPreMap.put("month", next_year_month.substring(4, 6));
				deptMonthPreMap.put("dept_id", deptMonthMap.get("dept_id"));
				deptMonthPreMap.put("index_code", deptMonthMap.get("index_code"));
				deptMonthPreMap.put("last_month_carried", carried_next_month);
				deptMonthUpdateList.add(deptMonthPreMap);
			}
			
			entityMap.put("work_flag", 1);
			entityMap.put("work_user", SessionManager.getUserCode());
			entityMap.put("work_date", DateUtil.getCurrenDate());
			
			//6.修改医院业务预算当前年月数据的下月结转值
			if(hosMonthUpdateList.size() > 0){
				budgWorkHosMonthMapper.updateBatchBudgWorkHosMonth(hosMonthUpdateList);
			}
			
			
			//7.修改科室业务预算下月数据的上月结转值
			if(deptMonthUpdateList.size() > 0){
				budgWorkDeptMonthMapper.updateBatchBudgWorkDeptMonth(deptMonthUpdateList);
			}
			
			//8.更新账表数据
			if(budgCarry == null){//当前年月无账表数据:新增
				
				budgCarryMapper.addBudgCarry(budgCarryMap);
			}else{//修改结账标记、结账人、结账日期
				
				budgCarryMapper.updateBudgCarry(entityMap);
			}
			
			return "{\"msg\":\"结转成功\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}

	}

	/**
	 * 反结
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String reChargeBudgWorkCarry(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			//1.查询当前模块启用年度
			/*entityMap.put("mod_code", "0201");//采购预算模块编码
			String start_year_month = budgCarryMapper.queryYearMonthStart(entityMap);//获取采购预算启用年月
			if(String.valueOf(entityMap.get("pre_year_month")).equals(start_year_month)){
				return "{\"warn\":\"当前年月为业务预算模块启用年月,不能反结  \"}";
			}*/
			
			//获取反结年月(已结转年月)
			String cur_year_month = String.valueOf(entityMap.get("year")) + String.valueOf(entityMap.get("month"));
			
			//获取待结转年月
			String next_year_month = DateUtil.getNextYear_Month(cur_year_month);
			
			
			List<Map<String,Object>> deptMonthUpdateList = new ArrayList<Map<String,Object>>();//修改科室业务预算List
			List<Map<String,Object>> HosMonthUpdateList = new ArrayList<Map<String,Object>>();//修改医院业务预算List
			
			//1.修改下月数据的上月结转值为0,组装Map
			Map<String, Object> NextYearMonthMap = new HashMap<String, Object>();
			NextYearMonthMap.put("group_id", entityMap.get("group_id"));
			NextYearMonthMap.put("hos_id", entityMap.get("hos_id"));
			NextYearMonthMap.put("copy_code", entityMap.get("copy_code"));
			NextYearMonthMap.put("year",next_year_month.substring(0, 4));
			NextYearMonthMap.put("month",next_year_month.substring(4, 6));
			NextYearMonthMap.put("last_month_carried", 0);
			
			//1.1将要修改的下月数据的上月结转Map,存入List
			deptMonthUpdateList.add(NextYearMonthMap);
			HosMonthUpdateList.add(NextYearMonthMap);
			
			//2.修改本月数据的下月结转值为0
			Map<String, Object> CurYearMonthMap = new HashMap<String, Object>();
			CurYearMonthMap.put("group_id", entityMap.get("group_id"));
			CurYearMonthMap.put("hos_id", entityMap.get("hos_id"));
			CurYearMonthMap.put("copy_code", entityMap.get("copy_code"));
			CurYearMonthMap.put("year",entityMap.get("year"));
			CurYearMonthMap.put("month",entityMap.get("month"));
			CurYearMonthMap.put("carried_next_month", 0);
			
			//2.1将要修改的本月数据的下月结转改Map,存入LiST
			deptMonthUpdateList.add(CurYearMonthMap);
			HosMonthUpdateList.add(CurYearMonthMap);
			
			//3.修改科室月份业务预算表
			budgWorkDeptMonthMapper.updateBatchBudgWorkDeptMonth(deptMonthUpdateList);
			
			//4.修改医院月份业务预算表
			budgWorkHosMonthMapper.updateBatchBudgWorkHosMonth(HosMonthUpdateList);
			
			
			//5.修改本月结账标记为0,结转日期为空,结账人为空
			
			entityMap.put("work_flag", "0");
			entityMap.put("work_user","");
			entityMap.put("work_date","");
			budgCarryMapper.updateBudgCarry(entityMap);
			
			return "{\"msg\":\"反结成功\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
}
	

