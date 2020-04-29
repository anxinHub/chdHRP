package com.chd.hrp.htc.dao.task.projectcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeWork;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcDeptChargeWorkMapper extends SqlMapper{

	public int addHtcDeptChargeWork(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHtcPlanDeptCharge(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcPlanDeptCharge(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPlanDeptWork(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcPlanDeptWork(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptChargeWork> queryHtcDeptChargeWork(Map<String, Object> entityMap) throws DataAccessException;
	public List<HtcDeptChargeWork> queryHtcDeptChargeWork(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcDeptChargeWork queryHtcDeptChargeWorkByCode(Map<String, Object> entityMap) throws DataAccessException;

	public int deleteHtcDeptChargeWork(Map<String, Object> entityMap) throws DataAccessException;

	public int deleteBatchHtcDeptChargeWork(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcDeptChargeWork(Map<String, Object> entityMap) throws DataAccessException;
	

}
