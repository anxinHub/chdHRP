package com.chd.hrp.htc.dao.task.projectcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeIassetRela;
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

public interface HtcDeptChargeIassetRelaMapper extends SqlMapper{
	
	 public int addHtcDeptChargeIassetRela(Map<String,Object> entityMap)throws DataAccessException;
		
	public List<HtcDeptChargeIassetRela> queryHtcDeptChargeIassetRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeIassetRela> queryHtcDeptChargeIassetRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptChargeIassetRela queryHtcDeptChargeIassetRelaByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateHtcDeptChargeIassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcDeptChargeIassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcDeptChargeIassetRela(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcDeptChargeIassetRela> queryHtcDeptChargeIassetRelaCharge(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeIassetRela> queryHtcDeptChargeIassetRelaCharge(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptChargeIassetRela> queryHtcDeptChargeIassetRelaIasset(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeIassetRela> queryHtcDeptChargeIassetRelaIasset(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
