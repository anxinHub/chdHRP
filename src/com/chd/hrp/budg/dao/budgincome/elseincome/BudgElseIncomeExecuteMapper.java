/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.elseincome;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 其他收入预算执行 
 * @Description:
 * BUDG_ELSE_INCOME_EXECUTE
 * @Table:
 * BUDG_CHARGE_STANDARD_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgElseIncomeExecuteMapper extends SqlMapper{
	
	/**
	 * 判断收入预算科目是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 收入预算科目下拉框（添加时用）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIncomeSubj(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	/**
	 * 财务取数
	 * @param entityMap
	 * @return 
	 */
	public List<Map<String, Object>> getDatafromAcc(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> getDatafromAcc2(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询结转表中  该年度 月份  数据是否已经结转
	 * @param entityMap
	 * @return
	 */
	public String queryIncomeFlag(Map<String, Object> entityMap) throws DataAccessException;
	public Integer queryIncomeFlag2(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量插入财务取出的数据   财务取数功能专用
	 * @param entityMap
	 * @return
	 */
	public void addBatchData(List<Map<String, Object>> dataList) throws DataAccessException;
	
	/*
	 * 查询系统参数  当前预算系统是否区分账套  0 不区分   1 区分
	 */
	public int queryParaValue(Map<String, Object> entityMap)  throws DataAccessException;

}
