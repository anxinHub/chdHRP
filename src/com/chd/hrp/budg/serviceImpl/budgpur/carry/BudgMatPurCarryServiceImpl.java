package com.chd.hrp.budg.serviceImpl.budgpur.carry;

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
import com.chd.hrp.budg.dao.budgpur.matpur.BudgMatPurCheckMapper;
import com.chd.hrp.budg.dao.budgpur.medpur.BudgMedPurCheckMapper;
import com.chd.hrp.budg.entity.BudgCarry;
import com.chd.hrp.budg.service.budgpur.carry.BudgMatPurCarryService;
import com.chd.hrp.budg.serviceImpl.budgcost.carry.BudgCostCarryServiceImpl;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;

/**
 * 
 * @Description:
 * 材料采购预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgMatPurCarryService")
public class BudgMatPurCarryServiceImpl implements BudgMatPurCarryService {
	
	private static Logger logger = Logger.getLogger(BudgMatPurCarryServiceImpl.class);
	
	@Resource(name = "budgCarryMapper")
	private final BudgCarryMapper budgCarryMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "budgMatPurCheckMapper")
	private final BudgMatPurCheckMapper budgMatPurCheckMapper = null;
	
	@Resource(name = "budgMedPurCheckMapper")
	private final BudgMedPurCheckMapper budgMedPurCheckMapper = null;

	@Override
	public String chargeBudgMatPurCarry(Map<String, Object> entityMap) throws DataAccessException {
		
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
			entityMap.put("mod_code", "0205");
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if(modStart == null){
				return "{\"warn\":\"未启用采购预算管理模块 \"}";
			}
			
			//2.判断是当前年月是否已经存在账表数据
			BudgCarry budgCarry = budgCarryMapper.queryBudgCarryByCode(entityMap);
			if(budgCarry != null){
				//2.1判断是否已经结转
				if(budgCarry.getPur_flag() == 1){
					return "{\"warn\":\"当前年月已经结转,不能重复结转 \"}";
				}
			}
			
			entityMap.put("is_state", "04");
			
			//3.判断是否存在未审批下达的数据
			//3.1判断当前年度材料采购预算是否存在未审批下达数据(BUDG_MAT_PUR_CHECK)
			List<Map<String,Object>> matPurCheckList = (List<Map<String,Object>>)budgMatPurCheckMapper.query(entityMap);
			if(matPurCheckList.size() > 0){
				return "{\"warn\":\"材料采购预算未下达,不可结转 \"}";
			}
			
			//3.2判断当前年度药品采购预算是否存在未审批下达数据(BUDG_MED_PUR_CHECK)
			List<Map<String,Object>> medPurCheckList = (List<Map<String,Object>>)budgMedPurCheckMapper.query(entityMap);
			if(medPurCheckList.size() > 0){
				return "{\"warn\":\"药品采购预算未下达,不可结转 \"}";
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
			budgCarryMap.put("cost_flag", "0");
			budgCarryMap.put("cost_user","");
			budgCarryMap.put("cost_date","");
			budgCarryMap.put("pur_flag", "1");
			budgCarryMap.put("pur_user", SessionManager.getUserCode());
			budgCarryMap.put("pur_date", DateUtil.getCurrenDate());
			budgCarryMap.put("proj_flag", "0");
			budgCarryMap.put("proj_user", "");
			budgCarryMap.put("proj_date", "");
			budgCarryMap.put("cash_flag", "0");
			budgCarryMap.put("cash_user", "");
			budgCarryMap.put("cash_date", "");
			
			
			//8.更新账表数据
			if(budgCarry == null){//当前年月无账表数据:新增
				
				budgCarryMapper.addBudgCarry(budgCarryMap);
			}else{//修改结账标记、结账人、结账日期
				
				entityMap.put("pur_flag", "1");
				entityMap.put("pur_user",SessionManager.getUserCode());
				entityMap.put("pur_date",DateUtil.getCurrenDate());
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
	public String reChargeBudgMatPurCarry(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			//1.查询当前模块启用年度
			/*entityMap.put("mod_code", "0205");//采购预算模块编码
			String state_year_month = budgCarryMapper.queryYearMonthStart(entityMap);//获取采购预算启用年月
			if(String.valueOf(entityMap.get("pre_year_month")).equals(state_year_month)){
				return "{\"warn\":\"当前年月为采购预算模块启用年月,不能反结  \"}";
			}*/
			
			//2.修改结账标记
			entityMap.put("pur_flag", "0");
			entityMap.put("pur_user","");
			entityMap.put("pur_date","");
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
		
		//1.查询预算结账表数据:采购预算已结账数据
		entityMap.put("pur_flag", 1);
		List<Map<String, Object>> budgCarryList = budgCarryMapper.queryBudgCarry(entityMap);
		
		//2.查询当前模块启用年度
		entityMap.put("mod_code", "0205");//采购预算模块编码
		cur_year_month = budgCarryMapper.queryYearMonthStart(entityMap);//获取采购预算启用年月
		
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
