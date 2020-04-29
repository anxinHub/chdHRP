/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.budgotherexpense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.budgotherexpense.BudgExpFixAmountMapper;
import com.chd.hrp.budg.entity.BudgExpenseApply;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpFixAmountService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 费用定额 
 * @Table: BUDG_EXP_FIX_AMOUNT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgExpFixAmountService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BudgExpFixAmountServiceImpl implements BudgExpFixAmountService {

	private static Logger logger = Logger.getLogger(BudgExpFixAmountServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgExpFixAmountMapper")
	private final BudgExpFixAmountMapper budgExpFixAmountMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgExpFixAmountMapper.addBatch(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
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
			budgExpFixAmountMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : String.valueOf(entityMap.get("paramVo")).split(",")) {

			Map<String, Object> map = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("budg_year", entityMap.get("budg_year"));
			map.put("payment_item_id", entityMap.get("payment_item_id"));
			map.put("payment_item_no", entityMap.get("payment_item_no"));
			map.put("fix_amount", entityMap.get("fix_amount"));
			map.put("dept_id", ids[0]);
			map.put("dept_no", ids[1]);

			listVo.add(map);

		}
		
		try {
			budgExpFixAmountMapper.delete(entityMap);
			budgExpFixAmountMapper.addBatch(listVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgExpenseApply> list = (List<BudgExpenseApply>) budgExpFixAmountMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgExpenseApply> list = (List<BudgExpenseApply>) budgExpFixAmountMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgExpFixAmountMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgExpFixAmountMapper.queryExists(entityMap);
	}

	@Override
	public String queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}

		return JSON.toJSONString(budgExpFixAmountMapper.queryBudgPaymentItem(mapVo, rowBounds));
	}
	
	@Override
	public String queryBudgDeptSelect(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}

		return JSON.toJSONString(budgExpFixAmountMapper.queryBudgDeptSelect(entityMap, rowBounds));
	}

	@Override
	public String queryBudgDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list = budgExpFixAmountMapper.queryBudgDept(entityMap);
			Map<String,Object> prarmMap = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				//是否被选中
				prarmMap.put("group_id", entityMap.get("group_id"));
				prarmMap.put("hos_id", entityMap.get("hos_id"));
				prarmMap.put("copy_code", entityMap.get("copy_code"));
				prarmMap.put("budg_year", entityMap.get("budg_year"));
				prarmMap.put("payment_item_id", entityMap.get("payment_item_id"));
				prarmMap.put("payment_item_no", entityMap.get("payment_item_no"));
				prarmMap.put("dept_id", map.get("dept_id"));
				prarmMap.put("dept_no", map.get("dept_no"));
				Map<String,Object> obj = budgExpFixAmountMapper.queryByCode(prarmMap);
				if(null != obj && obj.size()>0){
					map.put("isChecked", true);
				}
				
			}
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 校验数据 是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgExpFixAmountMapper.queryDataExist(mapVo);
	}


}
