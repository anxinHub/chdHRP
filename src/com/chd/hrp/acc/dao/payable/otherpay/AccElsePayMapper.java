package com.chd.hrp.acc.dao.payable.otherpay;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;

public interface AccElsePayMapper extends SqlMapper{
	
	/**
	 * 模版打印 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPrintData(Map<String, Object> entityMap) throws DataAccessException;
	

}	
