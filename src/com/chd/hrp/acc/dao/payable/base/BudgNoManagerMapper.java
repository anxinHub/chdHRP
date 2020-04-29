

 package com.chd.hrp.acc.dao.payable.base;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.budg.entity.BudgNoManage;

 


public interface BudgNoManagerMapper extends SqlMapper{
	
	public int addBudgNoManager(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchBudgNoManager(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateBudgNoManager(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBudgNoManagerMaxNo(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchBudgNoManager(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteBudgNoManager(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchBudgNoManager(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<BudgNoManager> queryBudgNoManager(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<BudgNoManager> queryBudgNoManager(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public BudgNoManager queryBudgNoManagerByCode(Map<String,Object> entityMap)throws DataAccessException;


	public BudgNoManager queryBudgProjSetUpApplyCode(HashMap<String, Object> mapVo);


	public void updateBudgProjSetUpApplyCode(HashMap<String, Object> mapVo);


	public void addBudgProjSetUpApplyCode(HashMap<String, Object> mapVo);

	/**
	 * 是否已存在最大流水号
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExists(Map<String, Object> map) throws DataAccessException;

	/**
	 * 获取已存在最大流水号
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMaxCode(Map<String, Object> map) throws DataAccessException;
	
}
