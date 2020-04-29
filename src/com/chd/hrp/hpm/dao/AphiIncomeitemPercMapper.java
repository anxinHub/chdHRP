package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;

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

public interface AphiIncomeitemPercMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchIncomeitemPerc(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPerc> queryIncomeitemPerc(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPerc> queryIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPerc queryIncomeitemPercByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int clearIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;
}
