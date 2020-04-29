/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.execute;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医疗收入执行数据
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedInExecuteMapper extends SqlMapper{
	/**
	 * 财务取数
	 * @param entityMap
	 * @return 
	 */
	public List<Map<String, Object>> getDatafromAcc(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> getDatafromAcc2(Map<String, Object> entityMap) throws DataAccessException;
	
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
	
	/**
	 * 删除 采集年度月份 执行数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteExecuteData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * his收入数据 采集
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int saveHisExecuteData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询对应关系 是否全部维护（以防新加科室或诊疗组后  造成采集数据错误 ）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryRelationExist(Map<String, Object> entityMap) throws DataAccessException;
	
}
