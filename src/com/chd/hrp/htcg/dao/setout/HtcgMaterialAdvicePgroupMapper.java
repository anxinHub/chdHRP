package com.chd.hrp.htcg.dao.setout;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMaterialAdvicePgroup;
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

public interface HtcgMaterialAdvicePgroupMapper extends SqlMapper{
	
    public Map<String,Object> initHtcgMaterialAdvicePgroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgMaterialAdvicePgroup> queryHtcgMaterialAdvicePgroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgMaterialAdvicePgroup> queryHtcgMaterialAdvicePgroup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public Map<String,Object> calculateHtcgMaterialAdvicePgroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> admittanceHtcgMaterialAdvicePgroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgMaterialAdvicePgroup(List<Map<String,Object>> list)throws DataAccessException;
	
}
