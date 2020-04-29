/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.budgotherexpense;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.budg.dao.business.budgotherexpense.BudgExpApplyDetailMapper;
import com.chd.hrp.budg.dao.business.budgotherexpense.BudgExpenseApplyMapper;
import com.chd.hrp.budg.entity.BudgExpenseApply;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpenseApplyService;
import com.github.pagehelper.PageInfo;

/**
 *  
 * @Description: 费用申报
 * @Table: BUDG_EXPENSE_APPLY
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgExpenseApplyService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BudgExpenseApplyServiceImpl implements BudgExpenseApplyService {

	private static Logger logger = Logger.getLogger(BudgExpenseApplyServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgExpenseApplyMapper")
	private final BudgExpenseApplyMapper budgExpenseApplyMapper = null;

	@Resource(name = "budgExpApplyDetailMapper")
	private final BudgExpApplyDetailMapper budgExpApplyDetailMapper = null;

	@Resource(name = "budgNoManagerService")
	private final BudgNoManagerService budgNoManagerService = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		String apply_id = String.valueOf(entityMap.get("apply_id"));
		try {
			BudgExpenseApply budgExpenseApply = budgExpenseApplyMapper.queryByCode(entityMap);
			if (budgExpenseApply != null) {
				apply_id = budgExpenseApply.getApply_id();
				budgExpenseApplyMapper.update(entityMap);
				budgExpApplyDetailMapper.delete(entityMap);
			} else {
				entityMap.put("state", "01");
				entityMap.put("table_name", "费用申报");
				entityMap.put("prefixe", "FYSB");
				entityMap.put("table_code", "BUDG_EXPENSE_APPLY");
				apply_id = budgNoManagerService.getBillNOSeqNo(entityMap);
				entityMap.put("apply_id", apply_id);
				int state = budgExpenseApplyMapper.add(entityMap);
				if (state > 0) {
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_EXPENSE_APPLY");
				}
			}

			List<Map> maps = JSONArray.parseArray(entityMap.get("ParamVo").toString(), Map.class);
			if(maps!=null){
			for (Map<String, Object> map : maps) {
				if (null != map.get("month") && null != map.get("reson") && null != map.get("amount")) {
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("budg_year", entityMap.get("budg_year"));
					map.put("apply_id", apply_id);
					budgExpApplyDetailMapper.add(map);
				}
			}
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"apply_id\":\"" + apply_id + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try{
			budgExpenseApplyMapper.addBatch(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		 
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			for (Map<String, Object> map : entityMap) {
				budgExpApplyDetailMapper.delete(map);
			}
			budgExpenseApplyMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgExpenseApply> list = (List<BudgExpenseApply>) budgExpenseApplyMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgExpenseApply> list = (List<BudgExpenseApply>) budgExpenseApplyMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgExpenseApplyMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException {
		/*RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}*/

		return JSON.toJSONString(budgExpenseApplyMapper.queryBudgPaymentItem(mapVo));
	}

	@Override
	public String queryEmpDict(Map<String, Object> mapVo) throws DataAccessException {
		/*RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}*/

		return JSON.toJSONString(budgExpenseApplyMapper.queryEmpDict(mapVo));
	}
	
	@Override
	public Map<String, Object> queryEmpDictByCode(Map<String, Object> mapVo) throws DataAccessException {
		return budgExpenseApplyMapper.queryEmpDictByCode(mapVo);
	}

	@Override
	public String queryBudgCostDutyDept(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}

		return JSON.toJSONString(budgExpenseApplyMapper.queryBudgCostDutyDept(mapVo, rowBounds));
	}

	@Override
	public String queryBudgExpApplyDetail(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) budgExpApplyDetailMapper
				.queryBudgExpApplyDetail(mapVo);
		return ChdJson.toJson(list);
	}

	/**
	 * 审核 、 消审
	 */
	@Override
	public String auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException {
		
		budgExpenseApplyMapper.auditOrUnAudit(listVo);
			
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	@Override
	public String affiOrUnAffi(List<Map<String, Object>> listVo) throws DataAccessException {
		
		budgExpenseApplyMapper.affiOrUnAffi(listVo);
			
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 根据 所传参数 查询数据状态
	 */
	@Override
	public List<String> queryBudgExpenseApplyState(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgExpenseApplyMapper.queryBudgExpenseApplyState(entityMap);
	}

	@Override
	public Map<String, Object> queryBudgPaymentItemByCode(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgExpenseApplyMapper.queryBudgPaymentItemByCode(mapVo);
	}

	@Override
	public Map<String, Object> queryBudgDeptByCode(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgExpenseApplyMapper.queryBudgDeptByCode(mapVo);
	}

	@Override
	public Map<String, Object> queryBudgEmpByCode(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgExpenseApplyMapper.queryBudgEmpByCode(mapVo);
	}

}
