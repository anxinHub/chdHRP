/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.dao.payable.otherpay;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 往来单位字典 
 * @Table:
 * BUDG_UNIT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgUnitMapper extends SqlMapper{
	
	/**
	 查询序列
	 */
	public int queryBudgUnitSeqNextVal() throws DataAccessException;
	
	/**
	 添加
	 */
	public int addBudgUnit(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	删除
	 */
	public int deleteBudgUnit(Map<String,Object> entityMap) throws DataAccessException;
	/**
	批量删除
	 */
	public int deleteBatchBudgUnit(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	修改
	 */
	public int updateBudgUnit(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	查询
	 */
	public Map<String,Object> queryBudgUnitByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	查询
	 */
	public List<Map<String,Object>> queryBudgUnit(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	查询 分页
	 */
	public List<Map<String,Object>> queryBudgUnit(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	public int queryBankNetIcbcCount(Map<String,Object> entityMap) throws DataAccessException;

	public void addBudgUnitBatch(List<Map<String, Object>> list) throws DataAccessException;
	
}
