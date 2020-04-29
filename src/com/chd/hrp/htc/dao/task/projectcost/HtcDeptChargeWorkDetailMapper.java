package com.chd.hrp.htc.dao.task.projectcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeWorkDetail;
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

public interface HtcDeptChargeWorkDetailMapper extends SqlMapper{
	
	public int addHtcDeptChargeWorkDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptChargeWorkDetail> queryHtcDeptChargeWorkDetailWork(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeWorkDetail> queryHtcDeptChargeWorkDetailWork(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptChargeWorkDetail> queryHtcDeptChargeWorkDetailTitle(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeWorkDetail> queryHtcDeptChargeWorkDetailTitle(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptChargeWorkDetail> queryHtcDeptChargeWorkDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeWorkDetail> queryHtcDeptChargeWorkDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptChargeWorkDetail queryHtcDeptChargeWorkDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcDeptChargeWorkDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcDeptChargeWorkDetail(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcDeptChargeWorkDetail(Map<String,Object> entityMap)throws DataAccessException;
	

}
