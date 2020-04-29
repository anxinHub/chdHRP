package com.chd.hrp.htc.dao.task.projectcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeFassetRela;
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

public interface HtcDeptChargeFassetRelaMapper extends SqlMapper{
	
    public int addHtcDeptChargeFassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptChargeFassetRela> queryHtcDeptChargeFassetRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeFassetRela> queryHtcDeptChargeFassetRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptChargeFassetRela queryHtcDeptChargeFassetRelaByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateHtcDeptChargeFassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcDeptChargeFassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcDeptChargeFassetRela(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcDeptChargeFassetRela> queryHtcDeptChargeFassetRelaCharge(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeFassetRela> queryHtcDeptChargeFassetRelaCharge(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptChargeFassetRela> queryHtcDeptChargeFassetRelaFasset(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeFassetRela> queryHtcDeptChargeFassetRelaFasset(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
