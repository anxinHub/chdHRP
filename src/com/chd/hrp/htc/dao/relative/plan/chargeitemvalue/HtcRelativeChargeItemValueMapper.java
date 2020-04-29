package com.chd.hrp.htc.dao.relative.plan.chargeitemvalue;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.plan.chargeitemvalue.HtcRelativeChargeItemValue;
import com.chd.hrp.htc.entity.relative.plan.deptrela.HtcRelativeDeptRela;
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

public interface HtcRelativeChargeItemValueMapper extends SqlMapper{
	
	public int addHtcRelativeChargeItemValue(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcRelativeChargeItemValue> queryHtcRelativeChargeItemValue(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativeChargeItemValue> queryHtcRelativeChargeItemValue(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public HtcRelativeChargeItemValue queryHtcRelativeChargeItemValueByCode(Map<String,Object> entityMap)throws DataAccessException;
    
	public int deleteBatchHtcRelativeChargeItemValue(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcRelativeChargeItemValue(Map<String,Object> entityMap)throws DataAccessException;
}
