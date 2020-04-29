package com.chd.hrp.htcg.dao.calculation;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgChargeCostDept;
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

public interface HtcgChargeCostDeptMapper extends SqlMapper{
	
    public int addBatchHtcgChargeCostSchemeRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcgChargeCostSchemeRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcgChargeCostSchemeRela(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<HtcgChargeCostDept> queryHtcgChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgChargeCostDept> queryHtcgChargeCostDept(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public Map<String,Object> initHtcgChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgChargeCostSchemeRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public int deleteBathcHtcgChargeCostDept(List<Map<String, Object>> list)throws DataAccessException;
}
