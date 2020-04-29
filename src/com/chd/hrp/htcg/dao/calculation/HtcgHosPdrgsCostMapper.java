package com.chd.hrp.htcg.dao.calculation;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgHosPdrgsCost;
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

public interface HtcgHosPdrgsCostMapper extends SqlMapper{
	
    public Map<String,Object> initHtcgHosPdrgsCost(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgHosPdrgsCost> queryHtcgHosPdrgsCost(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgHosPdrgsCost> queryHtcgHosPdrgsCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteBatchHtcgHosPdrgsCost(List<Map<String,Object>> list)throws DataAccessException;
	
}
