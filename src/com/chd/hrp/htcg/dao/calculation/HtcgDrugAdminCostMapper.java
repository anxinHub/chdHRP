package com.chd.hrp.htcg.dao.calculation;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDrugAdminCost;
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

public interface HtcgDrugAdminCostMapper extends SqlMapper{
	
	
	public Map<String,Object> initHtcgDrugAdminCost(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgDrugAdminCost> queryHtcgDrugAdminCost(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrugAdminCost> queryHtcgDrugAdminCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public Map<String,Object> collectHtcgDeptDrugAdminCost(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryHtcgDeptDrugAdminCost(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> workloadHtcgDrugAdminCost(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> revenueHtcgDrugAdminCost(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcgDrugAdminCost(List<Map<String, Object>> list)throws DataAccessException;
	
}
