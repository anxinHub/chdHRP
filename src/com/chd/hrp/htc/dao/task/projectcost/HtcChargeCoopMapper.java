package com.chd.hrp.htc.dao.task.projectcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcChargeCoop;
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

public interface HtcChargeCoopMapper extends SqlMapper{
	
	public int addHtcChargeCoop(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcChargeCoop> queryHtcChargeCoop(Map<String,Object> entityMap) throws DataAccessException;
	 
	public List<HtcChargeCoop> queryHtcChargeCoop(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcChargeCoop queryHtcChargeCoopByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcChargeCoop(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcChargeCoop(List<Map<String,Object>> list)throws DataAccessException;
    
	public int updateHtcChargeCoop(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcChargeCoop> queryHtcChargeCoopCharge(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcChargeCoop> queryHtcChargeCoopCharge(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HtcChargeCoop> queryHtcChargeCoopTitle(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcChargeCoop> queryHtcChargeCoopTitle(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
