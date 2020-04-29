package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostIncomeDetail;
/**
* @Title. @Description.
* 执行收入统计<BR>
* @Author: liuxu
* @email: linuxxu@s-chd.com
* @Version: 1.0
*/
public interface CostExecDeptMapper extends SqlMapper{

	
	List<CostIncomeDetail> queryCostExecDeptMain(Map<String, Object> entityMap) throws DataAccessException;

	List<CostIncomeDetail> queryCostExecDeptMain(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	List<Map<String, Object>> queryCostExecDeptMainPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	List<CostIncomeDetail> queryCostExecDeptDetail(Map<String, Object> entityMap) throws DataAccessException;

	List<CostIncomeDetail> queryCostExecDeptDetail(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	List<Map<String, Object>> queryCostExecDeptDetailPrint(Map<String, Object> entityMap) throws DataAccessException;

}
