package com.chd.hrp.budg.service.base.budgsubj;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface BudgSubjChargeKindService {

	public String queryBudgSubjChargeKind(Map<String,Object> mapVo) throws DataAccessException;
	
	public String deleteBatchBudgSubjChargeKind(List<Map<String,Object>> listVo) throws DataAccessException;
	
	public int querySubjCodeExist(Map<String,Object> mapVo) throws DataAccessException;
	
	public int queryIsExist(Map<String,Object> mapVo) throws DataAccessException;
	
	public String save(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
}
