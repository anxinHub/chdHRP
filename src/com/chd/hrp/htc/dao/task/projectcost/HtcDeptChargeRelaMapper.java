package com.chd.hrp.htc.dao.task.projectcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeRela;
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

public interface HtcDeptChargeRelaMapper extends SqlMapper{
	
    public int addHtcDeptChargeRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptChargeRela> queryHtcDeptChargeRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptChargeRela> queryHtcDeptChargeRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptChargeRela queryHtcDeptChargeRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcDeptChargeRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcDeptChargeRela(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
