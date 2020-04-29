package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;
import com.chd.hrp.hpm.entity.AphiIncomeitemPointData;
import com.chd.hrp.hpm.entity.AphiPointValue;
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

public interface AphiIncomeitemPointDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPointData> queryIncomeitemPointData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPointData> queryIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPointData queryIncomeitemPointDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPoint> getIncomeItemPointSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemPointDataCodes(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPoint getIncomeItemPointSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiPointValue getPointValueSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPointData> queryIncomeitemPointDept(Map<String, Object> entityMap) throws DataAccessException;

}
