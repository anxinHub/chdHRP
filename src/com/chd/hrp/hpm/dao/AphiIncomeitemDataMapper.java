package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemConf;
import com.chd.hrp.hpm.entity.AphiIncomeitemData;
import com.chd.hrp.hpm.entity.AphiSchemeConf;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiIncomeitemDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchIncomeitemData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemData> queryIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiIncomeitemData> queryIncomeitemData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemConf> getIncomeItemConfSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemConf getIncomeItemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemData queryIncomeitemDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemDataCodes(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemData> queryIncomeitemDept(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHisHrp(Map<String, Object> mapVo) throws DataAccessException;

	public List<AphiIncomeitemData> queryHisHrp(
			List<Map<String, Object>> deptIdList) throws DataAccessException;

}
