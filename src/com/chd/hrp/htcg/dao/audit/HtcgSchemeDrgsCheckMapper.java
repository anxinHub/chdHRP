package com.chd.hrp.htcg.dao.audit;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.audit.HtcgSchemeDrgsCheck;

public interface HtcgSchemeDrgsCheckMapper extends SqlMapper{

	/**
	 * 查询病种方案审核信息
	 */
	
	public List<HtcgSchemeDrgsCheck> queryHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeDrgsCheck> queryHtcgSchemeDrgsCheck(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;


	public int addHtcgSchemeConfirm(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryHtcgSchemeConfirmByCode(Map<String, Object> entityMap) throws DataAccessException;
	
    public int addHtcgSeqTable(Map<String, Object> entityMap) throws DataAccessException;
	
	public Integer queryHtcgSeqTableMaxSeqNo(Map<String, Object> entityMap) throws DataAccessException;
	
	public int addbatchHtcgSchemeConfirmSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 是否已经存在最大单号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> flagHtcgSchemeDrgsCheckDrgsCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询病种方案
	 */
	public List<HtcgSchemeDrgsCheck> querySchemeDrgsCheckReport(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeDrgsCheck> querySchemeDrgsCheckReport(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
