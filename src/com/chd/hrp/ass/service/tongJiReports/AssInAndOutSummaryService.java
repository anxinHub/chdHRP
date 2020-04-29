package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssInAndOutSummaryService {  
	
	/**
	 * 资产出库统计
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssInAndOutSummary(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 资产出库统计打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssInAndOutSummaryPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 入出库 - 入库明细查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssInDetail(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 入出库 - 出库明细查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssOutDetail(Map<String, Object> mapVo) throws DataAccessException;
}
