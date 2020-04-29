package com.chd.hrp.budg.serviceImpl.budgcost.carry;

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
import com.chd.hrp.budg.dao.budgcost.elsecost.BudgElseCostCheckMapper;
import com.chd.hrp.budg.dao.budgcost.elsecost.BudgElseCostMapper;
import com.chd.hrp.budg.dao.budgcost.medcostcheck.BudgMedCostCheckMapper;
import com.chd.hrp.budg.dao.business.med.BudgMedCostMapper;
import com.chd.hrp.budg.entity.BudgCarry;
import com.chd.hrp.budg.service.budgcost.carry.BudgCostCarryService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
/**
 * 
 * @Description:
 * 医疗支出预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
@Service("budgCostCarryService")
public class BudgCostCarryServiceImpl implements BudgCostCarryService {
	
	private static Logger logger = Logger.getLogger(BudgCostCarryServiceImpl.class);
	
	@Resource(name = "budgCarryMapper")
	private final BudgCarryMapper budgCarryMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "budgMedCostCheckMapper")
	private final BudgMedCostCheckMapper budgMedCostCheckMapper = null;
	
	@Resource(name = "budgElseCostCheckMapper")
	private final BudgElseCostCheckMapper budgElseCostCheckMapper = null;
	
	@Resource(name = "budgMedCostMapper")
	private final BudgMedCostMapper budgMedCostMapper = null;
	
	@Resource(name = "budgElseCostMapper")
	private final BudgElseCostMapper budgElseCostMapper = null;

	@Override
	public String chargeBudgCostCarry(Map<String, Object> entityMap) throws DataAccessException {
		
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
			
			//1.3判断支出预算模块是否启用
			entityMap.put("mod_code", "0203");
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if(modStart == null){
				return "{\"warn\":\"未启用支出预算管理模块 \"}";
			}
			
			//2.判断是当前年月是否已经存在账表数据
			BudgCarry budgCarry = budgCarryMapper.queryBudgCarryByCode(entityMap);
			if(budgCarry != null){
				//2.1判断是否已经结转
				if(budgCarry.getCost_flag() == 1){
					return "{\"warn\":\"当前年月已经结转,不能重复结转 \"}";
				}
			}
			
			entityMap.put("is_state", "04");
			
			//3.判断是否存在未审批下达的数据
			
			//3.1判断当前年度医疗支出预算是否存在未审批下达数据(BUDG_MED_COST_CHECK)
			List<Map<String,Object>> medCostCheckList = (List<Map<String,Object>>)budgMedCostCheckMapper.query(entityMap);
			if(medCostCheckList.size() > 0){
				return "{\"warn\":\"医疗支出预算未下达,不可结转 \"}";
			}
			
			//3.2判断当前年度其他支出预算是否存在未审批下达数据(BUDG_ELSE_COST_CHECK)
			List<Map<String,Object>> elseIncomeCheckList = (List<Map<String,Object>>)budgElseCostCheckMapper.query(entityMap);
			if(elseIncomeCheckList.size() > 0){
				return "{\"warn\":\"其他支出预算未下达,不可结转 \"}";
			}
			
			//组装账表默认数据
			Map<String,Object> budgCarryMap = new HashMap<String,Object>();
			
			budgCarryMap.put("group_id",entityMap.get("group_id"));
			budgCarryMap.put("hos_id", entityMap.get("hos_id"));
			budgCarryMap.put("copy_code", entityMap.get("copy_code"));
			budgCarryMap.put("year", entityMap.get("year"));
			budgCarryMap.put("month", entityMap.get("month"));
			budgCarryMap.put("work_flag", "0");
			budgCarryMap.put("work_user","");
			budgCarryMap.put("work_date","");
			budgCarryMap.put("income_flag", "0");
			budgCarryMap.put("income_user", "");
			budgCarryMap.put("income_date","");
			budgCarryMap.put("cost_flag", "1");
			budgCarryMap.put("cost_user", SessionManager.getUserCode());
			budgCarryMap.put("cost_date", DateUtil.getCurrenDate());
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
					entityMap.put("cost_flag", "1");
					entityMap.put("cost_user",SessionManager.getUserCode());
					entityMap.put("cost_date",DateUtil.getCurrenDate());
					budgCarryMapper.updateBudgCarry(entityMap);
				}
				return "{\"msg\":\"结转成功\",\"state\":\"true\"}";
			}
			
			
			
			
			String next_year_month = DateUtil.getNextYear_Month(String.valueOf(entityMap.get("cur_year_month")));//获取下月
			
			
			//5.查询支出预算数据
			List<Map<String,Object>> medCostList  = budgMedCostMapper.queryBudgMedCostByYearMonth(entityMap);
			
			List<Map<String,Object>> medCostUpdateList = new ArrayList<Map<String,Object>>();
			
			//5.1 处理支出预算数据
			//组装本月要修改下月结转的数据
			//组装下月要修改上月结转的数据
			for(Map<String,Object> obj : medCostList){
				if("0".equals(obj.get("is_carried"))){//如果指标结转类型为否
					continue;
				}
				
				Double carried_next_month = Double.parseDouble(String.valueOf(obj.get("next_value")));//获取下月结转值
				
				if(carried_next_month < 0){
					return "{\"warn\":\"医疗支出预算本月结转值小于0,不能结转 \"}";
				}
				
				//修改当前月数据的下月结转值Map
				Map<String,Object> costCurMonthMap = new HashMap<String,Object>();
				costCurMonthMap.put("group_id", obj.get("group_id"));
				costCurMonthMap.put("hos_id", obj.get("hos_id"));
				costCurMonthMap.put("copy_code", obj.get("copy_code"));
				costCurMonthMap.put("budg_year", obj.get("budg_year"));
				costCurMonthMap.put("month", obj.get("month"));
				costCurMonthMap.put("dept_id", obj.get("dept_id"));
				costCurMonthMap.put("subj_code", obj.get("subj_code"));
				costCurMonthMap.put("carried_next_month", carried_next_month);
				
				medCostUpdateList.add(costCurMonthMap);
				
				//修改下月数据的上月结转值Map
				Map<String,Object> costNextMonthMap = new HashMap<String,Object>();
				costNextMonthMap.put("group_id", obj.get("group_id"));
				costNextMonthMap.put("hos_id", obj.get("hos_id"));
				costNextMonthMap.put("copy_code", obj.get("copy_code"));
				costNextMonthMap.put("budg_year", next_year_month.substring(0, 4));
				costNextMonthMap.put("month", next_year_month.substring(4, 6));
				costNextMonthMap.put("dept_id", obj.get("dept_id"));
				costNextMonthMap.put("subj_code", obj.get("subj_code"));
				costNextMonthMap.put("last_month_carried", carried_next_month);
				medCostUpdateList.add(costNextMonthMap);
			}
			
			
			//查询其它支出预算预算
			List<Map<String,Object>> elseCostList = budgElseCostMapper.queryBudgElseCostByYearMonth(entityMap);
			List<Map<String,Object>> elseCostUpdateList = new ArrayList<Map<String,Object>>();
			
			
			//5.1 处理其它支出预算数据
			//组装本月要修改下月结转的数据
			//组装下月要修改上月结转的数据
			for(Map<String,Object> obj : elseCostList){
				if("0".equals(obj.get("is_carried"))){//如果指标结转类型为否
					continue;
				}
				
				Double carried_next_month = Double.parseDouble(String.valueOf(obj.get("next_value")));//获取下月结转值
				
				if(carried_next_month < 0){
					return "{\"warn\":\"其他支出预算本月结转值小于0,不能结转 \"}";
				}
				
				//修改当前月数据的下月结转值Map
				Map<String,Object> elseCostCurMonthMap = new HashMap<String,Object>();
				elseCostCurMonthMap.put("group_id", obj.get("group_id"));
				elseCostCurMonthMap.put("hos_id", obj.get("hos_id"));
				elseCostCurMonthMap.put("copy_code", obj.get("copy_code"));
				elseCostCurMonthMap.put("budg_year", obj.get("budg_year"));
				elseCostCurMonthMap.put("month", obj.get("month"));
				elseCostCurMonthMap.put("subj_code", obj.get("subj_code"));
				elseCostCurMonthMap.put("carried_next_month", carried_next_month);
				
				elseCostUpdateList.add(elseCostCurMonthMap);
				
				//修改下月数据的上月结转值Map
				Map<String,Object> elseCostNextMonthMap = new HashMap<String,Object>();
				elseCostNextMonthMap.put("group_id", obj.get("group_id"));
				elseCostNextMonthMap.put("hos_id", obj.get("hos_id"));
				elseCostNextMonthMap.put("copy_code", obj.get("copy_code"));
				elseCostNextMonthMap.put("budg_year", next_year_month.substring(0, 4));
				elseCostNextMonthMap.put("month", next_year_month.substring(4, 6));
				elseCostNextMonthMap.put("subj_code", obj.get("subj_code"));
				elseCostNextMonthMap.put("last_month_carried", carried_next_month);
				elseCostUpdateList.add(elseCostNextMonthMap);
			}
			
			
			//6.修改医疗支出预算：本月的下月结转值和下月的上月结转值
			if(medCostUpdateList.size() > 0){
				budgMedCostMapper.updateBatchBudgMedCostValue(medCostUpdateList);
			}
			
			
			//7.修改其它支出预算：本月的下月结转值和下月的上月结转值
			if(elseCostUpdateList.size() > 0){
				budgElseCostMapper.updateBatchBudgElseCostValue(elseCostUpdateList);
			}
			
			//8.更新账表数据
			if(budgCarry == null){//当前年月无账表数据:新增
				
				budgCarryMapper.addBudgCarry(budgCarryMap);
			}else{//修改结账标记、结账人、结账日期
				
				entityMap.put("cost_flag", "1");
				entityMap.put("cost_user",SessionManager.getUserCode());
				entityMap.put("cost_date",DateUtil.getCurrenDate());
				budgCarryMapper.updateBudgCarry(entityMap);
			}
			
			return "{\"msg\":\"结转成功\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
	
	

	@Override
	public String reChargeBudgCostCarry(Map<String, Object> entityMap) throws DataAccessException {

		try {
			//1.查询当前模块启用年度
			/*entityMap.put("mod_code", "0203");//采购预算模块编码
			String start_year_month = budgCarryMapper.queryYearMonthStart(entityMap);//获取采购预算启用年月
			if(String.valueOf(entityMap.get("pre_year_month")).equals(start_year_month)){
				return "{\"warn\":\"当前年月为支出预算模块启用年月,不能反结  \"}";
			}*/
			
			//获取已结转年月
			String cur_year_month = String.valueOf(entityMap.get("year")) + String.valueOf(entityMap.get("month"));
			
			//获取待结转年月
			String next_year_month = DateUtil.getNextYear_Month(cur_year_month);
			
			List<Map<String,Object>> medCostUpdateList = new ArrayList<Map<String,Object>>();//修改医疗支出预算List
			List<Map<String,Object>> elseCostUpdateList = new ArrayList<Map<String,Object>>();//修改其他支出预算List
			
			//1.修改下月数据的上月结转值为0,组装Map
			Map<String, Object> NextYearMonthMap = new HashMap<String, Object>();
			NextYearMonthMap.put("group_id", entityMap.get("group_id"));
			NextYearMonthMap.put("hos_id", entityMap.get("hos_id"));
			NextYearMonthMap.put("copy_code", entityMap.get("copy_code"));
			NextYearMonthMap.put("budg_year",next_year_month.substring(0, 4));
			NextYearMonthMap.put("month",next_year_month.substring(4, 6));
			NextYearMonthMap.put("last_month_carried", 0);
			
			//1.1将要修改的下月数据的上月结转Map,存入List
			medCostUpdateList.add(NextYearMonthMap);
			elseCostUpdateList.add(NextYearMonthMap);
			
			//2.修改本月数据的下月结转值为0
			Map<String, Object> CurYearMonthMap = new HashMap<String, Object>();
			CurYearMonthMap.put("group_id", entityMap.get("group_id"));
			CurYearMonthMap.put("hos_id", entityMap.get("hos_id"));
			CurYearMonthMap.put("copy_code", entityMap.get("copy_code"));
			CurYearMonthMap.put("budg_year",entityMap.get("year"));
			CurYearMonthMap.put("month",entityMap.get("month"));
			CurYearMonthMap.put("carried_next_month", 0);
			
			//2.1将要修改的本月数据的下月结转改Map,存入LiST
			medCostUpdateList.add(CurYearMonthMap);
			elseCostUpdateList.add(CurYearMonthMap);
			
			//3.修改医疗支出预算
			budgMedCostMapper.updateBatchBudgMedCostValue(medCostUpdateList);
			
			//4.修改其他支出预算
			budgElseCostMapper.updateBatchBudgElseCostValue(elseCostUpdateList);
			
			entityMap.put("cost_flag", "0");
			entityMap.put("cost_user","");
			entityMap.put("cost_date","");
			budgCarryMapper.updateBudgCarry(entityMap);
			
			return "{\"msg\":\"反结成功\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String queryYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		
		String pre_year_month = "";//已结转年月(上一结转月)
		String cur_year_month = "";//当前结转年月
		
		//1.查询预算结账表数据:支出预算已结账数据
		entityMap.put("cost_flag", 1);
		List<Map<String, Object>> budgCarryList = budgCarryMapper.queryBudgCarry(entityMap);
		
		//2.查询当前模块启用年度
		entityMap.put("mod_code", "0203");//支出预算模块编码
		cur_year_month = budgCarryMapper.queryYearMonthStart(entityMap);//获取支出预算启用年月
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

}
