package com.chd.hrp.htc.dao.income.cost.deptincome;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.cost.deptincome.HtcIncomeDeptIncome;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com 
 * @Version: 1.0
 */

public interface HtcIncomeDeptIncomeMapper extends SqlMapper{
	
	/**
	 * 
	 */
	public int addHtcIncomeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcIncomeDeptIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	

	/**
	 * 
	 */
	public List<HtcIncomeDeptIncome> queryHtcIncomeDeptIncome(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public List<HtcIncomeDeptIncome> queryHtcIncomeDeptIncome(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcIncomeDeptIncome queryHtcIncomeDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteHtcIncomeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
    /**
     * 
     */
    public int deleteBatchHtcIncomeDeptIncome(List<Map<String,Object>> list)throws DataAccessException;
    
	/**
	 * 
	 */
	public int updateHtcIncomeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
