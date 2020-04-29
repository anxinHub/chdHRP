package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;

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

public interface AphiIncomeitemPointMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchIncomeitemPoint(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPoint> queryIncomeitemPoint(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPoint> queryIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPoint queryIncomeitemPointByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int clearIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;
}
