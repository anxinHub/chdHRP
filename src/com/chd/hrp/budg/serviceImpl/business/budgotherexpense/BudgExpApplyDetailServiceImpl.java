/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.business.budgotherexpense;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.budg.dao.business.budgotherexpense.BudgExpApplyDetailMapper;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpApplyDetailService;

/**
 *  
 * @Description: 费用申报明细
 * @Table: BUDG_EXP_APPLY_DETAIL
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgExpApplyDetailService")
public class BudgExpApplyDetailServiceImpl implements BudgExpApplyDetailService {

	// 引入DAO操作
	@Resource(name = "budgExpApplyDetailMapper")
	private final BudgExpApplyDetailMapper budgExpApplyDetailMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		budgExpApplyDetailMapper.add(entityMap);
		return null;
	}
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		budgExpApplyDetailMapper.addBatch(entityMap);
		return null;
	}
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		budgExpApplyDetailMapper.update(entityMap);
		return null;
	}
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		budgExpApplyDetailMapper.updateBatch(entityMap);
		return null;
	}
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		budgExpApplyDetailMapper.delete(entityMap);
		return null;
	}
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		budgExpApplyDetailMapper.deleteBatch(entityMap);
		return null;
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
	}
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	


}
